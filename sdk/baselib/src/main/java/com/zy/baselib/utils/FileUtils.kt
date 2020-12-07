//声明类名，否则默认是Kt结尾
@file:JvmName("FileUtilKts")

package com.zy.baselib.utils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

fun copyFile(srcFile: File, detfile:File){
    srcFile?.copyTo(target = detfile, true)
}

fun copyDirectory(srcFile: File, target:File){
    srcFile.copyRecursively(target, true)
}

fun deleteFile(file: File){
    if(file.isDirectory) {
        file.deleteRecursively()
    }else{
        file.delete()
    }
}

//BIO
private fun copyFileBIO(source: File, dest: File) {
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
private fun copyFileNIO(source: File, dest: File) {
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

fun file2bytes(file:File) : ByteArray{
    return file?.readBytes();
}

fun bytes2File(bytes:ByteArray, file : File){
    file?.writeBytes(bytes);
}