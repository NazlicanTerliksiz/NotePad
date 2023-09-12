package com.nazlican.notepad.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Notes(var note_id:String?="", var note:String?=""):Parcelable
