package com.debduttapanda.jerokit.jerokit
data class Route(
    val name: String,
    val args: String = ""
){
    val full get() = "$name$args"
}
