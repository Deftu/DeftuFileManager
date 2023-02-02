plugins {
    java
    kotlin("jvm") version("1.6.21")
    val dgt = "1.4.1"
    id("xyz.deftu.gradle.tools") version(dgt)
    id("xyz.deftu.gradle.tools.blossom") version(dgt)
    id("xyz.deftu.gradle.tools.publishing") version(dgt)
}

dependencies {
    // Logging
    implementation("org.slf4j:slf4j-api:${libs.versions.slf4j.get()}")
    implementation("org.slf4j:slf4j-simple:${libs.versions.slf4j.get()}")
}

publishing {
    repositories {
        mavenLocal()
    }
}
