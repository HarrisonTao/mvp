package pattern.com.service

import io.reactivex.Observable
import okhttp3.ResponseBody
import pattern.com.base.City
import pattern.com.base.HttpResponseData
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*
import kotlin.collections.ArrayList

/**
 * 接口配置
 */
public interface UrlServiceApi {

    @FormUrlEncoded
    @POST("usermgt.do?act=getCityInfo")
    fun getCityCodeInfo(@FieldMap map:Map<String,@JvmSuppressWildcards Any >):  Observable<ResponseBody>

    @FormUrlEncoded
    @POST("usermgt.do?act=getCityInfo")
    fun getCityInfo(@FieldMap map:Map<String,@JvmSuppressWildcards Any >):  Observable<HttpResponseData<ArrayList<City>>>

}