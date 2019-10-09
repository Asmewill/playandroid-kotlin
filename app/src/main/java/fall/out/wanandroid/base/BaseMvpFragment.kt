package fall.out.wanandroid.base

import showToast

/**
 * Created by Owen on 2019/10/9
 */
 abstract class BaseMvpFragment<in V:IView,P:IPresenter<V>>:BaseFragment(),IView {
    protected var mPresenter:P?=null
    abstract fun createPresenter(): P
    override fun initView() {
        mPresenter=createPresenter()
        mPresenter?.attachView(this as V)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter=null
    }
    override fun showLoading() {

    }
    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }
}