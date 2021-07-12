package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.ImageLoader
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.adapter.HomeAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.ArticleResponseBody
import fall.out.wanandroid.bean.Banner
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.ContentActivity
import fall.out.wanandroid.widget.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Owen on 2019/10/9
 */
class HomeFragment : BaseFragment() {
    private var pageNum: Int = 0
    private var topList = mutableListOf<ArticleResponseBody.DatasBean>()
    private lateinit var bannerDatas: ArrayList<Banner>
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
    private val homeAdapter by lazy {
        HomeAdapter()
    }
    private var bannerView: View? = null

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        recyclerView.run {
            this.layoutManager = linearLayoutManager
            this.adapter = homeAdapter
        }
        recyclerView.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))
        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        bannerView?.findViewById<BGABanner>(R.id.banner)?.run {
            this.setDelegate(object : BGABanner.Delegate<ImageView, String> {
                override fun onBannerItemClick(
                    banner: BGABanner?,
                    itemView: ImageView?,
                    model: String?,
                    position: Int) {
                    startActivity(Intent(activity,ContentActivity::class.java)
                        .putExtra(Constant.CONTENT_TITLE_KEY,bannerDatas.get(position).title)
                        .putExtra(Constant.CONTENT_URL_KEY,bannerDatas.get(position).url)
                            .putExtra(Constant.CONTENT_ID_KEY,bannerDatas.get(position).id)
                    )

                }
            })
        }
        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            addHeaderView(bannerView)
            onItemClickListener = onMyItemClickListener
            onItemChildClickListener = onMyChildItemClickListener
        }
        swipeRefreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                pageNum = 0
                requestBanner()
            }
        })
        homeAdapter.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                pageNum++
                requestList(pageNum)

            }
        }, recyclerView)

    }

    override fun initData() {
        requestBanner()
    }

    /***
     * 头部banner
     */
     fun requestBanner() {
        RetrofitHelper.apiService.getBanners().applySchedulers().subscribe(OObserver(object :
            ApiCallBack<HttpResult<List<Banner>>> {
            override fun onSuccess(t: HttpResult<List<Banner>>) {
                bannerDatas = t.data as ArrayList<Banner>
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                Observable.fromIterable(bannerDatas).subscribe {
                    it.imagePath?.let { it1 -> bannerFeedList.add(it1) }
                    it.title?.let { it1 -> bannerTitleList.add(it1) }
                }
                bannerView?.findViewById<BGABanner>(R.id.banner)?.run {
                    setAutoPlayAble(bannerFeedList.size > 1)
                    setData(bannerFeedList, bannerTitleList)
                    this.setAdapter(object : BGABanner.Adapter<ImageView, String> {
                        override fun fillBannerItem(
                            banner: BGABanner?,
                            itemView: ImageView?,
                            model: String?,
                            position: Int
                        ) {
                            ImageLoader.load(activity, model, itemView)
                        }
                    })
                }
            }

            override fun onFailture(t: Throwable) {

            }
        }))
        requestTopList(pageNum)
        swipeRefreshLayout.isRefreshing = false
    }

    /****
     * 文章列表
     */
    fun requestTopList(pageNum: Int) {
        if (pageNum == 0) {
            RetrofitHelper.apiService.getTopArticles().applySchedulers()
                .subscribe(OObserver(object : ApiCallBack<HttpResult<MutableList<ArticleResponseBody.DatasBean>>> {
                    override fun onSuccess(t: HttpResult<MutableList<ArticleResponseBody.DatasBean>>) {
                        if (t.data != null) {
                            topList = t.data!!
                            requestList(pageNum)
                        }
                    }

                    override fun onFailture(t: Throwable) {

                    }
                }))
        } else {
            requestList(pageNum)
        }
    }

    /***
     * 置顶文章
     */
    fun requestList(pageNum: Int) {

        RetrofitHelper.apiService.getArticles(pageNum).applySchedulers()
            .subscribe(OObserver(object : ApiCallBack<HttpResult<ArticleResponseBody>> {
                override fun onSuccess(t: HttpResult<ArticleResponseBody>) {
                    t?.data?.datas?.let {
                        if (pageNum <= 0) {
                            if (topList != null && topList.size > 0) {
                                Observable.fromIterable(topList).subscribe {
                                    it.isTop = true
                                }

                                if(!SettingUtil.getIsShowTopArticle()){
                                    topList.clear()
                                }
                                topList.addAll(it)
                                homeAdapter.setNewData(topList)
                            }
                        } else {
                            homeAdapter.addData(it)
                        }
                        if (t?.data?.curPage!! < t?.data?.pageCount!!) {
                            homeAdapter?.loadMoreComplete()
                            homeAdapter?.setEnableLoadMore(true)
                        } else {
                            homeAdapter?.loadMoreEnd(false)
                        }
                    }
                }
                override fun onFailture(t: Throwable) {

                }
            }))
    }


    /***
     * interface 可以直接实例化成参数
     */
    private val onMyItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            //showToast(homeAdapter.data.get(position).link.toString())
            var intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra(Constant.CONTENT_URL_KEY, homeAdapter.data.get(position).link)
            intent.putExtra(Constant.CONTENT_TITLE_KEY, homeAdapter.data.get(position).title)
            intent.putExtra(Constant.CONTENT_ID_KEY, homeAdapter.data.get(position).id)
            startActivity(intent)
        }
    }
    private val onMyChildItemClickListener = object : BaseQuickAdapter.OnItemChildClickListener {
        override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            when (view?.id) {
                R.id.iv_like -> {
                    addCancelCollect(position)
                }
            }
        }

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

}