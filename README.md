## MvRx MVI Blueprint


Description
------------------------

A TMBD Kotlin app, following Clean Architecture and the MVI pattern on the UI layer using MvRx framework (Rx version).


Libraries used
------------------------

* [Hilt] - a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection

* [MvRx/Mavericks](https://github.com/airbnb/mavericks) - Android MVI framework built by Airbnb

* [Retrofit](https://square.github.io/retrofit/) - Http client for android

* [Moshi](https://github.com/square/moshi) - Modern JSON library for Android

* [Room](https://developer.android.com/jetpack/androidx/releases/room) - persistence library providing an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite

* [RxAndroid](https://github.com/ReactiveX/RxAndroid) - Reactive Extensions for Android

* [RxKotlin](https://github.com/ReactiveX/RxKotlin) - Kotlin Extensions for RxJava

* [RxBinding] - a set of support libraries to make the implementation of user interaction with the UI elements in Android easier

* [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns

* [Material](https://material.io/develop/android/docs/getting-started) - Material is a design system – backed by open-source code – that helps teams build high-quality digital experiences

* [Glide](https://bumptech.github.io/glide/) - Glide is a fast and efficient image loading library for Android focused on smooth scrolling

* [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API which provides utility on top of Android's normal Log class

* [JUnit4](https://junit.org/junit4/)

* [Mockk](https://mockk.io/) - mocking library for Kotlin


MvRx Pros and Cons
------------------------
### Pros
* Wrapping all the properties necessary to render a screen in a MvRxState, it makes it easier to scan over them.
* View decoupled of State.
* MvRx is lifecycle aware, because it’s built respecting Jetpack’s LifecycleOwner. This means no manual handle subscription needed. MvRxViewModel uses CompositeDisposable that’s automatically disposed in onCleared. Also, in the MvRxView, invalidate function — where you’re supposed to handle UI according to state changes — is only called when current View’s lifecyle is at least STARTED (that means STARTED and RESUMED).
* MvRxView offers specific subscribers - selectSubscribe, asyncSubscribe - which allow specific handling for each state property.

### Cons
* It doesn't support ViewModel handling for Activities.
* Being unable to create a ViewModel with empty or none State container.
* No explicit side effects.


License
-------
Copyright 2021 Cognizant Softvision.

Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. The ASF licenses this file to you under the Apache License, Version 2.0 (the “License”); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under the License.