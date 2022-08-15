package com.euv.euvbackendkotlin.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import kotlin.collections.ArrayDeque;

import java.util.ArrayList;
import java.util.List;

public class GraphQLException extends RuntimeException implements GraphQLError {

    String customMessage;

    public GraphQLException(String customMessage) {
        this.customMessage = customMessage;
    }

    @Override
    public String getMessage() {
        return customMessage;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return new ArrayList<>();
    }

    @Override
    public ErrorClassification getErrorType() {
        return new ErrorClassification() {
            @Override
            public Object toSpecification(GraphQLError error) {
                return ErrorClassification.super.toSpecification(error);
            }
        };
    }
}