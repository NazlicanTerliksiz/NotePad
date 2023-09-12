package com.nazlican.notepad.ui.addNote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nazlican.notepad.R
import com.nazlican.notepad.common.viewBinding
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.databinding.FragmentAddNoteBinding

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    private val binding by viewBinding(FragmentAddNoteBinding::bind)
    private lateinit var refNotes: DatabaseReference
    private lateinit var viewModel: AddNoteViewModel
    private val args by navArgs<AddNoteFragmentArgs>()
    val noteList = arrayListOf<Notes>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempViewModel:AddNoteViewModel by viewModels()
        viewModel = tempViewModel

        val db = FirebaseDatabase.getInstance()
        refNotes = db.getReference("notes")

        val note = args.notes
        if (note==null){
            binding.buttonUpdate.visibility = View.INVISIBLE
            binding.buttonAdd.visibility = View.VISIBLE

            binding.buttonAdd.setOnClickListener {

                findNavController().popBackStack()
                saveNote()

            }

        }else{
            binding.buttonUpdate.visibility = View.VISIBLE
            binding.buttonAdd.visibility = View.INVISIBLE

            binding.editTextText.setText(note.note)
            binding.buttonUpdate.setOnClickListener{

                findNavController().popBackStack()
                updateNote(note)

            }
        }
    }
    private fun saveNote() {
        val detail = binding.editTextText.text.toString().trim()
        val referance = refNotes.push()
        val note = Notes(referance.key, detail)
        referance.setValue(note)
        Log.d("message", note.note_id.toString())
        Log.d("message", note.note.toString())
    }

    private fun updateNote(note:Notes) {
        val detail = binding.editTextText.text.toString().trim()
        val noteHashMap = HashMap<String, Any>()
        noteHashMap.put("note", detail)
        note.note_id?.let { refNotes.child(it).updateChildren(noteHashMap) }
    }
}