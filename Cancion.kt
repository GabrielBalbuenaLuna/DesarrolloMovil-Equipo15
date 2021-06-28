class Cancion(
  val nombreCancion: String,
  val autorCancion: String,
  val generoCancion: String,
  val albumCancion: String,
  val duracionCancion: Float,
  val anioLanzamientoCancion: String)
  {
    init{
      println("Objeto canción creado!")
    }
  
  //Funcion reproducir cancion
  fun reproducir(){
    println("Reproduciendo Canción.")
  }

  //Funcion pausar la cancion
  fun pausar(){
    println("Pausar Canción.")
  }

  //Funcion saltar a la siguiente canción
  fun siguienteCancion(){
    println("Siguiente Canción.")
  }

  //Funcion saltar a la cancion previa
  fun anteriorCancion(){
    println("Canción Anterior.")
  }

  //Funcion repetir la cancion en curso
  fun anteriorCancion(nombreCancion: String: String){
    println("Repetir Canción.")
  }

  //Funcion reproducir las canciones aleatoriamente
  fun reproduccionAleatoria(){
    println("Modo Aleatorio.")
  }
}

