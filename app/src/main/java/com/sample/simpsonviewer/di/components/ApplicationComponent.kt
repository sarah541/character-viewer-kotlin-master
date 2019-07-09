package com.sample.simpsonviewer.di.components

import com.sample.simpsonviewer.MyApplication
import com.sample.simpsonviewer.di.modules.ApplicationModule
import com.sample.simpsonviewer.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(application: MyApplication)
}
