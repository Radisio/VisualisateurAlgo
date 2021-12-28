package com.algo.vector

import com.fasterxml.jackson.module.kotlin.jsonMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactor.asFlux
import reactor.core.publisher.Flux

class MergeSortAlgorithm(override val vector: Array<Int>,override val delayMs: Long ) : vectorAlgorithm {

    suspend fun swap (i: Int, value:Int, channel: Channel<String>, comparison: Int)
    {
        vector[i] = value
        delay(delayMs)
        channel.send(jsonMapper().writeValueAsString(vector)+"/"+ jsonMapper().writeValueAsString(comparison)+"/"+1)

    }


    suspend fun merge (low : Int,median:Int, high: Int, channel: Channel<String>)
    {
        println("Median = $median + low = $low")
        var n1=median -low+1
        var n2= high-median
        println("N1=$n1 / N2=$n2")

        var L  = IntArray(n1)
        var R = IntArray(n2)
        println("Size L = " + L.size)
        println("Size R = " + R.size)
        var i=0
        var j=0;
        var comp=0
        while(i<n1) {
            println("i : $i / n1 : $n1")
            println("vector low + i :" + vector[low+i])
            L[i] = vector[low + i]
            i++
            comp++
        }
        while(j<n2)
        {
            R[j]=vector[median+1+j]
            j++
            comp++
        }

        i =0
        j= 0
        var k = low
        while(i<n1 && j <n2)
        {
            comp+=2
            if(L[i] <=R[j])
            {

                var t = GlobalScope.launch{swap(k,L[i],channel,comp)}
                t.join()

                i++
            }
            else{
                var t = GlobalScope.launch{swap(k,R[j],channel,comp)}
                t.join()

                j++
            }
            k++
            comp=0
        }

        while(i<n1)
        {
            var t = GlobalScope.launch{swap(k, L[i],channel, 1)}
            t.join()

            i++
            k++
        }

        while(j< n2)
        {
            var t = GlobalScope.launch{swap(k, R[j],channel,1)}
            t.join()

            j++
            k++
        }


    }
    suspend fun sort(low : Int, high: Int, channel: Channel<String>)
    {
        if(low < high) {
            var m = low+(high-low)/2
            println("SORT LOW = $low / HGIH = $high / m = $m")

            sort(low, m,channel)
            sort(m+1,high,channel)

            merge(low,m,high, channel)
        }
    }
    override fun order(): Flux<String> {
        val channel = Channel<String>()
        GlobalScope.launch {
            sort(0, vector.size - 1, channel)
            channel.close()
        }

        return channel.receiveAsFlow().asFlux()
    }
}