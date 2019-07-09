package com.sample.simpsonviewer.di.modules

import android.content.Context
import com.sample.simpsonviewer.MyApplication
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [ActivityBindingModule::class, AndroidSupportInjectionModule::class])
class ApplicationModule(private val application: MyApplication) {

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return this.application
    }
}
