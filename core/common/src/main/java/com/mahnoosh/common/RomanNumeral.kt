package com.mahnoosh.common

fun Int.toRomanNumeral(): String {
    val values = listOf(
        1000 to "M", 900 to "CM", 500 to "D", 400 to "CD", 100 to "C",
        90 to "XC", 50 to "L", 40 to "XL", 10 to "X", 9 to "IX",
        5 to "V", 4 to "IV", 1 to "I"
    )
    var number = this
    val roman = StringBuilder()
    for ((value, symbol) in values) {
        while (number >= value) {
            roman.append(symbol)
            number -= value
        }
    }
    return roman.toString()
}

fun List<String>.toRomanNumeralList(): List<String> {
    return this.map { seasonString ->
        val numberString = seasonString.filter { it.isDigit() }
        val number = numberString.toIntOrNull()
        number?.toRomanNumeral() ?: seasonString
    }
}