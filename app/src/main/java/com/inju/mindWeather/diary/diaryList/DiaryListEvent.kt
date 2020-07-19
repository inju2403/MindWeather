package com.inju.mindWeather.diary.diaryList

sealed class DiaryListEvent {
    data class OnDiaryItemClick(val diaryId: String) : DiaryListEvent()
    object OnNewDiaryClick : DiaryListEvent()
    object OnStart : DiaryListEvent()
}