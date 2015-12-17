package com.llchyan.retrofitdemo.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by LinLin on 2015/12/17
 */
public class ResultService
{
    public static final String SERVICE = "http://2.0.hlstreet.com/mobile/";

    public static class ResultModel
    {
        @SerializedName("code")
        public int code;
        @SerializedName("msg")
        public String msg;
        @SerializedName("info")
        public Object info;

        public ResultModel(int code, String msg, Object info)
        {
            this.code = code;
            this.msg = msg;
            this.info = info;
        }

        @Override
        public String toString()
        {
            return "ResultMdel{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", info=" + info +
                    '}';
        }
    }

    public static interface ResultApi
    {
        @FormUrlEncoded
        @POST("login")
        Call<ResultModel> listResult(@FieldMap Map<String, String> names);

        @FormUrlEncoded
        @POST("login")
        Call<ResultModel> listResult2(@Field("mobile") String mobile, @Field("password") String password,
                                      @Field("client") String client, @Field("device_token") String device_token);

        @GET("login/check_device_login")
        Call<ResultModel> listResult3(@Header("TOKEN") String token);

        @GET("member_edit/getMemberInfo")
        Call<ResultModel> listResult4(@Header("TOKEN") String token);

        @FormUrlEncoded
        @POST("member_goods/publishGoods")
        Call<ResultModel> listResult5(@Header("TOKEN") String token, @FieldMap Map<String, String> names, @Field("images[]") String... images);

        @GET("member_edit/getMemberInfo")
        Observable<ResultModel> listResult6(@Header("TOKEN") String token);

    }

    public static void main(String... args) throws IOException
    {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE)
                .addConverterFactory(GsonConverterFactory.create())// 使用Gson作为数据转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// 使用RxJava作为回调适配器
                .build();

        // Create an instance of our GitHub API interface.
        ResultApi resultApi = retrofit.create(ResultApi.class);

        //        test1(resultApi);
        //        test2(resultApi);
//                test3(resultApi);
        //        test4(resultApi);
                test6(resultApi);

    }


    private static void test1(ResultApi resultApi)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", "18106821971");
        map.put("password", "123456789");
        map.put("client", "android");
        map.put("device_token", "");
        Call<ResultModel> call = resultApi.listResult(map);
        callResult("call1", call);
    }

    private static void test2(ResultApi resultApi)
    {
        Call<ResultModel> call = resultApi.listResult2("18106821971", "123456789", "android", "");
        callResult("call2", call);
        //TODO:call.enqueue();
    }

    private static void test3(ResultApi resultApi)
    {
        Call<ResultModel> call = resultApi.listResult3("4217f13f16a454139ab9b967f942a754");
        callResult("call3", call);
        //TODO:call.enqueue();
    }

    private static void test4(ResultApi resultApi)
    {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "test");
        hashMap.put("gc_id", "5");
        hashMap.put("description", "give me a hand");
        hashMap.put("city_name", "绍兴");
        hashMap.put("area_name", "越城");
        hashMap.put("longitude", "156.1");
        hashMap.put("latitude", "43.5");
        hashMap.put("condition", "8");
        Call<ResultModel> call = resultApi.listResult5("4217f13f16a454139ab9b967f942a754", hashMap, "/avatar/201511161601162058_401x401_1.0.jpg", "/avatar/201511161601162058_401x401_1.0.jpg", "/avatar/201511161601162058_401x401_1.0.jpg");
        callResult("call4", call);
    }

    private static void callResult(final String tag, Call<ResultModel> call)
    {
        call.enqueue(new Callback<ResultModel>()
        {
            @Override
            public void onResponse(Response<ResultModel> response, Retrofit retrofit)
            {
                if (IsFailed(response))
                    return;
                System.out.println(tag + "success ==" + response.body().toString());
            }

            @Override
            public void onFailure(Throwable t)
            {
                PrintFailedMsg(t);
            }
        });
    }

    private static void test6(ResultApi resultApi)
    {
        Observable<ResultModel> observable = resultApi.listResult6("4217f13f16a454139ab9b967f942a754");
        observable.subscribe(new Subscriber<ResultModel>()
        {
            @Override
            public void onCompleted()
            {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e)
            {
                System.out.println("onError ==" + e.getMessage());
            }

            @Override
            public void onNext(ResultModel resultModel)
            {
                System.out.println("onNext ==" + resultModel.toString());
            }
        });
        //TODO:call.enqueue();
    }


    public static boolean IsFailed(Response<?> response)
    {
        if (null == response)
        {
            return true;
        }
        int code = response.code();
        if (code != 200)
        {
            try
            {
                System.out.println("failed ==" + response.errorBody().string());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static void PrintFailedMsg(Throwable t)
    {
        System.out.println("failed ==" + t.getMessage());
    }
}
