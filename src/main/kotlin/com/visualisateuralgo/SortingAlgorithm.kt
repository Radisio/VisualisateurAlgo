package com.visualisateuralgo

import com.AlgoRequest
import com.algo.vector.QuickSortAlgorithm
import com.algo.vector.SortingAlgorithmFactory
import com.fasterxml.jackson.module.kotlin.jsonMapper

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalTime
@CrossOrigin
@RestController
class SortingAlgorithm {

    var logger: Logger = LoggerFactory.getLogger(SortingAlgorithm::class.java)


    @PostMapping("/test")
    fun test(@RequestBody test: Int) : String
    {
        logger.trace("TEST = "+test)
        return "Yes"
    }
    fun testEmitter(emitter:ResponseBodyEmitter)
    {
        println("TEST EMITTER")
        emitter.send("Hello")
        Thread.sleep(3000)
        emitter.send("It's me again")
        emitter.complete()
        println("FIN TEST EMITTER")


    }

    @RequestMapping(value= ["/sort"], method= [RequestMethod.POST], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun sort(@RequestBody algoNameVector: AlgoRequest) : Flux<String>
    {
        if(algoNameVector.algorithmName==null || algoNameVector.array == null)
        {
            logger.trace("Erreur")
           // return returnNull();
            return Flux.range(-1,1).map { i-> jsonMapper().writeValueAsString("empty") }

        }
        logger.trace("Pas d'erreur")
        val sort = SortingAlgorithmFactory.getSortAlgorithm(algoNameVector.algorithmName,algoNameVector.array, algoNameVector.delayMs);
        //sort.order();
        if(sort == null){
            return Flux.range(-1,1).map { i-> jsonMapper().writeValueAsString("empty")};
        }

        return sort.order();
    }
}