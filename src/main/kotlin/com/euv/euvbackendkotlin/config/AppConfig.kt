package com.euv.euvbackendkotlin.config

import com.euv.euvbackendkotlin.exceptions.GraphqlException
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter

@Configuration
class AppConfig {
    @Bean
    fun exceptionResolver(): DataFetcherExceptionResolverAdapter {
        return DataFetcherExceptionResolverAdapter.from { ex: Throwable?, env: DataFetchingEnvironment? ->
            if (ex is GraphqlException) {
                return@from GraphqlErrorBuilder.newError(env)
                    .message(ex.message).extensions(ex.extensions)
                    .errorType(ex.errorType)
                    .build()
            } else {
                return@from null
            }
        }
    }
}