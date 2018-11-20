package ali.ibrhim.fullyanimatedapptutorial.utils

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import java.util.*

class Application {

    companion object {
        fun changeLanguage(context: Context, lang: String) {
            Log.e(context.packageName, lang)
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = context.resources.configuration
            config.setLocale(locale)
            context.createConfigurationContext(config)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
            sharedPreference.edit().putString("lang", lang).apply()
        }

    }
}