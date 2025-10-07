package student.projects.beatsyncprototype

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()

        val logo = findViewById<ImageView>(R.id.logoImage)
        val title = findViewById<TextView>(R.id.appTitle)
        val subtitle = findViewById<TextView>(R.id.appSubtitle)
        val button = findViewById<MaterialButton>(R.id.btnGetStarted)

        // 🔹 Fade-in animation
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 1200
            fillAfter = true
        }

        logo.startAnimation(fadeIn)
        title.startAnimation(fadeIn)
        subtitle.startAnimation(fadeIn)

        // 🔹 If already signed in, skip login
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        // 🔹 Button → LoginActivity
        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
