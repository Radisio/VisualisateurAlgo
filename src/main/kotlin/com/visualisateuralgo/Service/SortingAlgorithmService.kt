package com.visualisateuralgo.Service

import com.algo.vector.SortingAlgorithmFactory
import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
@Service
class SortingAlgorithmService {

    fun sort(algorithmName:String?, array: Array<Int>?, delayMs:Long): Flux<String> {
        if(algorithmName==null || array == null)
        {
            return Flux.range(-1,1).map { i-> jsonMapper().writeValueAsString("empty") }

        }
        val sort = SortingAlgorithmFactory.getSortAlgorithm(algorithmName,array, delayMs);
        //sort.order();
        if(sort == null){
            return Flux.range(-1,1).map { i-> jsonMapper().writeValueAsString("empty")};
        }

        return sort.order();
    }
}