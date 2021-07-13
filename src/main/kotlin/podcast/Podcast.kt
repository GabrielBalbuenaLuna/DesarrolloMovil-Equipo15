package podcast

class Podcast(val usuario: String): ReproductorPodcast(usuario) {


    override fun reproducirPodcast(nombre: String) {
        println("""
            ----------------------------
            |   ▐▓█▀▀▀▀▀▀▀▀▀█▓▌░▄▄▄▄▄░  |
            |   ▐▓█░░▀░░▀▄░░█▓▌░█▄▄▄█░  |
            |   ▐▓█░░▄░░▄▀░░█▓▌░█▄▄▄█░  |
            |   ▐▓█▄▄▄▄▄▄▄▄▄█▓▌░█████░  |
            |   ░░░░▄▄███▄▄░░░░░█████░  |
                     $nombre            
            ----------------------------
         0:35 ────────────────────────── -5:32
        """.trimIndent())
    }
    override fun verMisPodcast(map: MutableMap<String, String>) {
        for ((nombrePodcast, descripcionPodcast) in map) {
            println("""
                 -------
                |   🎼  |   $nombrePodcast
                |   🎸  |   $descripcionPodcast
                |   📻  |
                 -------
            -----------------------------------------
            """.trimIndent())
        }
    }
}