package com.sample.simpsonviewer

import android.app.Activity
import android.app.Application
import android.content.Context
import com.sample.simpsonviewer.di.components.ApplicationComponent
import com.sample.simpsonviewer.di.components.DaggerApplicationComponent
import com.sample.simpsonviewer.di.modules.ApplicationModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        applicationComponent!!.inject(this)

    }

    override fun attachBaseContext(base: Context) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        super.attachBaseContext(base)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityInjector
    }

    companion object {
        private var applicationComponent: ApplicationComponent? = null
    }
}
