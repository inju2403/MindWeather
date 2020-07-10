package com.example.mindWeather.diary.diaryList

sealed class DiaryListEvent {
    data class OnDiaryItemClick(val diaryId: String) : DiaryListEvent()
    object OnNewDiaryClick : DiaryListEvent()
    object OnStart : DiaryListEvent()
}