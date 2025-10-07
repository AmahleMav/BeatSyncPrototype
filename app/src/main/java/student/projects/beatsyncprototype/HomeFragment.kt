package student.projects.beatsyncprototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import student.projects.beatsyncprototype.SessionAdapter
import student.projects.beatsyncprototype.ApiClient

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SessionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.sessionRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initial empty adapter
        adapter = SessionAdapter(listOf())
        recyclerView.adapter = adapter

        // Fetch data from API
        fetchSessions()

        return view
    }

    private fun fetchSessions() {
        lifecycleScope.launch {
            try {
                val sessions = ApiClient.service.getSessions()
                adapter = SessionAdapter(sessions)
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to load sessions", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
