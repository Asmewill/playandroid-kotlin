package com.example.oapp.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.oapp.R
import com.example.oapp.adapter.HomeAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.Banner
import com.example.oapp.bean.HomeData
import com.example.oapp.bean.HttpResult
import com.example.oapp.constant.Constant
import com.example.oapp.event.LoginEvent
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import com.example.oapp.ui.ContentActivity
import com.example.oapp.utils.ImageLoader
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import java.lang.System.`in`
import java.lang.System.load

/**
 * Created by jsxiaoshui on 2021/6/25
 */
class HomeFragment:BaseFragment() {
    private var topList=ArrayList<HomeData.DatasBean>()
    private  var bannerList=ArrayList<Banner>()
    private var headerView:View?=null
    private var banner:BGABanner?=null
    private var pageNum:Int=0
     private val homeAdapter by lazy{
         HomeAdapter()
     }

    override fun attachlayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        recyclerView?.let {
            it.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            it.adapter=homeAdapter
        }
        //headerView
        headerView=layoutInflater.inflate(R.layout.item_home_banner,null)
        banner=headerView?.findViewById<BGABanner>(R.id.banner)
        homeAdapter?.run {
            addHeaderView(headerView)
            this.setOnItemClickListener { baseQuickAdapter, view, i ->
                val intent=Intent(activity,ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_TITLE,topList.get(i).title)
                intent.putExtra(Constant.CONTENT_URL,topList.get(i).link)
                intent.putExtra(Constant.CONTENT_ID,topList.get(i).id)
                startActivity(intent)
            }
        }
        banner?.setDelegate(object:BGABanner.Delegate<ImageView,String>{
            override fun onBannerItemClick(p0: BGABanner?, p1: ImageView?, p2: String?, p3: Int) {
                val intent=Intent(activity,ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_TITLE,bannerList.get(p3).title)
                intent.putExtra(Constant.CONTENT_URL,bannerList.get(p3).url)
                intent.putExtra(Constant.CONTENT_ID,bannerList.get(p3).id)
                startActivity(intent)
            }
        })

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener {
            pageNum=0
            getBanners()
        }
        //上拉加载
        homeAdapter.setOnLoadMoreListener({
            pageNum++
            getTopList()
        },recyclerView)

    }

    override fun initData() {
        getBanners()
    }
    //只有interface才能使用object
   fun getBanners(){
       HttpRetrofit.apiService.getBanners().applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<List<Banner>>>{
           override fun onSuccess(t: HttpResult<List<Banner>>) {
               bannerList= t.data as ArrayList<Banner>
               var titleList=ArrayList<String>()
               var urlList=ArrayList<String>()
               t?.data?.forEach {
                   titleList.add(it.title+"")
                   urlList.add(it.imagePath+"")
               }
               banner?.let {
                   it.setAutoPlayAble(urlList.size>1)
                   it.setData(urlList,titleList)
                  it.setAdapter(object:BGABanner.Adapter<ImageView,String>{
                     override fun fillBannerItem(
                         p0: BGABanner?,
                         p1: ImageView?,
                         p2: String?,
                         p3: Int
                     ) {
                         //loadIv的三个参数都不能为null,所以....
                         activity?.let { it1 -> p1?.let { it2 -> p2?.let { it3 ->
                             ImageLoader.loadIv(it1,
                                 it3, it2)
                         } } }
                     }
                 })
               }
           }
           override fun onFailture(t: Throwable) {


           }
       }))

        getTopList()

       swipeRefreshLayout.isRefreshing=false
    }

    private fun getTopList() {
        if(pageNum==0){
            HttpRetrofit.apiService.getTopArticles().applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<ArrayList<HomeData.DatasBean>>>{
                override fun onSuccess(t: HttpResult<ArrayList<HomeData.DatasBean>>) {
                    t?.data?.let {
                        topList=it
                        topList?.forEach {
                            it.isTop=true
                        }
                    }
                    requestList(pageNum)
                }
                override fun onFailture(t: Throwable) {

                }
            }))
        }else{
            requestList(pageNum)
        }
    }

    private fun requestList(pageNum:Int) {
        HttpRetrofit.apiService.getArticles(pageNum).applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<HomeData>>{
            override fun onSuccess(t: HttpResult<HomeData>) {
                t.data?.datas?.let {
                    if(pageNum==0){
                        if(topList!=null&&topList.size>0){
                            topList.addAll(it)
                            homeAdapter.setNewData(topList)
                        }
                    }else{
                        homeAdapter.addData(it)
                    }
                    if(t.data!!.curPage< t.data!!.pageCount){
                        homeAdapter.loadMoreComplete()
                        homeAdapter.setEnableLoadMore(true)
                    }else{
                        homeAdapter.loadMoreEnd(false)
                    }
                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))
    }



}