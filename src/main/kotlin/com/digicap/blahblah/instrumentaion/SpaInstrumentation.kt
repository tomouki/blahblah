package com.hyundai.spa.instrumentation

import graphql.ExecutionResult
import graphql.execution.instrumentation.Instrumentation
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.InstrumentationState
import graphql.execution.instrumentation.parameters.InstrumentationCreateStateParameters
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import graphql.execution.instrumentation.parameters.InstrumentationFieldFetchParameters
import graphql.schema.DataFetcher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture


@Component
class SpaInstrumentation : Instrumentation {

    @Value("\${logging.graphql.maxLength}")
    private var maxLength: Int = 1000

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun createState(parameters: InstrumentationCreateStateParameters?): InstrumentationState? {
        return SpaInstrumentationState(parameters)
    }

    override fun beginExecution(
        parameters: InstrumentationExecutionParameters?,
        state: InstrumentationState?
    ): InstrumentationContext<ExecutionResult>? {
        return super.beginExecution(parameters, state)
    }

    override fun instrumentDataFetcher(
        dataFetcher: DataFetcher<*>,
        parameters: InstrumentationFieldFetchParameters?,
        state: InstrumentationState?
    ): DataFetcher<*> {
        return dataFetcher
    }

    override fun instrumentExecutionResult(
        executionResult: ExecutionResult,
        parameters: InstrumentationExecutionParameters?,
        state: InstrumentationState?
    ): CompletableFuture<ExecutionResult> {

        try {
            //IntrospectionQuery 제외
            if (parameters?.query?.contains("IntrospectionQuery", ignoreCase = true) == false) {
                logger.info("""instrumentExecutionResult
                |======================== GraphQL Operation ========================
                | Query       : ${parameters?.query?.take(maxLength)}${if (parameters?.query?.length ?: 0 > maxLength) "..." else ""}
                | Operation   : ${parameters?.operation}
                | Variables   : ${parameters?.variables}
                |-------------------------------------------------------------------
                | Response    : ${executionResult?.toString()?.take(maxLength)}${if (executionResult?.toString()?.length ?: 0 > maxLength) "..." else ""}
                |=====================================================================
            """.trimMargin())
            }
        } catch (e: Exception) {
            logger.error("An error occurred while logging GraphQL operation", e)
        }

        return CompletableFuture.completedFuture(executionResult)
    }
}
