package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ec.edu.uisek.githubclient.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment() {

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lista estática de repositorios (hardcoded)
        val repoList = listOf(
            Repository("Mi Primer Repositorio", "Esta es una descripción de prueba."),
            Repository("Proyecto Android", "Cliente de GitHub para Uisek."),
            Repository("Otro Proyecto", "Descripción de otro proyecto interesante.")
        )

        val adapter = RepoListAdapter(repoList)
        binding.rvRepoList.adapter = adapter

        binding.fabAddRepo.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewProjectFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
