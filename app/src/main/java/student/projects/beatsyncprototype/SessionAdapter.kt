package student.projects.beatsyncprototype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import student.projects.beatsyncprototype.R
import student.projects.beatsyncprototype.Session

class SessionAdapter(private val sessions: List<Session>) :
    RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    inner class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvSessionTitle)
        val artist: TextView = itemView.findViewById(R.id.tvSessionArtist)
        val image: ImageView = itemView.findViewById(R.id.ivSessionImage)
    }

    //Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_session, parent, false)
        return SessionViewHolder(view)
    }

    //Bind data to the view
    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.title.text = session.title
        holder.artist.text = session.artist

        //Load image using Glide
        Glide.with(holder.itemView.context)
            .load(session.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.image)
    }

    override fun getItemCount(): Int = sessions.size
}
