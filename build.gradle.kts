plugins {
    kotlin("jvm") version "1.9.23"
}

group = "ru.barabo"
version = "1.0-SNAPSHOT"

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


    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.jasypt:jasypt:1.9.3")

    implementation("org.cryptacular:cryptacular:1.2.6")

    implementation("com.oracle.database.jdbc:ojdbc6:11.2.0.4")

    implementation("com.github.dbarabo:dbjdb:0.9.10")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}