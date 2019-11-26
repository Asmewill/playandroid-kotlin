package fall.out.wanandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.TodoTypeBean

/**
 * Created by Owen on 2019/11/21
 */
class TodoPopupAdapter:BaseQuickAdapter<TodoTypeBean,BaseViewHolder>(R.layout.item_todo_popup_list) {
    override fun convert(helper: BaseViewHolder, item: TodoTypeBean) {
        val tv_popup=helper.getView<TextView>(R.id.tv_popup)
        tv_popup.setText(item?.name)
        if(item.isSelect){
            tv_popup.setTextColor(mContext.resources.getColor(R.color.colorAccent))
        }else{
            tv_popup.setTextColor(mContext.resources.getColor(R.color.common_color))
        }

    }
}