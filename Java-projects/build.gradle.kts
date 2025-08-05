plugins {
    java
    kotlin("jvm") version "2.0.21" apply false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Ensure Gradle compiles with Java 23
    }
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<JavaCompile> {
        options.release.set(21) // Ensure Java 21 compatibility
    }
}
