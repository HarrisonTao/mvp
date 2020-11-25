package pattern.com.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import pattern.com.base.City
import pattern.com.base.HttpResponseData
import pattern.com.observer.AnyBaseObserver
import pattern.com.observer.BaseObserver
import pattern.com.retrofit.HttpService
import pattern.com.view.BaseView

public  class  BasePresenter<V : BaseView> constructor(view:V){



    var view:V

    init {
        this.view=view
    }


    /**
     * 返回 view
     *
     * @return
     */
    fun getBaseView(): V {
        return view
    }



    /**
     * 解除绑定 85881294
     */
    fun detachView() {
        //释放资源


    }


    fun getCityCodeInfo(map:HashMap<String,Any>){
        var httpService=HttpService().initService()
            httpService.getCityCodeInfo(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :BaseObserver<ResponseBody>(view){
                override fun onSuccess(data: String) {
                    view.onSuccessData("getCityCodeInfo",data)
                }

            })
    }


    fun getCityCodeInfo2(map:HashMap<String,Any>){
        var httpService=HttpService().initService()
        httpService.getCityInfo(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : AnyBaseObserver<HttpResponseData<ArrayList<City>>>(view){
                override fun onSuccess(data: HttpResponseData<ArrayList<City>>) {
                 view.onSuccessAnyData("",data)
                }

            })
    }

}



