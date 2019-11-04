package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.KnowledgeTreeAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.KnowledgeTreeBody
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.KnowledgeActivity
import fall.out.wanandroid.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_know_tree.*

/**
 * Created by Owen on 2019/10/9
 */
class KnowledgeTreeFragment:BaseFragment() {
    private  val knowledgeTreeAdapter by lazy {
        KnowledgeTreeAdapter()
    }
    companion object{
        fun getInstance():KnowledgeTreeFragment=KnowledgeTreeFragment()
    }
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_know_tree
    }
    override fun initView() {
        recyclerView.layoutManager=LinearLayoutManager(activity,RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(SpaceItemDecoration(activity!!.applicationContext))
        recyclerView.adapter=knowledgeTreeAdapter
        swipeRefreshLayout.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
              getKnowledgeTree()
            }
        })
    }
    override fun initData() {
        getKnowledgeTree()
        knowledgeTreeAdapter.setOnItemClickListener(object: BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var intent= Intent(activity, KnowledgeActivity::class.java)
                intent.putExtra(Constant.CONTENT_TITLE_KEY,knowledgeTreeAdapter.data.get(position).name)
                intent.putExtra(Constant.ITEM_BEAN,knowledgeTreeAdapter.data.get(position))
                startActivity(intent)
            }
        })
    }

    fun getKnowledgeTree(){
        RetrofitHelper.apiService.getKnowledgeTree().applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<List<KnowledgeTreeBody>>>{
            override fun onSuccess(t: HttpResult<List<KnowledgeTreeBody>>) {
                if(t.data!=null&& t.data!!.size>0){
                     knowledgeTreeAdapter.setNewData(t.data)

                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))
        swipeRefreshLayout.isRefreshing=false
    }
}