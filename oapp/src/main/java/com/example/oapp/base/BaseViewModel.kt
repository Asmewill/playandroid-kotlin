package com.example.oapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oapp.bean.HttpResult
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by jsxiaoshui on 2021/7/22
 */
open class BaseViewModel:ViewModel() {


     val loadingDialog by lazy { UILoading() }

     inner class UILoading{
         val showLoading by lazy { UnPeekLiveData<String>() }
         val dismissDialog by lazy { UnPeekLiveData<String>() }
     }
     //高阶函数
    fun <T> request(
        block: suspend () -> HttpResult<T>,
        success: (HttpResult<T>) -> Unit,
        error: (Throwable) -> Unit={},
        isShowDialog: Boolean = false,
        loadingMessage: String = "loading..."
    ): Job {
        //如果需要弹窗 通知Activity/fragment弹窗
        return viewModelScope.launch {
            runCatching  {
                if(isShowDialog){
                  loadingDialog.showLoading.value=loadingMessage
                }
                block()
            }.onSuccess {
                loadingDialog.dismissDialog.value="close"
                success(it)
            }.onFailure {
                loadingDialog.dismissDialog.value="close"
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