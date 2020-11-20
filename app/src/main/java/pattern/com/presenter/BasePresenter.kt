package pattern.com.presenter

import io.reactivex.disposables.CompositeDisposable

import pattern.com.view.BaseView

public  class  BasePresenter<V : BaseView> constructor(view:V){

    var view:V?

    init {
        this.view=view
    }


    /**
     * 返回 view
     *
     * @return
     */
    fun getBaseView(): V? {
        return view
    }



    /**
     * 解除绑定
     */
    fun detachView() {
        view =null
    }


}