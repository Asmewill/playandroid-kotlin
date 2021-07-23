package com.example.oapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.oapp.base.BaseViewModel
import com.example.oapp.bean.ListDataUiState
import com.example.oapp.bean.PointBean
import com.example.oapp.http.HttpRetrofit

/**
 * Unable to create call adapter for class java.lang.Object
for method ApiService.getPointList
 * Created by jsxiaoshui on 2021/7/22
 */
class RankListViewModel:BaseViewModel() {



    var  rankListLiveData:MutableLiveData<ListDataUiState<PointBean>> = MutableLiveData()

    fun getHomeRankList(pageNo:Int) {

        request(
            block = { HttpRetrofit.apiService.getPointList(pageNo) },
            success = {
              val listDataUiState=  ListDataUiState<PointBean>(dataBean = it)
                rankListLiveData.value=listDataUiState

            },
            error = {
                val listDataUiState=  ListDataUiState<PointBean>(
                    isException = true,
                    error = it,
                    pageNo = pageNo
                )
                rankListLiveData.value=listDataUiState
            }
        )
    }

}