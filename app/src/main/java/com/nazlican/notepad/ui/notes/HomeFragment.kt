package com.nazlican.notepad.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nazlican.notepad.R
import com.nazlican.notepad.adapter.Adapter
import com.nazlican.notepad.common.viewBinding
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: Adapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var refNotes:DatabaseReference
    val noteList = arrayListOf<Notes>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tempViewModel:HomeViewModel by viewModels()
        viewModel = tempViewModel

        val db = FirebaseDatabase.getInstance()
        refNotes = db.getReference("notes")
        allNotes()


        binding.imageViewAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(null)
            findNavController().navigate(action)
        }


        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter = Adapter(noteList,refNotes,::navigateToAdd)
        binding.rv.adapter = adapter
    }

    private fun navigateToAdd(notes: Notes){
        val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(notes)
        findNavController().navigate(action)
    }

    private fun allNotes(){
        refNotes.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                noteList.clear()

                for(c in snapshot.children){
                    val note = c.getValue(Notes::class.java)

                    if(note != null){
                        note.note_id = c.key.toString()
                        noteList.add(note)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}