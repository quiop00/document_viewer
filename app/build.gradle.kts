plugins {
    id("com.android.application")
}

android {
    namespace = "com.ryu.document_reader"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ryu.document_reader"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("net.sourceforge.jexcelapi:jxl:2.6.12")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("com.adjust.sdk:adjust-android:4.38.0")
    implementation("com.google.android.gms:play-services-ads:22.6.0")
    implementation("com.elyeproj.libraries:loaderviewlibrary:2.0.0")
    implementation("io.github.elye:loaderviewlibrary:3.0.0")
    implementation("com.android.billingclient:billing:6.1.0")
    implementation("androidx.lifecycle:lifecycle-process:2.6.2")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Add the dependencies for the Remote Config and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.ironsource.sdk:mediationsdk:7.7.0")
    // ironSource Ad Quality SDK
    implementation("com.ironsource:adqualitysdk:7.14.3")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    // https://mvnrepository.com/artifact/androidx.exifinterface/exifinterface
    implementation("androidx.exifinterface:exifinterface:1.3.7")

    implementation("com.raed.drawingview:drawingview:1.0")
    // https://mvnrepository.com/artifact/de.hdodenhof/circleimageview
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // https://mvnrepository.com/artifact/com.itextpdf/itextpdf
    implementation("com.itextpdf:itextpdf:5.5.13.3")

    // https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // https://mvnrepository.com/artifact/com.github.barteksc/android-pdf-viewer
    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")


}