package com.fatihkirik.noteapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihkirik.noteapp.model.Note
import com.fatihkirik.noteapp.service.NoteListener
import com.fatihkirik.noteapp.service.NoteManager

class UpdateNoteViewModel : ViewModel() {

    val noteManager = NoteManager()
    var noteData = MutableLiveData<Note>()
    var error = MutableLiveData<String>()


    fun getNoteById(id:String){

        noteManager.getNoteById(id,object : NoteListener {
            override fun NoteSuccess() {
                TODO("Not yet implemented")
            }

            override fun NoteListSuccess(noteList: ArrayList<Note>?) {
                TODO("Not yet implemented")
            }

            override fun NoteDataSuccess(note: Note?) {
                noteData.value = note
            }

            override fun NoteFail(errorMessage: String) {
                error.value = errorMessage
            }

        })

    }

    fun updateNote(note:Note){

        noteManager.updateNote(note,object : NoteListener{
            override fun NoteSuccess() {

            }

            override fun NoteListSuccess(noteList: ArrayList<Note>?) {
                TODO("Not yet implemented")
            }

            override fun NoteDataSuccess(note: Note?) {
                TODO("Not yet implemented")
            }

            override fun NoteFail(errorMessage: String) {
                error.value = errorMessage
            }

        })

    }

}