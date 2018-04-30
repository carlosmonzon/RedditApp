# RedditApp
RedditApp code challenge

## Code complilation instructions
Android Studio 3.1

### Building
```sh
./gradlew app:assembleDebug
```
### Testing
To run **unit** tests only:
```sh
./gradlew test
```
### UI tests
To run **instrumentation** tests on connected devices:

```sh
./gradlew connectedAndroidTest
```
### Jacoco Report 
Generate Jacoco coverage reports. Both unit and espresso tests.
```sh
./gradlew jacocoTestReport
```
### Designing the app using MVP

The app consist of one UI Screen:
* FrontPage - Screen which manage a list of RedditPosts

This screen was implemented using the following classes:

* A Contract class ([FrontPageContract](https://github.com/carlosmonzon/RedditApp/blob/master/app/src/main/java/com/cmonzon/redditapp/ui/frontpage/FrontPageContract.java))
* An [Activity](https://github.com/carlosmonzon/RedditApp/blob/master/app/src/main/java/com/cmonzon/redditapp/ui/MainActivity.java) which ensembles the fragment and presenter.
* A [Fragment](https://github.com/carlosmonzon/RedditApp/blob/master/app/src/main/java/com/cmonzon/redditapp/ui/frontpage/FrontPageFragment.java) which implements the view interface. It contains almost any business logic. It is using DataBinding in order to make the fragment even cleaner and light. Using the BindAdapters and layout data variables the fragment class it is easy to read and more maintainable.
* A presenter which implements the presenter interface in the corresponding contract. The presenter handles the UI and it also has a references to the repository class. The presenter contains important business logic for the screen, it handles the single observable from the respository in order to the filtering, sorting and validations required.

## This project uses:
- [RxJava2](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit](http://square.github.io/retrofit/) / [OkHttp](http://square.github.io/okhttp/)
- [Gson](https://github.com/google/gson)
- [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
- [Picasso](http://square.github.io/picasso/)
- [Espresso](https://google.github.io/android-testing-support-library/) for UI tests
- [Robolectric](http://robolectric.org/) for framework specific unit tests
- [Mockito](http://mockito.org/)
