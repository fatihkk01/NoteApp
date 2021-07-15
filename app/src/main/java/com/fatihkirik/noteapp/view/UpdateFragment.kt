package com.fatihkirik.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.fatihkirik.noteapp.databinding.FragmentUpdateBinding
import com.fatihkirik.noteapp.model.Note
import com.fatihkirik.noteapp.viewmodel.NotesViewModel
import com.fatihkirik.noteapp.viewmodel.UpdateNoteViewModel


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UpdateNoteViewModel
    private var id: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(UpdateNoteViewModel::class.java)
        arguments.let {
            id = UpdateFragmentArgs.fromBundle(it!!).id
            println("Note Id -> " + id)
        }

        viewModel.getNoteById(id)

        if(viewModel.noteData.value!=null){
            println("noteData is not null")
        }
        else{
            println("noteData is null")
        }

        binding.btnUpdate.setOnClickListener {

            val noteId = id
            val header = binding.etHeader.text.toString()
            val content = binding.etContent.text.toString()

            viewModel.updateNote(Note(noteId,header,content))

            val action = UpdateFragmentDirections.actionUpdateFragmentToNotesFragment()
            Navigation.findNavController(it).navigate(action)

        }

        observeLiveData()

    }

    fun observeLiveData() {

        viewModel.noteData.observe(viewLifecycleOwner, Observer { noteData ->

            binding.etHeader.setText(noteData.header)
            println("Note Header -> " + noteData.header)
            binding.etContent.setText(noteData.content)
            println("Note Content -> " + noteData.content)

        })


        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
        })

    }


}