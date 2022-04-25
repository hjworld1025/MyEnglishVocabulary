package org.hans.myenglishvocabulary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DayMemo::class, ListMemo::class), version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun listDao(): ListDao

    companion object {
        var INSTANCE: MemoDatabase? = null

        fun getInstance(context: Context): MemoDatabase {
            if (INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MemoDatabase::class.java, "memo.db")
                        // DayMemo, ListMemo가 변경되면(버전 변경시) 드랍하고 새롭게 생성한다.
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE!!
        }
    }
}