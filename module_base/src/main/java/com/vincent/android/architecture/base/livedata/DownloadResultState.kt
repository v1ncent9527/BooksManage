package com.vincent.android.architecture.base.livedata

/**
 * ================================================
 * 作    者：e119166
 * 版    本：1.0.0
 * 创建日期：2024/2/23
 * 描    述：
 * 修订历史：
 * ================================================
 */
sealed class DownloadResultState {
    companion object {

        fun onPending(): DownloadResultState = Pending
        fun onProgress(currentBytes: Int, totalBytes: Int): DownloadResultState =
            Progress(currentBytes, totalBytes)

        fun onSuccess(filePath: String): DownloadResultState = Success(filePath)
        fun onError(errorMsg: String): DownloadResultState = Error(errorMsg)
    }

    object Pending : DownloadResultState()
    data class Progress(val currentBytes: Int, val totalBytes: Int) : DownloadResultState()
    data class Success(val filePath: String) : DownloadResultState()
    data class Error(val errorMsg: String) : DownloadResultState()
}