package pattern.com.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pattern.com.BASE_SERVER_URL
import pattern.com.service.UrlServiceApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络链接类
 */
class HttpService private constructor() {

    //设置请求对象
    private lateinit var   apiRetrofit: HttpService
    //retrofit
    private lateinit  var retrofit: Retrofit
    //OKhtt 访问端
    private lateinit var client: OkHttpClient
    //网络接口配置
    private lateinit var apiServer: UrlServiceApi

    /**
     * 半生对象 直接使用 类名称点属性
     * 获取网络对象
     */
    companion object {
        //网络访问对象
        val httpService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpService()
        }
    }

    init {
        //网络拦截
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
             client = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL) //解析类
            //.addConverterFactory(GsonConverterFactory.create())
            //支持RXJAVA2 工厂
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持RxJava2
            // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        apiServer = retrofit.create(UrlServiceApi::class.java)
    }

    /**
     * 返回对象
     */
    fun getApiService(): UrlServiceApi {
        return apiServer
    }

}