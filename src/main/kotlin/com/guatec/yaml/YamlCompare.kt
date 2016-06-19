package com.guatec.yaml

import com.esotericsoftware.yamlbeans.YamlReader
import java.io.FileReader

object YamlCompare {
    fun readMap(fileName: String) : Map<Any,Any> {
        println(fileName)
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

    fun Map<*,*>.getNested(nested: String, firstKey: Boolean = false): Any? {
        return getNested(nested.split("."), firstKey)
    }

    fun Map<*,*>.getNested(tree: List<String>, firstKey: Boolean = false): Any? {
        val key = getKey(tree[0])
        val value = this.get(key)

        if (tree.size == 1) {
            if (firstKey && value is Map<*,*>)
                return value.keys.first()
            return value
        }
        else if (value is Map<*,*>) {
            return value.getNested(tree.drop(1))
        }
        return null;
    }

    fun compare(files: List<String>, groupByKeyS:String, printKeys:List<String>) {
        val yamls = files.map { readMap(it) }

        val groupByKey = groupByKeyS.split(".")
        val grouped = yamls.groupBy {
            it.getNested(groupByKey)
        }

        printKeys.forEach { keyToPrint ->
            println("$keyToPrint:")
            grouped.keys.sortedBy { it.toString() }.forEach { groupKey ->
                val groupedEntry = grouped.get(groupKey)
                println("\t${groupKey}")
                groupedEntry?.forEach { yamlMap ->
                    //val name = yamlMap.getNested(nameKey, true)
                    val name = yamlMap["__yaml_file__"]
                    println("\t\t$name: ${yamlMap.getNested(keyToPrint)}")
                }
            }
        }
    }
}