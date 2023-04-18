plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    //Plugin.yml create plugins
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    //A plugin that reobfuscate the NMS code
    id("io.typecraft.gradlesource.spigot") version "1.0.0"
    //The running environment used to test the plugin
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

group = "com.github.coolloong"
version = "1.0.0-SNAPSHOT"
val minecraftVersion = "1.19.4"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
}

dependencies {
    //without NMS
    //compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")

    /*
    use NMS,You need to build the local dependency with BuildTools.jar
    Download from https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
    Run: java -jar BuildTools.jar --rev 1.19.4 --remapped
    */
    compileOnly("org.spigotmc:spigot:$minecraftVersion-R0.1-SNAPSHOT:remapped-mojang")

    implementation("org.jetbrains:annotations:24.0.0")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    testImplementation("org.jetbrains:annotations:21.0.0")
    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
}

bukkit {
    name = "SpigotExamplePlugin"
    version = project.version.toString().split("-")[0]
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

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
    build {
        dependsOn("shadowJar")
    }
    runServer {
        minecraftVersion(minecraftVersion)
    }
    jar {
        enabled = false
    }
}

spigotRemap {
    spigotVersion.set(minecraftVersion)
    sourceJarTask.set(tasks.shadowJar) // or `tasks.shadowJar` if you use Shadow plugin.
}
