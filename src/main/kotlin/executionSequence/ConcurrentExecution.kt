package executionSequence

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 2. Concurrent or parallel Execution
 */

fun main() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val time = measureTimeMillis {
        // ..code..
        val msgOne: Deferred<String> = async(Dispatchers.Default) {
            // ..more code..
            getMessageOne()
        }
        val msgTwo: Deferred<String> = async {
            // ..more code..
            getMessageTwo()
        }
        println("The entire msg is: ${msgOne.await() + msgTwo.await()}")
    }

    println("Completed in $time ms")

    println("Main program ends: ${Thread.currentThread().name}")
}


private suspend fun getMessageOne(): String {
    delay(1000L)
    return "Hello "
}


private suspend fun getMessageTwo(): String {
    delay(1000L)
    return "World!"
}