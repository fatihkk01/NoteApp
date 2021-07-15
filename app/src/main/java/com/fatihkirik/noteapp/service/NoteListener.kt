package com.fatihkirik.noteapp.service

import com.fatihkirik.noteapp.model.Note
import com.google.firebase.firestore.DocumentSnapshot

interface NoteListener {
    fun NoteSuccess()
    fun NoteListSuccess(noteList:ArrayList<Note>?)
    fun NoteDataSuccess(note:Note?)
    fun NoteFail(errorMessage:String)

}