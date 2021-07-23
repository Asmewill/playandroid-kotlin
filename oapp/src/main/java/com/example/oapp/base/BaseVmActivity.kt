package com.example.oapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by jsxiaoshui on 2021/7/22
 */
abstract class BaseVmActivity<VM : BaseViewModel>:AppCompatActivity() {
    lateinit var mViewModel:VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!isUseDB()){
            setContentView(layoutId())
        }else{
            initDataBind()
        }
        mViewModel=createViewModel()
        createObserver()
        initView()
        initData()

    }
    //设置是否使用DataBinding
   open fun isUseDB():Boolean{
        return  false
    }


    abstract  fun createViewModel():VM
    abstract fun layoutId(): Int
    abstract  fun initView()
    abstract  fun initData()
    abstract  fun createObserver()
    //如果使用DataBinding,供子类重写
    open fun initDataBind() {
    }
}