package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.KnowledgeAdapter
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
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 * Created by Owen on 2019/10/29
 */
class KnowledgeFragment:BaseFragment() {
    private var cid: Int=0
    private var pageNum=0
    private val knowledgeAdapter by lazy {
        KnowledgeAdapter()
    }

    companion object{
        fun getInstance(cid:Int):KnowledgeFragment{
            val knowledgeFragment=KnowledgeFragment()
            val bundle=Bundle()
            bundle.putInt(Constant.CONTENT_CID_KEY,cid)
            knowledgeFragment.arguments=bundle
            return knowledgeFragment
        }
    }
    override fun attachLayoutRes(): Int {
         return R.layout.fragment_knowledge
    }

    override fun initView() {
        cid=arguments?.getInt(Constant.CONTENT_CID_KEY)?:0
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter=knowledgeAdapter
        recyclerView?.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))
        swipeRefreshLayout?.setOnRefreshListener {
             pageNum=0
             getKnowledgeList()
        }
        knowledgeAdapter?.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getKnowledgeList()
            }

        },recyclerView)
        knowledgeAdapter.setOnItemClickListener(object:BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                 val intent= Intent(activity,ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_ID_KEY,knowledgeAdapter.data.get(position).id)
                intent.putExtra(Constant.CONTENT_TITLE_KEY,knowledgeAdapter.data.get(position).title)
                intent.putExtra(Constant.CONTENT_URL_KEY,knowledgeAdapter.data.get(position).link)
                startActivity(intent)
            }
        })
        knowledgeAdapter.onItemChildClickListener=onMyChildItemClickListener
    }

    override fun initData() {
        getKnowledgeList()
    }

    fun getKnowledgeList(){
        RetrofitHelper.apiService.getKnowledgeList(pageNum,cid).applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<ArticleResponseBody>>{
            override fun onSuccess(t: HttpResult<ArticleResponseBody>) {
                if(t.data?.datas?.size!!>0){
                    if(pageNum<=0){
                        knowledgeAdapter.setNewData(t.data!!.datas)
                    }else{
                        knowledgeAdapter.addData(t.data!!.datas!!)
                    }
                    if(t.data?.curPage!!<t.data?.pageCount!!){
                        knowledgeAdapter.loadMoreComplete()
                        knowledgeAdapter.setEnableLoadMore(true)
                    }else{
                        knowledgeAdapter.loadMoreEnd(false)
                    }
                }
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
                R.id.iv_like -> {
                    addCancelCollect(position)
                }
            }
        }

        fun addCancelCollect(position: Int) {
            var item = knowledgeAdapter.data.get(position)
            if (item.collect) {
                RetrofitHelper.apiService.cancelCollectArticle(item.id).applySchedulers()
                    .subscribe(OObserver(object : ApiCallBack<HttpResult<Any>> {
                        override fun onSuccess(t: HttpResult<Any>) {
                            if (t != null) {
                                showToast("取消收藏成功")
                                item.collect = false
                                knowledgeAdapter.notifyDataSetChanged()
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
                                knowledgeAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailture(t: Throwable) {
                        }
                    }))
            }
        }
    }




}