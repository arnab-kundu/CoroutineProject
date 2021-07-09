package coroutineExceptions

import kotlinx.coroutines.*

/**
 *
 * 1. How to cancel coroutine
 * @see coroutineExceptionHandle.kt Next
 */
fun main() {
    // Run one by one method() for better understanding of logcat message
    cancelCoroutineUsingCooperativeFunction()           // 1st process
    cancelCoroutineUsingIsActive()                      // 2nd process
}

/**
 * 1. How to cancel Coroutine using cooperative functions
 */
fun cancelCoroutineUsingCooperativeFunction() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    /**
     * To cancel coroutine, the coroutine must be cooperative.
     * Coroutine is cancelable/cooperative only if CoroutineScope contains any of this methods
     *  - delay()
     *  - yield()
     *  - withContext()
     *  - withTimeout()
     *  All this 4 method are cancelable suspend function
     */
    val job: Job = launch {
        for (i in 0..500) {
            print("${i}.")
            /**
             * Thread.sleep() is not cancelable suspend function.
             * So, if Thread.sleep() used coroutine does not become cooperative.
             * There fore it will not cancel after calling join.cancel() also. And total for loop will execute.
             * To verify uncomment Thread.sleep(10) and comment out delay(10)
             */
            // Thread.sleep(10) // is not cancelable suspend function
            // or
            delay(10) //or call yield() function to avoid any delay
        }
    }
    /**
     * To complete the for loop coroutine needs 5000ms or 5 seconds.
     * But after 1 second delay cancel() called.
     * So coroutine will be canceled before finish
     */
    delay(1000)

    job.cancel()    // If without using cancel() only join() called, then complete for loop will execute.
    job.join()      // After cancel() call join()
    // or
    job.cancelAndJoin()  // In place of calling cancel() and join() two method only cancelAndJoin() can be called

    println("\nMain program ends: ${Thread.currentThread().name}")
}


/**
 * 2. How to cancel Coroutine using CoroutineScope.isActive & Dispatchers.Default
 *      - i.e. without cooperative functions.
 */
fun cancelCoroutineUsingIsActive() = runBlocking {

    println("Main program starts: ${Thread.currentThread().name}")

    /**
     * Another way to cancel coroutine is use
     *  - isActive &
     *  - launch(Dispatchers.Default)
     */
    val job: Job = launch(Dispatchers.Default) {
        for (i in 0..500) {
            if (!isActive)
                break
            print("${i}.")
            /**
             * Thread.sleep() is not cancelable suspend function.
             * Not using any of this to cancel coroutine
             *  - delay()
             *  - yield()
             *  - withContext()
             *  - withTimeout()
             */
            Thread.sleep(1) // No need to use delay() cancelable suspend function here to cancel coroutine
            //delay(10)
        }
    }
    /**
     * To complete the for loop coroutine needs 500ms or 0.5 seconds.
     * But after 10ms delay cancel() called.
     * So coroutine will be canceled before finish
     */
    delay(10)
    job.cancelAndJoin()  // In place of calling cancel() and join() two method only cancelAndJoin() can be called.

    println("\nMain program ends: ${Thread.currentThread().name}")
}