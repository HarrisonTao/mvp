package pattern.com



import pattern.com.activityimp.BaseActvity
import pattern.com.presenter.BasePresenter
import pattern.com.view.BaseView
import kotlinx.android.synthetic.main.activity_main.*
import pattern.com.base.City
import pattern.com.base.HttpResponseData

class MainActivity : BaseActvity<BasePresenter<BaseView>>() {
//布局文件
    override var loader_layout_Id: Int  = R.layout.activity_main



  override fun createPresenter():BasePresenter<BaseView>{
        return BasePresenter(this)
    }




    override fun onSuccessData(type: String?, data: String?) {
        if(type.equals("getCityCodeInfo")){

            requsentText.text=data
        }
    }



    override fun initView() {
             sendBu.setOnClickListener{
                 var map=HashMap<String,Any>()
                 map.put("code","6666")
                    presenter.getCityCodeInfo2(map)
             }
    }

    override
    fun onSuccessAnyData(type: String?, data: HttpResponseData<ArrayList<City>>) {
        requsentText.text=data.data?.get(0)?.cityName
    }

}
