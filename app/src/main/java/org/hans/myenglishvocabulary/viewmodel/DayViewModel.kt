package org.hans.myenglishvocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hans.myenglishvocabulary.db.DayMemo
import org.hans.myenglishvocabulary.db.DayRepo

// Activity나 Fragment의 수명 주기를 고려하여 UI 데이터를 관리하는 ViewModel 클래스
class DayViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val dayRepo: DayRepo by lazy { DayRepo() }

    // SELECT_DAY
    fun loadAllDay(): LiveData<List<DayMemo>> {
        return dayRepo.loadAllDay()
    }

    // INSERT_DAY
    fun insertDay(dayMemo: DayMemo, next: () -> Unit) {
        compositeDisposable.add(dayRepo.insertDay(dayMemo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )

    }

    // DELETE_DAY
    fun deleteDay(dayMemo: DayMemo, next: () -> Unit) {
        compositeDisposable.add(dayRepo.deleteDay(dayMemo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { next() }
        )
    }

    // viewModel 인스턴스가 더 이상 사용되지 않고 메모리에서 소멸되는 순간 호출된다.
    // 설명 : 메모리 누수를 방지하기위해 구독해제 메서드를 작성한다.
    override fun onCleared() {
        compositeDisposable.dispose()

        super.onCleared()
    }
}