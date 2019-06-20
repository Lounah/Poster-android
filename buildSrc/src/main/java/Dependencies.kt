object ApplicationId {
    val id = "io.lounah.poster"
}

object Modules {
    val app = ":app"
    val core = ":core"
    val newsFeed = ":newsfeed"
}

object Releases {
    val versionCode = 1
    val versionName = "0.0.1"
}

object Versions {
    val gradle = "3.4.1"

    val compileSdk = 28
    val minSdk = 19
    val targetSdk = 28

    val fabric = "1.28.1"

    val leakCanary = "2.0-alpha-2"

    val googleServices = "4.2.0"

    val appcompat = "1.1.0-alpha04"
    val design = "1.0.0"
    val recyclerview = "1.0.0"

    val ktx = "1.0.0-alpha1"
    val ktLint = "0.24.0"

    val rxjava = "2.2.8"
    val rxkotlin = "2.3.0"

    val kotlin = "1.3.31"
    val retrofit = "2.5.0"
    val glide = "4.9.0"
    val moshi = "1.8.0"
    val lifecycle = "2.0.0"
    val crashlytics = "2.9.9"

    val playCore = "1.4.1"

    val junit = "4.12"
    val assertjCore = "3.12.2"
    val mockitoKotlin = "2.1.0"
    val mockitoInline = "2.27.0"

    val palette = "1.0.0"

    val constraintLayout = "1.1.2"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"

    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

    val palette = "androidx.palette:palette:${Versions.palette}"

    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val design = "com.google.android.material:material:${Versions.design}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
}

object GoogleLibraries {
    val playCore = "com.google.android.play:core:${Versions.playCore}"
}

object TestLibraries {
    val junit = "junit:junit:${Versions.junit}"
    val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    val lifecycleTesting = "androidx.arch.core:core-testing:${Versions.lifecycle}"
}