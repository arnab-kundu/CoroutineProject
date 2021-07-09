package coroutineBuilder

import kotlinx.coroutines.*

/**
 * @see launchBuilder.kt Before
 * 2. async { }
 * @see runblockingBuilder.kt Next
 */
fun main() = runBlocking { // Creates a blocking coroutine that executes in current thread (coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main)
    println("Main program starts: ${Thread.currentThread().name}")

    /**
     * launch { } block returns a Job object
     * where as
     * async { } block returns a Deferred<T> object.
     * <T> is the last line of code in async { } block
     */
    val deferred: Deferred<String> = async {
        println("Fake work starts: ${Thread.currentThread().name}")
        delay(1000)
        println("Fake work finished: ${Thread.currentThread().name}")
        "Output"
    }

    /**
     * async { } block calls await() method,
     * in place of join() in case of launch { }
     *
     * await() also stop coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main program execution & wait for coroutine execution to finish.
     * But await() return a value but join does not.
     * Its a Practical way to wait for coroutines to finish its job and get result of coroutine in coroutineExceptions.threadvsCoroutine.threadvsCoroutine.others.others.main Thread.
     */
    val result = deferred.await() // result is "Output"
    println(result)
    println("Main program ends: ${Thread.currentThread().name}")
}