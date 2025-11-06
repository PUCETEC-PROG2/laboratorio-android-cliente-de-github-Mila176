package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RepositoryListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repository_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repositories = listOf(
            Repository("Repo 1", "Description 1"),
            Repository("Repo 2", "Description 2"),
            Repository("Repo 3", "Description 3")
        )
        adapter = RepositoryAdapter(repositories)
        recyclerView.adapter = adapter
    }
}
