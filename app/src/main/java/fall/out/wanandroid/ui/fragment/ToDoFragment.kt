package fall.out.wanandroid.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CommonUtil
import fall.out.wanandroid.adapter.ToDoAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.ToDoBean
import fall.out.wanandroid.bean.TodoTypeBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.CommonActivity
import fall.out.wanandroid.widget.SwipeItemLayout
import kotlinx.android.synthetic.main.fragment_todo.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Owen on 2019/11/19
 */
class ToDoFragment:BaseFragment(){
    private var pageNum=1
    private var playType=""
    private var type=0
    private val todoAdapter by lazy {
        ToDoAdapter()
    }
    companion object{
        fun getInstance(planType:String):ToDoFragment{
            val toDoFragment=ToDoFragment()
            var bundle=Bundle()
            bundle.putString(Constant.PLANE_TYPE,planType)
            toDoFragment.arguments=bundle
            return toDoFragment
        }
    }
    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_todo
    }

    override fun initView() {
        playType=arguments?.getString(Constant.PLANE_TYPE).toString()
        todoAdapter.planType=playType
        recyclerView?.layoutManager=LinearLayoutManager(activity)
        recyclerView?.adapter=todoAdapter
        todoAdapter.bindToRecyclerView(recyclerView)
        recyclerView?.addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(activity))
        swipeRefreshLayout?.setOnRefreshListener(object:SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                pageNum=1
                getTodoList()
            }
        })
        todoAdapter.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getTodoList()
            }
        },recyclerView)
        todoAdapter.setOnItemClickListener(object :BaseQuickAdapter.OnItemClickListener{
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if(playType.equals("listnotdo")){
                    startActivity(
                        Intent(activity, CommonActivity::class.java)
                            .putExtra(Constant.TYPE_KEY,Constant.Type.EDIT_TODO_TYPE_KEY)
                    )
                }else{
                    startActivity(
                        Intent(activity, CommonActivity::class.java)
                            .putExtra(Constant.TYPE_KEY,Constant.Type.SEE_TODO_TYPE_KEY)
                    )
                }
            }
        })
    }

    override fun initData() {
        getTodoList()
    }

    private fun getTodoList() {
        RetrofitHelper.apiService.getTodoList(playType,type,pageNum).applySchedulers().subscribe(OObserver(object :
            ApiCallBack<HttpResult<ToDoBean>> {
            override fun onSuccess(t: HttpResult<ToDoBean>) {
                if(t!=null&&t.data!=null&&t.data?.size?:0>0){
                    if (pageNum <= 1) {
                        todoAdapter.setNewData(t.data?.datas)
                    } else {
                        todoAdapter.addData(t.data?.datas?:ArrayList<ToDoBean.DatasBean>())
                    }
                    if (t?.data?.curPage!! < t?.data?.pageCount!!) {
                        todoAdapter?.loadMoreComplete()
                        todoAdapter?.setEnableLoadMore(true)
                    } else {
                        todoAdapter?.loadMoreEnd(false)
                    }
                }
                recyclerView?.let {
                    CommonUtil.forceStopRecycleViewScroll(it)//防止recycleView惯性滑动
                }
                swipeRefreshLayout?.isRefreshing=false
            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false
            }
        }))
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if(isVisibleToUser){
            pageNum=0
            getTodoList()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }
    @Subscribe
    fun onEvent(item:TodoTypeBean){
        if(item.playType.equals("listnotdo")&&playType.equals("listnotdo")||item.playType.equals("listdone")&&playType.equals("listdone")){
            type=item.type
            pageNum=0
            getTodoList()
        }
    }
}