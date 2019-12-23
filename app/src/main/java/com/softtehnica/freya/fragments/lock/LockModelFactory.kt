package com.encorsa.fidelioseller.fragments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softtehnica.freya.fragments.introServer.IntroServerViewModel
import com.softtehnica.freya.fragments.lock.LockViewModel


class LockModelFactory(private val app: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LockViewModel::class.java)) {
            return LockViewModel(app) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}