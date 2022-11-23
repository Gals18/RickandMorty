package romilp.rickandmortyapiapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import romilp.newsfreshapp.adapter.DataAdapter
import romilp.newsly.Utils.ColorPicker
import romilp.rickandmortyapiapp.R
import romilp.rickandmortyapiapp.models.Episode
import romilp.rickandmortyapiapp.models.Result

class DetailAdapter(var context: Context, var detailsList: MutableList<Episode>) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.episode_view, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.episodeNameTextView.text = "${detailsList[position].name} #"+detailsList[position].id.toString()
        holder.episodeAirDateTextView.text = detailsList[position].air_date
        holder.episodeNumberTextView.text = detailsList[position].episode
        holder.detailCardContainer.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
    }

    override fun getItemCount(): Int {
        return detailsList.size
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val detailCardContainer = itemView.findViewById<ConstraintLayout>(R.id.detailCardContainer)
        val episodeNameTextView = itemView.findViewById<TextView>(R.id.episodeNameTextView)
        val episodeAirDateTextView = itemView.findViewById<TextView>(R.id.episodeAirDateTextView)
        val episodeNumberTextView = itemView.findViewById<TextView>(R.id.episodeNumberTextView)
    }
}