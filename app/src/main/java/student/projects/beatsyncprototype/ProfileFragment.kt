package student.projects.beatsyncprototype

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val ivProfilePic = view.findViewById<ImageView>(R.id.ivProfilePic)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val btnEditProfile = view.findViewById<MaterialButton>(R.id.btnEditProfile)
        val btnChangePassword = view.findViewById<MaterialButton>(R.id.btnChangePassword)

        // Display user info
        tvName.text = user?.displayName ?: "No Name Set"
        tvEmail.text = user?.email ?: "No Email"

        // Edit Display Name
        btnEditProfile.setOnClickListener {
            val editText = EditText(requireContext())
            editText.hint = "Enter new display name"

            AlertDialog.Builder(requireContext())
                .setTitle("Edit Display Name")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newName = editText.text.toString().trim()
                    if (newName.isNotEmpty()) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(newName)
                            .build()
                        user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                tvName.text = newName
                                android.widget.Toast.makeText(
                                    requireContext(),
                                    "Profile updated successfully",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Change Password
        btnChangePassword.setOnClickListener {
            val editText = EditText(requireContext())
            editText.hint = "Enter new password"

            AlertDialog.Builder(requireContext())
                .setTitle("Change Password")
                .setView(editText)
                .setPositiveButton("Update") { _, _ ->
                    val newPassword = editText.text.toString().trim()
                    if (newPassword.length < 6) {
                        android.widget.Toast.makeText(
                            requireContext(),
                            "Password must be at least 6 characters long",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        user?.updatePassword(newPassword)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                android.widget.Toast.makeText(
                                    requireContext(),
                                    "Password updated successfully",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        return view
    }
}
