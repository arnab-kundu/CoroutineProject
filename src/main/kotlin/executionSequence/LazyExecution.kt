package executionSequence

import kotlinx.coroutines.*

/**
 * 3. Lazy Execution
 */

fun main() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")


    // ..code..
    val msgOne: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageOne() }
    val msgTwo: Deferred<String> = async(start = CoroutineStart.LAZY) { getMessageTwo() }

    // If commend out this print() statement CoroutineStart.LAZY methods will not get called.
    println("The entire msg is: ${msgOne.await() + msgTwo.await()}")


    println("Main program ends: ${Thread.currentThread().name}")
}


private suspend fun getMessageOne(): String {
    delay(1000L)
    println("After working in messageOne()")
    return "Hello "
}


private suspend fun getMessageTwo(): String {
    delay(1000L)
    println("After working in messageTwo()")
    return "World!"
}