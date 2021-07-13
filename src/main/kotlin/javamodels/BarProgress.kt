package javamodels

import java.lang.Exception
import kotlin.Throws
import kotlin.jvm.JvmStatic

object BarProgress {
    @Throws(Exception::class)
    @JvmStatic
    fun main() {
        var i = 0
        while (i < 21) {
            print("[")
            for (j in 0 until i) {
                print("█")
            }
            for (j in 0 until 20 - i) {
                print(" ")
            }
            print("] " + i * 5 + "%")
            if (i < 20) {
                print("\r")
                Thread.sleep(300)
            }
            i++
        }
        println()
    }
}