package fall.out.wanandroid.base

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.KeyBoardUtil
import fall.out.wanandroid.Utils.Preference
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.Utils.StatusBarUtil
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.event.NetworkChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Owen on 2019/10/4
 */
abstract class BaseActivity :AppCompatActivity(){
    private lateinit var mLayoutParams: WindowManager.LayoutParams
    protected lateinit var mWindowManager: WindowManager
    lateinit  var mTipview:View
    protected  var mThemeColor:Int=SettingUtil.getColor()

    protected var  isLogin:Boolean by Preference(Constant.LOGIN_KEY,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        EventBus.getDefault().register(this)
        initTipView()//无网络连接UI
        initView()
        initData()
    }



    abstract fun attachLayoutRes(): Int
    abstract fun initView()
    abstract fun initData()


    private fun initTipView() {
        mTipview=layoutInflater.inflate(R.layout.layout_network_tip,null)
        mWindowManager=getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams=WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity=Gravity.TOP
        mLayoutParams.x=0
        mLayoutParams.y=0
        mLayoutParams.windowAnimations=R.style.anim_float_view
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

    /***
     * 只有Open的方法才可以被重写
     */
    open fun initColor() {
        mThemeColor=if(!SettingUtil.getIsNightMode()){
            SettingUtil.getColor()
        }else{
          resources.getColor(R.color.colorPrimary)
        }
         StatusBarUtil.setColor(this,mThemeColor,0)
        if(this.supportActionBar!=null){
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            if(SettingUtil.getNavBar()){
                window.navigationBarColor=mThemeColor
            }else{
               window.navigationBarColor=Color.BLACK
            }
        }
    }

    /***
     * 点击空白区域让键盘可以自动落下
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.action==MotionEvent.ACTION_UP){
             var foucusView=currentFocus
             if(KeyBoardUtil.isHideKeyboard(foucusView,ev)){
                 KeyBoardUtil.hideKeyBoard(this,foucusView)
             }
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun baseEvent(event: NetworkChangeEvent) {

    }

}