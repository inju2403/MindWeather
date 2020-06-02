package com.example.ttogilgi.diary.diaryList

sealed class DiaryListEvent {
    data class OnNoteItemClick(val position: Int) : DiaryListEvent()
    object OnNewNoteClick : DiaryListEvent()
    object OnStart : DiaryListEvent()
}