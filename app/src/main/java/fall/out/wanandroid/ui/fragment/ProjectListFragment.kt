package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CommonUtil
import fall.out.wanandroid.adapter.ProjectListAdapter
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
 * Created by Owen on 2019/10/30
 */
class ProjectListFragment:BaseFragment() {

    private var pageNum: Int=1
    private var cid: Int?=0
    private val projectListAdapter  by lazy {
        ProjectListAdapter()
    }
    companion object{
        fun getInstance(cid:Int):ProjectListFragment{
            val projectFragment=ProjectListFragment()
            var bundle=Bundle()
            bundle.putInt(Constant.CONTENT_CID_KEY,cid)
            projectFragment.arguments=bundle
            return projectFragment
        }
    }
    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_project_list
    }

    override fun initView() {
        cid=arguments?.getInt(Constant.CONTENT_CID_KEY)
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter=projectListAdapter
        recyclerView.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))

    }

    override fun initData() {
        getProjectList()
        swipeRefreshLayout?.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                pageNum=1
                getProjectList()
            }
        })
        projectListAdapter?.setOnLoadMoreListener(object :BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getProjectList()
            }
        },recyclerView)
        projectListAdapter.setOnItemClickListener(object:BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                val intent=Intent(activity,ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_URL_KEY,projectListAdapter.data.get(position).link)
                intent.putExtra(Constant.CONTENT_TITLE_KEY,projectListAdapter.data.get(position).title)
                intent.putExtra(Constant.CONTENT_ID_KEY,projectListAdapter.data.get(position).id)
                startActivity(intent)
            }
        })
        projectListAdapter.setOnItemChildClickListener(onMyChildItemClickListener)

    }

    fun getProjectList(){
        RetrofitHelper.apiService.getProjectList(pageNum,cid?:1).applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<ArticleResponseBody>>{
            override fun onSuccess(t: HttpResult<ArticleResponseBody>) {
                if(t?.data?.datas?.size!!>0){
                    if(pageNum<=1){
                        projectListAdapter.setNewData(t.data?.datas)
                    }else{
                        projectListAdapter.addData(t?.data?.datas!!)
                    }
                    if(t.data?.curPage!!<t.data?.pageCount!!-1){
                        projectListAdapter.loadMoreComplete()
                        projectListAdapter.setEnableLoadMore(true)
                    }else{
                        projectListAdapter.loadMoreEnd(false)
                    }
                }
                CommonUtil.forceStopRecycleViewScroll(recyclerView)
                swipeRefreshLayout?.isRefreshing=false
            }

            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false
            }

        }))

    }

    private val onMyChildItemClickListener = object : BaseQuickAdapter.OnItemChildClickListener {
        override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            when (view?.id) {
                R.id.item_project_list_like_iv -> {
                    addCancelCollect(position)
                }
            }
        }

        fun addCancelCollect(position: Int) {
            var item = projectListAdapter.data.get(position)
            if (item.collect) {
                RetrofitHelper.apiService.cancelCollectArticle(item.id).applySchedulers()
                    .subscribe(OObserver(object : ApiCallBack<HttpResult<Any>> {
                        override fun onSuccess(t: HttpResult<Any>) {
                            if (t != null) {
                                showToast("取消收藏成功")
                                item.collect = false
                                projectListAdapter.notifyDataSetChanged()
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
                                projectListAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailture(t: Throwable) {
                        }
                    }))
            }
        }
    }
}