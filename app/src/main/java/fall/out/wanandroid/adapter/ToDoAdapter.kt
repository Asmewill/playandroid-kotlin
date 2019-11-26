package fall.out.wanandroid.adapter

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.ToDoBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.ui.CommonActivity
import fall.out.wanandroid.widget.TiltTextView

/**
 * Created by Owen on 2019/11/19
 */
class ToDoAdapter:BaseQuickAdapter<ToDoBean.DatasBean,BaseViewHolder>(R.layout.item_todo_list) {
     var planType=""
    override fun convert(helper: BaseViewHolder, item: ToDoBean.DatasBean?) {
        if(helper==null||item==null){
           return
        }
        val tv_tilt=helper.getView<TiltTextView>(R.id.tv_tilt)
        val tv_header=helper.getView<TextView>(R.id.tv_header)
        val tv_todo_title=helper.getView<TextView>(R.id.tv_todo_title)
        val tv_todo_desc=helper.getView<TextView>(R.id.tv_todo_desc)
        val btn_done=helper.getView<Button>(R.id.btn_done)
        val btn_delete=helper.getView<Button>(R.id.btn_delete)
        val item_todo_content=helper.getView<RelativeLayout>(R.id.item_todo_content)
        if(item.priority==0){
            tv_tilt.visibility= View.GONE
        }else{
            tv_tilt.visibility=View.VISIBLE
        }
        tv_todo_title.setText(item.title)
        tv_todo_desc.setText(item.content)
        tv_header.setText(item.dateStr)
        if(item.status==0){
            btn_done.text=mContext.getString(R.string.mark_done)
        }else if(item.status==1){
            btn_done.text=mContext.getString(R.string.restore)
        }

        btn_done.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(item.status==0){//标记会已读
                    RetrofitHelper.apiService.markOrDelete("done",item.id,1).applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<ToDoBean.DatasBean>>{
                        override fun onSuccess(t: HttpResult<ToDoBean.DatasBean>) {
                            if(t!=null&&t.data!=null&&t.data?.status==1){
                                data.remove(item)
                                notifyDataSetChanged()
                                mContext.showToast("已完成")
                            }
                        }
                        override fun onFailture(t: Throwable) {

                        }

                    }))
                }else if(item.status==1){//复原
                    RetrofitHelper.apiService.markOrDelete("done",item.id,0).applySchedulers().subscribe(OObserver(object:
                        ApiCallBack<HttpResult<ToDoBean.DatasBean>> {
                        override fun onSuccess(t: HttpResult<ToDoBean.DatasBean>) {
                            if(t!=null&&t.data!=null){
                                data.remove(item)
                                notifyDataSetChanged()
                                mContext.showToast("已复原")
                            }
                        }
                        override fun onFailture(t: Throwable) {
                        }

                    }))
                }
            }
        })
        btn_delete.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
               mContext.showToast("Delete")
                RetrofitHelper.apiService.delete("delete",item.id).applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<ToDoBean.DatasBean>>{
                    override fun onSuccess(t: HttpResult<ToDoBean.DatasBean>) {
                        data.remove(item)
                        notifyDataSetChanged()
                        mContext.showToast("删除成功")

                    }
                    override fun onFailture(t: Throwable) {

                    }
                }))
            }
        })

        item_todo_content.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(planType.equals("listnotdo")){
                    val intent=Intent(mContext,CommonActivity::class.java).putExtra(Constant.TYPE_KEY,Constant.Type.EDIT_TODO_TYPE_KEY)
                    intent.putExtra(Constant.ITEM_BEAN,item)
                    mContext.startActivity(intent)
                }else{
                    val intent2=Intent(mContext,CommonActivity::class.java).putExtra(Constant.TYPE_KEY,Constant.Type.SEE_TODO_TYPE_KEY)
                    intent2.putExtra(Constant.ITEM_BEAN,item)
                    mContext.startActivity(intent2)
                }
            }
        })
    }
}