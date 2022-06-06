package mx.ipn.cic.geo.login_app

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import mx.ipn.cic.geo.login_app.databinding.FragmentLoginBinding
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCrearCuenta.setOnClickListener {

            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
            // Acción para crear cuenta.
        }
        // Se agrega el listener para el action de mostrar el mapa.
        binding.buttonIniciarSesion.setOnClickListener {
            // TODO: Agregar validación del nombre de usuario y contraseña (Firebase).
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            if ( email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Inicio correcto", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_LoginFragment_to_MapsFragment)
                    } else {
                        Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_permanecer)
                    }
                }
            }
            else Toast.makeText(context, "Rellenar los campos", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}