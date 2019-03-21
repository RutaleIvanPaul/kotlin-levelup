package com.example.ivanpaulrutale.kotlinone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*

class MainActivity : AppCompatActivity() {
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET)?:
                intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
        if( notePosition!= POSITION_NOT_SET)
            displayNote()
        else{
            createNewNote()
        }

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourses.adapter = adapterCourses

        val textBoxes = arrayListOf<EditText>(editNoteText,editNoteTitle)

        for(index in 0..textBoxes.lastIndex){
            textBoxes[index].addTextChangedListener(object: TextWatcher{
                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    invalidateOptionsMenu()
                }

            })
        }

    }

    private fun createNewNote() {
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        editNoteTitle.setText(note.title)
        editNoteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_save -> {
                saveNote()
                val activityIntent = Intent(this,NoteListActivity::class.java)
                startActivity(activityIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        saveNote()
        ++notePosition
        displayNote()
        invalidateOptionsMenu()

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem!=null){
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }
        val saveMenuItem = menu?.findItem(R.id.action_save)
        if(notePosition == DataManager.notes.lastIndex){
            if (saveMenuItem!=null){
                saveMenuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                saveMenuItem.isEnabled = false
            }
        }
        if(editNoteText.text.isNotEmpty() && editNoteTitle.text.isNotEmpty()){
            if (saveMenuItem!=null){
                saveMenuItem.icon = getDrawable(R.drawable.ic_save_black_24dp)
                saveMenuItem.isEnabled = true
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        if(editNoteText.text.isNotEmpty() && editNoteTitle.text.isNotEmpty()) {
            val note = DataManager.notes[notePosition]
            note.title = editNoteTitle.text.toString()
            note.text = editNoteText.text.toString()
            note.course = spinnerCourses.selectedItem as CourseInfo
        }
        else{
            DataManager.notes.remove(DataManager.notes.last())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(NOTE_POSITION,notePosition)
    }
}
