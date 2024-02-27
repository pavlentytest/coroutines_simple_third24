import kotlinx.coroutines.*

suspend fun main2() {
    for(i in 0..5){
        delay(500)
        println(i)
    }
    println("Test message!")
}
suspend fun main3() {
    coroutineScope {
        launch {
            heavyWork()
        }
        println("Test message!")
    }
}
suspend fun heavyWork() {
    for(i in 0..5){
        delay(500)
        println(i)
    }
}
suspend fun main4() {
    coroutineScope {
        val job: Job = launch {
            heavyWork()
        }
        println("Start!")
        job.join() // дождаться завершение работы корутины
        println("Finished!")
    }
}
suspend fun main5() {
    coroutineScope {
        // корутина сделана, но не запущена
        val job: Job = launch(start = CoroutineStart.LAZY) {
            delay(1000)
            println("Coroutine started!")
        }
        delay(2000)
        job.start() // корутина запущена
        delay(4000)
        job.cancel() // отменяем корутину
        job.join() // ждем завершение
        job.cancelAndJoin() // очевидно
    }
}
suspend fun main6() {
    coroutineScope {
        val result: Deferred<String> = async {
            getResult()
        }
        println("message: ${result.await()}")
        println("Finished!")
    }
}
suspend fun getResult() : String {
    delay(2000)
    return "Message!"
}
suspend fun main() {
    coroutineScope {
        // корутина создана, но не запущена
        val calc = async(start = CoroutineStart.LAZY){ calc(1,2) }
        delay(2000)
        calc.start() // запуск
        println("sum: ${calc.await()}") // получение результата
    }
}
suspend fun calc(a: Int, b: Int) : Int {
    return a+b
}