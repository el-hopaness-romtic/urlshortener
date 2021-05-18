package ru.tinkoff.edu.urlshortener

import org.springframework.stereotype.Component

@Component
class UrlGenerator {
    private val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
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
        val arr = mutableListOf<Char>()
        var numerator = number
        var remainder: Int

        do {
            remainder = (numerator % base).toInt()
            numerator = numerator / base
            arr.add(alphabet[remainder])
        } while (numerator != 0L)

        return arr.apply { reverse() }
            .joinToString(separator = "")
            .padStart(generatedUrlLength, '0')
    }

    fun next(): String {
        if (counter == maxCount) generatedUrlLength += 1

        return encode(counter++)
    }

    /**
     * Decode a Base X encoded string into the number
     * Arguments:
     * `string`: The encoded string
     * `alphabet`: The alphabet to use for decoding
     *
     fun decode(string: String) :Int{
     val strlen = string.length
     var num = 0

     var idx = 0
     var power: Int
     for (char in string) {
     power = (strlen - (idx + 1))
     num += alphabet.indexOf(char) * base.pow(power)
     idx += 1
     }

     return num
     }

     private fun Int.pow(power: Int): Int {
     var res = 1

     repeat(power) {
     res *= this
     }

     return res
     }*/
}
