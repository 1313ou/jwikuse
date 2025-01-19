package org.kwi.use

data class Relation<T>(
    val rel: String,
    val terms: Pair<T,T>
){
    override fun toString(): String {
        return "${terms.first} --$rel--> ${terms.second}"
    }
}