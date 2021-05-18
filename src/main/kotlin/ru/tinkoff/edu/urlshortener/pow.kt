package ru.tinkoff.edu.urlshortener

fun Int.pow(power: Int): Long {
    var result: Long = this.toLong()

    repeat(power - 1) {
        result *= this
    }

    return result
}
