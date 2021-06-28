package fall.out.wanandroid.ui

import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.ScoreAdapter
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.event.ColorEvent
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.ScoreBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.activity_score.*
import kotlinx.android.synthetic.main.fragment_home.recyclerView
import kotlinx.android.synthetic.main.fragment_home.swipeRefreshLayout
import kotlinx.android.synthetic.main.toolbar.toolbar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Owen on 2019/11/4
 */
class ScoreActivity:BaseActivity() {
    private var pageNum: Int=1
    private val scoreAdapter by lazy{
        ScoreAdapter()
    }
    override fun attachLayoutRes(): Int {
         return  R.layout.activity_score
    }
    override fun initView() {
        toolbar?.setTitle("积分")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_score?.setText(""+intent.getIntExtra(Constant.COIN_COUNT,0).toString())
        recyclerView?.layoutManager=LinearLayoutManager(this)
        recyclerView?.adapter=scoreAdapter
        swipeRefreshLayout.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                pageNum=1
                getScoreList()
            }
        })

        scoreAdapter.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                    pageNum++
                getScoreList()
            }
        },recyclerView)
        refreshColor(ColorEvent())
    }

    override fun initData() {
        getScoreList()
    }

    private fun getScoreList() {
        RetrofitHelper.apiService.getScoreList(pageNum).applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<ScoreBean>>{
            override fun onSuccess(t: HttpResult<ScoreBean>) {
                if(t.data!=null){
                    if(pageNum<=1){
                        scoreAdapter.setNewData(t.data?.datas)
                    }else{
                        scoreAdapter.addData(t.data?.datas?:ArrayList())
                    }
                    if (t.data?.curPage!! < t.data?.pageCount!!) {
                        scoreAdapter.loadMoreComplete()
                        scoreAdapter.setEnableLoadMore(true)
                    } else {
                        scoreAdapter.loadMoreEnd(true)
                    }
                }
                swipeRefreshLayout?.isRefreshing=false
            }

            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false

            }

        }))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent){
        initColor()
        rl_content.setBackgroundColor(mThemeColor)

    }
}