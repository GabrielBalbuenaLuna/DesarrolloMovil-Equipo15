class Playlist(
  val nombrePlaylist: String,
  val autorPlaylist: String,
  val numeroCancionesPlaylist: Int,
  val cantidadSeguidoresPlaylist: Int,
  val numeroReproduccionesPlaylist: Int
  {
    init{
      println("Objeto Playlist creado!")
    }
  
  //Funcion para agregar cancion a la Playlist
  fun agregarCancionPlaylist(nombrePlaylist: String, numeroCancionesPlaylist: Int){
    numeroCancionesPlaylist + 1
    println("Canción añadida a $nombrePlaylist")
  }

  //Funcion para eliminar una cancion de la Playlist
  fun eliminarCancionPlaylist(nombrePlaylist: String, numeroCancionesPlaylist: Int){
    numeroCancionesPlaylist - 1
    println("Canción eliminada de $nombrePlaylist")
  }
}

