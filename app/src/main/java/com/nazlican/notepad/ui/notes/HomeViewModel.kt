package com.nazlican.notepad.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.data.repo.NotesDaoRepository

class HomeViewModel:ViewModel() {
    private val noteRepo = NotesDaoRepository()

    var noteListRepo: MutableLiveData<List<Notes>> = MutableLiveData()

    fun uploadNotes(refNotes: DatabaseReference) = noteRepo.allNotes(refNotes,noteListRepo)
}