package practice

import kotlinx.coroutines.*

fun main() = runBlocking {

        val a: Deferred<String> = async(start = CoroutineStart.LAZY) {
            methodA()
            "Arnab"
        }
        println(a.await()) // comment or uncomment this & see the difference in logcat

        val b = launch {
            methodB()
        }
        b.join()

}


private suspend fun methodA() {
    delay(1000)
    println("This is A()")
}


private suspend fun methodB() {
    delay(1000)
    println("This is B()")
}