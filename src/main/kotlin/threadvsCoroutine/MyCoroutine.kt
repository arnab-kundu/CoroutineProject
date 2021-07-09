package threadvsCoroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * 1. Replacement of Thread and Thread.sleep()
 */
fun main() {                                                        // Executes in main thread

    println("Main program starts:${Thread.currentThread().name}")

    // Main program execution finishes before GlobalScope.launch{ } block
    // To stop Main thread uncomment "Thread.sleep(2000)" at lineNo: 28
    GlobalScope.launch {                             // Creates a background coroutine that runs on background thread (worker thread)
        println("Fake work starts:${Thread.currentThread().name}")
        delay(1000)
        println("Fake work finished:${Thread.currentThread().name}")
    }

    /**
     *  This sleep needed to pause main program to finish GlobalScope.launch{ } block
     *  Other wise main program execution will finish before GlobalScope.launch { } block
     *  We will not get any output for coroutineScope
     */
    //Thread.sleep(2000)


    /**
     * Now, This Thread.sleep(2000) can be replaced with
     *      runBlocking{
     *           delay(2000)
     *      }
     *      block,
     *      Which is a coroutine builder like launch{ }
     *
     * This runBlocking also stops the main thread execution
     * So, comment Thread.sleep(2000) and uncomment runBlocking using (ctrl + /)
     */
    //runBlocking {
    //    delay(2000)
    //}

    println("Main program ends:${Thread.currentThread().name}")
}