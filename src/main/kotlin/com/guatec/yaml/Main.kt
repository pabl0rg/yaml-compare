package com.guatec.yaml

fun main(args: Array<String>) {
    println("\n\nusage: <group-by> <print> [yaml files]\n")

    val printKeys = args[1].split(",")
    val files = args.slice(2..args.size-1)

    YamlCompare.compare(files, args[0], printKeys)
}
