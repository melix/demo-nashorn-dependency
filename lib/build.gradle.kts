plugins {
    `java-library`
    `maven-publish`
}

version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    api("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:30.1.1-jre")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val nashorn by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = false
}

val runtimeElementsJava16 by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    extendsFrom(nashorn)
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 16)
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        outgoing {
            artifact(tasks.named("jar"))
        }
    }
}

val apiElementsJava16 by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    extendsFrom(nashorn)
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_API))
        attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 16)
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(LibraryElements.JAR))
        outgoing {
            artifact(tasks.named("jar"))
        }
    }
}

dependencies {
    nashorn("org.openjdk.nashorn:nashorn-core:15.0")
}

publishing {
    repositories {
        maven {
            name = "build"
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

val javaComponent = components["java"] as AdhocComponentWithVariants
javaComponent.addVariantsFromConfiguration(runtimeElementsJava16) {

}
javaComponent.addVariantsFromConfiguration(apiElementsJava16) {

}
