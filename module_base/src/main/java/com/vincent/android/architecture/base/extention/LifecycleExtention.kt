package com.vincent.android.architecture.base.extention

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * ================================================
 * 作    者：v1ncent
 * 版    本：1.0.0
 * 创建日期：2022/4/25
 * 描    述：Lifecycle相关
 * 修订历史：
 * ================================================
 */

/**
 * 减少观察LiveData变化的重复代码
 *
 * @param data [LiveData] 被观察对象
 * @param block 接受和处理数据
 */
fun <T> AppCompatActivity.observe(data: LiveData<T>, block: (T) -> Unit) {
    data.observe(this, Observer(block))
}

/**
 * 减少观察LiveData变化的重复代码
 *
 * @param data [LiveData] 被观察对象
 * @param block 接受和处理数据
 */
fun <T> Fragment.observe(data: LiveData<T>, block: (T) -> Unit) {
    data.observe(this, Observer(block))
}
