package com.kymjs.kotlinprimer

import kotlin.coroutines.*


interface SequenceBuilder<in T> {
    suspend fun yield(value: T)
}

fun <T> buildSequence(block: suspend SequenceBuilder<T>.() -> Unit): Sequence<T> = Sequence {
    MyCoroutine<T>().apply {
        nextStep = block.createCoroutine(receiver = this, completion = this)
    }
}

private class MyCoroutine<T> : AbstractIterator<T>(), SequenceBuilder<T>, Continuation<Unit> {

    lateinit var nextStep: Continuation<Unit>
    override val context: CoroutineContext get() = EmptyCoroutineContext

    override fun computeNext() { nextStep.resume(Unit) }

    override suspend fun yield(value: T) {
        setNext(value)
        return suspendCoroutine { cont -> nextStep = cont }
    }

    override fun resumeWith(result: Result<Unit>) {
        done()
    }
}
