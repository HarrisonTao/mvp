package pattern.com.view

public interface BaseView {

    /**
     * 显示dialog
     */
   open fun showLoading()

    /**
     * 隐藏 dialog
     */
    open  fun hideLoading()

    /**
     * 显示错误信息
     *
     * @param msg
     */
    open fun showError(msg: String?)

    /**
     * 错误码
     */
    open fun onErrorCode(errorCode: String?)

    open  fun onSuccessData(type: String?, data: String?)



}