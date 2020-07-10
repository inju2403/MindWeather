package com.example.mindWeather.diary

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.mindWeather.diary.diaryDetail.DiaryDetailEvent
import com.example.mindWeather.model.pojo.ContentPOJO
import com.example.mindWeather.model.pojo.Diary
import com.example.mindWeather.model.repository.IDiaryRepository
import com.example.mindWeather.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel (
    private val repo: IDiaryRepository,
    uiContext: CoroutineContext
) : BaseViewModel<DiaryDetailEvent>(uiContext) {

    var diary = Diary()
    val diaryLiveData : MutableLiveData<Diary> by lazy {
        MutableLiveData<Diary>().apply {
            value = diary
        }
    }

    fun loadDiary(context: Context, id: String) = launch {
        diary = repo.getDiaryById(context, id)
        diaryLiveData.value = diary
//        Log.d(TAG,"loadDiary: ${diary} ")
    }

    fun addOrUpdateDiary(context: Context) = launch {
        val contentPOJO = ContentPOJO(diary.content)
        repo.updateDiary(context, contentPOJO,  diary.id)
    }

    fun deleteDiary(context: Context, id: String) = launch {
        repo.deleteDiary(context, id)
    }

    override fun handleEvent(event: DiaryDetailEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}