package com

data class AlgoRequest (
        val algorithmName: String?,
        val array : Array<Int>?,
        val delayMs: Long=100
                               ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlgoRequest

        if (algorithmName != other.algorithmName) return false
        if (array != null) {
            if (other.array == null) return false
            if (!array.contentEquals(other.array)) return false
        } else if (other.array != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = algorithmName?.hashCode() ?: 0
        result = 31 * result + (array?.contentHashCode() ?: 0)
        return result
    }
}