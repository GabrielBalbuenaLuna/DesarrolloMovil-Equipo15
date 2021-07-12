/*abstract class Podcast(
val nombrePodcast: String,
val autorPodcast: String,
val generoPodcast: String,
val albumPodcast: String,
val duracionPodcast: Float,
val anioLanzamientoPodcast: String)*/
abstract class Podcast{

    //Funcion reproducir Podcast
    fun reproducirPodcast(){
        println("Reproduciendo Podcast.")
    }

    //Funcion pausar la Podcast
    fun pausarPodcast(){
        println("Pausar Podcast.")
    }

    //Funcion saltar a la siguiente Podcast
    fun siguientePodcast(){
        println("Siguiente Podcast.")
    }

    //Funcion saltar a la Podcast previa
    fun anteriorPodcast(){
        println("Podcast Anterior.")
    }

    //Funcion repetir la Podcast en curso
    fun anteriorPodcast(nombrePodcast: String){
        println("Repetir Podcast.")
    }

    //Funcion reproducir las Podcasts aleatoriamente
    fun reproduccionAleatoriaPodcast(){
        println("Modo Podcast Aleatorio.")
    }
}