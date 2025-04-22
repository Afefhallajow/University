package Simple.University.System.demo.Extra;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ApiError implements GraphQLError {
    private final String message;
    private final List<SourceLocation> locations;
    private final List<Object> path;
    private final Map<String, Object> extensions;

    public ApiError(String message,
                    List<SourceLocation> locations,
                    List<Object> path,
                    Map<String, Object> extensions) {
        this.message    = message;
        this.locations  = locations;
        this.path       = path;
        this.extensions = extensions;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null; // you could implement a custom classification if you like
    }
}