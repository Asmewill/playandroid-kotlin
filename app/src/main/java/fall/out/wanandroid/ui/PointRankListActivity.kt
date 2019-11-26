package fall.out.wanandroid.ui

import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.PointListAdapter
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.ColorEvent
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.PointBean
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.activity_score.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Owen on 2019/11/9
 */
class PointRankListActivity : BaseActivity() {
    private var pageNum: Int = 1
    private val pointListAdapter by lazy {
        PointListAdapter()
    }
    override fun attachLayoutRes(): Int {
        return R.layout.activity_point_rank_list
    }
    override fun initView() {
        toolbar.title = "积分排行榜"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = pointListAdapter
        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNum = 1
                getPointList()
            }
        })
        pointListAdapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                pageNum++
                getPointList()
            }
        }, recyclerView)

    }

    override fun initData() {
        getPointList()
    }
    private fun getPointList() {
        RetrofitHelper.apiService.getPointList(pageNum).applySchedulers()
            .subscribe(OObserver(object : ApiCallBack<HttpResult<PointBean>> {
                override fun onSuccess(t: HttpResult<PointBean>) {
                    if (t != null && t.data != null && t.data?.datas != null && t.data?.datas?.size ?: 0 > 0) {
                        if(pageNum<=1){
                            pointListAdapter.setNewData(t?.data?.datas)
                        }else{
                            pointListAdapter.addData(t?.data?.datas?:ArrayList<PointBean.DatasBean>())
                        }
                        if (t?.data?.curPage!! < t?.data?.pageCount!!) {
                            pointListAdapter?.loadMoreComplete()
                            pointListAdapter?.setEnableLoadMore(true)
                        } else {
                            pointListAdapter?.loadMoreEnd(false)
                        }
                    }
                    swipeRefreshLayout?.isRefreshing = false
                }
                override fun onFailture(t: Throwable) {
                    swipeRefreshLayout?.isRefreshing = false
                }
            }))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }



}