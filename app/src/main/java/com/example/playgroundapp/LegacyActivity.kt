package com.example.playgroundapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playgroundapp.adapter.OneDelegate
import com.example.playgroundapp.adapter.ThreeDelegate
import com.example.playgroundapp.adapter.TwoDelegate
import com.example.playgroundapp.databinding.ActivityLegacyBinding
import com.example.playgroundapp.model.OneModel
import com.example.playgroundapp.model.ThreeModel
import com.example.playgroundapp.model.TwoModel
import com.github.pramahalqavi.adapterdelegate.adapter.DelegateAdapter

class LegacyActivity : ComponentActivity() {

    private var _binding: ActivityLegacyBinding? = null
    private val binding get() = _binding!!

    private val adapter = DelegateAdapter(
        delegates = listOf(
            OneDelegate(),
            TwoDelegate(),
            ThreeDelegate()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLegacyBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
    }

    private fun setupView() {
        with(binding) {
            rvItems.adapter = adapter
            rvItems.layoutManager = LinearLayoutManager(this@LegacyActivity)
        }
        adapter.submitList(items = listOf(
            OneModel("A"),
            TwoModel("Item 1", "Subtitle 1"),
            ThreeModel("Item 1", "Subtitle 1", "Description 1"),
            OneModel("B"),
            TwoModel("Item 2", "Subtitle 2"),
            ThreeModel("Item 2", "Subtitle 2", "Description 2"),
        ))
    }
}