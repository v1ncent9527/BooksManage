package com.vincent.android.architecture.base.component.qrcode

import android.Manifest
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PermissionUtils
import com.google.zxing.Result
import com.king.zxing.CameraScan.OnScanResultCallback
import com.king.zxing.DefaultCameraScan
import com.vincent.android.architecture.base.BR
import com.vincent.android.architecture.base.R
import com.vincent.android.architecture.base.config.C
import com.vincent.android.architecture.base.core.BaseToolbarActivity
import com.vincent.android.architecture.base.core.BaseViewModel
import com.vincent.android.architecture.base.databinding.ActivityQrcodeBinding
import com.vincent.android.architecture.base.extention.click
import com.vincent.android.architecture.base.extention.string
import com.vincent.android.architecture.base.extention.toast
import com.vincent.android.architecture.base.model.ToolbarModel

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/29
 * 描    述：二维码扫描
 * 修订历史：
 * ================================================
 */
@Route(path = C.RouterPath.Common.A_QRCODE)
class QrcodeActivity : BaseToolbarActivity<ActivityQrcodeBinding, BaseViewModel>(),
    OnScanResultCallback {

    lateinit var mCameraScan: DefaultCameraScan

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_qrcode
    }

    override fun initVariableId(): Int {
        return BR.qrcodeVM
    }

    override fun initToolbar(): ToolbarModel {
        return ToolbarModel(titleText = string(R.string.common_qrcode))
    }

    override fun initView() {
        PermissionUtils.permission(Manifest.permission.CAMERA)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    mCameraScan = DefaultCameraScan(this@QrcodeActivity, binding.previewView)
                    mCameraScan.setOnScanResultCallback(this@QrcodeActivity)
                        .setPlayBeep(true)      //设置是否播放音效，默认为false
                        .setVibrate(true)       //设置是否震动，默认为false
                        .setNeedAutoZoom(true)  //二维码太小时可自动缩放，默认为false
                        .startCamera()

                    binding.ivFlashlight.click {
                        mCameraScan.enableTorch(!mCameraScan.isTorchEnabled)
                        it.isSelected = mCameraScan.isTorchEnabled
                    }
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    toast(R.string.common_camera_permission)
                }

            }).request()
    }

    override fun onScanResultCallback(result: Result?): Boolean {
        try {
            // TODO: 2022/4/29
            //result?.text!!.toBean
        } catch (e: Exception) {
            toast(e.toString())
        }
        return false
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing)
            mCameraScan.release()
    }
}