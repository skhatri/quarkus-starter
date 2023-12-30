package com.github.starter.endpoint

import com.github.pokemon.model.Pokemon
import com.github.starter.repository.EntriesRepository
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import java.lang.RuntimeException
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/pokemon")
class EntriesEndpoint {

    @Inject
    lateinit var entriesRepository: EntriesRepository

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    fun listEntries(): Multi<Pokemon> {
        Log.info("list pokemon entries")
        return entriesRepository.listEntries()
    }

    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    fun viewItem(): Pokemon {
        Log.error("view is not implemented")
        throw RuntimeException("view not implemented")
    }
}