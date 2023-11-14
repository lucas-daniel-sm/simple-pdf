plugins {
    `java-library`
    `maven-publish`
    id("io.freefair.lombok") version "8.4"
}

repositories {
    mavenCentral()
}

group = "dev.lucasmendes"
version = "0.0.1-SNAPSHOT"

tasks.named<Jar>("jar") {
    archiveBaseName.set(rootProject.name)
}

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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "simple-pdf"
            from(components["java"])
            pom {
                name = "Simple PDF"
                description = "Pdf library based on openpdf"
                url = "https://github.com/lucas-daniel-sm/simple-pdf"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://github.com/lucas-daniel-sm/simple-pdf/blob/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "lucas-daniel-sm"
                        name = "Lucas Mendes"
                        email = "contato@lucasmendes.dev"
                    }
                }
            }
        }
    }
}
