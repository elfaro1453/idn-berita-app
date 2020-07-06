package id.idn.fahru.beritaapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.idn.fahru.beritaapp.model.remote.ArticlesItem


/**
 * Created by Imam Fahrur Rofi on 26/06/2020.
 */
@Database(
    entities = [ArticlesItem::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsDB? = null

        fun getDatabase(context: Context): NewsDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDB::class.java,
                    "FahruIDNSchID"
                )
                    // Wipes and rebuilds instead of migrating
                    // if no Migration object.
                    // Migration is not part of this practical.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}