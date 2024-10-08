import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "me.davipccunha.tests"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly(fileTree("libs") { include("*.jar") })
    compileOnly("net.md-5:bungeecord-chat:1.8-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    compileOnly(fileTree("D:\\Local Minecraft Server\\plugins") { include("bukkit-utils.jar") })

    compileOnly(fileTree("D:\\Minecraft Dev\\artifacts\\") { include("moderation-api.jar") })
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set("${project.name}.jar")

    destinationDirectory.set(file("D:\\Local Minecraft Server\\plugins"))
}

bukkit {
    name = project.name
    prefix = "Chat" // As shown in console
    apiVersion = "1.8"
    version = "${project.version}"
    main = "me.davipccunha.tests.chatchannels.ChatChannelsPlugin"
    depend = listOf("bukkit-utils")
    softDepend = listOf("moderation")
    description = "Plugin that separates the chat into different channels and format it accordingly."
    author = "Davi C"

    commands {
        register("global") {
            aliases = listOf("g")
            description = "Envia uma mensagem para todos os jogadores online"
        }

        register("local") {
            aliases = listOf("l")
            description = "Envia uma mensagem para os jogadores próximos"
        }

        register("tell") {
            aliases = listOf("msg")
            description = "Envia uma mensagem privada para um jogador"
        }

        register("responder") {
            aliases = listOf("r", "resp")
            description = "Envia uma mensagem privada à última pessoa que te enviou uma mensagem privada"
        }

        register("staffchat") {
            aliases = listOf("sc")
            description = "Envia uma mensagem para todos os membros da equipe"
        }
    }
}

