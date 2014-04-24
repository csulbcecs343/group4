package com.closetshare.app;

import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

interface ApiClosetShare {

    final String BASE_URL = "/index.php";

    @FormUrlEncoded
    @POST(BASE_URL)
    public ApiResponses simplePost(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(BASE_URL)
    public ApiSearchResult searchPost(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(BASE_URL)
    public ApiViewCloset viewClosetPost(@FieldMap Map<String, String> options);

}

public class Api {

    private static final Api INSTANCE = new Api();

    private static final String BASE_URL = "http://build.vibrantdavee.com/test";

    private RestAdapter mAdapter;

    private ApiClosetShare mApi;

    private Api() {
        mAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        mApi = mAdapter.create(ApiClosetShare.class);
    }

    public static Api getInstance() {
        return INSTANCE;
    }

    public ApiResponses post(Map<String, String> options) {
        return mApi.simplePost(options);
    }

    public ApiSearchResult search(Map<String, String> options) {
        return mApi.searchPost(options);
    }

    public ApiViewCloset viewCloset(Map<String, String> options) {
        return mApi.viewClosetPost(options);
    }
}