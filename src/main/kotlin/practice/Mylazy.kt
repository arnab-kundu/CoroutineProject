package practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val time = measureTimeMillis {
        // ..code..
        val msgOne =  getMessageOne()
        val msgTwo = getMessageTwo()
        println("The entire msg is: ${msgOne + msgTwo}")
    }

    println("Completed in $time ms")

    println("Main program ends: ${Thread.currentThread().name}")
}


suspend fun getMessageOne(): String {
    delay(1000L)
    return "Hello "
}


suspend fun getMessageTwo(): String {
    delay(1000L)
    return "World!"
}