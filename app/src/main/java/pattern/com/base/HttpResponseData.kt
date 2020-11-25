package pattern.com.base



/**
 * 响应请求类
 */
class HttpResponseData<T> {

    /**
     * header : {"code":"0000","message":"操作成功"}
     * data : [{"cityName":"百色市","cityCode":"451000","py":"B"}]
     */
    var header: HeaderBean? = null
    var data: T? = null


    class HeaderBean {
        /**
         * code : 0000
         * message : 操作成功
         */
         val code: String? = null
         val message: String? = null
    }


}