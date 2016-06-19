import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.kotlin.*

val repos = repos("https://jitpack.io")


val p = project {

    name = "yaml-compare"
    group = "com.guatec"
    artifactId = name
    version = "0.1"

    sourceDirectories {
        path("src/main/kotlin")
    }

    sourceDirectoriesTest {
        path("src/test/kotlin")
    }

    dependencies {
        //compile(file("lib/kotlin-cli-master-1.0-SNAPSHOT.jar"))
        compile("com.esotericsoftware.yamlbeans:yamlbeans:1.08")
    }

    dependenciesTest {
        compile("org.testng:testng:6.9.9")

    }

    assemble {
        jar {
            fatJar = true
            manifest {
                attributes("Main-Class", "com.guatec.yaml.MainKt")
            }
        }
    }

    application {
        mainClass = "com.guatec.yaml.MainKt"
    }
}
