package models

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

  var number = duracionCancion.toString()

  //Funcion reproducir cancion
  fun reproducir() {
    var inicioMinutos = 0
    var inicioSegundos = 0
    var tiempoAcumulado: Long = 0
    //calculando los segundos de la duracion de la cancion
    val finalSegundos = (number.substring(number.indexOf('.') + 1)).toInt()
    //calculando los minutos de la duracion de la cancion
    val finalMinutos = (number.substring(0, number.indexOf('.'))).toInt()
    //se cancula el total de milisegundos que dura la cancion
    // para que no sea tan lenta la simulacion se acortan un poco
    val totalMiliSegundos = (((finalMinutos * 60) + finalSegundos) * 10).toLong()
    var rayitaGruesa = ""
    var rayitaDelgada = "──────────────────────────────"
    //Se dividen el total de milisegundos que avanzaran por cada "rayita" de musica
    val cambioRayita = (totalMiliSegundos / 30).toLong();
    println("""
              $nombreCancion - $autorCancion
              VOLUME: ▁▂▃▂▃▂▃▂▃▄▅▆▅▆▅▆▇ 100%
              ↻        ⊲  Ⅱ  ⊳        ↺""".trimIndent())
    for(cambio in 0..totalMiliSegundos step cambioRayita) {
      print("\r$inicioMinutos:$inicioSegundos $rayitaGruesa❍$rayitaDelgada $finalMinutos:$finalSegundos")
      Thread.sleep(cambioRayita)
      rayitaGruesa += "━"
      tiempoAcumulado += cambioRayita
      if (rayitaDelgada.length > 0) {
        rayitaDelgada = rayitaDelgada.substring(0, rayitaDelgada.length - 1)
      }
      inicioMinutos = (tiempoAcumulado / 600).toInt()
      inicioSegundos = (tiempoAcumulado % 600).toInt()
    }
  }

  //Funcion pausar la cancion
  fun pausar(){
    println()
    println()
    println("Se detuvo la reproducción")
    println()
  }

  //Funcion saltar a la siguiente canción
  fun siguienteCancion(){
    println()
    println()
    println("Siguiente Canción.")
    reproducir()
    println()
  }

  //Funcion saltar a la cancion previa
  fun anteriorCancion(){
    println()
    println()
    println("Canción Anterior.")
    reproducir()
    println()
  }

  //Funcion repetir la cancion en curso
  fun reperirCancion(numeroRepeticion: Int) {
    println()
    println()
    println("Repeticion numero $numeroRepeticion")
    reproducir()
    println()
  }

  //Funcion reproducir las canciones aleatoriamente
  fun reproduccionAleatoria(modoAleatorio: Boolean) {
    println("-----------------Modo aleatorio-----------------")
    if(modoAleatorio) {
      reproducir()
    } else {
      pausar()
    }
  }
}
