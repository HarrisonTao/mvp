package pattern.com.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pattern.com.BASE_SERVER_URL
import pattern.com.service.UrlServiceApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络链接类
 */
class HttpService() {

    //设置请求对象
    private lateinit var   apiRetrofit: HttpService
    //retrofit
    private lateinit  var retrofit: Retrofit
    //OKhtt 访问端
    private lateinit var client: OkHttpClient
    //网络接口配置
    private lateinit var apiServer: UrlServiceApi



  fun  initService():UrlServiceApi {
        //监控日志
        val loggingInterceptor = HttpLoggingInterceptor(object:HttpLoggingInterceptor.Logger{

            override
            fun log(message: String) {

             Log.d("请求url-->",message);

            }
        })
        //拦截请求参数
        var myInterceptor=MyInterceptor();
        //网络拦截
        //val interceptor =  HttpLoggingInterceptor()
        loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
             client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
          //  .addInterceptor(myInterceptor)  //设置拦截器 打印 请求参数 或添加 公共参数
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL) //解析类
          .addConverterFactory(GsonConverterFactory.create())
            //支持RXJAVA2 工厂
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //支持RxJava2
            // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        apiServer = retrofit.create(UrlServiceApi::class.java)
      return  apiServer
    }










}