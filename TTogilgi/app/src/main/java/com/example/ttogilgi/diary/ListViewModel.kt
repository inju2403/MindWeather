package com.example.ttogilgi.diary

import androidx.lifecycle.MutableLiveData
import com.example.ttogilgi.diary.diaryList.DiaryListEvent
import com.example.ttogilgi.model.pojo.Diary
import com.example.ttogilgi.model.repository.IDiaryRepository
import com.example.ttogilgi.utils.BaseViewModel
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

    override fun handleEvent(event: DiaryListEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}