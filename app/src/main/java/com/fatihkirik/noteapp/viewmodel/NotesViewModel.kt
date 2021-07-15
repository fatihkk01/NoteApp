package com.fatihkirik.noteapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fatihkirik.noteapp.model.Note
import com.fatihkirik.noteapp.service.NoteListener
import com.fatihkirik.noteapp.service.NoteManager

class NotesViewModel : ViewModel() {
    val noteManager = NoteManager()
    val notes = MutableLiveData<List<Note>>()
    var error = MutableLiveData<String>()



    fun getNotes(){

        noteManager.getNotes(object : NoteListener{
            override fun NoteSuccess() {

            }

            override fun NoteListSuccess(noteList: ArrayList<Note>?) {
                notes.value = noteList
            }

            override fun NoteDataSuccess(note: Note?) {

            }

            override fun NoteFail(errorMessage: String) {
                error.value = errorMessage
            }

        })

    }



    fun deleteNote(id:String){

        noteManager.deleteNote(id,object : NoteListener{
            override fun NoteSuccess() {
                println("Note deleted !")
            }

            override fun NoteListSuccess(noteList: ArrayList<Note>?) {

            }

            override fun NoteDataSuccess(note: Note?) {

            }

            override fun NoteFail(errorMessage: String) {
                error.value = errorMessage
            }

        })

    }

}