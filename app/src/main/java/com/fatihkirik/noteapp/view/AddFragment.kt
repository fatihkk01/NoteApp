package com.fatihkirik.noteapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.fatihkirik.noteapp.R
import com.fatihkirik.noteapp.databinding.FragmentAddBinding
import com.fatihkirik.noteapp.model.Note
import com.fatihkirik.noteapp.service.NoteListener
import com.fatihkirik.noteapp.service.NoteManager


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var noteManager: NoteManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteManager = NoteManager()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAdd.setOnClickListener {

            val header = binding.etHeader.text.toString()
            val content = binding.etNote.text.toString()

            val note = Note(null,header,content)

            noteManager.addNote(note,object:NoteListener{
                override fun NoteSuccess() {
                    Toast.makeText(requireContext(),"Note added !",Toast.LENGTH_LONG).show()
                    val action = AddFragmentDirections.actionAddFragmentToNotesFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }

                override fun NoteListSuccess(noteList: ArrayList<Note>?) {
                    TODO("Not yet implemented")
                }

                override fun NoteDataSuccess(note: Note?) {
                    TODO("Not yet implemented")
                }

                override fun NoteFail(errorMessage: String) {
                    Toast.makeText(requireContext(),errorMessage,Toast.LENGTH_LONG).show()
                }

            })

        }

    }


}