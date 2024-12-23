import org.jetbrains.kotlin.gradle.utils.extendsFrom

plugins {
    `java-library`
    `maven-publish`
    idea
    alias(libs.plugins.neoforged.moddev)
    kotlin("jvm") version "2.0.21"
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}

val mod_id: String by project
val mod_version: String by project
val mod_group_id: String by project
val mod_name: String by project
val mod_license: String by project
val mod_authors: String by project
val mod_description: String by project

val parchment_mappings_version: String by project
val parchment_minecraft_version: String by project
val minecraft_version: String by project
val minecraft_version_range: String by project
val neo_version: String by project
val neo_version_range: String by project
val mod_loader: String by project
val loader_version_range: String by project

version = mod_version
group = mod_group_id

repositories    {
    mavenLocal()

    maven {
        name = "BulkIt! API"
        setUrl("https://repo.repsy.io/mvn/asch/bulkit-api")
    }

    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }

    maven {
        name = "Jared's maven"
        setUrl("https://maven.blamejared.com/")
    }

    maven {
        name = "ModMaven"
        setUrl("https://modmaven.dev")
    }

    maven {
        setUrl("https://maven.teamresourceful.com/repository/maven-public/")
    }
}

base {
    archivesName = mod_id
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

sourceSets {
    register("datagen") {
        compileClasspath += main.get().output + main.get().compileClasspath
        runtimeClasspath += main.get().output + main.get().runtimeClasspath
    }
}

neoForge {
    version = neo_version

    parchment.mappingsVersion = parchment_mappings_version
    parchment.minecraftVersion = parchment_minecraft_version

    mods {
        register(mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        register("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }

        register("data") {
            data()
            programArguments.addAll(
                "--mod",
                mod_id,
                "--all",
                "--output",
                file("src/generated/resources/").absolutePath,
                "--existing",
                file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    unitTest {
        enable()
        testedMod = mods[mod_id]
    }
}

configurations {
    val localRuntime = register("localRuntime")
    named("runtimeClasspath").extendsFrom(localRuntime)
}

dependencies {
    api(libs.bulkit.api)
    implementation(libs.kotlinforforge)
    implementation(libs.olympus)

    compileOnly(libs.jei.api)
    runtimeOnly(libs.jei.runtime)

    testImplementation(libs.junit.jupiter)
    testImplementation("net.neoforged:testframework:${neo_version}")
    testRuntimeOnly(libs.junit.platform)
}

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "neo_version" to neo_version,
        "neo_version_range" to neo_version_range,
        "mod_loader" to mod_loader,
        "loader_version_range" to loader_version_range,
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_license" to mod_license,
        "mod_version" to mod_version,
        "mod_authors" to mod_authors,
        "mod_description" to mod_description
    )

    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}

sourceSets.main.get().resources.srcDir(generateModMetadata)
neoForge.ideSyncTask(generateModMetadata)

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {

    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
