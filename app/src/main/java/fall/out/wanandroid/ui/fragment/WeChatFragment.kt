package fall.out.wanandroid.ui.fragment

import fall.out.wanandroid.R
import fall.out.wanandroid.adapter.WeChatPagerAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.WXChapterBean
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_wechat.*

/**
 * Created by Owen on 2019/10/9
 */
class WeChatFragment:BaseFragment() {
    private var dataList=ArrayList<WXChapterBean>()
    private val weChatPagerAdapter by lazy {
        WeChatPagerAdapter(dataList,childFragmentManager!!)
    }

    companion object{
        fun getInstance():WeChatFragment=WeChatFragment()
    }
    override fun attachLayoutRes(): Int {
         return  R.layout.fragment_wechat
    }
    override fun initView() {



    }
    override fun initData() {
        getWeChatTab()
    }

    fun getWeChatTab(){
        RetrofitHelper.apiService.getWxChapters().applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<List<WXChapterBean>>>{
            override fun onSuccess(t: HttpResult<List<WXChapterBean>>) {
                if(t.data!=null&& t.data!!.size>0){
                    dataList=t.data as ArrayList<WXChapterBean>
                    viewPager.adapter= weChatPagerAdapter
                    weChatPagerAdapter.notifyDataSetChanged()
                    tabLayout.setupWithViewPager(viewPager)

                }

            }
            override fun onFailture(t: Throwable) {

            }
        }))

    }
}