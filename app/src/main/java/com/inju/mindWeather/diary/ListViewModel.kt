package com.inju.mindWeather.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inju.mindWeather.diary.diaryList.DiaryListEvent
import com.inju.mindWeather.model.pojo.Diary
import com.inju.mindWeather.model.repository.IDiaryRepository
import com.inju.mindWeather.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListViewModel(
    private val repo: IDiaryRepository,
    uiContext: CoroutineContext
) : BaseViewModel<DiaryListEvent>(uiContext) {

    var diarys: MutableList<Diary> = mutableListOf()
    val diaryListLiveData : MutableLiveData<MutableList<Diary>> by lazy {
        MutableLiveData<MutableList<Diary>>().apply {
            value = diarys
        }
    }

    fun getDiarys() = launch {
        diarys = repo.getDiarys()
        diaryListLiveData.value = diarys
    }

    private val editDiaryState = MutableLiveData<String>()
    val editDiary: LiveData<String> get() = editDiaryState

    override fun handleEvent(event: DiaryListEvent) {
        when (event) {
            is DiaryListEvent.OnStart -> getDiarys()
            is DiaryListEvent.OnDiaryItemClick -> editDiary(event.diaryId)
        }
    }

    private fun editDiary(diaryId: String) {
        editDiaryState.value = diaryId
    }

}