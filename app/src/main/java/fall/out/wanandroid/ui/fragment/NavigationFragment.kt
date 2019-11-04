package fall.out.wanandroid.ui.fragment


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.NavigationAdapter
import fall.out.wanandroid.adapter.NavigationTabAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.NavigationBean
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by Owen on 2019/10/9
 */
class NavigationFragment:BaseFragment() {
    private var bClickTab: Boolean = false
    private var currentIndex=0
    private var bScroll=false
    private val navigationAdapter by lazy {
        NavigationAdapter()
    }
    private val layoutManager by lazy {
        LinearLayoutManager(activity)
    }

    companion object{
        fun getInstance():NavigationFragment=NavigationFragment()
    }
    override fun attachLayoutRes(): Int {
         return  R.layout.fragment_navigation
    }

    override fun initView() {
        recyclerView?.layoutManager=layoutManager
        recyclerView?.adapter=navigationAdapter
        navigation_tab_layout.addOnTabSelectedListener(object:VerticalTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabView?, position: Int) {
                bClickTab = true
                currentIndex=position
                recyclerView.stopScroll()
                smoothScrollToPosition(position)
            }
            override fun onTabReselected(tab: TabView?, position: Int) {

            }
        })
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(bScroll&&(newState==RecyclerView.SCROLL_STATE_IDLE)){
                    scrollRecyclerView()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(bScroll){
                    scrollRecyclerView()
                }
            }

        })

    }
    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - layoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < recyclerView!!.childCount) {
            val top: Int = recyclerView.getChildAt(indexDistance).top
            recyclerView.smoothScrollBy(0, top)
        }
    }
    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int = layoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = layoutManager.findLastVisibleItemPosition()
          when{
              position<=firstPosition->{
                    recyclerView.smoothScrollToPosition(position)
              }
              position<=lastPosition->{
                  val top:Int=recyclerView.getChildAt(position-firstPosition).top
                  recyclerView.smoothScrollBy(0,top)
              }
              else->{
                     recyclerView.smoothScrollToPosition(position)
                     bScroll = true
              }
          }

    }
    override fun initData() {
        getNavigationData()
    }
    fun getNavigationData(){
        RetrofitHelper.apiService.getNavigationList().applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<List<NavigationBean>>>{
            override fun onSuccess(t: HttpResult<List<NavigationBean>>) {
                if(t.data?.size!!>0){
                    navigation_tab_layout?.setTabAdapter(NavigationTabAdapter(activity!!.applicationContext,t.data!!))
                    navigationAdapter?.setNewData(t.data)
                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))
    }
}