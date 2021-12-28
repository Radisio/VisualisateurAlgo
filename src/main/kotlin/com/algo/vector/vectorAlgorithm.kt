package com.algo.vector

import com.algo.AlgorithmInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

interface vectorAlgorithm {
    val vector: Array<Int>
    val delayMs: Long
    fun order() : Flux<String>

    //fun getInfo(): AlgorithmInfo
}