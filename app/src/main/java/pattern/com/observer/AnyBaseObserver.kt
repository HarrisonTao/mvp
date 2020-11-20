package pattern.com.observer

import com.google.gson.JsonParseException
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONException
import pattern.com.base.HttpResponseData

import pattern.com.view.BaseView
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 针对于对象解析返回操作
 */
abstract class AnyBaseObserver<T> constructor(view:BaseView): DisposableObserver<HttpResponseData<T>>() {

    lateinit  var view:BaseView

    /**
     * 初始化赋值
     */
    init {
        this.view=view
    }


    /**
     * 解析数据失败
     */
    val PARSE_ERROR = 1001

    /**
     * 网络问题
     */
    val BAD_NETWORK = 1002

    /**
     * 连接错误
     */
    val CONNECT_ERROR = 1003

    /**
     * 连接超时
     */
     val CONNECT_TIMEOUT = 1004

    var isOpen = true


    init {
        this.view=view;
    }

    constructor(view: BaseView,isOpen:Boolean):this(view){
        this.isOpen=isOpen;


    }

    override fun onStart() {
        super.onStart()
        if(!isOpen){
            return;
        }
        view.showLoading()

    }


    /**

     */
    override fun onComplete() {

        if(!isOpen){
            return;
        }

        view.hideLoading()

    }

    override fun onError(e: Throwable) {
        if (view != null) {
            view.hideLoading()
        }
        if (e is HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK)
        } else if (e is ConnectException
            || e is UnknownHostException
        ) {
            //   连接错误
            onException(CONNECT_ERROR)
        } else if (e is InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT)
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            //  解析错误
            onException(PARSE_ERROR)
        } else {
            if (e != null) {
                view.showError(e.toString())
            } else {
                view.showError("未知错误")
            }
        }
    }


     open fun onException(unknownError: Int) {
        when (unknownError) {
            CONNECT_ERROR -> view.showError("连接错误")
            CONNECT_TIMEOUT -> view.showError("连接超时")
            BAD_NETWORK -> view.showError("网络问题")
            PARSE_ERROR -> view.showError("解析数据失败")

        }
    }

    /**

     * @param t
     * the item emitted by the Observable
     */
    override fun onNext(t: HttpResponseData<T>) {
        if(t.code==0) {
            onSuccess(t)
        }else{
            //把错误信息发送出去
            view.showError(t.message)
        }

    }

    abstract fun onSuccess(data:HttpResponseData<T>)

    

}


