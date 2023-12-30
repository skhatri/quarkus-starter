package com.github.starter.repository

import com.github.pokemon.model.Pokemon
import io.smallrye.mutiny.Multi
import io.vertx.mutiny.pgclient.PgPool

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.inject.Inject
import javax.ws.rs.ext.Provider

@Provider
class EntriesRepository {

    @Inject
    @ConfigProperty(name = "features.pokemon.table-name")
    lateinit var tableName: String

    @Inject
    lateinit var client: PgPool

    fun listEntries(): Multi<Pokemon> {
        val res = client.query("select * from $tableName")
                .execute()
                .onItem().transformToMulti { rows -> Multi.createFrom().iterable(rows) }
                .map { row ->
                    Pokemon.fromMap(mapOf(
                            "name" to row.getString("name"),
                            "base_stat" to row.getInteger("base_stat"),
                            "primary_type" to row.getString("primary_type"),
                            "secondary_type" to row?.getString("secondary_type").let { s -> s },
                            "weight" to row.getBigDecimal("weight"),
                            "height" to row.getBigDecimal("height"),
                            "weakness" to row.getArrayOfStrings("weakness"),
                            "location" to row.getString("location"),
                            "legendary" to row.getBoolean("legendary")
                    ))
                }
        return res
    }
}