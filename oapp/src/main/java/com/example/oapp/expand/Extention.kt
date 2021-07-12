package com.example.oapp.expand

import android.app.Activity
import android.app.Application
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.oapp.MyApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by jsxiaoshui on 2021/6/25
 */
fun Activity.showToast(str:String){
    Toast.makeText(applicationContext,str,Toast.LENGTH_LONG).show();
}
fun <T> Observable<T>.applySchdules():Observable<T>{
    return subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
fun <T,k : BaseViewHolder?>BaseQuickAdapter<T,k>.showToast(str:String){
    Toast.makeText(MyApp.instance,str,Toast.LENGTH_LONG).show();
}

