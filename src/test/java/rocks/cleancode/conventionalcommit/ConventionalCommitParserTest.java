package rocks.cleancode.conventionalcommit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConventionalCommitParserTest {

    private ConventionalCommitParser parser;

    @BeforeEach
    public void setUp() {
        parser = new ConventionalCommitParser();
    }

    @Test
    public void should_throw_exception_when_commit_message_has_not_conventional_commit_format() {
        assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("My commit message")
        );
    }

    @ParameterizedTest
    @MethodSource("types")
    public void should_parse_types(String type) {
        String simpleCommitMessage = String.format(
                "%s: My commit description",
                type
        );

        ConventionalCommit conventionalCommit = parser.parse(simpleCommitMessage);

        assertThat(conventionalCommit.type(), is(equalTo(type)));
    }

    private static Stream<String> types() {
        return Stream.of(
                "fix",
                "feat",
                "build",
                "chore",
                "ci",
                "docs",
                "style",
                "refactor",
                "perf",
                "test"
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void should_parse_conventional_commit_message(ConventionalCommitTestCase testCase) {
        ConventionalCommit conventionalCommit = parser.parse(testCase.message());

        assertThat("Type", conventionalCommit.type(), is(equalTo(testCase.expected().type())));
        assertThat("Scope", conventionalCommit.scope().orElse(null), is(equalTo(testCase.expected().scope())));
        assertThat("Description", conventionalCommit.description(), is(equalTo(testCase.expected().description())));
        assertThat("Exclamation", conventionalCommit.exclamation(), is(testCase.expected().exclamation()));
        assertThat("Body", conventionalCommit.body().orElse(null), is(equalTo(testCase.expected().body())));
        assertThat("Footer", conventionalCommit.footer(), is(equalTo(testCase.expected().footer())));
        assertThat("Breaking change", conventionalCommit.breakingChange(), is(testCase.expected().breakingChange()));
    }

    private static Stream<ConventionalCommitTestCase> testCases() throws IOException {
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

}
