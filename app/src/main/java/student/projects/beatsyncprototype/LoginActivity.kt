package student.projects.beatsyncprototype

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        setupGoogleSignIn()

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val continueButton = findViewById<MaterialButton>(R.id.btnContinue)
        val googleButton = findViewById<MaterialButton>(R.id.btnGoogle)
        val appleButton = findViewById<MaterialButton>(R.id.btnApple)

        // 🔹 Email Sign-In
        continueButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
            } else {
                signInWithEmail(email)
            }
        }

        // 🔹 Google Sign-In
        googleButton.setOnClickListener { signInWithGoogle() }

        // 🔹 Placeholder for Apple Sign-In
        appleButton.setOnClickListener {
            Toast.makeText(this, "Apple Sign-In coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        // If already signed in, go directly to MainActivity
        auth.currentUser?.let { goToMain() }
    }

    // 🔹 Firebase Email/Password Sign-In (with auto-registration)
    private fun signInWithEmail(email: String) {
        val password = "beatsync_default"
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    goToMain()
                } else {
                    // Auto-register if not existing
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { goToMain() }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }
    }

    // 🔹 Configure Google Sign-In
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Web client ID from Firebase
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    // 🔹 Launcher for Google Sign-In result
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnSuccessListener { goToMain() }
                .addOnFailureListener {
                    Toast.makeText(this, "Google login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign-in error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
