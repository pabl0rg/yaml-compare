package com.guatec.yaml

fun main(args: Array<String>) {
    try {
        val printKeys = args[1].split(",")
        val files = args.slice(2..args.size - 1)

        YamlCompare.compare(files, args[0], printKeys)
    } catch (e: Exception) {
        println("\nusage: <group-by> <print> [yaml files]")
    }
    println()
}
