package fall.out.wanandroid.mvp.model

import fall.out.wanandroid.base.BaseModel
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.mvp.contract.CommonContract
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/26
 */
open class CommonModel: BaseModel() ,CommonContract.Model{
    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return  RetrofitHelper.apiService.addCollectArticle(id)
    }
    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
         return RetrofitHelper.apiService.cancelCollectArticle(id)
    }
}