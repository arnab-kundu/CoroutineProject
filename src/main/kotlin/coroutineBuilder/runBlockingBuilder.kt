package coroutineBuilder

import kotlinx.coroutines.*

/**
 * @see asyncBuilder.kt Before
 * 3. runBlocking { }
 */
fun main() = runBlocking { // Creates a blocking coroutine that executes in current thread (coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main)

    println("Main program starts: ${Thread.currentThread().name}")

    // ..code..

    println("Main program ends: ${Thread.currentThread().name}")
}