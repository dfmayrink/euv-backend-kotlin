package com.euv.euvbackendkotlin.exceptions

import graphql.ErrorClassification
import graphql.ErrorType

open class GraphqlException(
        errorMessage: String? = "",
        private val errorType: ErrorType = ErrorType.DataFetchingException,
        private val parameters: Map<String, Any>? = mutableMapOf()
) : GraphQLException(errorMessage) {
    override val message: String?
        get() = super.message

    override fun getExtensions(): MutableMap<String, Any> {
        return mutableMapOf("parameters" to (parameters ?: mutableMapOf()))
    }

    override fun getErrorType(): ErrorClassification {
        return this.errorType
    }
}