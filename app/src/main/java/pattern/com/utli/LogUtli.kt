package pattern.com.utli

import android.util.Log
import pattern.com.TGM
import pattern.com.isOpenLog

/**
 * 日志输入
 */
class LogUtli {



    fun logErrer(msg: String?) {
        if (isOpenLog) {
            Log.e(TGM, msg)
        }
    }

    fun logDubug(msg: String?) {
        if (isOpenLog) {
            Log.e(TGM, msg)
        }
    }

    /**
     * 打印map
     */
    fun logMap(map: Map<String, String?>) {
        if (isOpenLog) {
            for (key in map.keys) {
                logDubug("key:" + key + " == " + map[key])
            }
        }
    }
}