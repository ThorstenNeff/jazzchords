package com.neffapps.jazzchords.timing

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class FlowTimer {
    var active: Boolean = false
    var isPlaying: Boolean = false
    private var _period: Long = 0

    private fun tickerFlow(period: Long, initialDelay: Long = 0) = flow {
        active = false
        _period = period
        while (true) {
            if (active) {
                if (!isPlaying) {
                    isPlaying = true
                    for (i in 0..15) {
                        delay(_period)
                        emit(0)
                    }
                    for (i in 0..14) {
                        delay(_period)
                        emit(0)
                    }
                    delay(_period)
                    emit(1)
                }
                emit(2)
                delay(_period)
            }
        }
    }.onStart { delay(initialDelay) }

    fun setPeriod(period: Long) {
        Log.d("timer","Period: $period")
        _period = period
    }

    fun build(period: Long, initialDelay: Long = 0)  =
        tickerFlow(period, initialDelay)
            .flowOn(Dispatchers.Default)

    fun pause() {
        active = false
    }

    fun stop() {
        isPlaying = false
        active = false
    }

    fun play() {
        active = true
    }
}