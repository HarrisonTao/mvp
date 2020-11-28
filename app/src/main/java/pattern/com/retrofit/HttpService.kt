package pattern.com.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pattern.com.BASE_SERVER_URL
import pattern.com.service.UrlServiceApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


/**
 * 网络链接类
 */
class HttpService() {

    //设置请求对象
    private lateinit var   apiRetrofit: HttpService
    //retrofit
    private lateinit  var retrofit: Retrofit
    //OKhtt 访问端
      var client: OkHttpClient? = null

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
        loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

      //   client=getUnsafeOkHttpClient()
           client = OkHttpClient.Builder()

            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(myInterceptor)  //设置拦截器 打印 请求参数 或添加 公共参数
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



    fun  initSSLService():UrlServiceApi {
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
        loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

        client = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(myInterceptor)  //设置拦截器 打印 请求参数 或添加 公共参数
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






    /**
     * 忽略SSL正说
     */
    private fun getSSLFactory(): SSLSocketFactory {
        //证书忽略添加下面代码（1）打开即可
//         Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }

    fun getOkHttpClientService(): UrlServiceApi {


        val loggingInterceptor = HttpLoggingInterceptor(object:HttpLoggingInterceptor.Logger{
            override
            fun log(message: String) {
                Log.d("请求url-->",message);
            }
        })
        //拦截请求参数
        var myInterceptor=MyInterceptor();

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(myInterceptor)  //设置拦截器 打印 请求参数 或添加 公共参数
        httpClientBuilder.connectTimeout(6, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(6,  TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(6, TimeUnit.SECONDS)
        //证书忽略
        httpClientBuilder.sslSocketFactory(getSSLFactory(),object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }
        })

        httpClientBuilder.hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return true
            }
        })

        client=httpClientBuilder.build();
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_SERVER_URL) //解析类
            .addConverterFactory(GsonConverterFactory.create())
            //支持RXJAVA2 工厂
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
        apiServer = retrofit.create(UrlServiceApi::class.java)
        return  apiServer

    }





}


