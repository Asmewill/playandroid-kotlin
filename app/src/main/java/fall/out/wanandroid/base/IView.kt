package fall.out.wanandroid.base

/**
 * Created by Owen on 2019/10/5
 */
interface IView {
    /***
     * 显示加载
     */
    fun showLoading()

    /**
     *隐藏加载
     */
    fun hideLoading()

    /***
     * 使用默认的显示消息样式
     */
    fun showDefaultMsg(msg:String)

    /***
     * 显示消息
     */
    fun showMsg(msg:String)

    /***
     * 显示错误消息
     */
    fun showError(errorMsg:String)

}