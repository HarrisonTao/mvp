package pattern.com



import pattern.com.activityimp.BaseActvity
import pattern.com.presenter.BasePresenter
import pattern.com.view.BaseView

class MainActivity : BaseActvity<BasePresenter<BaseView>>() {
//布局文件
    override var loader_layout_Id: Int  = R.layout.activity_main



  override fun createPresenter():BasePresenter<BaseView>{
        return BasePresenter(this)
    }




    override fun onSuccessData(type: String?, data: String?) {
        if(type.equals("1")){

        }
    }

    override fun initView() {

    }

}
