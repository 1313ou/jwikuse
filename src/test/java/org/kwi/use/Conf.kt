package org.kwi.use

import edu.mit.jwi.Config
import org.kwi.use.KWI.Companion.fromFile
import org.kwi.use.KWI.Companion.makeURLFactory
import java.io.OutputStream
import java.io.PrintStream
import java.nio.charset.Charset

fun makeKWI(wnHomeEnv: String = "SOURCE"): KWI {
    val wnHome = System.getProperty(wnHomeEnv)
    val factory = System.getProperty("FACTORY")
    val configure = System.getProperty("CONFIGURE").toBoolean()
    val config = Config()
    config.charSet = Charset.defaultCharset()
    return KWI(fromFile(wnHome, config = if (configure) config else null, factory = makeURLFactory(factory)))
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