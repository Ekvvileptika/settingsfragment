package com.example.myapplication.settingspage

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.*
import com.example.myapplication.R
import com.example.myapplication.datastore.DataStore
import com.example.myapplication.datastore.DataStoreKeys
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat(),
    Preference.OnPreferenceClickListener,
        Preference.OnPreferenceChangeListener
{
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        byXml()
    }

    private fun byXml(){
        val switch: SwitchPreferenceCompat? = findPreference("notifications")
        switch?.onPreferenceClickListener = this

        val list: ListPreference? = findPreference("list")
        list?.onPreferenceChangeListener = this
    }

    private fun programmatically(){
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val notificationPreference = SwitchPreferenceCompat(context)
        notificationPreference.key = "notifications"
        notificationPreference.title = "Enable message notifications"
        notificationPreference.onPreferenceClickListener = this

        val feedbackPreference = Preference(context)
        feedbackPreference.key = "feedback"
        feedbackPreference.title = "Send feedback"
        feedbackPreference.summary = "Report technical issues or suggest new features"

        val listPreferences = ListPreference(context)
        listPreferences.key = "list"
        listPreferences.title = "Send feedback"
        listPreferences.summary = "Report technical issues or suggest new features"
        listPreferences.entries = arrayOf("Black", "White")
        listPreferences.entryValues = arrayOf("Black", "White")
        listPreferences.onPreferenceChangeListener = this

        screen.addPreference(notificationPreference)
        screen.addPreference(feedbackPreference)
        screen.addPreference(listPreferences)

        preferenceScreen = screen
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        val p = preference?.key
        //val z = (preference as SwitchPreferenceCompat).isChecked

        return false
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val p = preference?.key

        when(p){
            "list" -> {
                val selectedIndex = newValue.toString().toInt()
                val selectedVal = (preference as ListPreference).entries[selectedIndex]
                preference.setValueIndex(selectedIndex)
                preference.summary = selectedVal

                val themechanger = DarkMode()
                themechanger.darkmodemaker(selectedIndex)
                saveTheme(selectedIndex)
            }
        }

        return false
    }

    private fun saveTheme(themeindex: Int){
        viewModel.save(themeindex)
    }
}