快速开发框架
=========
## 一.集成Library
>>compile 'com.jakewharton:butterknife:7.0.1'
>>compile 'com.squareup.retrofit2:retrofit:2.1.0'<br> 
>>compile 'com.squareup.retrofit2:converter-gson:2.1.0'<br> 
>>compile 'com.squareup.retrofit2:adapter-rxjava:2.1.0'<br> 
>>compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'<br> 
>>compile 'com.squareup.okhttp3:okhttp-urlconnection:3.4.1'<br> 
>>compile 'io.reactivex:rxandroid:1.2.1'<br> 
>>compile 'io.reactivex:rxjava:1.1.6'<br> 
>>compile 'com.squareup.picasso:picasso:2.5.2'<br>
--------------------------------------------------------------------------------------------------------------------------------
## 二.集成工具<br> 
### 1.RxJava目前已经很火的RxBus<br>
* 使用方法如下:<br> 
* 发送事件:RxBus.getInstance().post(new PendingEvent());<br> 
* 接收事件:<br> 
       private void rxBusObservers() {
            addSubscription(RxBus.getInstance()
                .toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override<br> 
                    public void onCompleted() {

                    }
                   @Override<br> 
                   public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override<br> 
                    public void onNext(Object event) {
                        if (event instanceof PendingEvent) {
                            //是否有事件
                        }
                    }
                }));
    }<br>
	
	
## 三.集成工具<br>   
### 2.Activity统一管理工具<br> 
* 使用方法:<br> 
* 定义<br> 
* public ActivityManagerUtil activityManagerUtil;<br> 
* 初始化<br> 
     @Override<br> 
      protected void onCreate(Bundle savedInstanceState) {<br> 
        super.onCreate(savedInstanceState);<br>
        activityManagerUtil = ActivityManagerUtil.getInstance();<br> 
        activityManagerUtil.pushOneActivity(this)<br> 
    }<br> 
* 结束Activity&从栈中移除该Activity<br> 
    @Override<br> 
    protected void onDestroy() {<br> 
        super.onDestroy();<br> 
         activityManagerUtil.popOneActivity(this);<br> 
     }<br>
	 
* 方法1.把一个activity压入栈中 pushOneActivity(Activity actvity)<br>
* 方法2.移除一个activity       popOneActivity(Activity activity)<br>
* 方法3.获取栈顶的activity，先进后出原则 getLastActivity()<br>
* 结束指定的Activity           finishActivity(Activity activity)<br>
* 结束指定类名的Activity       finishActivity(Class<?> cls)<br>
* 结束所有activity             finishAllActivity()<br>
* 退出应用程序                 appExit()        
 
	 
    








