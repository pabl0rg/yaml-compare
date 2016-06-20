package com.guatec.yaml

import com.beust.jcommander.JCommander

fun main(args: Array<String>) {
    val options = CommandLineOptions()

    val jcommander = JCommander(options);
    try {
        jcommander.parse(*args)
    } catch (e: Exception) {
        jcommander.usage()
        System.exit(2)
    }

    YamlCompare.compare(options.files, options.groupBy, options.printKeys, options.verbose)

    println()
}
