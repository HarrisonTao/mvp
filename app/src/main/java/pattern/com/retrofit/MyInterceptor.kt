package pattern.com.retrofit

import android.util.Log
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request: Request = chain.request()
        if ("GET" == request.method) {
            val urlBuilder = request.url.newBuilder()
            val httpUrl: HttpUrl = urlBuilder.build()
            // 打印所有get参数
            val paramKeys = httpUrl.queryParameterNames
            for (key in paramKeys) {
                val value = httpUrl.queryParameter(key)
                Log.d("请求参数-->", "$key =$value")
            }
        }else if("POST" == request.method){
            val formBody = request.body as FormBody?

            for (i in 0 until formBody!!.size) {
              //  bodyBuilder.addEncoded(formBody!!.encodedName(i), formBody!!.encodedValue(i))
                    //打印请求数据
                    Log.d("请求参数-->", formBody.encodedName(i) + "=" + formBody.encodedValue(i))
            }
        }
        //返回请求数据
        return chain.proceed(request)
    }


}