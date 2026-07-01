# DataStorageDemo

A simple Android application demonstrating fundamental data storage techniques: **SharedPreferences** and **Internal Storage**.

## Features

### 1. User Preferences (SharedPreferences)
- Stores lightweight key-value pairs.
- Persists user data such as **Username**, **Age**, and **Notification Toggle**.
- Data remains available even after the app is closed and reopened.
- Includes functionality to **Save**, **Load**, and **Clear** preferences.

### 2. Note (Internal Storage)
- Saves and retrieves text data from a file in the app-private directory.
- Demonstrates file I/O operations in Kotlin using `openFileOutput` and `openFileInput`.
- Uses `BufferedReader` and `InputStreamReader` for efficient reading.
- Includes functionality to **Save to File**, **Load from File**, and **Clear** the note.

## Technologies Used
- **Kotlin**: Primary programming language.
- **View Binding**: For safe and easy interaction with UI elements.
- **Material Design Components**: Using `MaterialCardView`, `TextInputLayout`, and `TextInputEditText` for a modern UI.
- **Android KTX**: Utilizing `sharedPref.edit { ... }` for idiomatic preference management.

## How it Works
- **SharedPreferences**: Data is stored in an XML file within the app's internal storage (`/data/data/<package_name>/shared_prefs/`).
- **Internal Storage**: The note is saved as `note.txt` in the app's private files directory (`/data/data/<package_name>/files/`).

## Setup
1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an emulator or physical device.

---
*Created as part of the Android Data Storage Codelab.*
