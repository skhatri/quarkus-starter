package com.github.starter.config

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ErrorMappingProvider : ExceptionMapper<Exception> {

    override fun toResponse(exception: Exception?): Response {
        val code = 500
        val errorObject = mapOf<String, Any>(
                "code" to code,
                "message" to exception?.let { e -> e.message }.orEmpty()
        )
        return Response.status(code)
                .entity(errorObject)
                .build()
    }
}