package com.demo.aadityak.taskapp.services.retro;

import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

/**
 * Created by aadityak on 15/11/2017.
 */
abstract public class JBLRetrofitCallback<T> implements Callback<T>{
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Timber.e(t,"Call %s   ===========>>>>>>>> F A I L E D  <<<<<<<===========",call);
    }
}
