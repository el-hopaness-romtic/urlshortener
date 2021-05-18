package ru.tinkoff.edu.urlshortener

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UrlGenerator(
    @Value("\${urlgenerator.alphabet}")
    private val alphabet: String
) {
    private val base = alphabet.length

    var maxCount: Long = 0
    var counter: Long = 0

    var generatedUrlLength: Int = 1
        set(value) {
            field = value
            maxCount = base.pow(value)
            counter = 0
        }

    /**
     * Encode a positive number into Base 62 and return the string.
     *
     * @param number The number to encode
     */
    fun encode(number: Long): String {
        val arr = StringBuilder(generatedUrlLength)
        var numerator = number
        var remainder: Int

        do {
            remainder = (numerator % base).toInt()
            numerator = numerator / base
            arr.append(alphabet[remainder])
        } while (numerator != 0L)

        return arr.reverse()
            .toString()
            .padStart(generatedUrlLength, '0')
    }

    fun next(): String {
        if (counter == maxCount) generatedUrlLength += 1

        return encode(counter++)
    }
}

/**
 * Returns the value of this raised to the power of the argument
 *
 * @param power The exponent (power > 0)
 */
fun Int.pow(power: Int): Long {
    var result: Long = this.toLong()

    repeat(power - 1) {
        result *= this
    }

    return result
}
