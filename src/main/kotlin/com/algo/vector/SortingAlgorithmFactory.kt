package com.algo.vector

class SortingAlgorithmFactory {
companion object Factory {
    fun getSortAlgorithm(contextName: String, vector: Array<Int>, delayMs: Long): vectorAlgorithm? {
        return when (contextName) {
            "QuickSort" -> QuickSortAlgorithm(vector, delayMs)
            "MergeSort" -> MergeSortAlgorithm(vector, delayMs)
            "BubbleSort"-> BubbleSortAlgorithm(vector,delayMs)
            else -> return null
        }
    }
}
}