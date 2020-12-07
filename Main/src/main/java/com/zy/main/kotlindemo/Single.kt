package com.zy.kotlindemo
//单例模式
class Single private constructor(){
    companion object{
        fun instance():Single{
            return Holder.instance;
        }
    }
    private object Holder{
        val  instance = Single();
    }

    fun isEmpty(str:String):Boolean{
        return str == null || str.length == 0
    }
}