data class Artist(
    val nombre: String,
    val fans: Int,
    val bio: String,
    val numCancionesTop: Int,
    val numAlbums: Int,
    val numPlaylist: Int,
    val numArtistasSimilares: Int = 6
)