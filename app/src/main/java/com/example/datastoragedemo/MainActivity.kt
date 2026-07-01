package com.example.datastoragedemo



import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.datastoragedemo.databinding.ActivityMainBinding

import java.io.BufferedReader

import java.io.File

import java.io.InputStreamReader



class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPref: android.content.SharedPreferences



    companion object {

        private const val PREFS_NAME = "MyPrefs"

        private const val KEY_USERNAME = "username"

        private const val KEY_AGE = "age"

        private const val KEY_NOTIFICATIONS = "notifications"

        private const val FILE_NAME = "note.txt"

    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)



        // Initialize SharedPreferences
        sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)



        // Set up click listeners

        binding.btnPrefSave.setOnClickListener { savePreferences() }

        binding.btnPrefLoad.setOnClickListener { loadPreferences() }

        binding.btnPrefClear.setOnClickListener { clearPreferences() }



        binding.btnFileSave.setOnClickListener { saveNoteToFile() }

        binding.btnFileLoad.setOnClickListener { loadNoteFromFile() }

        binding.btnFileClear.setOnClickListener { clearNote() }



        // Load data on start to show previously saved values

        loadPreferences()

        loadNoteFromFile()

    }



    // ==================== SharedPreferences Methods ====================



    private fun savePreferences() {
        val username = binding.etUsername.text.toString()
        val ageText = binding.etAge.text.toString()
        val age = ageText.toIntOrNull() ?: 0
        val notifications = binding.cbNotifications.isChecked

        sharedPref.edit {
            putString(KEY_USERNAME, username)
            putInt(KEY_AGE, age)
            putBoolean(KEY_NOTIFICATIONS, notifications)
        }

        Toast.makeText(this, "Preferences saved!", Toast.LENGTH_SHORT).show()
    }



    private fun loadPreferences() {

        val username = sharedPref.getString(KEY_USERNAME, "") ?: ""

        val age = sharedPref.getInt(KEY_AGE, 0)

        val notifications = sharedPref.getBoolean(KEY_NOTIFICATIONS, false)



        binding.etUsername.setText(username)

        binding.etAge.setText(if (age == 0) "" else age.toString())

        binding.cbNotifications.isChecked = notifications

    }



    private fun clearPreferences() {
        sharedPref.edit {
            clear()
        }
        binding.etUsername.setText("")
        binding.etAge.setText("")
        binding.cbNotifications.isChecked = false
        Toast.makeText(this, "Preferences cleared!", Toast.LENGTH_SHORT).show()
    }



    // ==================== Internal Storage Methods ====================



    private fun saveNoteToFile() {
        val note = binding.etNote.text.toString()
        try {
            openFileOutput(FILE_NAME, MODE_PRIVATE).use { fos ->
                fos.write(note.toByteArray())
            }
            Toast.makeText(this, "Note saved to internal storage", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(this, "Error saving note: ${e.message}", Toast.LENGTH_SHORT).show()

        }

    }



    private fun loadNoteFromFile() {
        val file = File(filesDir, FILE_NAME)
        if (!file.exists()) {
            // File doesn't exist, clear the EditText (or leave empty)
            binding.etNote.setText("")
            return
        }



        try {

            openFileInput(FILE_NAME).use { fis ->

                InputStreamReader(fis).use { reader ->

                    BufferedReader(reader).use { bufferedReader ->

                        val stringBuilder = StringBuilder()

                        var line: String?

                        while (bufferedReader.readLine().also { line = it } != null) {

                            stringBuilder.append(line).append('\n')

                        }

                        binding.etNote.setText(stringBuilder.toString().trimEnd())

                    }

                }

            }

        } catch (e: Exception) {

            e.printStackTrace()

            Toast.makeText(this, "Error loading note: ${e.message}", Toast.LENGTH_SHORT).show()

        }

    }



    private fun clearNote() {
        binding.etNote.setText("")
        // Optionally delete the file:
        val file = File(filesDir, FILE_NAME)

        if (file.exists()) {

            file.delete()

            Toast.makeText(this, "Note file deleted", Toast.LENGTH_SHORT).show()

        }

    }

} 