package com.utku.sensorball.ui.motion_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.data.data.entities.SensorRecord
import com.utku.sensorball.databinding.ItemMotionRecordBinding

class MotionListAdapter(
    private val sensorRecordList: List<SensorRecord>,
    private val onPlayRecord: (SensorRecord) -> Unit
) : RecyclerView.Adapter<MotionListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemMotionRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sensorRecordList[position])
    }

    override fun getItemCount(): Int = sensorRecordList.size

    inner class ViewHolder(private val viewBinding: ItemMotionRecordBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(sensorRecord: SensorRecord) {
            viewBinding.apply {
                root.setOnClickListener { onPlayRecord(sensorRecord) }
                recordIdTextView.text = sensorRecord.id.toString()
                recordTimeTextView.text = sensorRecord.time
            }

        }
    }
}