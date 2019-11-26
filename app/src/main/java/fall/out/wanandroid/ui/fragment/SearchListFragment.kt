package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CommonUtil
import fall.out.wanandroid.adapter.HomeAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.ArticleResponseBody
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.ContentActivity
import fall.out.wanandroid.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Owen on 2019/11/18
 */
class SearchListFragment:BaseFragment(){
    private val homeAdapter by lazy {
        HomeAdapter()
    }
    private var pageNum: Int=0
    private  var key=""
    companion object{
        fun getInstance(key:String):SearchListFragment{
            val searchListFragment=SearchListFragment()
            val bundle=Bundle()
            bundle.putString(Constant.SEARCH_WORD_KEY,key)
            searchListFragment.arguments=bundle
            return searchListFragment
        }
    }
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_search_list
    }
    override fun initView() {
        key=arguments?.getString(Constant.SEARCH_WORD_KEY) as String
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))
        homeAdapter.bindToRecyclerView(recyclerView)
        recyclerView.adapter=homeAdapter
        swipeRefreshLayout.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                pageNum=0
                getSearchList(key)
            }
        })
        homeAdapter.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getSearchList(key)
            }
        },recyclerView)
        homeAdapter.setOnItemClickListener(object:BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var intent = Intent(activity, ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_URL_KEY, homeAdapter.data.get(position).link)
                intent.putExtra(Constant.CONTENT_TITLE_KEY, homeAdapter.data.get(position).title)
                intent.putExtra(Constant.CONTENT_ID_KEY, homeAdapter.data.get(position).id)
                startActivity(intent)

            }
        })

        homeAdapter.setOnItemChildClickListener(object :BaseQuickAdapter.OnItemChildClickListener{
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (view?.id) {
                    R.id.iv_like -> {
                        addCancelCollect(position)
                    }
                }
            }
        })
    }

    override fun initData() {
        getSearchList(key)
    }

    private fun getSearchList(key: String) {
        RetrofitHelper.apiService.getSearchList(pageNum,key).applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<ArticleResponseBody>>{
            override fun onSuccess(t: HttpResult<ArticleResponseBody>) {
                if(t!=null&&t.data!=null&&t.data?.size?:0>0){
                    if (pageNum <= 0) {
                        homeAdapter.setNewData(t.data?.datas)
                     } else {
                        homeAdapter.addData(t.data?.datas?:ArrayList<ArticleResponseBody.DatasBean>())
                    }
                    if (t?.data?.curPage!! < t?.data?.pageCount!!) {
                        homeAdapter?.loadMoreComplete()
                        homeAdapter?.setEnableLoadMore(true)
                    } else {
                        homeAdapter?.loadMoreEnd(false)
                    }
                    CommonUtil.forceStopRecycleViewScroll(recyclerView)//防止recycleView惯性滑动
                }
                swipeRefreshLayout?.isRefreshing=false
            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false
            }
        }))

    }

    /***
     * 取消加入收藏
     */
    fun addCancelCollect(position: Int) {
        var item = homeAdapter.data.get(position)
        if (item.collect) {
            RetrofitHelper.apiService.cancelCollectArticle(item.id).applySchedulers()
                .subscribe(OObserver(object : ApiCallBack<HttpResult<Any>> {
                    override fun onSuccess(t: HttpResult<Any>) {
                        if (t != null) {
                            showToast("取消收藏成功")
                            item.collect = false
                            homeAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailture(t: Throwable) {
                    }
                }))
        } else {
            RetrofitHelper.apiService.addCollectArticle(item.id).applySchedulers()
                .subscribe(OObserver(object : ApiCallBack<HttpResult<Any>> {
                    override fun onSuccess(t: HttpResult<Any>) {
                        if (t != null) {
                            showToast("加入收藏成功")
                            item.collect = true
                            homeAdapter.notifyDataSetChanged()
                        }
                    }
                    override fun onFailture(t: Throwable) {
                    }
                }))
        }
    }
}