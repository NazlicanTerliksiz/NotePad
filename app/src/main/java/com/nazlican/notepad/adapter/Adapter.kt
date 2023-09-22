package com.nazlican.notepad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.nazlican.notepad.data.Notes
import com.nazlican.notepad.databinding.ListItemBinding

class Adapter(
    private val noteList: List<Notes>,
    private val refNotes: DatabaseReference,
    private val onClick: (Notes) -> Unit,

) : RecyclerView.Adapter<Adapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Notes) {
            with(binding) {
                noteItemText.text = note.note
                imageViewDelete.setOnClickListener {
                    Snackbar.make(it, "Silmek istiyor musun?", Snackbar.LENGTH_LONG)
                        .setAction("Evet") {
                            refNotes.child(note.note_id!!).removeValue()
                            notifyDataSetChanged()
                        }.show()
                }
            }
            itemView.setOnClickListener {
                onClick.invoke(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note)
    }

}