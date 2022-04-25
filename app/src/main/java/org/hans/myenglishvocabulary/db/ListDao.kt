package org.hans.myenglishvocabulary.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {
    // SELECT_LIST
    @Query("SELECT * FROM list_table")
    fun loadAllList(): LiveData<List<ListMemo>>

    @Query("""
        SELECT *
        FROM list_table JOIN day_table
        ON list_table.dayId = day_table.id
    """)
    fun loadDayAndList(): LiveData<Map<DayMemo, List<ListMemo>>>

    // INSERT_LIST
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(vararg listMemo: ListMemo)

    // UPDATE_LIST
    @Update
    fun updateList(listMemo: ListMemo)

    // DELETE_LIST
    @Delete
    fun deleteList(listMemo: ListMemo)
}