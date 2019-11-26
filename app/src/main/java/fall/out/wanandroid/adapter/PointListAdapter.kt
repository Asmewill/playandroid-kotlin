package fall.out.wanandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.PointBean

/**
 * Created by Owen on 2019/11/15
 */
class PointListAdapter:BaseQuickAdapter<PointBean.DatasBean,BaseViewHolder>(R.layout.item_rank_list) {

    override fun convert(helper: BaseViewHolder, item: PointBean.DatasBean?) {
        if(helper==null||item==null){
            return
        }
        val tv_ranking=helper.getView<TextView>(R.id.tv_ranking)
        val tv_username=helper.getView<TextView>(R.id.tv_username)
        val tv_score=helper.getView<TextView>(R.id.tv_score)
        tv_ranking.setText((helper.layoutPosition+1).toString())
        tv_username.setText(item.username)
        tv_score.setText(item.coinCount.toString())


    }
}