package com.example.myapplication.settingspage

import android.content.Context
import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.datastore.DataStore
import com.example.myapplication.datastore.DataStoreKeys
import com.example.myapplication.sharedpreferece.SharedName
import com.example.myapplication.sharedpreferece.SharedPreference
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @ViewModelInject constructor(
    @ActivityContext val context: Context
): ViewModel(){
    val sh = SharedPreference(context)

    fun save(index: Int){
        sh.putIntData(SharedName.theme, index)
    }
}