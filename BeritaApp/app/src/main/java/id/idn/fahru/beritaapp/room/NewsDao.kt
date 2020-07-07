package id.idn.fahru.beritaapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 26/06/2020.
 */

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesItem: ArticlesItem): Long

    @Query("DELETE FROM articlesitem_table WHERE url = :url")
    suspend fun delete(url: String)

    @Query("DELETE FROM articlesitem_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM articlesitem_table WHERE url = :url")
    fun checkIsNewsSaved(url: String): LiveData<Int>

    @Query("SELECT * from articlesitem_table ORDER BY newsID DESC")
    fun getAllSavedNews(): LiveData<List<ArticlesItem>>
}