plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(
                providers.systemProperty("version").map(String::toInt)
                        .orElse(11)
                        .get())
        )
    }
}
