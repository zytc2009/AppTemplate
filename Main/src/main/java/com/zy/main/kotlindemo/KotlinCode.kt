//声明类名，否则默认是Kt结尾
@file:JvmName("KotlinCode")
//处理类重名，重名类会合并
@file:JvmMultifileClass
package com.zy.kotlindemo

object StaticTest {
    //声明静态方法
    @JvmStatic
    open fun sayMessage(msg: String) {
        println(msg)
    }
}

fun doFunction(block: (i: Int) -> Unit) {
    block.invoke(1)
}

fun doFunction(block: () -> Unit) {
    block.invoke()
}

