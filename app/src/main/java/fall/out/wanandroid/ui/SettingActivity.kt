package fall.out.wanandroid.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import com.afollestad.materialdialogs.color.ColorChooserDialog
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CacheDataUtil
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.ColorEvent
import fall.out.wanandroid.ext.showSnackMsg
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Owen on 2019/11/4
 */
class SettingActivity:BaseActivity(),View.OnClickListener,ColorChooserDialog.ColorCallback{

    override fun attachLayoutRes(): Int {
        return R.layout.activity_setting
    }
    override fun initView() {
        toolbar.title="设置"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cb_nophoto.isChecked=SettingUtil.getIsNoPhotoMode()
        cb_showTop.isChecked=SettingUtil.getIsShowTopArticle()
        cb_nav_color.isChecked=SettingUtil.getNavBar()
        if(!SettingUtil.getIsNightMode()){
            iv_theme_color.setBackgroundColor(SettingUtil.getColor())
        }else{
            iv_theme_color.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }
        iv_theme_color.setBackgroundColor(mThemeColor)
        tv_cache_size.setText(CacheDataUtil.getTotalCacheSize(this))
        cb_nophoto.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                SettingUtil.putNoPhotoMode(isChecked)
            }
        })

        cb_showTop.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                SettingUtil.putShowTopArticle(isChecked)
            }
        })

        cb_nav_color.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                SettingUtil.setNavBar(isChecked)
                initColor()
            }
        })
        ll_clear_cache.setOnClickListener(this)
        ll_update_log.setOnClickListener(this)
        ll_origin_code.setOnClickListener(this)
        ll_copyright.setOnClickListener(this)
        ll_version.setOnClickListener(this)
        ll_theme_color.setOnClickListener(this)


    }

    override fun initData() {

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_clear_cache->{
                CacheDataUtil.clearAllCache(this)
                showSnackMsg(getString(R.string.clear_cache_successfully))
                tv_cache_size.setText(CacheDataUtil.getTotalCacheSize(this))
            }
            R.id.ll_update_log->{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))))
            }
            R.id.ll_origin_code->{
                startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.source_code_url))))
            }
            R.id.ll_copyright->{
                AlertDialog.Builder(this).setTitle("版权声明").setMessage(R.string.copyright_content).setCancelable(true).show()
            }
            R.id.ll_version->{
                AlertDialog.Builder(this).setTitle("版本信息").setMessage("当前版本为 PlayAndroid 1.1.2").setCancelable(true).show()
            }
            R.id.ll_theme_color->{
                ColorChooserDialog.Builder(this,R.string.choose_theme_color).show()

            }
        }
    }
    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
        SettingUtil.setColor(selectedColor)
        initColor()
        iv_theme_color.setBackgroundColor(selectedColor)
        EventBus.getDefault().post(ColorEvent(true,selectedColor))

    }

    override fun onColorChooserDismissed(dialog: ColorChooserDialog) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
               finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}