package com.zy.kotlindemo.coroutine

import android.os.AsyncTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

/**
 * Created by ZhangTao on 18/7/11.
 */
object AndroidCommonPool : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(block)
    }
}