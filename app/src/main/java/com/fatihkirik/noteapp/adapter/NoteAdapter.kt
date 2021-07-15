package com.fatihkirik.noteapp.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.fatihkirik.noteapp.model.Note
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.fatihkirik.noteapp.R
import com.fatihkirik.noteapp.databinding.CustomRowBinding
import com.fatihkirik.noteapp.view.MainActivity
import com.fatihkirik.noteapp.view.NotesFragment
import com.fatihkirik.noteapp.view.NotesFragmentDirections

class NoteAdapter (val noteList:ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {


    class NoteHolder(var view:CustomRowBinding) :RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CustomRowBinding>(inflater, R.layout.custom_row,parent,false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        holder.view.note = noteList[position]

        holder.view.cardView.setOnClickListener {

            val action = NotesFragmentDirections.actionNotesFragmentToUpdateFragment(noteList[position].id!!)
            Navigation.findNavController(it).navigate(action)

        }


    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateNoteList(newNoteList:List<Note>){
        noteList.clear()
        noteList.addAll(newNoteList)
        notifyDataSetChanged()
    }

}