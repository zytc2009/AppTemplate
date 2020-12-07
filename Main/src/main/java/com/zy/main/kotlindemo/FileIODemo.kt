package com.kymjs.kotlinprimer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.io.core.ByteReadPacket
import kotlinx.io.core.buildPacket
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

/**
 * 可以对比FileUtils
 *
 */
//BIO
private fun copyFile(source: File, dest: File) {
    FileInputStream(source).use { input ->
        FileOutputStream(dest).use { output ->
            val buf = ByteArray(1024)
            while (true) {
                val bytesRead = input.read(buf)
                if (bytesRead <= 0) {
                    break
                }
                output.write(buf, 0, bytesRead)
            }
        }
    }
}

//NIO
private fun copyFile2(source: File, dest: File) {
    FileInputStream(source).channel.use { inputChannel ->
        FileOutputStream(dest).channel.use { outputChannel ->
            val byteBuffer = ByteBuffer.allocate(1024)
            while (true) {
                byteBuffer.clear()
                if (inputChannel.read(byteBuffer) < 0) {
                    break
                }
                byteBuffer.flip()
                outputChannel.write(byteBuffer)
            }
        }
    }
}


fun main(args: Array<String>) = runBlocking {
    val tee = Channel<ByteReadPacket>()

    val file = File("./build.gradle")
    val packet = buildPacket(1) {
        writeStringUtf8(file.readText())
    }

    GlobalScope.launch {
        tee.send(packet.copy())
    }

    GlobalScope.launch {
        println(tee.receive().readText())
    }


    delay(1000)
    Unit
}
