package com.guatec.yaml

import com.esotericsoftware.yamlbeans.YamlReader
import com.guatec.yaml.YamlCompare.getNested
import java.io.FileReader

object YamlCompare {
    fun readMap(fileName: String) : Map<Any,Any> {
        val reader = YamlReader(FileReader(fileName))
        val obj = reader.read()
        if (obj is Map<*,*>) {
            val map = obj as MutableMap<Any, Any>
            map["__yaml_file__"] = fileName
            return map
        }
        return emptyMap()
    }

    fun Map<*,*>.getKey(default: Any): Any? {
        if (default == "*")
            return keys.first()
        return default
    }

    fun Map<*,*>.getNested(nested: String): Any? {
        return getNested(nested.split("."))
    }

    fun Map<*,*>.getNested(tree: List<String>): Any? {
        val key = getKey(tree[0])
        val value = this.get(key)

        if (tree.size == 1) {
            return value
        } else if (value is Map<*,*>) {
            if (tree.size == 2 && tree.last() == "*") {
                return value.keys
            }
            return value.getNested(tree.drop(1))
        }
        return null;
    }

    fun compare(files: List<String>, groupByKey:String, rawKeys:List<String>, verbose: Boolean=false) {
        val yamls = files.map {
            if (verbose)
                println("loading: $it")
            readMap(it)
        }
        println("-----")

        val grouped = yamls.groupBy {
            it.getNested(groupByKey)
        }

        //convert wildcard checkKeys by resolving available keys
        val realKeys: List<String> = rawKeys.flatMap { rawPrintKey ->
            var resolved: Any? = null
            if (rawPrintKey.endsWith(".*"))
                resolved = yamls.first().getNested(rawPrintKey)

            when (resolved) {
                is Iterable<*> -> resolved.map { leaf ->
                    rawPrintKey.replace(Regex("\\*$"), leaf as String)
                }
                else -> listOf(rawPrintKey)
            }
        }.filterNotNull()

        grouped.keys.sortedBy { it.toString() }.forEach { groupKey ->
            val group = grouped.get(groupKey)!!
            println("Group: ${groupKey}")

            if (verbose) {
                realKeys.forEach { keyToPrint ->
                    println("\t$keyToPrint")
                    group.forEach { yaml ->
                        val name = yaml["__yaml_file__"]
                        println("\t\t$name: ${yaml.getNested(keyToPrint)}")
                    }
                }
            } else {
                realKeys.forEach { keyToCheck ->
                    val groupsWithCollision = group.groupBy { it.getNested(keyToCheck) }
                            .filterValues { instancesInGroup -> instancesInGroup.size > 1 }

                    groupsWithCollision.entries.forEach { groupWithCollision ->
                        if (groupWithCollision.key != null) {
                            println("\tCollision @ $keyToCheck: ${groupWithCollision.key}")
                            groupWithCollision.value.forEach {
                                val name = it["__yaml_file__"]
                                println("\t\t$name")
                            }
                        }
                    }
                }
            }
        }
    }
}