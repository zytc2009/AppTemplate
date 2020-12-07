package com.zy.kotlindemo.eventbus

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel


//事件缓存
var map = mutableMapOf<String, Channel<Any>>()

inline fun <reified R> R.post() {
    if (!map.containsKey(R::class.java.name)) {
        map.put(R::class.java.name, Channel())
    }
    println("post! ${Thread.currentThread().name}")
    GlobalScope.launch() {
        println("post2! ${Thread.currentThread().name}")
        map[R::class.java.name]?.send(this@post as Any)
    }
}

inline fun <T, reified R> T.onEvent(noinline action: suspend (R) -> Unit) {
    if (!map.containsKey(R::class.java.name)) {
        map.put(R::class.java.name, Channel())
    }
    GlobalScope.launch() {
        println("onEvent() ")
        val receive = map[R::class.java.name]?.receive()
        launch() {
            println("onEvent() invoke")
            action.invoke(receive as R)
        }
    }
}


//发送者.post("event")
//接受者 onEvent(event:String)

fun main(args: Array<String>) = runBlocking<Unit> {

    val job = launch() {
        onEvent { str: String ->
            println(str)
        }
    }

    val job2 = async() {
        "按钮1".post()
        delay(500L)
        println("async..." + Thread.currentThread().name)
        return@async "hello"
    }

    println("job2的输出：" + job2.await())
    delay(1300L)
}
