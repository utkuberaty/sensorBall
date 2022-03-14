package com.utku.sensorball.ui.motion_record

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.base.util.TAG

class SensorBall(context: Context?, attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint().apply {
        color = Color.BLUE
    }
    private var screen = 0 to 0
    private val ballRadius = 50f

    var pos = 0f to 0f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        screen = width to height
        pos = width / 2f to height / 2f
    }

    fun onSensorEvent(eventX: Float, eventY: Float) {
        Log.i("sensor", "event X${eventX} / Y${eventY}")
        pos = (pos.first - (eventX * 3)) to (pos.second + (eventY * 3))

        if (pos.first <= 0 + ballRadius) {
            pos = 0f + ballRadius to pos.second
        }
        if (pos.first >= screen.first - ballRadius) {
            pos = screen.first - ballRadius to pos.second
        }
        if (pos.second <= 0 + ballRadius) {
            pos = pos.first to 0 + ballRadius
        }
        if (pos.second >= screen.second - ballRadius) {
            pos = pos.first to screen.second - ballRadius
        }
    }

    override fun onDraw(canvas: Canvas) {
        Log.i(TAG, "movement => X${pivotX} / Y${pivotY}")
        canvas.drawCircle(pos.first, pos.second, ballRadius, paint)
        invalidate()
    }
}