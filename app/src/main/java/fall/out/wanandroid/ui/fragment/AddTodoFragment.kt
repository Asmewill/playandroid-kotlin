package fall.out.wanandroid.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.ToDoBean
import fall.out.wanandroid.bean.TodoTypeBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.fragment_add_todo.*
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by Owen on 2019/11/21
 *
 */
class AddTodoFragment:BaseFragment() {
    private var item: ToDoBean.DatasBean?=null
    private var editType="" //1，2,3分别表示新增，编辑，查看
    private var addType="0" //0,1,2,3分别表示只选这一个，工作，学习，生活
    private   var title=""
    private var content=""
    private var selectDate=""
    private var priority="0"
    private  val nowDate  by lazy {
          Calendar.getInstance()
    }
    companion object{
        fun getInstance(editType:String,item:ToDoBean.DatasBean?):AddTodoFragment{
            val addTodoFragment=AddTodoFragment()
            val bundle=Bundle()
            bundle.putString(Constant.EDIT_TYPE,editType)
            bundle.putSerializable(Constant.ITEM_BEAN,item)
            addTodoFragment.arguments=bundle
            return  addTodoFragment
        }
    }
    override fun attachLayoutRes(): Int {
        return  R.layout.fragment_add_todo
    }
    override fun initView() {
        tv_date.setText(nowDate.get(Calendar.YEAR).toString()+"-"+(nowDate.get(Calendar.MONTH)+1)+"-"+nowDate.get(Calendar.DAY_OF_MONTH))
        editType=arguments?.getString(Constant.EDIT_TYPE)?:""
        addType=arguments?.getString(Constant.ADD_TYPE)?:"0"
        if(arguments?.getSerializable(Constant.ITEM_BEAN)!=null){
            item=arguments?.getSerializable(Constant.ITEM_BEAN) as ToDoBean.DatasBean
        }
        rg_priority.setOnCheckedChangeListener(object:RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.rb0->{
                        priority=0.toString()
                    }
                    R.id.rb1->{
                        priority=1.toString()
                    }
                }
            }
        })
        ll_date.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                selectDate=tv_date.text.toString()
                val datePicker=DatePickerDialog(activity,object:DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        tv_date.setText(year.toString()+"-"+(month+1)+"-"+dayOfMonth)
                    }
                },selectDate.split("-")[0].toInt(),selectDate.split("-")[1].toInt()-1,selectDate.split("-")[2].toInt())
                datePicker.show()
            }
        })

        when(editType){
            "1"->{

            }
            "2"->{//编辑
                if(item==null){
                    return
                }
                et_title.setText(item?.title)
                et_content.setText(item?.content)
                if(item?.priority==0){
                    rb0.isChecked=true
                }else{
                    rb1.isChecked=true
                }
                tv_date.setText(item?.dateStr)

            }
            "3"->{//查看
                if(item==null){
                    return
                }
                btn_save.visibility=View.GONE
                et_title.setText(item?.title)
                et_title.isEnabled=false
                et_title.isClickable=false
                et_content.setText(item?.content)
                et_content.isEnabled=false
                et_content.isClickable=false
                rb0.isClickable=false
                rb1.isClickable=false
                ll_date.setOnClickListener(null)
                if(item?.priority==0){
                    rb0.isChecked=true
                }else{
                    rb1.isChecked=true
                }
                tv_date.setText(item?.dateStr)
                btn_save.visibility=View.GONE
            }
        }

    }
    override fun initData() {
        btn_save.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                 title= et_title.text.toString()
                 content =et_content.text.toString()
                 selectDate=tv_date.text.toString()
                 if(TextUtils.isEmpty(title)){
                      showToast("请输入标题")
                     return
                 }
                if(TextUtils.isEmpty(content)){
                    showToast("请输入详情")
                    return
                }
                if(editType.equals("1")){
                    RetrofitHelper.apiService.addToDo(addType.toInt(),title,content,selectDate,priority)
                        .applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<ToDoBean.DatasBean>>{
                            override fun onSuccess(t: HttpResult<ToDoBean.DatasBean>) {
                                if(t!=null&&t.data!=null){
                                    showToast("Success...")
                                    val todoTypeBean=TodoTypeBean(0,"",false)
                                    //发送EventBus更新列表数据
                                    todoTypeBean.playType="listnotdo"
                                    EventBus.getDefault().post(todoTypeBean)
                                    activity?.finish()
                                }
                            }
                            override fun onFailture(t: Throwable) {
                                showToast("Failed...")

                            }
                        }))
                }else{
                    RetrofitHelper.apiService.editToDo(item?.id?:0,addType.toInt(),title,content,selectDate,priority)
                        .applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<ToDoBean.DatasBean>>{
                            override fun onSuccess(t: HttpResult<ToDoBean.DatasBean>) {
                                if(t!=null&&t.data!=null){
                                    showToast("Success...")
                                    val todoTypeBean=TodoTypeBean(0,"",false)
                                    //发送EventBus更新列表数据
                                    todoTypeBean.playType="listnotdo"
                                    EventBus.getDefault().post(todoTypeBean)
                                    activity?.finish()
                                }
                            }
                            override fun onFailture(t: Throwable) {
                                showToast("Failed...")
                            }
                        }))
                }
            }
        })
    }
}