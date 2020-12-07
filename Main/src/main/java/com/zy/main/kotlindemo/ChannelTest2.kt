package com.zy.kotlindemo

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

/**
 * 协程
 */

// ChannelCoroutine 的动态代理
fun put() = GlobalScope.produce() {
    var i = 0
    while (true) {
        send(i++)
    }
}

fun get(channel: ReceiveChannel<Int>) = GlobalScope.launch() {
    repeat(10){
        println("接收到： "+ channel.receive())
    }
//    channel.take(10).consumeEach {
//        println("接收到：： $it")
//    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val producer = put()
    get(producer).join()
    producer.cancel()

    //需要时才启动job，每秒输出两个数字
    val job1 = launch(Unconfined, CoroutineStart.LAZY) {
        println("launch")
        var count = 0
        while (true) {
            count++
            //delay()表示将这个协程挂起500ms
            delay(500)
            println("count::$count")
        }
    }

    //job2会立刻启动
    val job2 = async {
        println("async")
        job1.start()
        "job2"
    }

    launch(Dispatchers.Default) {
        delay(3000)
        job1.cancel()
        //await()的规则是：如果此刻job2已经执行完则立刻返回结果，否则等待job2执行
        println(job2.await())
    }
}



