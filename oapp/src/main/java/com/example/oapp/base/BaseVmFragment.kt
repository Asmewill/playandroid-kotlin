package com.example.oapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by jsxiaoshui on 2021/7/22
 */
abstract class BaseVmFragment<VM:BaseViewModel>:Fragment() {
    lateinit var mViewModel:VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(layoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel=createViewModel()
        createObserver()
        initView()
        initData()
    }

    private fun initData() {
    }
    abstract fun initView()
    abstract fun createObserver()
    abstract fun createViewModel(): VM
    abstract  fun layoutId():Int


}