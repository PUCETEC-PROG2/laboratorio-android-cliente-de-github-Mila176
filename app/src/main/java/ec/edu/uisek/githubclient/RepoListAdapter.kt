package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.edu.uisek.githubclient.databinding.RepoListItemBinding

class RepoListAdapter(private val repositories: List<Repository>) :
    RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = RepoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount() = repositories.size

    class RepoViewHolder(private val binding: RepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.tvRepoName.text = repository.name
            binding.tvRepoDescription.text = repository.description
        }
    }
}
