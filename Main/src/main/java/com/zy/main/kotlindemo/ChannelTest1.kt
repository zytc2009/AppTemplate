package com.zy.kotlindemo

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val c = Channel<Int>()
    launch {
        get(c)
    }
    launch {
        put(c)
    }
    Unit
}

suspend fun get(channel: Channel<Int>) {
    while (true) {
        println(channel.receive())
    }
}

suspend fun put(channel: Channel<Int>) {
    var i = 0
    while (true) {
        channel.send(i++)
    }
}
