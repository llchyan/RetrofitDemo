package com.llchyan.retrofitdemo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.google.gson.Gson;
import com.llchyan.retrofitdemo.base.BaseActivity;
import com.llchyan.retrofitdemo.model.Coupon;
import com.llchyan.retrofitdemo.utils.TestApiUtil;

import java.util.HashMap;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

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


    private void test1()
    {
        HashMap<String, String> paramsMap = new HashMap<>();
        //例如
        TestApiUtil.getInstance().getTestApi().getCourseList(paramsMap, new Callback<Coupon>()
        {

            @Override
            public void success(Coupon coupn, Response response)
            {
                //在这里Json已经通过Retrofit中含有的Gson包为我们解析好了, 直接拿出来用就好了!
                //TODO:do something
            }

            @Override
            public void failure(RetrofitError error)
            {
                Log.d(TAG, error.getMessage());
                //TODO:do something
            }
        });
    }

    private void test2()
    {
        TestApiUtil.getInstance().getTestApi().getCouponList2(new Callback<Response>()
        {
            @Override
            public void success(Response response, Response response2)
            {
                //注意这里用第一个Response参数的
                String jsonString = new String(((TypedByteArray) response.getBody()).getBytes());

                //再使用Retrofit自带的JSON解析（或者别的什么）
                Coupon coupon = new Gson().fromJson(jsonString, Coupon.class);
                //TODO:do something
            }

            @Override
            public void failure(RetrofitError error)
            {
                //TODO:do something
            }
        });
    }

}
