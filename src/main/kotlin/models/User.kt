package models

class User(
  val username: String,
  val password: String,
  var playlists: Int = 0,
  val seguidores: Int = 0)
{
  init{
    println("Objeto usuario creado!")
  }

  //Funcion reproducir cancion, recibe titulo, artista y album
  fun reproducirCancion(cancion: String, artista: String, album: String){
    println("-------------------------------------")
    println("""$artista - $cancion - $album
    0:35 ━❍────────────────────────── -5:32
          ↻        ⊲  Ⅱ  ⊳        ↺
    VOLUME: ▁▂▃▂▃▂▃▂▃▄▅▆▅▆▅▆▇ 100%""")
    println("-------------------------------------")
  }

  fun añadirPlaylist(){
    playlists++
  }

  //Cerrar sesion
  fun cerrarSesion(){
    println("Cerrando Sesion..")
  }
}

