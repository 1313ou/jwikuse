package org.jwi.use

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream

class JWITests {

    @Test
    fun walkWord() {
        jwi!!.walk(word!!, PS)
    }

    companion object {

        private val PROPS = System.getProperties()

        private val VERBOSE = true // TODO !System.getProperties().containsKey("SILENT")

        private val PS: PrintStream = if (VERBOSE) System.out else PrintStream(object : OutputStream() {
            override fun write(b: Int) {
                //DO NOTHING
            }
        })

        private var word: String? = null

        private var jwi: JWI? = null

        @JvmStatic
        @BeforeAll
        @Throws(IOException::class)
        fun init() {
            word = System.getProperty("WORD")
            val wnHome = System.getProperty("SOURCE")
            // val config: Config = Config()
            // config.charSet = Charset.defaultCharset()
            // jwi = JWI(wnHome, config)
            jwi = JWI(wnHome)
        }
    }
}
