package com.zy.kotlindemo

sealed class CompareResult {
    object LESS : CompareResult() {
        override fun toString(): String {
            return "小于"
        }
    }

    object MORE : CompareResult() {
        override fun toString(): String {
            return "大于"
        }
    }

    object EQUAL : CompareResult() {
        override fun toString(): String {
            return "等于"
        }
    }
}

//中缀
infix fun Int.vs(num: Int): CompareResult =
        if (this - num < 0) {
            CompareResult.LESS
        } else if (this - num > 0) {
            CompareResult.MORE
        } else {
            CompareResult.EQUAL
        }

infix fun Double.vs(num: Int): CompareResult =
        if (this - num < 0) {
            CompareResult.MORE
        } else if (this - num > 0) {
            CompareResult.LESS
        } else {
            CompareResult.EQUAL
        }

infix fun Long.vs(num: Int): CompareResult =
        if (this - num < 0) {
            CompareResult.MORE
        } else if (this - num > 0) {
            CompareResult.LESS
        } else {
            CompareResult.EQUAL
        }

fun main(args: Array<String>) {
    println(5.vs(6))
}
