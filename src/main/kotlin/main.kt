import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import models.Cancion
import models.Playlist
import models.User
import java.io.File
import java.io.FileNotFoundException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume

fun main(args: Array<String>) = runBlocking {
    // Lista para guardar a los usuarios y contraseñas
    val users = mutableListOf<User>()
    // Listas temporales para guardar los usuarios y contraseñas desde los archivos
    val usuarios = mutableListOf<String>()
    val contras = mutableListOf<String>()
    // Variables para los archivos donde se almacenarán los usuarios y contraseñas
    val file1 = "usuarios.txt"
    val file2 = "passwords.txt"
    val usersFile = File(file1)
    val passFile = File(file2)
    // Leemos los archivos para verificar si ya existen o es necesario crearlos
    println("Leyendo los archivos donde se guardan los usuarios y contraseñas...")
    // Exception en caso de que no se pueda realizar la lectura de los archivos en donde se almacenan usuarios y contraseñas
    try {
        val leerUsuarios = usersFile.bufferedReader()// Aqui puede ocurrir la excepcion: FileNotFoundException
        val text1 = leerUsuarios.readLines()
        for(line in text1){ usuarios.add(line) }
        val leerPass = passFile.bufferedReader()// Aqui puede ocurrir la excepcion: FileNotFoundException
        val text2 = leerPass.readLines()
        for(line in text2){ contras.add(line) }
    } catch (e: FileNotFoundException){
        // Creamos nuevos archivos para almacenar a los usuarios y contraseñas
        println("Error al importar usuarios/contraseñas: $e")
        println("Se han creado nuevos archivos para guardar la información.")
        usersFile.createNewFile()
        passFile.createNewFile()
        usuarios.add("root")    // Agregamos las contraseñas que se tendrán por default
        contras.add("1234")
    }

    // Se instancia la Lista utilizando la clase usuario con los datos leidos desde los archivos
    val tam = usuarios.size-1
    for (num in 0..tam) {
        users.add(User(usuarios[num], contras[num]))
    }
    usuarios.clear()    // Se limpian las listas temporales de usuarios y contraseñas que se usaron para leer los archivos
    contras.clear()

    // Map de Artistas
    var artist = mutableMapOf( "001" to "Metallica", "010" to "Iron Maiden", "020" to "Black Sabbath")
    //Map de Canciones
    var songs = mutableMapOf<Int, Cancion>(
        1 to Cancion("One", "Metallica", "Rock", "and Justice for All", 7.27f, "1988"),
        2 to Cancion("Hero of the day", "Metallica", "Rock", "Load", 4.21f, "1996"),
        3 to Cancion("The Number of the Beast' You", "Iron Maiden", "Rock", "Number of the Beast", 4.50f, "1982"),
        4 to Cancion("Paranoid", "Black Sabbath", "Rock", "Paranoid", 2.48f, "1970"),
    )
    // Map de Albumes
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
                // Simulacion de request a un servidor
                try {
                    println("Iniciando recuperación de usuario")
                    val usuario = users.find{it.username == user}
                    if(usuario?.username == user.toString() && usuario.password == pass.toString()){ //Verificar que el usuario corresponda a la contraseña
                        val usuario = fetchUserCoroutine(user.toString(), pass.toString(), true)
                        println(usuario)
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
                                println("""
                                 ==================================
                                            CANCIONES
                                 ==================================
                                """.trimIndent())
                                for ((k, v) in songs) {
                                    println("$k. ${v.nombreCancion}")
                                }
                                println("==================================")
                                menuCanciones()
                                when(readLine()) {
                                    "1" -> {
                                        //Opcion para elegir cancion a reproducir y que se cambie descendentemente
                                        println("Numero de la cancion: ")
                                        var songNumber = readLine()?.toInt()
                                        if (songNumber != null) {
                                            while (songNumber > 0) {
                                                val previousSong = songs.get(songNumber)
                                                previousSong?.anteriorCancion()
                                                if(songNumber == 1) {
                                                    previousSong?.pausar()
                                                }
                                                songNumber--
                                            }
                                        }
                                    }
                                    "2" -> {
                                        //Opcion para reproducir cancion y se cambie ascendentemente
                                        println("Numero de la cancion: ")
                                        val songNumber = readLine()?.toInt()
                                        val songsSize = songs.size
                                        if (songNumber != null) {
                                            for(cambio in (songNumber)..songsSize) {
                                                val nextSong = songs.get(cambio)
                                                nextSong?.siguienteCancion()
                                                if (cambio == songsSize) {
                                                    nextSong?.pausar()
                                                }
                                            }
                                        }
                                    }
                                    "3" -> {
                                        //Modo de repeticion para que se repita la cancion hasta que el usuario desee detenerlo
                                        var modoRepeticion: Boolean = true
                                        println("Numero de la cancion: ")
                                        val songNumber = readLine()?.toInt()
                                        val chosenSong = songs.get(songNumber)
                                        var cont = 0
                                        while (modoRepeticion) {
                                            cont++
                                            chosenSong?.reperirCancion(cont)
                                            //se le da la opcion al usuario para detener la reproduccion
                                            println("\nPulsa p para detener reproduccion o cualquier otra tecla para continuar")
                                            val opcAleatoria = readLine()?.get(0)?.toUpperCase() ?: throw IllegalArgumentException()
                                            if(opcAleatoria == 'P') {
                                                modoRepeticion = false
                                            }
                                        }
                                    }
                                    "4" -> {
                                        //Modo aleatorio de reproduccion
                                        var modoAleatorio: Boolean = true
                                        while (modoAleatorio) {
                                            // se hace el calculo aleatorio de la cancion a reproducir despues
                                            val aleatorio = (1..songs.size).random()
                                            val chosenSong = songs.get(aleatorio)
                                            //se le da la opcion al usuario para detener la reproduccion
                                            println("\nPulsa p para detener reproduccion o cualquier otra tecla para continuar")
                                            val opcAleatoria = readLine()?.get(0)?.toUpperCase() ?: throw IllegalArgumentException()
                                            if(opcAleatoria == 'P') {
                                                modoAleatorio = false
                                            }
                                            chosenSong?.reproduccionAleatoria(modoAleatorio)
                                        }
                                    }
                                }
                            }
                            "4" -> { //Reproducción aleatoria
                                usuario.reproducirCancion("Swing Lynn", "Twin Cabins", "I'm sure")
                            }
                            "5" -> { //Añadir una playlist
                                print("Nombre de la playlist: ")
                                var nombrePlaylist = readLine().toString()

                                if(nombrePlaylist!!.isEmpty()) {
                                    println("Imposible añadir canción...")
                                    println("Error: se ingresó un campo vacío en la canción o artista")
                                }else {
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
                            }
                            "6" -> { //Añadir una cancion a la playlist
                                print("Nombre de la cancion: ")
                                var song = readLine().toString()
                                print("Nombre del artista: ")
                                var artista = readLine().toString()

                                // Se verifica que no se haya presionado enter sin ingresar los datos
                                if(song!!.isEmpty() || artista!!.isEmpty()) {
                                    println("Imposible añadir canción...")
                                    println("Error: se ingresó un campo vacío en la canción o artista")
                                } else{
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
                            "8" -> { //Ver models.Playlist
                                var c = 1
                                println("""
                                 ==================================
                                            PLAYLISTS
                                 ==================================
                                """.trimIndent())
                                // Se verifica si ya existen playlists añadidas para el usuario
                                if (userPlaylist.isEmpty()){
                                    println("$user aún no tienes ninguna playlist añadida.")
                                } else {
                                    for (playlist in userPlaylist){
                                        println("$c.- ${playlist.nombrePlaylist}")
                                        c++
                                    }
                                    print("Nombre de la playlist: ")
                                    var nomPlay = readLine().toString()

                                    for (playlist in userPlaylist) {
                                        if (nomPlay.equals(playlist.nombrePlaylist)) {
                                            playlist.verPlaylist()
                                        }
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
                    fetchUserCoroutine(user.toString(), pass.toString(), false)
                }
                } catch (exception: Exception) {
                    println("Usuario y contraseña no coinciden o no existe usuario")
                    println("Hubo un fallo: $exception")
                }
            }
            //Añadir un nuevo usuario
            "2" -> {
                print("Ingresa un nombre de usuario: ")
                val user = readLine()
                print("Ingresa una contraseña: ")
                val pass = readLine()
                // Se verifica que no se haya presionado enter sin ingresar los datos
                if(user!!.isEmpty() || pass!!.isEmpty()) {
                    println("Imposible crear usuario...")
                    println("Error: se ingresó un campo vacío en usuario o contraseña")
                } else{
                    users.add(User(user.toString(), pass.toString()))
                }
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
    | 5. Añadir models.Playlist        |
    | 6. Añadir Canción a playlist     |
    | 7. Eliminar cancion de playlist  |
    | 8. Ver models.Playlist           |
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
    | $artist                          |
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

//Menu para las acciones que puede hacer el usuario para las canciones
fun menuCanciones(){

    println("""
     -----------------------------------
    |            BEDU MUSIC             |
    |                                   |
    |                                   |
    | 1. Reproducir cancion(descendente)|
    | 2. Reproducir cancion(ascendente) |
    | 3. Reproducir una sola canción    |
    | 4. Reproducción aleatoria         |
    | 5. Regresar al menu anterior      |
    |                                   |
     -----------------------------------
     """)
    println(" ")
    print("Opcion: ")
}

// Se evalua la condicion para devolver fallo o estado exitoso, se simula la espera del request con Thead.sleep
private fun fetchUser(callback: Callback, username: String, password:String, isSuccess: Boolean) {
    Thread {
        Thread.sleep(3_000)

        if(isSuccess){
            callback.onSuccess(
                User(
                    username,
                    password
                ))
        }
        else {
            callback.onFailure(Exception("Excepción genérica"))
        }
    }.start()
}

//Se simula la recuperacion del usuario y este devolvera al usuario si las credenciales coinciden o lanzará una excepcion si hay un error
private suspend fun fetchUserCoroutine(username: String, password:String, isSuccess:Boolean): User = suspendCancellableCoroutine {
        cancellableContinuation ->
    fetchUser(object : Callback {
        override fun onSuccess(user: User) {
            cancellableContinuation.resume(user)
        }

        override fun onFailure(exception: Exception) {
            cancellableContinuation.resumeWithException(exception)
        }
    }, username, password, isSuccess)
}