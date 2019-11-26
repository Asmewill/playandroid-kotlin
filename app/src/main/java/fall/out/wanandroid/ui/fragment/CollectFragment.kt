package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.CollectAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.CollectArticle
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.ContentActivity
import fall.out.wanandroid.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Owen on 2019/11/5
 */
class CollectFragment:BaseFragment() {
    private val collectAdapter by lazy {
        CollectAdapter()
    }
    private var pageNum: Int=0

    companion object{
         fun getIntance():CollectFragment{
            return CollectFragment()
        }
    }

    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_collect
    }

    override fun initView() {
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter=collectAdapter
        recyclerView?.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))
        collectAdapter?.setOnItemClickListener(object:BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                startActivity(Intent(activity,ContentActivity::class.java)
                    .putExtra(Constant.CONTENT_TITLE_KEY,collectAdapter.data.get(position).title)
                    .putExtra(Constant.CONTENT_URL_KEY,collectAdapter.data.get(position).link)
                        .putExtra(Constant.CONTENT_ID_KEY,collectAdapter.data.get(position).id)
                )
            }
        })
        swipeRefreshLayout?.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                pageNum=0
                getCollectList()
            }
        })
        collectAdapter.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getCollectList()

            }
        },recyclerView)

    }

    override fun initData() {
        getCollectList()

    }

    private fun getCollectList() {
        RetrofitHelper.apiService.getCollectList(pageNum).applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<CollectArticle>>{
            override fun onSuccess(t: HttpResult<CollectArticle>) {
                swipeRefreshLayout?.isRefreshing=false
                if(t!=null&&t.data!=null&&t.data?.datas!=null){
                    if(pageNum<=0){
                        collectAdapter.setNewData(t.data?.datas?:null)
                    }else{
                       collectAdapter.addData(t.data?.datas!!)
                    }
                    if (t.data?.curPage!! < t.data?.pageCount!!) {
                        collectAdapter.loadMoreComplete()
                        collectAdapter.setEnableLoadMore(true)
                    } else {
                        collectAdapter.loadMoreEnd(false)
                    }
                }
            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false
            }
        }))
    }
}