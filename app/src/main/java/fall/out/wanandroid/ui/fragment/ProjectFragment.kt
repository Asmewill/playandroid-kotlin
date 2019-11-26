package fall.out.wanandroid.ui.fragment

import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.adapter.ProjectPagerAdapter
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.ColorEvent
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.ProjectTreeBean
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_project.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Owen on 2019/10/9
 */
class ProjectFragment:BaseFragment() {
    companion object{
        fun getInstance():ProjectFragment=ProjectFragment()
    }
    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_project
    }

    override fun initView() {
        refreshColor(ColorEvent())
    }
    override fun initData() {
        getProjectTab()
    }

    private fun getProjectTab() {
        RetrofitHelper.apiService.getProjectTree().applySchedulers().subscribe(OObserver(object :ApiCallBack<HttpResult<List<ProjectTreeBean>>>{
            override fun onSuccess(t: HttpResult<List<ProjectTreeBean>>) {
                if(t.data!=null&&t.data?.size!!>0){
                    viewPager?.adapter= ProjectPagerAdapter(t.data!!,childFragmentManager!!)
                    tabLayout?.setupWithViewPager(viewPager)
                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent){
        if(!SettingUtil.getIsNightMode()){
            tabLayout.setBackgroundColor(SettingUtil.getColor())
        }
    }
}