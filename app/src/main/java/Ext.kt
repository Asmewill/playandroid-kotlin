import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import fall.out.wanandroid.R
import fall.out.wanandroid.widget.CustomToast

/**
 * Created by Owen on 2019/10/5
 */


fun Fragment.showToast(content: String){
    CustomToast(this?.activity?.applicationContext,content).show()

}
fun Context.showToast(content:String){
    CustomToast(this,content).show()
}

fun Activity.showSnackMsg(msg:String){
   val snackbar=Snackbar.make(this.window.decorView,msg,Snackbar.LENGTH_SHORT)
   val view=snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this,R.color.white))
    snackbar.show()
}