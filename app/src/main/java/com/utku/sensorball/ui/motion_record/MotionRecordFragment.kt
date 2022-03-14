package com.utku.sensorball.ui.motion_record

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.base.ui.BaseFragment
import com.data.data.entities.SensorRecord
import com.utku.sensorball.databinding.FragmentMotionRecordBinding
import com.utku.sensorball.ui.root.RootViewModel
import com.utku.sensorball.util.currentTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.LocalDateTime

class MotionRecordFragment : BaseFragment<FragmentMotionRecordBinding>(
    { FragmentMotionRecordBinding.inflate(it) }) {

    private var countDownTimer: CountDownTimer? = null

    private val sensorManager: SensorManager? by lazy {
        activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val sensor: Sensor? by lazy {
        sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private val movementRecord = mutableListOf<Pair<Float, Float>>()

    private val sensorEventListener: SensorEventListener by lazy {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    viewBinding.sensorBall.onSensorEvent(event.values[0], event.values[1])
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        }
    }

    private val motionRecordArgs: MotionRecordFragmentArgs by navArgs()

    override val viewModel: RootViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCountDownVisibility()
        if (motionRecordArgs.isRecord) {
            lifecycleScope.launch {
                playRecord()
            }
        } else setCountDownTimer()
    }

    override fun onResume() {
        super.onResume()
        if (!motionRecordArgs.isRecord)
            sensorManager?.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_GAME
            )
    }

    override fun onPause() {
        super.onPause()
        if (!motionRecordArgs.isRecord)
            sensorManager?.unregisterListener(sensorEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    private fun setCountDownVisibility() {
        if (!motionRecordArgs.isRecord) {
            viewBinding.countTimerTextView.visibility = View.VISIBLE
        } else {
            viewBinding.countTimerTextView.visibility = View.GONE
        }
    }

    private suspend fun playRecord() {
        val selectedRecord = viewModel.selectedRecord.value?.movementRecord
        selectedRecord?.forEachIndexed { index, pair ->
            viewBinding.sensorBall.pos = pair
            if (selectedRecord.size - 1 != index) delay(100)
            else findNavController().popBackStack()
        }
    }

    private fun setCountDownTimer() {
        countDownTimer = object : CountDownTimer(10000, 100) {
            override fun onTick(time: Long) {
                viewBinding.countTimerTextView.text = (time / 1000).toString()
                saveMomentRecord()
            }

            override fun onFinish() {
                saveWholeMoment()
            }
        }.start()
    }

    private fun saveMomentRecord() {
        movementRecord.add(viewBinding.sensorBall.pos.first to viewBinding.sensorBall.pos.second)
    }

    private fun saveWholeMoment() {
        viewModel.saveRecord(
            SensorRecord(
                time = LocalDateTime.now().currentTime(),
                movementRecord = movementRecord
            )
        ).observe(this) {
            findNavController().popBackStack()
        }
    }

}