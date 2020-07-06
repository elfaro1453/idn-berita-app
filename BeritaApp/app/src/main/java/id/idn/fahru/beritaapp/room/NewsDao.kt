package id.idn.fahru.beritaapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.idn.fahru.beritaapp.model.remote.ArticlesItem

/**
 * Created by Imam Fahrur Rofi on 26/06/2020.
 */

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articlesItem: ArticlesItem): Long

    @Delete
    suspend fun delete(articlesItem: ArticlesItem)

    @Query("DELETE FROM articlesitem_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM articlesitem_table WHERE url = :url")
    fun checkIsNewsSaved(url: String): LiveData<Int>

    @Query("SELECT * from articlesitem_table ORDER BY newsID DESC")
    fun getAllSavedNews(): LiveData<List<ArticlesItem>>
}