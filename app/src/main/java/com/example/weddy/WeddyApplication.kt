package com.example.weddy

import android.app.Application
import com.example.impl.di.AuthComponent
import com.example.impl.di.AuthModule
import com.example.impl.di.DaggerAuthComponent
import com.example.impl.di.FirebaseModule

class WeddyApplication : Application() {

    lateinit var authComponent: AuthComponent

    override fun onCreate() {
        super.onCreate()

        authComponent = DaggerAuthComponent.builder()
            .authModule(AuthModule())
            .firebaseModule(FirebaseModule())
            .build()
    }
}