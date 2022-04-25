package org.hans.myenglishvocabulary.db

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import org.hans.myenglishvocabulary.stuff.MyApplication

class ListRepo() {
    private val listDao: ListDao by lazy {
        val memoDatabase: MemoDatabase = MemoDatabase.getInstance(MyApplication.applicationContext())
        memoDatabase.listDao()
    }

    // SELECT_LIST
    fun loadAllList(): LiveData<List<ListMemo>> {
        return listDao.loadAllList()
    }

    fun loadDayAndList(): LiveData<Map<DayMemo, List<ListMemo>>> {
        return listDao.loadDayAndList()
    }

    // INSERT_LIST
    fun insertList(listMemo: ListMemo): Observable<Unit> {
        return Observable.fromCallable { listDao.insertList(listMemo) }
    }

    // UPDATE_LIST
    fun updateList(listMemo: ListMemo): Observable<Unit> {
        return Observable.fromCallable { listDao.updateList(listMemo) }
    }

    // DELETE_LIST
    fun deleteList(listMemo: ListMemo): Observable<Unit> {
        return Observable.fromCallable { listDao.deleteList(listMemo) }
    }
}