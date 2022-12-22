package com.xxun.watch.picture_translate.camera;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp3NetUtils {
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private static volatile OkHttp3NetUtils sOkHttp3NetUtils;
    public MediaType mMediaTypeJson;
    private final String TAG = "OkHttp3NetUtils";

    private OkHttp3NetUtils() {
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
        mMediaTypeJson = MediaType.parse("application/json;charset=utf-8");
    }

    public static OkHttp3NetUtils getInstance() {
        if (sOkHttp3NetUtils == null) {
            synchronized (OkHttp3NetUtils.class) {
                if (sOkHttp3NetUtils == null) {
                    sOkHttp3NetUtils = new OkHttp3NetUtils();
                }
            }
        }
        return sOkHttp3NetUtils;
    }

    /**
     * 在线翻译
     * 主线程
     * @param sourceLanguage 源语种
     * @param targetLanguage 目标语种
     * @param text           文本数据，UTF-8字符集，base64编码. 要求编码后大小不超过 1024 bytes（约256个汉字）。注: base64编码后大小会增加约1/3。
     * @param sid            手表登录后32位token
     * @param callBack       回调
     * @param <T>            转换类型
     */
    public <T> void textTranslate(@NonNull String sourceLanguage,
                                  @NonNull String targetLanguage,
                                  @NonNull String text,
                                  @NonNull String sid,
                                  @NonNull final JsonCallback<T> callBack) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("from", sourceLanguage);
        map.put("to", targetLanguage);
        map.put("text", text);
        map.put("sid", sid);
        String json = mGson.toJson(map);
        RequestBody requestBody = RequestBody.create(mMediaTypeJson, json);
        Request request = new Request.Builder()
                .url(UrlConstance.URL_TEXT_TRANSLATE)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (callBack!=null) {
                            callBack.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (callBack!=null) {
                            callBack.convertResponse(mGson,response);
                        }
                    }
                });
    }

    /**
     * 图像识别
     * 主线程
     * @param image64  图像数据，需保证图像文件大小base64编码后不超过4MB
     * @param sid      手表登录后32位token
     * @param callBack 回调
     * @param <T>      转换类型
     */
    public <T> void imageIdentifyToText(@NonNull String image64,
                                        @NonNull String sid,
                                        @NonNull final JsonCallback<T> callBack) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("image", image64);
        map.put("sid", sid);
        String json = mGson.toJson(map);
        RequestBody requestBody = RequestBody.create(mMediaTypeJson, json);
        Request request = new Request.Builder()
                .url(UrlConstance.URL_IMAGE_IDENTIFY_TO_TEXT)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (callBack!=null) {
                            callBack.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (callBack!=null) {
                            callBack.convertResponse(mGson,response);
                        }
                    }
                });

    }

}
