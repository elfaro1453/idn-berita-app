package id.idn.fahru.beritaapp

import android.app.Application
import android.content.Context

/**
 * Created by Imam Fahrur Rofi on 03/06/2020.
 */
class NewsApp : Application() {
    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}