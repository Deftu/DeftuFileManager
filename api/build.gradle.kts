plugins {
    java
    kotlin("jvm") version("1.6.21")
    val dgt = "1.4.1"
    id("xyz.deftu.gradle.tools") version(dgt)
    id("xyz.deftu.gradle.tools.blossom") version(dgt)
    id("xyz.deftu.gradle.tools.publishing") version(dgt)
}

publishing {
    repositories {
        mavenLocal()
    }
}
