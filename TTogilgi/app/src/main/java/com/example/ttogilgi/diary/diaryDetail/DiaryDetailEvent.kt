package com.example.ttogilgi.diary.diaryDetail

sealed class DiaryDetailEvent {
    data class OnDoneClick(val contents: String) : DiaryDetailEvent()
    object OnDeleteClick : DiaryDetailEvent()
    object OnDeleteConfirmed : DiaryDetailEvent()
    data class OnStart(val diaryId: String) : DiaryDetailEvent()
}
