Blocking Web-Servers
--

The first concept of blocking, multi-threaded server has a finite set amount of threads 
in a pool. Every request will get assigned to specific thread and this thread will be 
assigned until the request has been fully served. This is basically the same as how a 
the checkout queues in a super market works, a customer at a time with possible parallel lines. 

In most circumstances a request in a web server will be cpu-idle for the majority of the time 
while processing the request. This is due the fact that it has to wait for I/O: read the socket, 
write to the db (which is also basically IO) and read the result and write to the socket. 
Additionally using/creating a bunch of threads is slow (context switching) and requires a 
lot of memory. Therefore this concept often does not use the hardware resources it has very 
efficiently and has a hard limit on how many clients can be served in parallel. 

This property is misused in so called starvation attacks, e.g. the slow loris, an attack where 
usually a single client can DOS a big multi-threaded web-server with little effort.

Non-Blocking Web-Servers
----

In contrast a non-blocking web-server can serve multiple clients with only a single thread. 
That is because it uses the non-blocking kernel I/O features. These are just kernel calls 
which immediately return and call back when something can be written or read, making the cpu free 
to do other work instead. Reusing our supermarket metaphor, this would be like, when a cashier 
needs his supervisor to solve a problem, he does not wait and block the whole lane, but starts 
to check out the next customer until the supervisor arrives and solves the problem of the first 
customer.

This is often done in an event loop or higher abstractions as green-threads or fibers. 
In essence such servers can't really process anything concurrently 
(of course you can have multiple non-blocking threads), but they are able to serve thousands 
of clients in parallel because the memory consumption will not scale as drastically as with 
the multi-thread concept (read: there is no hard limit on max parallel clients).

Also there is no thread context-switching. The downside is, that non-blocking code is often more 
complex to read and write (e.g. callback-hell) and doesn't prefrom well in situations where a 
request does a lot of cpu-expensive work.


See:
- https://stackoverflow.com/a/56808576/432903
- https://blog.twitter.com/engineering/en_us/a/2011/twitter-search-is-now-3x-faster