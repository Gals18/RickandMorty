package romilp.rickandmortyapiapp.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romilp.newsly.Utils.ColorPicker
import romilp.rickandmortyapiapp.R
import romilp.rickandmortyapiapp.adapter.DetailAdapter
import romilp.rickandmortyapiapp.api.ApiService
import romilp.rickandmortyapiapp.databinding.ActivityDetailBinding
import romilp.rickandmortyapiapp.models.Episode
import romilp.rickandmortyapiapp.models.Result

class DetailActivity : AppCompatActivity() {
    private var mTAG = "Romil"
    lateinit var binding: ActivityDetailBinding
    lateinit var adapter: DetailAdapter
    private var dataCha = mutableListOf<Episode>()

    //var sa: Int = 1
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.apply {
            adapter = DetailAdapter(this@DetailActivity, dataCha)
            episodes.adapter = adapter

        }
        getList()
    }

    private fun getList() {
        val url = intent.getIntExtra("URL", 1)

        //Log.d(mTAG, "Request sent for $pageNum")
        val character = ApiService.dataInstance.getCharacterById(url)
        character.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val result = response.body()
                if (result != null) {
                    binding.apply {
                        dCharacterName.text = result.name
                        dOrigin.text = result.origin.name
                        dSpecies.text = result.species
                        dStatus.text = result.status
                        Glide.with(this@DetailActivity).load(result.image).into(dCharacterImage)
                        //dataCha.addAll(character.episode)
                        if (index < result.episode.size) {
                            var ok = result.episode[index].split("/").last().toInt()

                            getEpisode(ok)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.d(mTAG, "Error", t)
            }
        })
    }

    private fun getEpisode(s: Int) {

        val character = ApiService.dataInstance.getEpisodeById(s)
        character.enqueue(object : Callback<Episode> {
            override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                val episode = response.body()
                if (episode != null) {
                    //Log.d(mTAG, "Request sent for $s")
                    dataCha.add(episode)

                    getE()
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {
                Log.d(mTAG, "Error", t)
            }
        })
    }

    private fun getE() {

        val linearLayoutManager =
            StackLayoutManager(StackLayoutManager.ScrollOrientation.RIGHT_TO_LEFT, 2)
        linearLayoutManager.setPagerMode(false)
        //linearLayoutManager.setPagerFlingVelocity(3000)

        binding.episodes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //  binding.episodes.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                val visibleItemCount = linearLayoutManager.getFirstVisibleItemPosition()
                val pastVisibleItem = linearLayoutManager.itemCount
                val total = adapter.itemCount
//                Log.d(mTAG, "First visible Item: ${pastVisibleItem}")
//                Log.d(mTAG, "Total Items on Layout: ${index}")
//                Log.d(mTAG, "Total : ${total}")
                if (index < total) {
                    index++
                    getList()
                }

            }
        })

        binding.episodes.layoutManager = linearLayoutManager

    }
}