package coroutineExceptions

import kotlinx.coroutines.*

/**
 * @see coroutineCancelation.kt Before
 * 2. How to handle coroutine Exception
 * @see coroutineTimeout.kt Next
 */

fun main() {
    // Run all 3 method separately one by one for better understanding of logcat message
    handleCoroutineCancelException()
    finallyBlock()
    ownCaCancellationExceptionMessage()
}

/**
 *  Whenever a coroutine get canceled, it throws an exception.
 *  Now, how to catch that exception using try{ } - catch { } block
 */
fun handleCoroutineCancelException() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val job: Job = launch {
        try {
            for (i in 0..500) {
                print("${i}.")
                delay(5) //or use yield() or any other suspending function
            }
        } catch (ex: CancellationException) {
            println("\nException caught safely")
        } finally {
            println("Close resource in finally")
        }
    }

    delay(10)
    job.cancelAndJoin()  // In place of calling cancel() and join() two method only cancelAndJoin() can be called

    println("\nMain program ends: ${Thread.currentThread().name}")
}

/**
 *  How about finally { } block?
 *  How to call suspending function in finally block when the coroutine already canceled?
 */
fun finallyBlock() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val job: Job = launch {
        try {
            for (i in 0..500) {
                print("${i}.")
                delay(5) //or use yield() or any other suspending function
            }
        } catch (ex: CancellationException) {
            println("\nException caught safely")
        } finally {
            withContext(NonCancellable) {
                delay(1000) // Generally we don't use suspending function in finally block. Bcoz if it throws Exception app will crash.
                println("Close resource in finally") // Without withContext(NonCancellable) { } block this line will not execute
            }
        }
    }

    delay(10)
    job.cancelAndJoin()  // In place of calling cancel() and join() two method only cancelAndJoin() can be called

    println("\nMain program ends: ${Thread.currentThread().name}")
}

/**
 * How to set own custom message in CancellationException
 */
fun ownCaCancellationExceptionMessage() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    val job: Job = launch {
        try {
            for (i in 0..500) {
                print("${i}.")
                delay(5) //or use yield() or any other suspending function
            }
        } catch (ex: CancellationException) {
            println("\nException caught safely '${ex.message}'") // Own CancellationException message over here
        } finally {
            withContext(NonCancellable) {
                delay(1000) // Generally we don't use suspending function in finally block. Bcoz if it throws Exception app will crash.
                println("Close resource in finally") // Without withContext(NonCancellable) { } block this line will not execute
            }
        }
    }

    delay(10)
    // In place of calling cancelAndJoin() call cancel() & join() separately for this
    job.cancel(CancellationException("My own crash message"))  // Type your own message here for exception
    job.join()

    println("\nMain program ends: ${Thread.currentThread().name}")
}