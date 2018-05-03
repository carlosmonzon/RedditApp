# RedditApp
RedditApp code challenge using android arch components

## Code compilation instructions
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
After executing the command line, the code coverge report can be found in this path:
```
/build/reports/jacoco/jacocoTestReport/html/index.html
```
### Designing the app using MVVM

The app consist of one UI Screen:
* FrontPage - Screen which manage a list of RedditPosts

This screen was implemented using the following classes:

* An [Activity](https://github.com/carlosmonzon/RedditApp/blob/master/app/src/main/java/com/cmonzon/redditapp/ui/MainActivity.java).
* A [Fragment](https://github.com/carlosmonzon/RedditApp/blob/master/app/src/main/java/com/cmonzon/redditapp/ui/frontpage/FrontPageFragment.java) It contains almost any business logic. It is using DataBinding in order to make the fragment even cleaner and light. Using the BindAdapters and layout data variables the fragment class it is easy to read and more maintainable.
* The ViewModel doesn't have any reference to the UI and it does have an instance of the repository. The ViewModel contains important business logic for the screen, it handles the single observable from the respository in order to the filtering, sorting and validations required.

## This project uses:
- [Android Arch Components](https://developer.android.com/topic/libraries/architecture/)
- [RxJava2](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit](http://square.github.io/retrofit/) / [OkHttp](http://square.github.io/okhttp/)
- [Gson](https://github.com/google/gson)
- [DataBinding](https://developer.android.com/topic/libraries/data-binding/)
- [Picasso](http://square.github.io/picasso/)
- [Espresso](https://google.github.io/android-testing-support-library/) for UI tests
- [Robolectric](http://robolectric.org/) for framework specific unit tests
- [Mockito](http://mockito.org/)
