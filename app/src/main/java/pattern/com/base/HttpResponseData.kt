package pattern.com.base



/**
 * 响应请求类
 */
class HttpResponseData<T> {
    //返回状态
    var code:Int=0

    //返回信息
     lateinit  var  message:String

    //返回数据
    var data:T?=null

}