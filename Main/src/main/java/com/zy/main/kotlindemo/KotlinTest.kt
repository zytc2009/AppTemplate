package com.zy.kotlindemo

//第二个参数是参数为空，返回值Unit的函数
fun only(isDebug:Boolean, block :() ->Unit){
    if(isDebug) block()
}


interface Animal {
    fun bark();
}

class Dog : Animal{
    override fun bark() {
        print("wang");
    }
}

//将Zoo中所有的实现都委托给animal对象
class Zoo(animal: Animal):Animal by animal

interface PlayerView{
    fun showView();
    fun getPlayButton()
}

/**
 *sealed 可以继承，扩展
 */
sealed class PlayerViewType {
    object GREEN : PlayerViewType()
    object BLUE : PlayerViewType()
    object VIP : PlayerViewType(), PlayerView {
        override fun showView() {
            println("VIP视图")
        }
        override fun getPlayButton() {
        }
    }
}

interface ITimePrinter {
    val time: Long
        get() = System.currentTimeMillis()

    fun printTime();
}

fun main() {
    Zoo(Dog()).bark()
}


