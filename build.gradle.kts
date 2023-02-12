plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "4.0.4"
    application
}

group = "net.minearte.web"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

tasks.shadowJar {
    archiveFileName.set("Minearte-backend.jar")
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.3")
    implementation("com.github.Angelillo15:ConfigManager:1.4")
    implementation("org.yaml:snakeyaml:1.33")
    implementation("com.konghq:unirest-java:3.11.09")
    implementation("io.javalin:javalin:5.3.2")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation("org.projectlombok:lombok:1.18.20")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val run = "net.minearte.web.Server"

application {
    mainClass.set(run)
}

project.setProperty("mainClassName", "net.minearte.web.Server")