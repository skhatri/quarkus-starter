package com.github.starter

import com.github.starter.endpoint.EntriesEndpoint
import io.helidon.config.Config
import io.helidon.config.ConfigSources.classpath
import io.helidon.microprofile.server.Server
import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/pokemon")
open class Application : Application() {
    override fun getClasses(): Set<Class<EntriesEndpoint>> {
        return setOf(EntriesEndpoint::class.java)
    }
}

fun main() {
    val server = Server.builder()
        .config(
            Config.builder().disableEnvironmentVariablesSource()
                .sources(classpath("META-INF/microprofile-config.properties"))
                .build()
        )
        .addApplication(com.github.starter.Application::class.java)
        .build()
    server.start()
}