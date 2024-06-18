plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(BuildVersion.compileSdkVersion)
    buildToolsVersion(BuildVersion.buildToolsVersion)
    defaultConfig {
        minSdkVersion(BuildVersion.minSdkVersion)
        targetSdkVersion(BuildVersion.targetSdkVersion)
        versionCode(BuildVersion.versionCode)
        versionName(BuildVersion.versionName)
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                //阿里路由框架配置
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }

        ndk {
//            abiFilters += setOf("armeabi","armeabi-v7a","arm64-v8a")
            abiFilters += setOf("armeabi-v7a")
        }

        ndkVersion = "22.1.7171670"

        vectorDrawables.useSupportLibrary = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    sourceSets.getByName("main") {
        jniLibs.srcDir("libs")
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.4")
    debugImplementation("com.didichuxing.doraemonkit:dokitx:3.3.5")
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")

    //AndroidX
    api(AndroidX.constraintlayout)
    api(AndroidX.recyclerview)
    api(AndroidX.viewpager2)
    api(AndroidX.appcompat)
    api(AndroidX.material)
    api(AndroidX.flexbox)
    api(AndroidX.annotation)
    api(AndroidX.lifecycle_extensions)
    api(AndroidX.lifecycle_compiler)
    api(AndroidX.junit)
    api(AndroidX.runner)
    api(AndroidX.espresso_core)

    //Kotlin
    api(Kotlin.kotlin_stdlib)
    api(Kotlin.kotlin_core)
    api(Kotlin.kotlinx_coroutines_core)
    api(Kotlin.kotlinx_coroutines_android)

    //Deps
    api(Deps.rxlifecycle)
    api(Deps.rxlifecycle_components)
    api(Deps.rxbinding)
    api(Deps.gson)
    api(Deps.lifecycle_extensions)
    kapt(Deps.lifecycle_compiler)
    api(Deps.arouter_api)
    kapt(Deps.arouter_compiler)
    api(Deps.autosize)
    api(Deps.bugly)
    api(Deps.immersionbar)
    api(Deps.immersionbar_ktx)
    api(Deps.immersionbar_components)
    api(Deps.okhttp3)
    api(Deps.net)
    api(Deps.logging_interceptor)
    api(Deps.consecutiveScroller)
    api(Deps.utilcodex)
    api(Deps.mmkv)
    api(Deps.coil)
    api(Deps.brv)
    api(Deps.channel)
    api(Deps.xpopup)
    api(Deps.dslTablayout)
    api(Deps.viewPager1Delegate)
    api(Deps.shimmer)
    api(Deps.litepal)
    api(Deps.avi_loading)
    api(Deps.zxing_lite)
    api(Deps.banner)
    api(Deps.qmui)
    api(Deps.glide)
    api(Deps.fileOperator_core)
    api(Deps.fileOperator_selector)
    api(Deps.fileOperator_compressor)
    api(Deps.fileOperator_q)
    api("io.github.bmob:android-sdk:3.9.4")
    api("io.reactivex.rxjava2:rxjava:2.2.8")
    api("io.reactivex.rxjava2:rxandroid:2.1.1")
    api("com.squareup.okio:okio:2.2.2")
}