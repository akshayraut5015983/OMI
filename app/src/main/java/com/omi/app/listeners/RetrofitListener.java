package com.omi.app.listeners;


import com.omi.app.utils.ErrorObject;

public interface RetrofitListener {
    void onResponseSuccess(Object responseBody, int apiFlag);

    void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag);

}
