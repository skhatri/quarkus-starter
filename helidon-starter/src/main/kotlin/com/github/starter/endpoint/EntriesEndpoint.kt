package com.github.starter.endpoint

import com.github.pokemon.model.Pokemon
import com.github.starter.repository.EntriesRepository
import io.helidon.common.reactive.Multi
import io.helidon.common.reactive.Single
import jakarta.enterprise.context.RequestScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/")
@RequestScoped
open class EntriesEndpoint() {

    val entriesRepository: EntriesRepository = EntriesRepository()

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun listEntries(): Single<List<Pokemon>> {
        return entriesRepository.listEntries().collectList()
    }

    @GET
    @Path("/value")
    @Produces(MediaType.APPLICATION_JSON)
    fun test1(): Single<List<String>> {
        return Multi.just("1", "2", "a").collectList()
    }
}