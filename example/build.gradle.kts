plugins {
    application
    id("io.freefair.lombok") version "8.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
    implementation("com.github.librepdf:openpdf:1.3.30")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("dev.lucasmendes.simple_pdf.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.register("complete-clean", Delete::class) {
    delete(rootProject.file("example/pdf-out"))
    dependsOn("clean")
    group = "build"
}
