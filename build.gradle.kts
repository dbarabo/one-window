plugins {
    kotlin("jvm") version "1.9.23"
}

group = "ru.barabo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    //maven { url 'https://jitpack.io' }


    maven {
        // change to point to your repo, e.g. http://my.org/repo
        url = uri(layout.buildDirectory.dir("E:/kotlin/repo/jcsp"))
    }

    mavenLocal()
/*
    flatDir {
        dirs("E:/kotlin/repo/jcsp")
    }*/
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


    //implementation("org.slf4j':slf4j-api:1.7.+")
    //implementation("ch.qos.logback:logback-classic:1.1.11")

    implementation("org.cryptacular:cryptacular:1.2.6")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}