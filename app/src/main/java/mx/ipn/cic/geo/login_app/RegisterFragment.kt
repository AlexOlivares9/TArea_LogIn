package mx.ipn.cic.geo.login_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewbinding.ViewBindings
import com.google.firebase.auth.FirebaseAuth
import mx.ipn.cic.geo.login_app.databinding.ActivityMainBinding
import mx.ipn.cic.geo.login_app.databinding.FragmentRegisterBinding
import java.util.regex.Pattern
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegistrar.setOnClickListener {
            val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            if ( email.isNotEmpty() && password.isNotEmpty()){
                if(validarPass(password)){
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if(it.isSuccessful) {
                                Toast.makeText(context, "Registro Adecuado", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
                            } else Toast.makeText(context, "Error al hacer el registro", Toast.LENGTH_SHORT).show()
                        }
                } else Toast.makeText(context, "La contraseña debe tener al menos 8 caractéres entre ellos una mayúscula, una minúscula, un número y un caractér especial", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(context, "Rellenar los campos", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun validarPass(pass: String): Boolean {
        val passwordRegex = Pattern.compile(
            "^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{8,}" + "$")
        return passwordRegex.matcher(pass).matches()
    }

}