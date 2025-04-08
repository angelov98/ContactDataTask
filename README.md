1. This Android app, built in Kotlin, fetches contact data from a public REST API and displays it in a master/detail UI layout.
The app allows users to view contact details, swipe-to-delete, and store contact data locally using Room for offline access. 
It supports features such as pull-to-refresh for data synchronization, user-friendly error handling

2. Setup Instructions
2.1 Clone this repository to your local machine.
https://github.com/angelov98/ContactDataTask.git
2.2 Open the project in Android Studio.
2.3 Make sure your project is set to use Kotlin 1.9.0 and the necessary dependencies as listed in build.gradle files.
2.4 Sync the project with Gradle to download required dependencies.
2.5 Build and run the app on an emulator or a physical device.

3. Notes and Tradeoffs
Local Data Persistence: The app uses Room for local data storage. When contacts are deleted, they are only removed from the local database and will reappear after the next data refresh.
API Data Fetching: The app fetches contact data via Retrofit and OkHttp, handling network errors gracefully with user-friendly error messages.
Profile Image Caching: Coil is used for profile image loading and caching, reducing unnecessary network calls.
UI Design: The app uses Jetpack Compose for the UI with Material3 components for modern design.
Dark Mode: The app supports system-wide dark mode.

4.External Libraries Used
Room: For local data persistence.
Retrofit: For network calls and data fetching from the REST API.
OkHttp: For handling network requests.
Coil: For image loading and caching.
Hilt: For dependency injection.
Lifecycle & LiveData: For managing UI lifecycle and data handling.
Navigation: For managing app navigation and the master/detail UI flow.
Material3: For Material Design components.
Coroutines: For managing background tasks and network calls.
