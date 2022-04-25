package org.hans.myenglishvocabulary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "list_table",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = DayMemo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("dayId")
        )
    )
)
data class ListMemo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "english")
    var english: String,
    @ColumnInfo(name = "korean")
    var korean: String,
    @ColumnInfo(name = "dayId")
    var dayId: Long?
)