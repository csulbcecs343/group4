package com.closetshare.app;

import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

interface ClosetShareAPI {

    final String BASE_URL = "/index.php";

    @FormUrlEncoded
    @POST(BASE_URL)
    public APIResponses simplePost(@FieldMap Map<String, String> options);

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

    public APIResponses post(Map<String, String> options) {
        ClosetShareAPI csAPI = ra.create(ClosetShareAPI.class);
        return csAPI.simplePost(options);
    }
}