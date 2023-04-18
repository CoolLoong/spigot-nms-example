import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

group = "com.github.coolloong"
version = "1.0.0-SNAPSHOT"
val artifact = "SpigotExamplePlugin"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
}

dependencies {
    //without NMS
    //compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    //use NMS
    compileOnly("org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT:remapped-mojang")

    implementation("org.jetbrains:annotations:21.0.0")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testImplementation("org.jetbrains:annotations:21.0.0")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks{
    shadowJar{
        archiveClassifier.set("")
        mergeServiceFiles()
    }

    jar{
        manifest {
            attributes["Main-Class"] = "com.github.coolloong.ExamplePlugin"
        }
    }

    build{
        dependsOn("shadowJar")
    }

    runServer {
        minecraftVersion("1.19.4")
    }
}

bukkit {
    name = "SpigotExamplePlugin"
    version = "1.0"
    description = "This is a example plugin"
    website = "https://github.com/CoolLoong"
    author = "CoolLoong"
    main = "com.github.coolloong.ExamplePlugin"
    apiVersion = "1.19"
    prefix = "SpigotExamplePlugin"

    // Other possible properties from plugin.yml (optional)
    /*load = BukkitPluginDescription.PluginLoadOrder.STARTUP // or POSTWORLD
    authors = listOf("Notch", "Notch2")
    depend = listOf("WorldEdit")
    softDepend = listOf("Essentials")
    loadBefore = listOf("BrokenPlugin")
    defaultPermission = BukkitPluginDescription.Permission.Default.OP // TRUE, FALSE, OP or NOT_OP
    provides = listOf("TestPluginOldName", "TestPlug")

    commands {
        register("test") {
            description = "This is a test command!"
            aliases = listOf("t")
            permission = "testplugin.test"
            usage = "Just run the command!"
            // permissionMessage = "You may not test this command!"
        }
        // ...
    }

    permissions {
        register("testplugin.*") {
            children = listOf("testplugin.test") // Defaults permissions to true
            // You can also specify the values of the permissions
            childrenMap = mapOf("testplugin.test" to true)
        }
        register("testplugin.test") {
            description = "Allows you to run the test command"
            default = BukkitPluginDescription.Permission.Default.OP // TRUE, FALSE, OP or NOT_OP
        }
    }*/
}

spigotRemap {
    spigotVersion.set("1.19.4")
    sourceJarTask.set(tasks.shadowJar) // or `tasks.shadowJar` if you use Shadow plugin.
}
