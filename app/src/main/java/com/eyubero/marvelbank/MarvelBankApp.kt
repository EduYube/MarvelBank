package com.eyubero.marvelbank

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelBankApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}