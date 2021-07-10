data class Artist(
  val fans: Int,
  val cancionesTop: cancionesTop,
  val discografia: discografia
)

data class cancionesTop(
  val numero: Int,
  val cancion: String,
  val album: String
)


data class discografia(
  val nombreAlbum: String,
  val numeroCanciones: Int,
  val lanzamiento: String
)
