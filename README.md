# 똑일기 (Android AI Diary App)    


## Project Description    

딥러닝을 활용한 NLP를 사용하여 한글어 감정 분석을 통해 사용자가 작성한 일기를 분석하여 감정 상태를 분석, 도출한 뒤, 사용자에게 결과를 그래프로 시각화하여 제공하여 피드백을 줄 수 있는 일기 안드로이드 애플리케이션    


## Background    

일기의 경우, 단순 기록으로만 끝나는 경우가 많은데 이를 넘어서 감정도와 레포트를 제공함으로써 사용자에게 동기부여 및 의미있는 기록을 돕고 사회적으로 긍정적인 결과를 기대할 수 있음



## Role    

안드로이드 모바일 어플리케이션 UI/UX, 아키텍쳐 설계 및 구축, 데이터 시각화 및 전처리, User contacts    


## Used Libraries
 - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) (Store and manage UI-related data)
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)  (Observable data)
 - [Retrofit](https://github.com/square/retrofit) (HTTP client)
 - [Kotlin Coroutine](https://github.com/Kotlin/kotlinx.coroutines) (Light-weight threads)
 - [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) (A powerful & easy to use chart library for Android)
 - [DiffUtil](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil) (A utility class that calculates the difference between two lists and outputs a list of update operations that converts the first list into the second one)    



## Architecture

<div><img src="https://user-images.githubusercontent.com/56947879/86445071-b8929600-bd4c-11ea-9135-f188bb72d92e.png" align="left" width="100%"></div>

This AI Diary app uses MVVM architecture. There is also a Repository layer, which is for interacting with API calls or DB transactions.

```kt

// View
viewModel!!.diaryLiveData.observe (this, Observer {
    // TODO
})

// ViewModel
var diary = Diary()
val diaryLiveData : MutableLiveData<Diary> by lazy {
    MutableLiveData<Diary>().apply {
        value = diary
    }
}
diary = repo.getData() // get data from API and/or DB

```

## Author

* **LEE SEUNGJU**
    * **Github** - (https://github.com/inju2403)
    * **Blog**    - (https://inju2403.blog.me)



## Licence
```
Copyright 2020 LEE SEUNGJU.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```