package com.example.mindWeather.model

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.util.*

open class DiaryDao(private val realm : Realm) {

    fun getAllDiary() : RealmResults<DiaryData> {
        return realm.where(DiaryData::class.java)
            .sort("createdAt", Sort.DESCENDING)
            .findAll()
    }

    fun selectDiary(id : String) : DiaryData {
        return realm.where(DiaryData::class.java)
            .equalTo("id", id)
            .findFirst() as DiaryData
    }

    fun addOrUpdateDiary(diaryData : DiaryData)  {
        realm.executeTransaction {
            if(diaryData.createdAt.equals("")) {
                diaryData.createdAt = Date()
                diaryData.updatedAt = Date()
            }
            diaryData.updatedAt = Date()
            it.copyToRealmOrUpdate(diaryData)
        }
    }

    fun deleteDiary(id : String) {
        realm.executeTransaction {
            it.where(DiaryData::class.java)
                .equalTo("id", id)
                .findFirst()?.let {
                    it?.deleteFromRealm()
                }
        }
    }

}
