package fall.out.wanandroid.base

/**
 * Created by Owen on 2019/10/5
 */
interface IPresenter<in V:IView> {
    /***
     * 绑定View
     */
    fun attachView(mView:V)

    /***
     * 解绑View
     */
    fun detachView()
}