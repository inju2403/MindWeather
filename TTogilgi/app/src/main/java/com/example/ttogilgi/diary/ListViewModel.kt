package com.example.ttogilgi.diary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ttogilgi.diary.diaryList.DiaryListEvent
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository
import com.example.ttogilgi.utils.BaseViewModel
import com.example.ttogilgi.utils.Constants
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
    private val editDiaryState = MutableLiveData<String>()
    val editDiary: LiveData<String> get() = editDiaryState

    fun getDiarys() = launch {
        diarys = repo.getDiarys()
        diaryListLiveData.value = diarys
    }

    override fun handleEvent(event: DiaryListEvent) {
        when (event) {
            is DiaryListEvent.OnStart -> getDiarys()
            is DiaryListEvent.OnDiaryItemClick -> editDiary(event.position)
        }
    }

    private fun editDiary(position: Int) {
        editDiaryState.value = diaryListLiveData.value!![position].id
        Log.d(Constants.TAG,"viewModel diary id: ${diaryListLiveData.value!![position].id}")
    }

}