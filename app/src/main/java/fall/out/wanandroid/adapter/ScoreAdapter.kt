package fall.out.wanandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.ScoreBean

/**
 * Created by Owen on 2019/11/5
 */
class ScoreAdapter:BaseQuickAdapter<ScoreBean.DatasBean,BaseViewHolder>(R.layout.item_socre_list){

    override fun convert(helper: BaseViewHolder, item: ScoreBean.DatasBean?) {
        val tv_reason=helper.getView<TextView>(R.id.tv_reason)
        val tv_desc=helper.getView<TextView>(R.id.tv_desc)
        val tv_score=helper.getView<TextView>(R.id.tv_score)
        item?:return
        tv_reason.setText(item.reason)
        tv_desc.setText(item.desc)
        tv_score.setText("+"+item.coinCount)
    }
}