package com.sample.simpsonviewer.di.modules

import com.sample.simpsonviewer.MainActivity
import com.sample.simpsonviewer.di.scope.PerActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun provideMainActivity(): MainActivity
}
