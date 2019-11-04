package fall.out.wanandroid.mvp.contract

import fall.out.wanandroid.bean.ArticleResponseBody
import fall.out.wanandroid.bean.Banner
import fall.out.wanandroid.bean.HttpResult
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/26
 */
interface HomeContract {
    interface View:CommonContract.View{
        fun scrollToTop()
        fun setBanner(banners:List<Banner>)
        fun setArticles(articles:ArticleResponseBody)
    }
    interface Presenter:CommonContract.Presenter<View>{
        fun requestBanner()
        fun requestHomeData()
        fun requestArticles(num:Int)
    }
    interface Model:CommonContract.Model{
        fun requestBanner():Observable<HttpResult<List<Banner>>>
        fun requestTopArticles():Observable<HttpResult<MutableList<ArticleResponseBody.DatasBean>>>
        fun requestArticles(num:Int):Observable<HttpResult<ArticleResponseBody>>
    }
}