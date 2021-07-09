package coroutineBuilder

import kotlinx.coroutines.*

/**
 * 1. launch { }
 * @see asyncBuilder.kt Next
 */
fun main() {
    /**
     * Run globalScope() & localScope() method one by one
     * to check actually on which Thread coroutine code is gets executed.
     */
    globalScope()   // Follow globalScope() method() first
    localScope()    // Follow localScope() method next for better understanding
}

fun globalScope() = runBlocking { // Creates a blocking coroutine that executes in current thread (coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main)
    println("Main program starts: ${Thread.currentThread().name}")

    // In GlobalScope.launch { } block code will execute on a different Thread.
    GlobalScope.launch {
        println("Fake work starts: ${Thread.currentThread().name}")
        delay(1000)
        println("Fake work finished: ${Thread.currentThread().name}")
    }
    delay(2000) // Manually stop the coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main Thread to complete the execution of coroutine block. Not practical.
    println("Main program ends: ${Thread.currentThread().name}")
}


fun localScope() = runBlocking { // Creates a blocking coroutine that executes in current thread (coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main)
    println("Main program starts: ${Thread.currentThread().name}")

    /**
     * only launch { } is a child coroutine of runBlocking { } block.
     * So it will run on the same Thread of its parent. (i.e. coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main)
     */
    val job: Job = launch {
        println("Fake work starts: ${Thread.currentThread().name}")
        delay(1000)
        println("Fake work finished: ${Thread.currentThread().name}")
    }
    /**
     * without join() coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main program execution will not stop for coroutine execution.
     * Practical way to wait for coroutines to finish its job.
     */
    job.join()
    //delay(2000) // Manually stop the coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main Thread to complete the execution of coroutine block. Not practical.

    println("Main program ends: ${Thread.currentThread().name}")
}
