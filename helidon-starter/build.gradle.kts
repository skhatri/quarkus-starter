import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.sonarqube") version "3.5.0.2730"
    id("jacoco")
    kotlin("plugin.spring") version "1.8.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

sonarqube {
    properties {
        property("sonar.projectName", "${project.name}")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.projectKey", "${rootProject.name}-${project.name}")
        property("sonar.projectVersion", "${project.version}")
        property("sonar.junit.reportPaths", "${projectDir}/build/test-results/test")
        property("sonar.coverage.jacoco.xmlReportPaths", "${projectDir}/build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.coverage.exclusions", "**/R.java,**/proto/*.java")
    }
}

dependencies {
    implementation(project(":pokemon-common"))
    implementation(platform("io.helidon:helidon-dependencies:3.1.0"))
    implementation("io.helidon.webserver:helidon-webserver")
    implementation("io.helidon.messaging:helidon-messaging")
    implementation("io.helidon.common:helidon-common-reactive")
    implementation("io.helidon.media:helidon-media-jsonp")
    implementation("io.helidon.config:helidon-config-yaml")
    implementation("io.helidon.health:helidon-health")
    implementation("io.helidon.health:helidon-health-checks")
    implementation("io.helidon.metrics:helidon-metrics")
    implementation("io.helidon.microprofile.bundles:helidon-microprofile")

    implementation("io.helidon.dbclient:helidon-dbclient")
    implementation("io.helidon.dbclient:helidon-dbclient-jdbc")
    implementation("org.postgresql:postgresql:42.5.3")

    implementation("org.glassfish.jersey.media:jersey-media-json-binding")

}


task("runApp", JavaExec::class) {
    main = "com.github.starter.ApplicationKt"
    classpath = sourceSets["main"].runtimeClasspath
    jvmArgs = listOf("-Xms512m", "-Xmx512m")
}
