plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

dependencies {
    implementation("io.ktor:ktor-server-cio:2.3.6")

    implementation("io.ktor:ktor-serialization:2.3.6")
    implementation("io.ktor:ktor-serialization-kotlinx-cbor:2.3.6")

    implementation("io.ktor:ktor-server-websockets:2.3.6")

    implementation(project(":lib"))
    implementation("io.ktor:ktor-server-auth-jvm:2.3.6")
    implementation("io.ktor:ktor-server-core-jvm:2.3.6")
}

kotlin {

}