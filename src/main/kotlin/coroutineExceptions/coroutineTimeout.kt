package coroutineExceptions

import kotlinx.coroutines.*
import java.lang.Exception

/**
 * @see coroutineExceptionHandle.kt Before
 * 3. Set timeout for long running coroutine operation
 */
fun main() {
    // Run all 3 method separately one by one for better understanding of logcat message
    withTimeOutCoroutineBuilder()
    tryToHandleTimeoutException()
    handleTimeoutException()
}


/**
 * 1. Like launch { } and async { } block, **withTimeout()** is also a CoroutineBuilder function
 * 2. And it need time in milliseconds as a parameter
 * 3. If the coroutine code inside withTimeout { } block, does not execute in between given time,
 *  - then it throws a Exception
 */
fun withTimeOutCoroutineBuilder() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    withTimeout(2000) {
        for (i in 0..500) {
            print("$i.")
            delay(500) // comment and uncomment this delay to test
        }
    }

    println("Main program ends: ${Thread.currentThread().name}")
}


/**
 * The exception throws by withTimeout is TimeoutCancellationException
 */
fun tryToHandleTimeoutException() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    try {
        withTimeout(2000) {

            for (i in 0..500) {
                print("$i.")
                delay(500)
            }
        }
    } catch (ex: TimeoutCancellationException) {
        println("\n${ex.message}")
    } finally {
        // ..code..
    }

    println("Main program ends: ${Thread.currentThread().name}")
}


/**
 * 1. Exception **not thrown** by withTimeoutOrNull()
 *
 * 2. If coroutine block code executes in between given time
 *      - It returns a the value at the last line of Coroutine block { } i.e.  "I am done" here
 *
 *  3. Or if task does not finish in time
 *      - It returns null
 */
fun handleTimeoutException() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val result: String? = withTimeoutOrNull(2000) {
        for (i in 0..500) {
            print("$i.")
            //delay(500)
        }
        "I am done"
    }
    println("\n$result")

    println("Main program ends: ${Thread.currentThread().name}")
}