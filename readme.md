#### 解决的问题：
1. 当我们使用systrace分析卡顿问题的时候，我们可以看到很多系统的tag，比如input，animator，traversal等等，
但是根据这个，我们无法直观的定位到具体是哪个方法的问题，这个sdk可以辅助快速定位到具体的方法。

2. 在开发过程中我们可能会无意中在主线程中引入一些耗时的方法，这个sdk可以设置主线程方法的耗时阈值，如果超过阈值，
会有回调，使用者可以在该回调中做自己的操作，比如log.e

3. 以上两个是目前已经内嵌好的功能，这个sdk也提供了主线程所有方法（不包括三方库）进入跟退出的回调，使用者可以根据自己的需求扩展使用

#### 效果展示
1. 原生的trace文件 
![Image 原生trace文件](https://github.com/kbjay/KJ_Method_Monitor/blob/44167b67b587764016f9015ffc6feb797225e998/pic/v1.png)
   * 数据贫瘠 
   * 距离应用侧有很深的调用栈
   * 基本无法定位问题

2. FrameWork层增加了一些trace 
![Image FrameWork增强](https://github.com/kbjay/KJ_Method_Monitor/blob/44167b67b587764016f9015ffc6feb797225e998/pic/v2.png)
   * 信息会丰富一些，
   * 距离应用层仍然有很深的调用栈，
   * 很难定位问题

3. 接入该库  
![Image 接入该库](https://github.com/kbjay/KJ_Method_Monitor/blob/44167b67b587764016f9015ffc6feb797225e998/pic/v3.png)
   * 卡顿时可以直观看到应用侧主线程方法的调用栈以及耗时
   * 直接定位到应用侧具体的方法

#### 使用方法
1. 引入插件
   
   project:
    ```groovy
    
   buildscript {
       
        repositories {
            //..
    
            maven { url 'https://jitpack.io' }
        }
        dependencies {
            //...
            classpath "com.github.kbjay.KJ_Method_Monitor:plugin:v1.0.1"
        }
    }
    ```
   
    app:
   
    ```groovy
    plugins {
        //..
        id 'com.kj.method.monitor.plugin'
    }
    
    ```
   
2. 引入lib
   
    project:
   
   ```groovy
   allprojects {
       repositories {
           // ..

           maven { url 'https://jitpack.io' }
        }
   }
   ```
   
    app:
    ```groovy
    implementation 'com.github.kbjay.KJ_Method_Monitor:lib_method_monitor:v1.0.1'
   ```
   
3. 使用
   
   ```java
   public class MyApplication extends Application {

       @Override
       public void onCreate() {
           super.onCreate();
           initMethodHook();
       }
   
       private void initMethodHook() {
           KJMethodManager.addMethodHook(new KJSystraceHook());
           KJMethodManager.addMethodHook(new KJMethodDurationHook(50, new KJMethodDurationHook.KJMethodDurationCallBack() {
               @Override
               public void onMethodOverLimit(long duration, String methodName) {
                   Log.w("kbjay_method", methodName + ":" + duration);
               }
           }));
       }
   }
   ```