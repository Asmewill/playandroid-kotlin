package fall.out.wanandroid.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.KnowledgeTreeBody

/**
 * Created by Owen on 2019/10/29
 */
class KnowledgeTreeAdapter :BaseQuickAdapter<KnowledgeTreeBody,BaseViewHolder>(R.layout.item_knowledge_tree_list) {

    override fun convert(helper: BaseViewHolder, item: KnowledgeTreeBody?) {
        val title_first=helper.getView<TextView>(R.id.title_first)
        val title_second=helper.getView<TextView>(R.id.title_second)
        val imageView=helper.getView<ImageView>(R.id.imageView)
        item?:return
        title_first.setText(item.name)
        var content=""
        item.children?.forEach {
            content+=it.name+"  "
        }
        title_second.setText(content)
    }
}