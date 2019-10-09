package fall.out.wanandroid.mvp.presenter

import fall.out.wanandroid.base.BasePresenter
import fall.out.wanandroid.mvp.contract.MainContract

/**
 * Created by Owen on 2019/10/5
 */
class MainPresenter :BasePresenter<MainContract.Model,MainContract.View>(),MainContract.Presenter{


    override fun logout() {

    }

    override fun getUserInfo() {

    }

    override fun createModel(): MainContract.Model? {
        return null
    }
}