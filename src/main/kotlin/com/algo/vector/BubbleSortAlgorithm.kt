package com.algo.vector

import com.fasterxml.jackson.module.kotlin.jsonMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.asFlux
import reactor.core.publisher.Flux

class BubbleSortAlgorithm(override val vector: Array<Int>,override val delayMs: Long ) : vectorAlgorithm {

    suspend fun sort(channel:Channel<String>) {
        val n = vector.size

        for (i in 0 until n - 1) for (j in 0 until n - i - 1)
            if (vector[j] > vector[j + 1]) {
                var comparison = 1;
            val temp = vector[j]
            vector[j] = vector[j + 1]
            vector[j + 1] = temp
                var swap = 2
            delay(delayMs)
            channel.send(jsonMapper().writeValueAsString(vector)+"/"+ jsonMapper().writeValueAsString(comparison)+"/"+ jsonMapper().writeValueAsString(swap))
        }
    }
    override fun order(): Flux<String> {
        val channel = Channel<String>()
        GlobalScope.launch {
            sort(channel)
            channel.close()
        }

        return channel.receiveAsFlow().asFlux()
    }

}