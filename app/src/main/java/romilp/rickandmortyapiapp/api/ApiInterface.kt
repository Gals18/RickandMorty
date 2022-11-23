package romilp.rickandmortyapiapp.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import romilp.rickandmortyapiapp.models.Character
import romilp.rickandmortyapiapp.models.Episode
import romilp.rickandmortyapiapp.models.Result


const val BASE_URL = "https://rickandmortyapi.com/api/"

//https://rickandmortyapi.com/api/character/?page=1
//https://rickandmortyapi.com/api/character/1

interface ApiInterface {
    @GET("character/")
    fun getCharacter(@Query("page") page: Int): Call<Character>

    @GET("character/{character-id}")
    fun getCharacterById(
        @Path("character-id") characterId: Int
    ): Call<Result>

    @GET("episode/{episode-id}")
    fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Call<Episode>
}

object ApiService {
    val dataInstance: ApiInterface

    init {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        dataInstance = retrofit.create(ApiInterface::class.java)
    }
}