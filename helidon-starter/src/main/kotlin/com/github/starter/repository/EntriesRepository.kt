package com.github.starter.repository

import com.github.pokemon.model.Pokemon
import io.helidon.common.reactive.Multi
import io.helidon.config.Config
import io.helidon.dbclient.DbClient
import jakarta.inject.Singleton
import org.postgresql.jdbc.PgArray

@Singleton
open class EntriesRepository {

    private val client: DbClient = DbClient.builder()
        .config(Config.create().get("db"))
        .build()

    fun listEntries(): Multi<Pokemon> {
        return client.execute { f ->
            f.createQuery("select * from pokedex.entries").execute()
                .map { dbRow ->
                    Pokemon.fromMap(
                        mapOf<String, Any?>(
                            "name" to dbRow.column("name").value(),
                            "base_stat" to dbRow.column("base_stat").value(),
                            "primary_type" to dbRow.column("primary_type").value(),
                            "secondary_type" to dbRow.column("secondary_type").value()?.let { a -> a },
                            "weight" to dbRow.column("weight").value(),
                            "height" to dbRow.column("height").value(),
                            "weakness" to dbRow.column("weakness").`as`(PgArray::class.java).array,
                            "location" to dbRow.column("location").value(),
                            "legendary" to dbRow.column("legendary").value()
                        )
                    )
                }
        }
    }
}