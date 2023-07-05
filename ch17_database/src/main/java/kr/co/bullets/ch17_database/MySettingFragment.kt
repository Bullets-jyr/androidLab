package kr.co.bullets.ch17_database

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class MySettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        // EditTextPreference
        val idPreference: EditTextPreference? = findPreference("id")
        // ListPreference
        val colorPreference: ListPreference? = findPreference("color")

        idPreference?.summaryProvider =
            Preference.SummaryProvider<EditTextPreference>{ preference ->
                // 유저가 설정한 값
                val text = preference.text
                if (TextUtils.isEmpty(text)) {
                    "설정이 되지 않았습니다."
                } else {
                    "설정된 id 값은 $text 입니다. "
                }
            }

        // 유저가 지정한 문자열을 그대로 나올 수 있도록 적용
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }
}