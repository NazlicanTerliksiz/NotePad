package com.nazlican.notepad.ui.addNote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.data.repo.NotesDaoRepository

class AddNoteViewModel : ViewModel() {
    private val noteRepo = NotesDaoRepository()

    var noteListRepo: MutableLiveData<List<Notes>> = MutableLiveData()

    fun uploadNewNotes(refNotes: DatabaseReference,detail:String) = noteRepo.savedNotes(refNotes,detail)

    fun uploadUpdatedNotes(note:Notes, refNotes: DatabaseReference, detail: String) = noteRepo.updatedNotes(note,refNotes,detail)
}