package com.github.starter.config

import io.helidon.config.Config
import io.helidon.dbclient.DbClient

open class JdbcConfig {

    fun jdbcClient(): DbClient {
        return DbClient.builder()
            .config(Config.create().get("db"))
            .build()
    }
}