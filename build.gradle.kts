import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
}

group = "ru.tinkoff.edu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.4.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.32")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.32")

    // Flyway
    implementation("org.flywaydb:flyway-core:7.1.1")

    // Exposed
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc:2.4.5")
    implementation("org.postgresql:postgresql:42.2.19")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.31.1")

    // Swagger UI
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger2:3.0.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
