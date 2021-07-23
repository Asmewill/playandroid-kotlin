package com.example.oapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oapp.bean.HttpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jsxiaoshui on 2021/7/22
 */
open class BaseViewModel:ViewModel() {


    fun <T> request(
        block: suspend () -> HttpResult<T>,
        success: (HttpResult<T>) -> Unit,
        error: (Throwable) -> Unit = {},
        isShowDialog: Boolean = false,
        loadingMessage: String = "请求网络中..."
    ): Job {
        //如果需要弹窗 通知Activity/fragment弹窗
        return viewModelScope.launch {
            runCatching  {
                block()
            }.onSuccess {
                success(it)
            }.onFailure {
                error(it)
            }
        }
    }

    fun <T> launch(
        block: () -> T,
        success: (T) -> Unit,
        error: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }.onSuccess {
                success(it)
            }.onFailure {
                error(it)
            }
        }
    }




}