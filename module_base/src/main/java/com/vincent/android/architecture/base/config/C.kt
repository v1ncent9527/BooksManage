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
            const val A_LOGIN = "$MAIN/LoginActivity"
            const val A_CATEGORY = "$MAIN/CategoryActivity"
        }

        object Index {
            private const val INDEX = "/index"
            const val A_BOOK_LIST = "$INDEX/BookListActivity"
            const val A_BOOK_DETAILS = "$INDEX/BookDetailsActivity"
        }

        object Mine {
            private const val MINE = "/mine"
            const val A_FOCUS = "$MINE/FocusActivity"
            const val A_FORUM = "$MINE/ForumActivity"
            const val A_FORUM_PUBLISH = "$MINE/ForumPublishActivity"
            const val A_SEAT_SELECT = "$MINE/SeatSelectActivity"
            const val A_TODO = "$MINE/TodoActivity"
        }

        object Community {
            private const val COMMUNITY = "/community"
            const val A_COMMUNITY_PUBLISH = "$COMMUNITY/CommunityPublishActivity"
        }
    }

    /**
     * 事件TAG
     */
    object BusTAG {
        const val LOGIN_STATUE = "LOGIN_STATUE"
        const val FORUM_PUBLISH = "FORUM_PUBLISH"
        const val COMMUNITY_PUBLISH = "COMMUNITY_PUBLISH"
    }
}