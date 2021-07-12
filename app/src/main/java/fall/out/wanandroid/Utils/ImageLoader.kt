package fall.out.wanandroid.Utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Owen on 2019/10/28
 */
object ImageLoader {

    fun load(context:Context?,url :String ?,iv:ImageView?){
            iv?.apply {
                Glide.with(context!!).clear(iv)
                val option=RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(context!!).load(url).transition(DrawableTransitionOptions().crossFade()).apply(option).into(iv)
            }

    }
}