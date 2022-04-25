package org.hans.myenglishvocabulary.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DayDao {
    // SELECT_DAY
    @Query("SELECT * FROM day_table")
    fun loadAllDay(): LiveData<List<DayMemo>>

    // INSERT_DAY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDay(vararg dayMemo: DayMemo)

    // DELETE_DAY
    @Delete
    fun deleteDay(dayMemo: DayMemo)
}