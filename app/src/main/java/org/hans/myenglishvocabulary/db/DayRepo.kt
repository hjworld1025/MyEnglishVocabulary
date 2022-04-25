package org.hans.myenglishvocabulary.db

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import org.hans.myenglishvocabulary.stuff.MyApplication

class DayRepo {
    private val dayDao: DayDao by lazy {
        val memoDatabase: MemoDatabase = MemoDatabase.getInstance(MyApplication.applicationContext())
        memoDatabase.dayDao()
    }

    // SELECT_DAY
    fun loadAllDay(): LiveData<List<DayMemo>> {
        return dayDao.loadAllDay()
    }

    // INSERT_DAY
    fun insertDay(dayMemo: DayMemo): Observable<Unit> {
        return Observable.fromCallable { dayDao.insertDay(dayMemo) }
    }

    // DELETE_DAY
    fun deleteDay(dayMemo: DayMemo): Observable<Unit> {
        return Observable.fromCallable { dayDao.deleteDay(dayMemo) }
    }
}