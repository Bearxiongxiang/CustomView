package com.foxconn.okhttpmanager;


import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Bear on 2016/8/8.
 */
public class OkhttpManager {
    private OkHttpClient client;
    private static OkhttpManager okhttpManager;
    private Handler mHandler;

    private OkhttpManager(){
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static OkhttpManager getInstance(){
        if(okhttpManager==null){
            okhttpManager = new OkhttpManager();
        }
        return okhttpManager;
    }

    //*********内部逻辑处理方法**********
    private Response getSync(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    private String getSyncAsString(String url) throws IOException{
        return getSync(url).body().string();
    }

    private void getAsync(String url, final DataCallBack callBack){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response){
                try {
                    String result = response.body().string();
                    deliverDataSeccess(result,callBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callBack);
                }

            }
        });
    }

    private void postAsync(String url, Map<String, String> params,final DataCallBack callBack){
        RequestBody requestBody = null;
        if(params == null){
            params=new HashMap<String, String>();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()) {
            String key = entry.getKey().toString();
            String value =null;
            if(entry.getValue()==null){
                value="";
            }else{
                value = entry.getValue().toString();
            }
            builder.add(key,value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSeccess(result,callBack);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callBack);
                }
            }
        });
    }
    //************数据分发的方法******************
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callBack){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(callBack!=null){
                        callBack.requestFailure(request,e);
                    }
                }
            });
    }

    private void deliverDataSeccess(final String result, final DataCallBack callBack){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(callBack!=null){
                    callBack.requestSuccess(result);
                }
            }
        });
    }

    //*********对外公布的方法************
    public static Response httpGetSync(String url) throws IOException{
        return getInstance().getSync(url);
    }

    public static String httpGetSyncAsString(String url) throws IOException{
        return getInstance().getSyncAsString(url);
    }

    public static void httpGetAsync(String url,DataCallBack callBack){
        getInstance().getAsync(url,callBack);
    }

    public static void httpPostAsync(String url, Map<String,String> params,DataCallBack callBack){
        getInstance().postAsync(url,params,callBack);
    }

    private interface DataCallBack {
        void requestFailure(Request request, IOException e);
        void requestSuccess(String result);
    }
}
