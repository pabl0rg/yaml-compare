package com.guatec.yaml

import com.beust.jcommander.Parameter
import java.util.*

class CommandLineOptions {
    @Parameter
    var parameters = ArrayList<String>();

    @Parameter(names = arrayOf("--check", "-c"), required = true, variableArity = true, description = "Comma-separated list of keys to check for conflicts")
    var printKeys: List<String> = emptyList();

    @Parameter(names = arrayOf("--group-by", "-g"), required = true, description = "The key to group by")
    var groupBy = "";

    @Parameter(names = arrayOf("--files", "-f"), required = true, variableArity = true, description = "The yaml files to compare")
    var files: List<String> = emptyList()

    @Parameter(names = arrayOf("--verbose", "-v"), description = "Print all values (The default only prints conflicts)")
    var verbose = false
}