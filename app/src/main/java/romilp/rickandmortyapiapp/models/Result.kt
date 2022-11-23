package romilp.rickandmortyapiapp.models

data class Result(
    val created: String,
    val episode: List<String>,
    val episodes: List<Episode>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)