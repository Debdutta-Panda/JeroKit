package com.debduttapanda.jerokit.jerokit

object Routes{
    data class Route(
        val name: String,
        val args: String = ""
    ){
        val full get() = "$name$args"
    }
    val main = Route("main")
}