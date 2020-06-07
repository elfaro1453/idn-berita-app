package id.idn.fahru.beritaapp

import android.app.Application
import timber.log.Timber

/**
 * Created by Imam Fahrur Rofi on 03/06/2020.
 * https://stackoverflow.com/a/46938919
 */
class App : Application() {
    companion object {
        lateinit var context: App private set
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        context = this
    }
}
