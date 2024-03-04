package com.analytiks.addon.appvisor.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.analytiks.addon.appvisor.R
import com.analytiks.addon.appvisor.databinding.ActivityVisorBinding


class AppVisorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisorBinding
    private val adapter by lazy {
        EventsAdapter {}
    }

    private fun updateUiState(emptyState: Boolean) {
        if (emptyState) {
            binding.visorsRecyclerView.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.visorsRecyclerView.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.visorsRecyclerView.layoutManager = layoutManager
        binding.visorsRecyclerView.adapter = adapter

        AppVisorDataCollector.getInstance().getEvents().observe(this) { logs ->
            updateUiState(logs.isEmpty())
            adapter.submitList(logs)
        }

        binding.tvLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://github.com/aminekarimii/analytiks/blob/dev/README.md"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.intent_queries_not_found_description),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        fun initialize(): LoggingAnalytiksInterceptor {
            return LoggingAnalytiksInterceptor(collector = AppVisorDataCollector.getInstance())
        }
    }
}