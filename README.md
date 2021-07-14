# CoroutineProject
Kotlin Coroutine tutorial

## Guide lines to use Coroutines Dispatchers in Andorid

1. **CoroutineScope(Dispatchers.Main).launch { ...your_main_thread_code... }**
    1. When ever you are **touching** any **UI component**, Do it in a (Dispatcher.Main) { } block.
    2. Touching a UI component/View in ~~(Dispatcher.IO).async { }~~ block will definitely crash the application.
    3. But **touching a UI component/View** in **(Dispatcher.Main) { }** block will not crash the application.

2. **CoroutineScope(Dispatchers.IO).async { ...your_background_thread_code... }**
    1. Delay of 5 sec in (Dispatcher.IO) { } block will not crash the application.
    2. But delay of 5 sec in ~~(Dispatcher.Main).launch { }~~ block will definitely  crash the application.
    3. So, where ever you have possible delay in result.
    4. Like **network or Database operation**, Do it in a **(Dispatcher.IO) { }** block.

> A sample code snipped               
```kotlin
CoroutineScope(Dispatchers.Main).launch {
            val deferredString: Deferred<String> = CoroutineScope(Dispatchers.IO).async {
                delay(5000)
                /**
                 * Delay of 5 sec in (Dispatcher.IO) { } block will not crash the application.
                 * But in (Dispatcher.Main( { } block will definitely  crash the application.
                 * So, where ever you have possible delay in result.
                 * Like network or Database operation, Do it in a (Dispatcher.IO) { } block.
                 */
                
                anyDatbaseOperation()
                anyNetworkOperation()
            }
            val timeStampString = deferredString.await()
            /** 
            * On the other hand:
            * When ever you are touching any UI component, Do it in a (Dispatcher.Main) { } block.
            * Touching a UI component/View in (Dispatcher.IO) { } block will definitely crash the application.
            * But in (Dispatcher.Main) { } block will not crash the application.
            */
            your_text_view!!.text = string_returned_anyNetworkOperation_method
        }
```