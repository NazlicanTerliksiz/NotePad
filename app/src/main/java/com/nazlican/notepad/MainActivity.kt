package com.nazlican.notepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nazlican.notepad.common.viewBinding
import com.nazlican.notepad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}