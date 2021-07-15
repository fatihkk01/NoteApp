package com.fatihkirik.noteapp.service

import android.content.Context
import com.fatihkirik.noteapp.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NoteManager{

    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore

    init {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }


    fun addNote(note:Note, noteListener: NoteListener){

        val user = auth.currentUser
        if(user!=null){

            val noteDb = hashMapOf<String,Any>()
            noteDb.put("header",note.header)
            noteDb.put("content",note.content)

            db.collection("Users").document(user.uid).collection("Notes").add(noteDb).addOnSuccessListener {
                noteListener.NoteSuccess()
            }.addOnFailureListener { e ->

                if(e!=null){
                    noteListener.NoteFail(e.localizedMessage.toString())
                }

            }

        }

    }

    fun getNoteById(noteId:String,noteListener: NoteListener){
        val user = auth.currentUser

        if(user!=null){

            db.collection("Users").document(user.uid).collection("Notes").document(noteId).addSnapshotListener { value, error ->

                if(error!=null){
                    noteListener.NoteFail(error.localizedMessage.toString())
                }
                else{

                    if(value!=null){

                        val data = value.data

                        if(data!=null){

                            val id = noteId
                            val header = data.get("header") as String
                            val content = data.get("content") as String

                            val note = Note(id,header,content)

                            noteListener.NoteDataSuccess(note)

                        }

                    }


                }

            }

        }

    }

    fun updateNote(note:Note,noteListener: NoteListener){

        val user = auth.currentUser

        if(user!=null){

            val updatedNote = hashMapOf<String,Any>()
            updatedNote.put("header",note.header)
            updatedNote.put("content",note.content)

            db.collection("Users").document(user.uid).collection("Notes").document(note.id!!).update(updatedNote).addOnSuccessListener {

                noteListener.NoteSuccess()

            }.addOnFailureListener { e ->

                noteListener.NoteFail(e.localizedMessage.toString())

            }

        }

    }

    fun getNotes(noteListener: NoteListener){

        val noteList = ArrayList<Note>()

        val user = auth.currentUser
        if(user!=null){

            db.collection("Users").document(user.uid).collection("Notes").addSnapshotListener { snapshot, exception ->

                if(exception!=null){
                    noteListener.NoteFail(exception.localizedMessage.toString())
                }
                else{

                    if(snapshot!=null){
                        if(!snapshot.isEmpty){

                            noteList.clear()

                            val documents = snapshot.documents

                            for(document in documents){

                                val id = document.id
                                val header = document.get("header") as String
                                val content = document.get("content") as String

                                /*
                                println("Id -> "+id)
                                println("Header -> "+header)
                                println("Content -> "+content)
                                */

                                val note = Note(id,header,content)
                                noteList.add(note)
                            }

                            noteListener.NoteListSuccess(noteList)
                        }
                    }

                }

            }

        }

    }

    fun deleteNote(id:String,noteListener: NoteListener){

        val user = auth.currentUser

        if(user!=null){
            println("Note Id -> "+id)
            db.collection("Users").document(user.uid).collection("Notes").document(id).delete().addOnSuccessListener {
                noteListener.NoteSuccess()
            }.addOnFailureListener { e->
                if(e!=null){
                    noteListener.NoteFail(e.localizedMessage.toString())
                }
            }

        }

    }


}