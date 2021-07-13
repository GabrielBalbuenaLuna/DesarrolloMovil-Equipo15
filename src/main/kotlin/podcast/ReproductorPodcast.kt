package podcast

abstract class ReproductorPodcast(val nombreUsuario: String){

    //Funcion reproducir Podcast
    abstract fun reproducirPodcast(name: String)


    //Funcion para ver los podcast
    abstract fun verMisPodcast(map: MutableMap<String, String>)

}