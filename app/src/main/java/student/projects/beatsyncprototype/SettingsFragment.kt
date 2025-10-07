package student.projects.beatsyncprototype

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val switchDarkMode = view.findViewById<Switch>(R.id.switchDarkMode)
        val switchNotifications = view.findViewById<Switch>(R.id.switchNotifications)
        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)

        // Dark Mode Toggle
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Notifications Toggle (placeholder for Firebase or local notifications)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->

        }

        // Logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return view
    }
}
