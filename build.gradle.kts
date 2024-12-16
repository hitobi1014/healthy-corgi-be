plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("kapt") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.allopen") version "1.9.22"

    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Database
    runtimeOnly("org.postgresql:postgresql")

    // Querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // Development Tools
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // Logging API
    // SLF4J Logging API
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Querydsl Logging
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.0")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core") // Mockito 제외
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // MockK 관련 라이브러리
    testImplementation("io.mockk:mockk:1.13.9") // MockK 코어 라이브러리
    testImplementation("com.ninja-squad:springmockk:4.0.2") // Spring 통합용 MockK

}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
}

tasks.withType<Test> {
    useJUnitPlatform()
}