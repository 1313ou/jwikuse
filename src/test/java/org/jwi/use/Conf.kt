package org.jwi.use

import edu.mit.jwi.Config
import org.jwi.use.JWI.Companion.makeURLFactory
import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset

fun makeJWI(wnHomeEnv: String = "SOURCE"): JWI {
    val wnHome = System.getProperty(wnHomeEnv)
    val factory = System.getProperty("FACTORY")
    val configure = System.getProperty("CONFIGURE").toBoolean()
    val config = Config()
    config.charSet = Charset.defaultCharset()
    return JWI(wnHome, config = if (configure) config else null, factory = makeURLFactory(factory))
}

private val NULLPS = PrintStream(object : OutputStream() {
    override fun write(b: Int) { /* DO NOTHING */
    }
})

fun makePS(): PrintStream {
    // val props = System.getProperties()
    val verbose = true // TODO !props.containsKey("SILENT")
    return if (verbose) System.out else NULLPS
}