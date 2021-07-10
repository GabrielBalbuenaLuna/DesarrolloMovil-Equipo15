open class Playlist(
  val nombrePlaylist: String,
  private val autorPlaylist: String,
  private val numeroCancionesPlaylist: Int,
  private val cantidadSeguidoresPlaylist: Int,
  //val numeroReproduccionesPlaylist: Int,
  private val playlist: MutableMap<String, String>
)
{
  init{
    println("--> Playlist creada!")
  }

  //Funcion para agregar cancion a la Playlist
  fun agregarCancionPlaylist(añadirCancion: String, añadirArtista: String){
    numeroCancionesPlaylist + 1
    playlist[añadirCancion] = añadirArtista
    println("--> Canción añadida a $nombrePlaylist")
  }

  //Funcion para eliminar una cancion de la Playlist
  fun eliminarCancionPlaylist(cancion: String, artista: String){
    numeroCancionesPlaylist - 1
    playlist.remove(cancion, artista)
    println("--> Canción eliminada de $nombrePlaylist")
  }

  //Funcion para ver las canciones de la playlist
  fun verPlaylist(){
    println("""
        ---------------------------
        |        ROCK 101         |
        |    ┈┈▂▂▂▂┏━┓▂▂▂▂▂       |
        |    ┈╱┈┈┈╱┛┈┗╱┈┈┈╱▏      |
        |    ▕▔▔▔▔▔▔▔▔▔▔▔▔▏▏      |
        |    ┈▏╭━╮┏━━┓╭━╮▕▕       |
        |    ┈▏┃╳┃┣━━┫┃╳┃▕▕       |
        |    ┈▏╰━╯▔▔▔▔╰━╯▕╱       |
        |    ┈▔▔▔▔▔▔▔▔▔▔▔         |
        ---------------------------
                    °°°°
              $nombrePlaylist
        De $autorPlaylist - Seguidores: $cantidadSeguidoresPlaylist
        """.trimIndent())
    for ((k, v) in playlist) {
      println("""
                 -------
                |   🎼  |   $k
                |   🎸  |   $v
                |   📻  |
                 -------
            -----------------------------------------
            """.trimIndent())
    }
  }
}