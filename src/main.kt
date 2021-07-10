fun main(args: Array<String>) {

    //Map para guardar usuarios
    var users = mutableMapOf("root" to "1234", "Elias" to "5678")

    //Map de Artistas
    var artist = mutableMapOf( "001" to "Metallica", "010" to "Iron Maiden", "020" to "Black Sabbath")
    //Map de Canciones
    var songs = mutableMapOf( "001" to "One", "002" to "Hero of the day", "010" to "The Number of the Beast", "020" to "Paranoid")
    //Map de Albumes
    var records = mutableMapOf( "001" to "and Justice for All", "002" to "Load", "010" to "Number of the Beast", "020" to "Paranoid")

    val interpol = Artist(
        1263564,
        cancionesTop(12, "Do I wanna know?", "Sunshine"),
        discografia("Beautiful", 15, "1996")
    )

    //Esta lista guardara objetos playlist
    val userPlaylist: MutableList<Playlist> = mutableListOf()

    var flag: Boolean = true

    //Este while permite que se imprima constantemente la primer pantalla del menu
    while(flag){

        //Menu de Inicio
        println(" ----------------------------------")
        println("|            BEDU MUSIC            |")
        println("|                                  |")
        println("| 1. Log In                        |")
        println("| 2. Register                      |")
        println("| 3. Close                         |")
        println("|                                  |")
        println(" ----------------------------------")
        println(" ")
        print("Opcion: ")

        val input = readLine()

        //Switch del menu
        when(input){
            //Para logearse se utiliza la primera opcion y se validan credenciales
            "1" -> {
                var flagTwo: Boolean = true
                print("Usuario: ")
                val user = readLine()
                print("Password: ")
                val pass = readLine()

                //Validar credenciales
                if(users[user].equals(pass)){ //Verificar que el usuario corresponda a la contraseña
                    val usuario = User(user.toString(), pass.toString()) //Crear objeto usuario
                    //Una vez validado el usuario se pasa al siguiente while y se imprime otro menu
                    while(flagTwo){
                        println(menuLogIn(usuario.username, usuario.playlists.toString())) //Imprimir menu usuario
                        print("Opcion: ")
                        val opcion = readLine()//Escoger una opcion del menu
                        when(opcion){
                            "1" -> {
                                for ((k, v) in artist) {
                                    println("- $v")
                                }
                            }
                            "2" -> {
                                for ((k, v) in records) {
                                    println("- $v")
                                }
                            }
                            "3" -> {
                                for ((k, v) in songs) {
                                    println("- $v")
                                }
                            }
                            "4" -> { //Reproducción aleatoria
                                usuario.reproducirCancion("Swing Lynn", "Twin Cabins", "I'm sure")
                            }
                            "5" -> { //Añadir una playlist
                                print("Nombre de la playlist: ")
                                var nombrePlaylist = readLine().toString()

                                var miPlaylist = Playlist(
                                    nombrePlaylist,
                                    usuario.username,
                                    2,
                                    31,
                                    mutableMapOf("Evil" to "Interpol", "Someday" to "The Strokes")
                                )

                                userPlaylist.add(miPlaylist)
                                usuario.playlists++
                            }
                            "6" -> { //Añadir una cancion a la playlist
                                print("Nombre de la cancion: ")
                                var song = readLine().toString()
                                print("Nombre del artista: ")
                                var artista = readLine().toString()

                                var c = 1
                                println("""
                                 ==================================
                                            PLAYLISTS
                                 ==================================
                                """.trimIndent())
                                for (playlist in userPlaylist){
                                    println("$c.- ${playlist.nombrePlaylist}")
                                    c++
                                }
                                print("Nombre de playlist: ")
                                var nomPlayLi = readLine().toString()

                                for (playlist in userPlaylist){
                                    if (nomPlayLi.equals(playlist.nombrePlaylist)){
                                        playlist.agregarCancionPlaylist(song, artista)
                                    }
                                }
                            }
                            "7" -> { //Eliminar cancion de una playlist
                                var c = 1
                                println("""
                                 ==================================
                                            PLAYLISTS
                                 ==================================
                                """.trimIndent())
                                for (playlist in userPlaylist){
                                    println("$c.- ${playlist.nombrePlaylist}")
                                    c++
                                }
                                print("Nombre de playlist: ")
                                var nomPlay = readLine().toString()
                                print("Nombre de la cancion: ")
                                var song = readLine().toString()
                                print("Nombre del artista: ")
                                var artista = readLine().toString()

                                for (playlist in userPlaylist){
                                    if (nomPlay.equals(playlist.nombrePlaylist)){
                                        playlist.eliminarCancionPlaylist(song, artista)
                                    }
                                }
                            }
                            "8" -> { //Ver Playlist
                                var c = 1
                                println("""
                                 ==================================
                                            PLAYLISTS
                                 ==================================
                                """.trimIndent())
                                for (playlist in userPlaylist){
                                    println("$c.- ${playlist.nombrePlaylist}")
                                    c++
                                }
                                print("Nombre de playlist: ")
                                var nomPlay = readLine().toString()

                                for (playlist in userPlaylist){
                                    if (nomPlay.equals(playlist.nombrePlaylist)){
                                        playlist.verPlaylist()
                                    }
                                }
                            }
                            "9" -> {
                                flagTwo = false
                                usuario.cerrarSesion()
                            }
                        }
                    }
                    //Si los datos ingresados en el LogIn no son correctos
                }else{
                    println("Credenciales incorrectas..")
                }
            }
            //Añadir un nuevo usuario
            "2" -> {
                print("Ingresa un nombre de usuario: ")
                val user = readLine()
                print("Ingresa una contraseña: ")
                val pass = readLine()
                //
                users.put(user.toString(), pass.toString())
            }
            //Terminar programa
            "3" -> {
                println("Proceso terminado...")
                flag = false
            }
            else -> println("Ingresa una opcion valida")
        }
    }
}

//Imprimir menu de usuario, dependiendo del usuario se imprime un menu con diferentes atributos
fun menuLogIn(user: String, playlist: String){

    println("""
     ----------------------------------
    |            BEDU MUSIC            |
    |                                  |
    | Usuario: $user       Playlists: $playlist    
    |                                  |
    | 1. Ver Artistas                  |
    | 2. Ver Albumes                   |
    | 3. Ver Canciones                 |
    | 4. Reproducción aleatoria        |
    | 5. Añadir Playlist               |
    | 6. Añadir Canción a playlist     |
    | 7. Eliminar cancion de playlist  |
    | 8. Ver Playlist                  |
    | 9. Salir                         |
    |                                  |
     ----------------------------------
     """)
}

//Imprimir menu de usuario, dependiendo del usuario se imprime un menu con diferentes atributos
fun pantallaArtista(artist: String, topSongs: Int){

    println("""
     ----------------------------------
    |            BEDU MUSIC            |
    |                                  |
    | $artist          
    |                                  |
    | 1. Ver Artistas                  |
    | 2. Ver Albumes                   |
    | 3. Ver Canciones                 |
    | 4. Reproducción aleatoria        |
    | 5. Salir                         |
    |                                  |
     ----------------------------------
     """)
}