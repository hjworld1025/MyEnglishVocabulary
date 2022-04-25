package org.hans.myenglishvocabulary.db

import androidx.room.*

@Entity(tableName = "day_table")
data class DayMemo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "date")
    val date: String
)
