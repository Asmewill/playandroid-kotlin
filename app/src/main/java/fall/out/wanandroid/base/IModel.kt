package fall.out.wanandroid.base

import io.reactivex.disposables.Disposable

/**
 * Created by Owen on 2019/10/5
 */
interface IModel {
    /***
     * 添加订阅
     */
    fun addDisposable(disposable: Disposable)

    /***
     * 解除订阅
     */
    fun onDetach()
}