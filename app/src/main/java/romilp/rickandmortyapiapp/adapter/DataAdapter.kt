package romilp.newsfreshapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import romilp.newsly.Utils.ColorPicker
import romilp.rickandmortyapiapp.R
import romilp.rickandmortyapiapp.activities.DetailActivity
import romilp.rickandmortyapiapp.models.Result

class DataAdapter(private val context: Context, private val characterList: List<Result>) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return DataViewHolder(view)
    }
//'onCreateViewHolder()' berfungsi untuk membuat objek ViewHolder.
// Di method ini kita juga akan menentukan layout XML yang akan digunakan untuk setiap item.
// 'onBindViewHolder()' berfungsi untuk menghubungkan data yang ada dengan objek ViewHolder.

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.characterName.text = characterList[position].name
        holder.status.text = characterList[position].status
        holder.species.text = characterList[position].species
        holder.location.text = characterList[position].location.name
        holder.gender.text = characterList[position].gender
        Glide.with(context).load(characterList[position].image).into(holder.characterImage)

        holder.container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL", characterList[position].id)
            context.startActivity(intent)
        }
    }
//‘onBindViewHolder()’ berfungsi untuk menghubungkan data yang ada dengan objek ViewHolder.
    override fun getItemCount(): Int {
        return characterList.size
    }
//method getItemCount() berfungsi untuk mengambil data list dari kelas character
    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterImage: ImageView = itemView.findViewById(R.id.characterImage)
        val characterName: TextView = itemView.findViewById(R.id.characterName)
        val status: TextView = itemView.findViewById(R.id.status)
        val species: TextView = itemView.findViewById(R.id.species)
        val location: TextView = itemView.findViewById(R.id.location)
        val gender: TextView = itemView.findViewById(R.id.gender)
        val container = itemView.findViewById<ConstraintLayout>(R.id.cardContainer)
    }
}
//RecyclerView memudahkan untuk menampilkan kumpulan data
// dalam jumlah besar secara efisien. Anda menyediakan data dan menentukan
// tampilan setiap item, dan library RecyclerView secara dinamis membuat elemen saat diperlukan.
