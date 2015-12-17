package com.llchyan.retrofitdemo.retrofit;

import com.llchyan.retrofitdemo.model.BoxOfficeMovieResponse;
import com.llchyan.retrofitdemo.model.Coupon;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by LinLin on 2015/12/17.测试接口
 */
public interface TestApi
{

    //1. 无参数post请求, 这里的Coupon则是用来解析服务器返回Json字符串的javabean类, Retrofit默认使用Gson解析
    @POST("/interface/xxxxxx")
    void getCouponList(Callback<Coupon> reponse);

    //2. 一个或几个参数的post请求
    // 注: 如果使用官方指南中提到的@Query和@QueryMap对参数进行封装, 那么默认会将参数拼到URL之中
    // 首先并不建议将参数拼如URL, 其次由于编码原因, 如果选择采用@Query或者@QueryMap类型的注解, 则参数中必然不能有中文
    // 其次如果使用@Query或者@QueryMap, 也就不能使用@FormUrlEncoded注解, 否则参数会消失
    // 如果使用了@FormUrlEncoded注解, 则必须使用@Field或者@FieldMap注解来作为参数
    @FormUrlEncoded
    @POST("/interface/xxxxxx")
    void getCourseList(@Field("userId") String userId, @Field("orderId") String orderId, Callback<Coupon> response);

    //3. 通过@FieldMap注解来封装参数
    //注: 如果你的参数只有一个或者两三个, 那么用@Field就基本足够了, 但如果你的参数非常之多, 七八个甚至十几个
    //那么我们就可以用一个Map去封装, 通过key-value的形式封装好Map对象后再传入, 代码会相对整洁, 在做参数传递的时候也不至于乱套
    @FormUrlEncoded
    @POST("/interface/xxxxxx")
    void getCourseList(@FieldMap Map<String, String> paramsMap, Callback<Coupon> response);

    //4. 提交multipart请求
    // 注: 如果我们要提交用户拍的的照片, 或者是录好的语音, 那么我们就要用到@Multipart这个注解了
    // 在使用Multipart的时候, 注意参数的注解必须是@Part或者@PartMap
    // @PartMap和上面的@FieldMap类似, 都是通过Map封装好参数, 就不过多解释了.
    //    @Multipart
    //    @POST("/interface/photoUpload_servlet")
    //    void submitUserPhoto(@Part("userId") String userId, @Part("file") TypedFile file, Callback<Coupon> response );


    //    //首先定义一个mimeType类型, 如果我要上传的是一张图片, 就按如下定义
    //    String mimeType = "image/jpg";
    //    //String mimeType = "audio/m4a";//如果是m4a的声音文件, 就这么定义
    //    TypedFile typedFile = new TypedFile(mimeType, file);//调用这个方法将文件转为TypedFile形势, 传参上传即可


    //但如果我们想获得JSON字符串，Callback的泛型里就不能写POJO类了，要写Response（retrofit.client包下）
    @POST("/interface/xxxxxx")
    void getCouponList2(Callback<Response> reponse);


    //在Retrofit 2.0上，只能定义一个模式，因此要简单得多.
    //Call<BoxOfficeMovieResponse> call = service.listRepos();
    //同步请求 :
    //          BoxOfficeMovieResponse b = call.execute();
    //异步请求 : call.execute(new Callback<Repo>() {
    //              @Override
    //              public void onResponse(Response<Repo> response) {
    //               // Get result Repo from response.body()
    //               }
    //
    //              @Override
    //               public void onFailure(Throwable t) {
    //
    //                 }
    //          });
    @GET("lists/movies/box_office.json")
    public Call<BoxOfficeMovieResponse> listRepos();

    @GET("lists/movies/box_office.json")
    public Call<Response> listRepos2();
}
