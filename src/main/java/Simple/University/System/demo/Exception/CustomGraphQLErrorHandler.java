package Simple.University.System.demo.Exception;

import Simple.University.System.demo.Extra.ApiError;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        List<GraphQLError> formatted = new ArrayList<>(errors.size());
        for (GraphQLError error : errors) {
            formatted.add(format(error));
        }
        return formatted;
    }

    private GraphQLError format(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            Throwable ex = ((ExceptionWhileDataFetching) error).getException();
            // 1) Not found → 404 style
            if (ex instanceof EntityNotFoundException) {
                return toApiError(
                        ex.getMessage(),
                        error.getLocations(),
                        error.getPath(),
                        Map.of("code", "NOT_FOUND")
                );
            }
            // 2) Bad input / validation
            if (ex instanceof IllegalArgumentException          ||
                    ex instanceof DataIntegrityViolationException) {
                return toApiError(
                        ex.getMessage(),
                        error.getLocations(),
                        error.getPath(),
                        Map.of("code", "BAD_REQUEST")
                );
            }

            // 3) fallback for any other exception
            log.error("Unexpected exception in GraphQL data fetcher", ex);
            return toApiError(
                    ex.getMessage(),
                    error.getLocations(),
                    error.getPath(),
                    Map.of("code", "INTERNAL_ERROR")
            );
        }
        // 4) syntax/validation errors (missing argument, bad enum, etc.) — pass through
        return error;
    }

    private ApiError toApiError(String message,
                                List<SourceLocation> locations,
                                List<Object> path,
                                Map<String,Object> extensions) {
        return new ApiError(message, locations, path, extensions);
    }
}