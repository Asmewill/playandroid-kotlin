package com.example.oapp.expand

import android.app.Activity
import android.widget.Toast


/**
 * Created by jsxiaoshui on 2021/6/25
 */
fun Activity.showToast(str:String){
    Toast.makeText(applicationContext,str,Toast.LENGTH_LONG).show();
}