package com.waxym.utils


fun buildFizzBuzzList(start: Int = 1, end: Int = 100, fizzMultiple: Int = 0, fizzLabel: String = "", buzzMultiple: Int = 0, buzzLabel: String = ""): List<String> {
    val fizzBuzzMultiple = fizzMultiple * buzzMultiple
    val fizzBuzzLabel = "$fizzLabel$buzzLabel"
    return (start..end).map {
        when {
            fizzBuzzMultiple > 0 && it % fizzBuzzMultiple == 0 -> fizzBuzzLabel
            fizzMultiple > 0 && it % fizzMultiple == 0 -> fizzLabel
            buzzMultiple > 0 && it % buzzMultiple == 0 -> buzzLabel
            else -> "$it"
        }
    }
}