package com.visualisateuralgo.Controler

import com.AlgoRequest
import com.algo.vector.SortingAlgorithmFactory
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.visualisateuralgo.Service.SortingAlgorithmService

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import reactor.core.publisher.Flux

@CrossOrigin
@RestController
class SortingAlgorithmController {

    @Autowired
    private lateinit var sortingAlgorithmService:SortingAlgorithmService;

    @RequestMapping(value= ["/sort"], method= [RequestMethod.POST], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun sort(@RequestBody algoNameVector: AlgoRequest) : Flux<String>
    {
        return sortingAlgorithmService.sort(algoNameVector.algorithmName,algoNameVector.array, algoNameVector.delayMs)
    }
}