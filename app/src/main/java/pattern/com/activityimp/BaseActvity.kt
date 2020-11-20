package pattern.com.activityimp

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import pattern.com.base.HttpResponseData
import pattern.com.presenter.BasePresenter
import pattern.com.view.BaseView


abstract class BaseActvity<P:BasePresenter<BaseView>>() : Activity() , BaseView {

     lateinit var  dialog: Dialog

    lateinit var presenter: P

   lateinit var toast: Toast
    //布局文件
   abstract var loader_layout_Id:Int

    //设置逻辑操作对象
     abstract fun createPresenter(): P



    /**
     * 初始化数据
     */
    abstract fun initView ()



    override fun showLoading() {

    }


    override fun hideLoading() {

    }

    override fun onErrorCode(errorCode: String?) {

    }

    override fun onSuccessData(type: String?, data: String?) {

    }


    /**
     * 错误提示框
     */
    override  fun showError(msg: String?){
        if (toast == null) {
            toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG)
        }
        toast.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loader_layout_Id)
        presenter=createPresenter()
        initView();
    }



    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}