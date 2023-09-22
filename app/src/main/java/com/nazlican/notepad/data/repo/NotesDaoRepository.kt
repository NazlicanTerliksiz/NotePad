package com.nazlican.notepad.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.nazlican.notepad.data.Notes

class NotesDaoRepository {

    //var noteListRepo:MutableLiveData<List<Notes>> = MutableLiveData()

    //private lateinit var refNotes: DatabaseReference


    fun allNotes(refNotes: DatabaseReference, noteListLiveData: MutableLiveData<List<Notes>>) {
        refNotes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val noteList = ArrayList<Notes>()

                for (c in snapshot.children) {
                    val note = c.getValue(Notes::class.java)

                    if (note != null) {
                        note.note_id = c.key.toString()
                        noteList.add(note)
                        noteListLiveData.postValue(noteList)
                    } else {
                        noteListLiveData.postValue(null)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun savedNotes(refNotes: DatabaseReference, detail: String) {

        val referance = refNotes.push()
        val note = Notes(referance.key, detail)
        referance.setValue(note)
        Log.d("message", note.note_id.toString())
        Log.d("message", note.note.toString())
    }

    fun updatedNotes(note: Notes, refNotes: DatabaseReference, detail: String) {

        val noteHashMap = HashMap<String, Any>()
        noteHashMap.put("note", detail)
        note.note_id?.let {
            refNotes.child(it).updateChildren(noteHashMap)
        }
    }
}