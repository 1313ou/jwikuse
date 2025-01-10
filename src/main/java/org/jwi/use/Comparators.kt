package org.jwi.use

object Comparators {

    @JvmField
    val cmpLOUpperFirst: java.util.Comparator<String?> = Comparator { s1: String?, s2: String? ->
        val c = s1!!.compareTo(s2!!, ignoreCase = true)
        if (c != 0) {
            return@Comparator c
        }
        s1.compareTo(s2)
    }

    @JvmField
    val cmpLOLowerFirst: Comparator<String?> = Comparator { s1: String?, s2: String? ->
        val c = s1!!.compareTo(s2!!, ignoreCase = true)
        if (c != 0) {
            return@Comparator c
        }
        -s1.compareTo(s2)
    }
}
