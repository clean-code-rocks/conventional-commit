package rocks.cleancode.conventionalcommit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ConventionalCommitTestCase {

    public static Stream<ConventionalCommitTestCase> testCases() throws IOException {
        InputStream testFileInputStream =
            ConventionalCommitParserTest.class
                .getResourceAsStream("/test-cases.yaml");

        TypeReference<List<ConventionalCommitTestCase>> testCasesType =
            new TypeReference<List<ConventionalCommitTestCase>>() {
            };

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        return objectMapper.readValue(
                testFileInputStream,
                testCasesType
            )
            .stream();
    }

    public static class Expectation {

        private final String type;

        private final String scope;

        private final boolean exclamation;

        private final boolean breakingChange;

        private final String description;

        private final String body;

        private final Map<String, String> footer;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public Expectation(
                @JsonProperty("type") String type,
                @JsonProperty("scope") String scope,
                @JsonProperty("exclamation") boolean exclamation,
                @JsonProperty("breaking-change") boolean breakingChange,
                @JsonProperty("description") String description,
                @JsonProperty("body") String body,
                @JsonProperty("footer") Map<String, String> footer
        ) {
            this.type = type;
            this.scope = scope;
            this.exclamation = exclamation;
            this.breakingChange = breakingChange;
            this.description = description;
            this.body = Optional.ofNullable(body).map(String::trim).orElse(null);
            this.footer = Optional.ofNullable(footer).orElseGet(HashMap::new);
        }

        public String type() {
            return type;
        }

        public String scope() {
            return scope;
        }

        public boolean exclamation() {
            return exclamation;
        }

        public boolean breakingChange() {
            return breakingChange;
        }

        public String description() {
            return description;
        }

        public String body() {
            return body;
        }

        public Map<String, String> footer() {
            return footer;
        }

    }

    private final String name;

    private final String message;

    private final Expectation expected;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ConventionalCommitTestCase(
            @JsonProperty("name") String name,
            @JsonProperty("message") String message,
            @JsonProperty("expected") Expectation expectation
    ) {
        this.name = name;
        this.message = message;
        this.expected = expectation;
    }

    public String name() {
        return name;
    }

    public String message() {
        return message;
    }

    public Expectation expected() {
        return expected;
    }

    @Override
    public String toString() {
        return name;
    }
}
