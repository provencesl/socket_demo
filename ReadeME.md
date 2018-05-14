
java socket实现C/S通信，socket,aio,bio,nio的基础实现demo.还有netty运行的小demo，仅供参考


## 消息处理机制 ##

消息处理机制的本质：一个线程开启循环模式持续监听并且依次处理其他线程给它发的消息。

简单的说：一个线程开启一个无限循环模式，不断遍历自己的消息列表。如果有消息就挨个拿出来做处理。如果列表没有消息，则阻塞。【相当于wait，让出CPU给其他线程处理】。其他线程如果想让线程做些什么，就该往线程的消息队列插入消息，该线程会不断从队列里拿出消息做处理。



Handler:作为消息分发对象。消息分发，依赖于消息循环。一个线程之中，消息循环会阻塞线程，等待消息构成循环。而有了消息，分配到对应的Handler，让他们进一步分发处理。