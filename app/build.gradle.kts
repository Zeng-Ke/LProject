import  com.example.buildplugin.*

plugins {
    id("com.android.application")
    id("com.example.buildplugin")
    id("org.jetbrains.kotlin.android")
}


android {
    namespace = "com.example.lproject"
    compileSdk = 33

    defaultConfig {
        applicationId = BuildCf.applicationId
        minSdk = BuildCf.minSdk
        targetSdk = BuildCf.targetSdk
        versionCode = BuildCf.versionCode
        versionName = BuildCf.versionName
        buildConfigField("boolean", "DEBUG", "${BuildCf.DEBUG}")

    }

    //或者gradle plugin 7.0以上也可以用如下写法
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }



    buildTypes {
        release {
            isMinifyEnabled = false  //控制是否开启混淆
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            //isShrinkResources = true // 移除无用的resource文件
            isZipAlignEnabled = true
            isDebuggable = false
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            isDebuggable = true
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(Deps.androidxCoreKtx)
    implementation(Deps.androidx)
    implementation(Deps.material)
    implementation(Deps.constraintlayout)
    implementation(Deps.databinding)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation(Deps.activityKtx)
    implementation(Deps.utilcodex)
    implementation(Deps.gson)
    implementation(Deps.screenAutosize)

    http()
    lifecycle()


}