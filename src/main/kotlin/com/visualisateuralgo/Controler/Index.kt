package com.visualisateuralgo.Controler

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Index {

    @CrossOrigin(origins = ["http://192.168.0.13:8081","http://localhost:8081"])
    @GetMapping
    fun index() : List<String> = listOf(
            "QuickSort",
            "MergeSort",
            "BubbleSort"
    )

}