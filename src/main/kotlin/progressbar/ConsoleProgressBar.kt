package progressbar

class ConsoleProgressBar() {
    var animationChars = charArrayOf('|', '/', '-', '\\')

    fun progressBar(time: Long) {
        for (i in 0..100) {
            print("Cargando: " + i + "% " + animationChars[i % 4] + "\r")
            try {
                Thread.sleep(time)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        //println("Cargado: Completado!          ")
    }
}