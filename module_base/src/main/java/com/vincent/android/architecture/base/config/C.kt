package com.vincent.android.architecture.base.config

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/22
 * 描    述：Constant
 * 修订历史：
 * ================================================
 */
object C {

    object Common {

    }

    /**
     * 规则 :  /(module后缀)/(所在类名)
     * 路由 A_ : Activity
     *     F_ : Fragment
     */
    interface RouterPath {
        object Common {
            private const val Common = "/common"
            const val A_H5 = "$Common/H5Activity"
            const val A_QRCODE = "$Common/QrCodeActivity"
        }

        object Main {
            private const val MAIN = "/main"
            const val A_MAIN = "$MAIN/MainActivity"
        }
    }

    /**
     * 事件TAG
     */
    object BusTAG {
        const val LOGIN_STATUE = "LOGIN_STATUE"
    }
}