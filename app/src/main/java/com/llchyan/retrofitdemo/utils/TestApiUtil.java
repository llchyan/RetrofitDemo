package com.llchyan.retrofitdemo.utils;

import com.llchyan.retrofitdemo.retrofit.TestApi;

import retrofit.Retrofit;

/**
 * Created by LinLin on 2015/12/17.
 */
public class TestApiUtil
{
    private static TestApiUtil mInstance;
    //此前定义的接口的实例
    private TestApi testApi;
    //我们的主域名
    private static final String HOST = "http://111.111.111.111";

    //构造函数
    private TestApiUtil()
    {
        //在构造函数中我们要通过实例化RestAdapter拿到我们的ZhixueApi
        //注: setRequestInterceptor()在这里是为了在请求头中加入设备信息, 方便我们后台的调试
        Retrofit restAdapter = new Retrofit.Builder().baseUrl(HOST).build();
        testApi = restAdapter.create(TestApi.class);
    }

    //一个简单的单例
    public static TestApiUtil getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new TestApiUtil();
        }
        return mInstance;
    }

    public TestApi getTestApi()
    {
        return testApi;
    }

    //在这里我们还定义了一个RequestInterceptor, 作用是在请求头中拼入一些信息方便我们后台的调试
    //否则请求头中就只会出现okhttp 2.2.0的字样(Retrofit默认是直接使用OkhttpClient的)
//    RequestInterceptor defaultInterceptor = new RequestInterceptor()
//    {
//        @Override
//        public void intercept(RequestFacade request)
//        {
//            request.addHeader("User-Agent", "some code here");
//        }
//    };
}
