package com.algo.vector

import reactor.core.publisher.Flux

interface vectorAlgorithm {
    val vector: Array<Int>
    val delayMs: Long
    fun order() : Flux<String>

    //fun getInfo(): AlgorithmInfo
}