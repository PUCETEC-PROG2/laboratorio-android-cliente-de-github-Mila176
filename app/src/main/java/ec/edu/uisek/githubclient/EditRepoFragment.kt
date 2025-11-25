package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ec.edu.uisek.githubclient.databinding.FragmentEditRepoBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.services.RepoUpdateRequest
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRepoFragment : Fragment() {

    private var _binding: FragmentEditRepoBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var currentRepo: Repo

    // Función para crear el fragment con el repositorio que queremos editar
    companion object {
        fun newInstance(repo: Repo): EditRepoFragment {
            val fragment = EditRepoFragment()
            val args = Bundle()
            args.putSerializable("repo", repo)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            currentRepo = it.getSerializable("repo") as Repo
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Llenar los campos con los datos actuales
        binding.etEditRepoName.setText(currentRepo.name)
        binding.etEditRepoName.isEnabled = false // NO EDITABLE según las instrucciones
        binding.etEditRepoDescription.setText(currentRepo.description ?: "")
        
        // Cuando presione "Guardar" - CON API REAL
        binding.btnSaveChanges.setOnClickListener {
            simularGuardado()
        }
    }

    private fun simularGuardado() {
        // Obtener el texto de los campos
        val nuevoNombre = binding.etEditRepoName.text.toString().trim()
        val nuevaDescripcion = binding.etEditRepoDescription.text.toString().trim()

        // Ya no validamos el nombre porque no es editable
        // Solo validamos que haya algún cambio en la descripción
        if (nuevaDescripcion == (currentRepo.description ?: "")) {
            Toast.makeText(context, "No hay cambios en la descripción", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar que está cargando
        binding.btnSaveChanges.isEnabled = false
        binding.btnSaveChanges.text = "Guardando..."
        Toast.makeText(context, "Guardando en GitHub...", Toast.LENGTH_SHORT).show()

        // Crear el objeto para enviar a la API (solo descripción es editable)
        val updateRequest = RepoUpdateRequest(
            name = null, // El nombre NO es editable según las instrucciones
            description = if (nuevaDescripcion != (currentRepo.description ?: "")) nuevaDescripcion else null
        )

        // Llamar a la API REAL de GitHub
        val apiService = RetrofitClient.gitHubApiService
        val call = apiService.updateRepository(
            owner = currentRepo.owner.login,
            repoName = currentRepo.name,
            updateRequest = updateRequest
        )

        // Enviar la petición a GitHub
        call.enqueue(object : Callback<Repo> {
            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                // Rehabilitar botón
                binding.btnSaveChanges.isEnabled = true
                binding.btnSaveChanges.text = "Guardar"

                if (response.isSuccessful) {
                    // ¡ÉXITO! El repositorio se actualizó en GitHub
                    val repoActualizado = response.body()
                    if (repoActualizado != null) {
                        Toast.makeText(context, "¡Actualizado en GitHub!", Toast.LENGTH_LONG).show()
                        
                        // Actualizar la lista local
                        (activity as? MainActivity)?.onRepoUpdated(repoActualizado)
                        
                        // Volver a la lista
                        parentFragmentManager.popBackStack()
                    }
                } else {
                    // Error al actualizar
                    val errorMsg = when (response.code()) {
                        401 -> " Token inválido o expirado"
                        403 -> " No tienes permisos para este repositorio"
                        404 -> "Repositorio no encontrado"
                        422 -> " Datos inválidos"
                        else -> "Error ${response.code()}"
                    }
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Repo>, t: Throwable) {
                // Rehabilitar botón
                binding.btnSaveChanges.isEnabled = true
                binding.btnSaveChanges.text = "Guardar"
                
                // Error de conexión
                Toast.makeText(context, "Sin conexión a internet", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}