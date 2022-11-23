package romilp.newsfreshapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romilp.newsfreshapp.adapter.DataAdapter
import romilp.rickandmortyapiapp.R
import romilp.rickandmortyapiapp.api.ApiService
import romilp.rickandmortyapiapp.databinding.ActivityMainBinding
import romilp.rickandmortyapiapp.models.Result
import romilp.rickandmortyapiapp.models.Character


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DataAdapter
    private var result = mutableListOf<Result>()
    var pageNum = 1
    var totalResults = -1
    private var mTAG = "Romil"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {

            adapter = DataAdapter(this@MainActivity, result)
            characterList.adapter = adapter
            characterList.layoutManager = LinearLayoutManager(this@MainActivity)

            layoutManager()


            getList()
        }
    }

    private fun layoutManager() {

        val linearLayoutManager = LinearLayoutManager(this)
        binding.characterList.layoutManager = linearLayoutManager

        binding.characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = linearLayoutManager.childCount
                val pastVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount
//                Log.d(mTAG, "Total is  $total")
//                Log.d(mTAG, "visibleItemCount is  $visibleItemCount")
//                Log.d(mTAG, "pastVisibleItem is  $pastVisibleItem")
                if ((visibleItemCount + pastVisibleItem) >= total) {
                    pageNum++
                    getList()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getList() {
        //Log.d(mTAG, "Request sent for $pageNum")
        val character = ApiService.dataInstance.getCharacter(pageNum)
        character.enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                val character = response.body()
                if (character != null) {
                    result.addAll(character.results)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.d(mTAG, "Error", t)
            }

        })
    }
}