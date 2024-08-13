plugins {
    id("org.springframework.boot") version "2.7.18" //"3.1.12"
    id("io.spring.dependency-management") version "1.1.6"
    //id("io.spring.security.release") version "1.0.3"

    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.allopen") version "1.9.24"
}

group = "ru.barabo"
//version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        // change to point to your repo, e.g. http://my.org/repo
        url = uri(layout.buildDirectory.dir("E:/kotlin/repo/jcsp"))
    }

    mavenLocal()
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation("ru.cryptopro.jcp:jcp-jscp:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-jcrypto:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-jcpRevCheck:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-asn1p:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-asn1rt:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-jcp:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-xml:5.0.40363-A")

    implementation("ru.cryptopro.jcp:jcp-cpssl:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-revtools:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-rutoken:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-j6cf:5.0.40363-A")
    implementation("ru.cryptopro.jcp:jcp-j6oscar:5.0.40363-A")


    //implementation("org.slf4j:slf4j-api:2.0.13")
    //implementation("ch.qos.logback:logback-classic:1.5.6")
    //implementation("ch.qos.logback:logback-classic:1.2.13")

    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.jasypt:jasypt:1.9.3")

    implementation("org.cryptacular:cryptacular:1.2.6")

    implementation("com.oracle.database.jdbc:ojdbc6:11.2.0.4")

    implementation("com.github.dbarabo:dbjdb:0.9.10")

    /*implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")*/
    implementation("org.springframework.boot:spring-boot-starter-logging")


    implementation("org.springframework.boot:spring-boot-starter-actuator:2.7.18")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.18")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.18")
    implementation("org.springframework.boot:spring-boot-starter-data-ldap:2.7.18")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.18")

    implementation("org.springframework.security:spring-security-web:5.8.13")
    implementation("org.springframework.security:spring-security-core:5.8.13")
    implementation("org.springframework.security:spring-security-ldap:5.8.13")
    implementation("org.springframework.security:spring-security-data:5.8.13")
    implementation("org.springframework.ldap:spring-ldap-core:2.4.1")

    implementation("com.unboundid:unboundid-ldapsdk:6.0.11") //пусто

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.apache.directory.api:api-all:2.1.7")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}