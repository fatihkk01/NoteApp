package com.fatihkirik.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatihkirik.noteapp.R
import com.fatihkirik.noteapp.adapter.NoteAdapter
import com.fatihkirik.noteapp.databinding.FragmentNotesBinding
import com.fatihkirik.noteapp.model.Note
import com.fatihkirik.noteapp.viewmodel.NotesViewModel
import com.google.firebase.Timestamp


class NotesFragment : Fragment() {

    private var noteList = ArrayList<Note>()
    private lateinit var viewModel:NotesViewModel
    private lateinit var noteAdapter:NoteAdapter
    private lateinit var binding: FragmentNotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(NotesViewModel::class.java)

        viewModel.getNotes()



        noteAdapter = NoteAdapter(noteList)

        binding.recyclerview.adapter = noteAdapter


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteList.get(viewHolder.adapterPosition)
                deleteNote(note.id!!)
                noteList.removeAt(viewHolder.adapterPosition)
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerview)

        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.notes.observe(viewLifecycleOwner, Observer { notes ->

            notes?.let {
                noteAdapter.updateNoteList(notes)
            }

        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun deleteNote(id:String){
        viewModel.deleteNote(id)
    }



}