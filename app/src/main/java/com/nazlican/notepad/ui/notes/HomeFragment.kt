package com.nazlican.notepad.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nazlican.notepad.R
import com.nazlican.notepad.adapter.Adapter
import com.nazlican.notepad.common.viewBinding
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: Adapter
    private lateinit var refNotes:DatabaseReference
    private val viewModel:HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseDatabase.getInstance()
        refNotes = db.getReference("notes")

        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        viewModel.uploadNotes(refNotes)

       observe()

        binding.imageViewAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(null)
            findNavController().navigate(action)
        }

    }

    private fun navigateToAdd(notes: Notes){
        val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(notes)
        findNavController().navigate(action)
    }

    private fun observe(){
        viewModel.noteListRepo.observe(viewLifecycleOwner){
            if (it != null){
                adapter = Adapter(it,refNotes,::navigateToAdd)
                binding.rv.adapter = adapter
                adapter.notifyDataSetChanged()
            }else{
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_LONG).show()
            }
        }
    }


}