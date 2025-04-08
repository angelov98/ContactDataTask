package com.nikolaa.faktorzweitask.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikolaa.faktorzweitask.util.EnumClasses
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvent = MutableLiveData<EnumClasses.NavigationEvent>()
    val navigationEvent: LiveData<EnumClasses.NavigationEvent> get() = _navigationEvent

    fun onFinishSplashScreen() {
        _navigationEvent.value = EnumClasses.NavigationEvent.NavigateToMainNavigation
    }
}