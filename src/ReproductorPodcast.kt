interface ReproductorPodcast {

    fun reproducePodcast(){
        println("Se reproduce la cancion")
    }

    fun pausarPodcast(){
        println("Se pausa la cancion")
    }

    fun saltarSiguientePodcast(){
        println("Siguiente cancion")
    }

    fun regresarAnteriorPodcast(){
        println("Anterior cancion")
    }
}