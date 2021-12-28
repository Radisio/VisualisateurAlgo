package com.algo.vector

import com.algo.AlgorithmInfo
import com.fasterxml.jackson.module.kotlin.jsonMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asPublisher
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

class QuickSortAlgorithm(override val vector: Array<Int>,override val delayMs: Long ) : vectorAlgorithm {

    suspend fun swap (i: Int, j:Int, channel: Channel<String>, comparison: Int)
    {
        var temp = vector[i]
        vector[i] = vector[j]
        vector[j]=temp
        delay(delayMs)
        channel.send(jsonMapper().writeValueAsString(vector)+"/"+jsonMapper().writeValueAsString(comparison)+"/"+"1")

    }


    suspend fun partition (low : Int, high: Int, channel: Channel<String>): Int
    {
        val pivot = vector[high];
        var i = (low -1)
        var j = low;
        println("Partition : pivot = $pivot , i:$i , j:$j")
        while(j<= high-1)
        {
            println("J = $j")
            println("Vector j = "+ vector[j])
            println("Pivot = $pivot")
            if (vector[j] < pivot)
            {
                i++;

                var t = GlobalScope.launch{swap(i, j, channel,3)}
                t.join()
                println("ON EST SORTI DU RUN BLOCKING 1")
            }
            j++
        }
        println("ON EST SORTI DE LA BOUCLE PARTITION")
        var l= GlobalScope.launch { swap(i+1, high, channel,2); }
        l.join();

        println("ON EST SORTI DU RUN BLOCKING 2")

        return i+1
    }
    suspend fun quickSort(low : Int, high: Int, channel: Channel<String>)
    {
        if(low < high) {
            println("Low = $low / High = $high")
            var pi= partition(low, high, channel)

            quickSort(low, pi-1,channel)
            quickSort(pi+1,high,channel)
        }
    }
    override fun order(): Flux<String> {
        val channel = Channel<String>()
        GlobalScope.launch {
            quickSort(0, vector.size - 1, channel)
            channel.close()
            }
            return channel.receiveAsFlow().asFlux()
        }



    /*override fun getInfo(): AlgorithmInfo {
        TODO("Not yet implemented")
    }*/
}