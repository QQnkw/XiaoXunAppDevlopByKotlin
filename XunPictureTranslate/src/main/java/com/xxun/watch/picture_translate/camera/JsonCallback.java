package com.xxun.watch.picture_translate.camera;

import android.util.Log;

import com.google.gson.Gson;
import com.xxun.watch.picture_translate.bean.IdentityPictureResponse;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class JsonCallback<T> {
    private final String TAG = "JsonCallback";

    /**
     * 发生的错误
     *
     * @param exception 异常
     */
    public void onError(Exception exception) {
        Log.e(TAG, "onError", exception);
    }

    /**
     * 处理数据成功
     *
     * @param t 转换后的数据
     */
    public abstract void onSuccess(T t);


    public final void convertResponse(Gson gson, Response response) {
        try {
            Type genType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            ResponseBody responseBody = response.body();
            Reader reader = responseBody.charStream();
            T t = gson.fromJson(reader, type);
            onSuccess(t);
        } catch (Exception e) {
            onError(e);
        }
    }
}
