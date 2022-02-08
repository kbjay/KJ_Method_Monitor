#### 解决的问题：
1. 当我们使用systrace分析卡顿问题的时候，我们可以看到很多系统的tag，比如input，animator，traversal等等，
但是根据这个，我们无法直观的定位到具体是哪个方法的问题，这个sdk可以辅助快速定位到具体的方法。

2. 在开发过程中我们可能会无意中在主线程中引入一些耗时的方法，这个sdk可以设置主线程方法的耗时阈值，如果超过阈值，
会有回调，使用者可以在该回调中做自己的操作，比如log.e

3，以上两个目前已经内嵌好的功能，这个sdk也提供了主线程所有方法（不包括三方库）进入跟退出的回调，使用者可以根据自己的需求扩展使用