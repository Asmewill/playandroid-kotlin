package fall.out.wanandroid.mvp.model

import fall.out.wanandroid.bean.ArticleResponseBody
import fall.out.wanandroid.bean.Banner
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.mvp.contract.HomeContract
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/26
 */
class HomeModel :CommonModel(),HomeContract.Model{
    override fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return  RetrofitHelper.apiService.getBanners()
    }
    override fun requestTopArticles(): Observable<HttpResult<MutableList<ArticleResponseBody.DatasBean>>> {
       return  RetrofitHelper.apiService.getTopArticles()
    }
    override fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>> {
       return  RetrofitHelper.apiService.getArticles(num)
    }
}