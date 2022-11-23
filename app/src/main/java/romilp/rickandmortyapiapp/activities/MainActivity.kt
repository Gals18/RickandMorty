package romilp.newsfreshapp.activities
//kode diatas merupakan nama paket yang kita gunakan dalam folder activities.
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
//kode diatas merupakan kumpulan paket atau library yang didalmnya terdapat berbagai fungsi
//yang nantinya dapat digunakan sesuai fungsi yang dipanggil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DataAdapter
    private var result = mutableListOf<Result>()
    var pageNum = 1
    var totalResults = -1
    private var mTAG = "Romil"
//kode diatas merupakan pendeklarasian suatu variable yang nantinya akan digunakan
    
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
    //kode diatas merupakan metode onCreate dari setiap Aktivitas Android.
// Aktivitas memiliki kemampuan, dalam keadaan khusus,
// untuk memulihkan dirinya sendiri ke keadaan sebelumnya
// menggunakan data yang disimpan dalam bundel ini. dalam method ini kita juga
// membuat data binding yang nantinya akan digunakan sebagai proses membangun koneksi antarmuka.
  
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
//pada kode diatas merupakan suatu method yang dibuat untuk menampilkan tampilan recycle view.
// RecyclerView adalah ViewGroup yang berisi tampilan yang sesuai dengan data Anda. 
// ViewGroup sendiri juga merupakan tampilan, 
// jadi Anda menambahkan RecyclerView ke tata letak dengan cara yang sama seperti 
// menambahkan elemen UI lainnya. Setiap elemen individual dalam daftar ditentukan oleh 
// objek pemegang tampilan

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
//dan perintah method fun getlist digunakan menampilkan onjek listnya.
            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.d(mTAG, "Error", t)
            }

        })
    }
}
