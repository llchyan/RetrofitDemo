package com.llchyan.retrofitdemo.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.llchyan.retrofitdemo.R;
import com.llchyan.retrofitdemo.base.BaseActivity;
import com.llchyan.retrofitdemo.model.BoxOfficeMovieResponse;
import com.llchyan.retrofitdemo.model.Coupon;
import com.llchyan.retrofitdemo.retrofit.TestApi;
import com.llchyan.retrofitdemo.utils.TestApiUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity
{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    private static final String TAG = "MainActivity";


    @Override
    protected int getContentViewLayoutID()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initView()
    {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null == actionBar)
        {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("MainActivity");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.action_settings)
        {

//            test3();
            test4();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void test1()
    {
        HashMap<String, String> paramsMap = new HashMap<>();
        //例如
        TestApiUtil.getInstance().getTestApi().getCourseList(paramsMap, new Callback<Coupon>()
        {

            @Override
            public void onResponse(Response<Coupon> response, Retrofit retrofit)
            {
                //在这里Json已经通过Retrofit中含有的Gson包为我们解析好了, 直接拿出来用就好了!
                //TODO:do something
            }

            @Override
            public void onFailure(Throwable t)
            {
                Log.d(TAG, t.getMessage());
                //TODO:do something
            }

        });
    }

    private void test2()
    {
        TestApiUtil.getInstance().getTestApi().getCouponList2(new Callback<Response>()
        {

            @Override
            public void onResponse(Response<Response> response, Retrofit retrofit)
            {
                ////注意这里用第一个Response参数的
                // String jsonString = new String(((TypedByteArray) response.getBody()).getBytes());

                Response body = response.body();
                //再使用Retrofit自带的JSON解析（或者别的什么）
                //                Coupon coupon = new Gson().fromJson(jsonString, Coupon.class);
                Coupon coupon = new Gson().fromJson(body.body().toString(), Coupon.class);
                //TODO:do something
            }

            @Override
            public void onFailure(Throwable t)
            {
                //TODO:do something
            }
        });
    }


    private void test3()
    {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor()
        {

            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException
            {
                // Do anything with response here
                return chain.proceed(chain.request());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://api.rottentomatoes.com/api/public/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestApi testApi = retrofit.create(TestApi.class);
        Call<BoxOfficeMovieResponse> call = testApi.listRepos();
        call.enqueue(new Callback<BoxOfficeMovieResponse>()
        {
            @Override
            public void onResponse(Response<BoxOfficeMovieResponse> response, Retrofit retrofit)
            {
                // handle response here
                //                BoxOfficeMovieResponse boxOfficeMovieResponse = response.body();
                //                Log.i(TAG, boxOfficeMovieResponse.toString());
                Log.i(TAG, "code==" + response.code());
                Log.i(TAG, "message" + response.message());
                //                Log.i(TAG,"hehe ----  "+response.toString());
            }

            @Override
            public void onFailure(Throwable t)
            {
                //                Log.e(TAG,t.getMessage());
                t.printStackTrace();
            }
        });

    }
    private void test4()
    {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor()
        {

            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException
            {
                // Do anything with response here
                return chain.proceed(chain.request());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://api.rottentomatoes.com/api/public/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestApi testApi = retrofit.create(TestApi.class);
        Call<Response> call = testApi.listRepos2();
        call.enqueue(new Callback<Response>()
        {
            @Override
            public void onResponse(Response<Response> response, Retrofit retrofit)
            {
                // handle response here
                //                BoxOfficeMovieResponse boxOfficeMovieResponse = response.body();
                //                Log.i(TAG, boxOfficeMovieResponse.toString());
                Log.i(TAG, "code=="+response.code());
                Log.i(TAG, "message"+response.message());
                //                Log.i(TAG,"hehe ----  "+response.toString());
            }

            @Override
            public void onFailure(Throwable t)
            {
                //                Log.e(TAG,t.getMessage());
                t.printStackTrace();
            }
        });

    }
}
