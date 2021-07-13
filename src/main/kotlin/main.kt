import javamodels.BarProgress
import kotlinx.coroutines.*
import models.Cancion
import models.Playlist
import models.User
import podcast.Podcast
import podcast.Podcast_DC
import progressbar.ConsoleProgressBar
import java.io.File
import java.io.FileNotFoundException
import java.security.SecureRandom
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.resume

fun main(args: Array<String>) = runBlocking {
    // -------------Usuarios por defecto-----------
    // -------User: root     Password: 1234--------
    val barra = BarProgress
    println("Iniciando programa...")
    barra.main()
    //Barra de Progreso
    val progreso = ConsoleProgressBar()
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
    }

    // Se instancia la Lista utilizando la clase usuario con los datos leidos desde los archivos
    if (usuarios.size==0) {
        usuarios.add("root")    // Agregamos las contraseñas que se tendrán por default
        contras.add("1234")
    }
    val tam = usuarios.size-1
    for (num in 0..tam) {
        users.add(User(usuarios[num], contras[num]))
    }
    usuarios.clear()    // Se limpian las listas temporales de usuarios y contraseñas que se usaron para leer los archivos
    contras.clear()

    //Map de Canciones
    var songs = mutableMapOf<Int, Cancion>(
        1 to Cancion("One", "Metallica", "Rock", "and Justice for All", 7.27f, "1988"),
        2 to Cancion("Hero of the day", "Metallica", "Rock", "Load", 4.21f, "1996"),
        3 to Cancion("The Number of the Beast", "Iron Maiden", "Rock", "Number of the Beast", 4.50f, "1982"),
        4 to Cancion("Paranoid", "Black Sabbath", "Rock", "Paranoid", 2.48f, "1970"),
    )

    //Lista de Objetos Artista
    val listArt = ArrayList < Artist > ()
    listArt.add(Artist(rand(3000,5000), "Artic Monkeys", cancionesTop(12, "Do I wanna know?", "AM"), discografia("Tranquility Base", 11, "2018")))
    listArt.add(Artist(rand(3000,5000), "The Strokes", cancionesTop(11, "Reptilia", "Room on fire"), discografia("The new abnormal", 15, "2020")))
    listArt.add(Artist(rand(3000,5000), "The Killers",cancionesTop(10, "Mr. Brightside", "Hot Fuss"), discografia("Imploding the mirage", 13, "2020")))
    listArt.add(Artist(rand(3000,5000), "Dayglow", cancionesTop(9, "Can I Call You Tonigh", "Fuzzybrain"), discografia("Harmony House", 11, "2021")))

    //Lista de Objetos Podcast
    val listPod = ArrayList < Podcast_DC > ()
    listPod.add(Podcast_DC("Creativo", "Roberto Mtz", "Sociedad y Cultura", "Descripcion...."))
    listPod.add(Podcast_DC("La Hora Feliz", "Cojo Feliz", "Comedia", "Descripcion...."))
    listPod.add(Podcast_DC("El podcast de A F", "Alex Fernandez", "Comedia", "Descripcion...."))
    listPod.add(Podcast_DC("En cortinas", "Luisito Comunica", "Comedia", "Descripcion...."))
    listPod.add(Podcast_DC("El pulso de la republica", "Chumel Torres", "Politica", "Descripcion...."))

    //Esta lista guardara objetos podcast
    val userPodcast: MutableMap<String, String> = mutableMapOf()
    listPod.forEach { userPodcast[it.nombrePodcast] = it.descripcion }

    //Esta lista guardara objetos playlist
    val userPlaylist: MutableList<Playlist> = mutableListOf()

    var flag: Boolean = true
    val pares = {num: Int -> num % 2 == 0} //FUNCION LAMBDA PARA CALCULAR PARES

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
                var key = pares(rand(2,4)) //Esta variable guarda un true o false dependiendo del numero aleatorio generado
                var flagTwo: Boolean = true
                print("Usuario: ")
                val user = readLine()
                print("Password: ")
                val pass = readLine()

                //Validar credenciales
                // Simulacion de request a un servidor
                try {
                    println("Iniciando recuperación de usuario")
                    GlobalScope.launch {
                        progreso.progressBar(19)
                    }
                    val usuario = users.find{it.username == user}

                    if(usuario?.username == user.toString() && usuario.password == pass.toString()){ //Verificar que el usuario corresponda a la contraseña
                        val usuario = fetchUserCoroutine(user.toString(), pass.toString(), key) //Pasamos un numero aleatorio, si es par continua de lo contrario da error
                        println(usuario)
                    //Una vez validado el usuario se pasa al siguiente while y se imprime otro menu
                    while(flagTwo){
                        println(menuLogIn(usuario.username, userPlaylist.size.toString())) //Imprimir menu usuario
                        print("Opcion: ")
                        val opcion = readLine()//Escoger una opcion del menu
                        when(opcion){
                            "1" -> {
                                println("""
                                 ==================================
                                            Artistas
                                 ==================================
                                """.trimIndent())
                                GlobalScope.launch {
                                    progreso.progressBar(30)
                                }
                                val sortedArtist = listArt.sortedWith(compareBy({ it.name })) //Ordenar la lista de Objetos(artistas) con funciones
                                sortedArtist.forEach{
                                        a -> println("• ${a.name} -- ${a.fans} fans")
                                    Thread.sleep(1300)
                                }
                            }
                            "2" -> {
                                println("""
                                 ==================================
                                            Álbumes
                                 ==================================
                                """.trimIndent())
                                GlobalScope.launch {
                                    progreso.progressBar(70)
                                }
                                val sortedArtist = listArt.sortedWith(compareBy({ it.cancionesTop.album })) //Ordenar la lista de Objetos(artistas)
                                sortedArtist.forEach{
                                        a -> println("• ${a.cancionesTop.album} -- ${a.name}")
                                    Thread.sleep(1200)
                                    println("• ${a.discografia.nombreAlbum} -- ${a.name}")
                                    Thread.sleep(1200)
                                }
                            }
                            "3" -> {
                                println("""
                                 ==================================
                                            CANCIONES
                                 ==================================
                                """.trimIndent())
                                GlobalScope.launch {
                                    progreso.progressBar(30)
                                }
                                for ((k, v) in songs) {
                                    println("$k. ${v.nombreCancion}")
                                    Thread.sleep(1200)
                                }
                                println("==================================")
                                menuCanciones()
                                when(readLine()) {
                                    "1" -> {
                                        //Opcion para elegir cancion a reproducir y que se cambie descendentemente
                                        print("Numero de la cancion: ")
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
                                        print("Numero de la cancion: ")
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
                                        print("Numero de la cancion: ")
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
                            "4" -> {
                                val aleatoria = rand(1,5)
                                when (aleatoria){
                                    1 -> {
                                        usuario.reproducirCancion("1901", "Phoenix", "Wolfgang Amadeus Phoenix")
                                    }
                                    2 -> {
                                        usuario.reproducirCancion("L.S.F", "Kasabian", "Kasabian")
                                    }
                                    3 -> {
                                        usuario.reproducirCancion("Hey Boy Hey Girl", "The Chemical Brothers", "Surrender")
                                    }
                                    4 ->{
                                        usuario.reproducirCancion("Song 2", "Blur", "The Best Of")
                                    }
                                    5 -> {
                                        usuario.reproducirCancion("Starlight", "Muse", "Black Holes & Revelations")
                                    }
                                }

                            }
                            "5" -> { //Añadir una playlist
                                print("Ingresa el nombre de la Playlist: ")
                                var nombrePlaylist = readLine().toString()

                                if(nombrePlaylist.isNullOrBlank()) {
                                    println("Imposible añadir canción...")
                                    println("Error: se ingresó un campo vacío en la canción o artista")
                                }else {
                                    var miPlaylist = Playlist(
                                        nombrePlaylist,
                                        usuario.username,
                                        2,
                                        rand(50,300),
                                        mutableMapOf("Evil" to "Interpol", "Someday" to "The Strokes")
                                    )
                                    userPlaylist.add(miPlaylist)
                                    usuario.playlists++
                                }
                            }
                            "6" -> { //Añadir una cancion a la playlist
                                print("Ingresa el nombre de la canción: ")
                                var song = readLine().toString()
                                print("Ingresa el nombre del artista: ")
                                var artista = readLine().toString()

                                // Se verifica que no se haya presionado enter sin ingresar los datos
                                if(song.isNullOrBlank() || artista.isNullOrBlank()) {
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
                                    print("Ingresa el nombre de la playlist: ")
                                    var nomPlayLi = readLine().toString()
                                    var bandera = false

                                    if(!nomPlayLi.isNullOrBlank()){
                                        for (playlist in userPlaylist){
                                            if (nomPlayLi.equals(playlist.nombrePlaylist)){
                                                playlist.agregarCancionPlaylist(song, artista)
                                                bandera = true
                                            }
                                        }
                                    }else{
                                        println("-> Se ingreso un campo vacio")
                                    }
                                    if (bandera){
                                        println("Cancion Añadida!")
                                    }else{
                                        println("-> Se ingreso mal el nombre de la Playlist o no existe")
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
                                print("Ingresa el nombre de la playlist: ")
                                var nomPlay = readLine().toString()
                                print("Ingresa el nombre de la cancion: ")
                                var song = readLine().toString()
                                print("Ingresa el nombre del artista: ")
                                var artista = readLine().toString()

                                if(!nomPlay.isNullOrBlank() && !song.isNullOrBlank() && !artista.isNullOrBlank()){
                                    for (playlist in userPlaylist){
                                        if (nomPlay.equals(playlist.nombrePlaylist)){
                                            playlist.eliminarCancionPlaylist(song, artista)
                                        }
                                    }
                                }else{
                                    println("Se ingreso un campo vació")
                                }


                            }
                            "8" -> { //Ver models.Playlist
                                var c = 1
                                println("""
                                 ==================================
                                            PLAYLISTS
                                 ==================================
                                """.trimIndent())

                                val sortedArtist = userPlaylist.sortedWith(compareBy({ it.nombrePlaylist })) //Ordenar la lista de Objetos(artistas) con funciones

                                // Se verifica si ya existen playlists añadidas para el usuario
                                if (userPlaylist.isEmpty()){
                                    println("$user aún no tienes ninguna playlist añadida.")
                                } else {
                                    sortedArtist.forEach{
                                            a -> println("•$c.- ${a.nombrePlaylist}")
                                        c++
                                    }
                                    /*
                                    for (playlist in userPlaylist){
                                        println("$c.- ${playlist.nombrePlaylist}")
                                        c++
                                    }*/
                                    print("Ingresa el nombre de la playlist: ")
                                    var nomPlay = readLine().toString()

                                    for (playlist in userPlaylist) {
                                        if (nomPlay.equals(playlist.nombrePlaylist)) {
                                            playlist.verPlaylist()
                                        }
                                    }
                                }
                            }
                            "9" ->{
                                println("""
                                 ==================================
                                            PODCAST
                                 ==================================
                                """.trimIndent())
                                var podcast1 = Podcast(usuario.username)
                                podcast1.verMisPodcast(userPodcast)

                            }
                            "10" -> {
                                println("""
                                 ==================================
                                            PODCAST
                                 ==================================
                                """.trimIndent())
                                var podcast1 = Podcast(usuario.username)
                                podcast1.reproducirPodcast("Creativo")
                            }
                            "11" -> {
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
                    if (key == false){
                        println("Hubo un fallo: $exception")
                    }else{
                        println("Usuario y contraseña no coinciden o no existe usuario")
                    }
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
                println("Cerrando programa...")
                barra.main()
                flag = false
            }
            else -> println("Ingresa una opcion valida")
        }
    }
    // Escritura de usuarios y contraseñas actualizados en los archivos
    // Vaciamos el contenido de los archivos en las listas temporales antes de escribir los datos
    users.forEach{
        usuarios.add(it.username)
        contras.add(it.password)
    }
    escribirArchivo(usersFile,usuarios)
    escribirArchivo(passFile,contras)
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
    | 9. Ver Podcast                   |
    | 10. Reproducir podcast           |
    | 11. Salir                        |
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
            callback.onFailure(Exception("Error 420 (Significa que el numero aleatorio dio falso)"))
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

fun escribirArchivo(archivo: File, datos: MutableList<String>){
    println("Iniciando escritura de $archivo...")
    archivo.writeText("")
    datos.forEach { archivo.appendText("$it\n") }
    println("La escritura del archivo fue un éxito :)")
}
//Funcion para obtener un generador aleatorio criptográficamente fuerte, usando la libreria SecureRandom
fun rand(start: Int, end: Int): Int {
    require(start <= end) { "Argumentos no validos!" }
    val random = SecureRandom()
    random.setSeed(random.generateSeed(20))

    return random.nextInt(end - start + 1) + start
}