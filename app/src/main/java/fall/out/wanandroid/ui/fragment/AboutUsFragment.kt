package fall.out.wanandroid.ui.fragment

import android.text.Html
import android.text.method.LinkMovementMethod
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Created by Owen on 2019/11/16
 */
class AboutUsFragment :BaseFragment() {
    private var versionStr: String=""

    companion object{
        fun getInstance():AboutUsFragment{
            val fragment=AboutUsFragment()
           return fragment
        }
    }

    override fun attachLayoutRes(): Int {
         return  R.layout.fragment_about
    }

    override fun initView() {
        var text=Html.fromHtml(getString(R.string.about_content))
        about_content?.setText(text)
        about_content?.movementMethod=LinkMovementMethod.getInstance()//响应超链接
        versionStr=getString(R.string.app_name)+" V"+activity?.packageManager?.getPackageInfo(activity?.packageName,0)?.versionName
        about_version?.text=versionStr
    }

    override fun initData() {

    }
}