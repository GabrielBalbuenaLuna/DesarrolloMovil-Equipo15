fun main(args: Array<String>) {

  //Map para guardad usuarios
  var users = mutableMapOf("root" to "1234", "Elias" to "5678")

  //Map de Artistas
  var artist = mutableMapOf( "001" to "Metallica", "010" to "Iron Maiden", "020" to "Black Sabbath")
  //Map de Canciones                         
  var songs = mutableMapOf( "001" to "One", "002" to "Hero of the day", "010" to "The Number of the Beast", "020" to "Paranoid")
  //Map de Albumes
  var records = mutableMapOf( "001" to "and Justice for All", "002" to "Load", "010" to "Number of the Beast", "020" to "Paranoid")


  //Menu de Inicio
  println(" ----------------------------------")
  println("|            BEDU MUSIC            |")
  println("|                                  |")
  println("| 1. Log In                        |")
  println("| 2. Register                      |")
  println("| 3. Close                         |")
  println("|                                  |")
  println(" ----------------------------------")

  val input = readLine()

  //Switch del menu
  when(input){
    "1" -> {
      var flag: Boolean = true
      print("Usuario: ")
      val user = readLine()
      print("Password: ")
      val pass = readLine()

      //Validar credenciales
      if(users[user].equals(pass)){ //Verificar que el usuario corresponda a la contraseña
        val usuario = User(user.toString(), pass.toString()) //Crear objeto usuario

        while(flag){
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
            "5" -> {
              flag = false
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
    "3" -> println("Proceso terminado...")
  }

}

//Imprimir menu de usuario
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
    | 5. Salir                         |
    |                                  |
     ----------------------------------
     """)
}

