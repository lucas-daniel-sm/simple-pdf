plugins {
    `java-library`
    id("io.freefair.lombok") version "8.4"
}

repositories {
    mavenCentral()
}

group = "dev.lucasmendes"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("com.github.librepdf:openpdf:1.3.30")
    implementation("org.apache.commons:commons-lang3:3.13.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
