package com.closetshare.app;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

//public class API {
//
//    private static final API instance;

//
//    static {
//        try {
//            instance = new API();
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred!", e);
//        }
//    }
//
//    private API() {
//        // ....
//        ra = new RestAdapter.Builder()
//                .setEndpoint(BASE_URL)
//                .build();
//    }
//
//    public static API getInstance() {
//        return instance;
//    }
//
//    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
//    }
//
//
//}

interface ClosetShareAPI {
    final String BASE_URL = "/index.php";

    @FormUrlEncoded
    @POST(BASE_URL)
    public void simplePost(@FieldMap Map<String,String> options, Callback<APIResponses> cb);

//    public void simplePost(@FieldMap Map<String, String> options, Callback<String> cb);

}

public class API {
    private static final API INSTANCE = new API();

    private static final String BASE_URL = "http://build.vibrantdavee.com/test";
    private RestAdapter ra;

    private API() {
        ra = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
    }

    public static API getInstance() {
        return INSTANCE;
    }


    public void post(String args) {

        // Create an instance of our SimpleApi interface.
        ClosetShareAPI simpleApi = ra.create(ClosetShareAPI.class);


        String un = "great2@excake.com";
        String pass = "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d";

        // Call our method
        Map<String, String> options = new HashMap<String, String>();
        options.put("command", "login");
        options.put("username", un);
        options.put("password", pass);

        Callback <APIResponses> cb = new Callback<APIResponses>() {
            @Override
            public void success(APIResponses strings, Response response) {

                Log.d("suc","suc");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("fail","fail");
            }
        };

        simpleApi.simplePost(options, cb);

        Log.d("post", "fine");
//        System.out.println("simpleApi.simplePost()=<<" + cb. + ">>");
    }

}