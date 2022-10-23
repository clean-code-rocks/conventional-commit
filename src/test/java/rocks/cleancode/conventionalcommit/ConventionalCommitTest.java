package rocks.cleancode.conventionalcommit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

class ConventionalCommitTest {

    @ParameterizedTest
    @MethodSource("rocks.cleancode.conventionalcommit.ConventionalCommitTestCase#testCases")
    public void should_get_conventional_commit_message(ConventionalCommitTestCase testCase) {
        ConventionalCommit conventionalCommit = new ConventionalCommit(
            testCase.expected().type(),
            testCase.expected().scope(),
            testCase.expected().exclamation(),
            testCase.expected().description(),
            testCase.expected().body(),
            testCase.expected().footer()
        );

        assertThat(conventionalCommit.toString(), is(equalTo(testCase.message())));
    }

    @Test
    public void should_build_conventional_commit() {
        ConventionalCommit conventionalCommit = new ConventionalCommit.Builder()
            .type("feat")
            .scope("feature-scope")
            .exclamation(true)
            .description("My feature description")
            .body("Full feature description")
            .footer("Reviewed-by", "John DOE")
            .footer("Refs", "#123")
            .build();

        Map<String, String> expectedFooter = new HashMap<>();
        expectedFooter.put("Reviewed-by", "John DOE");
        expectedFooter.put("Refs", "#123");

        assertThat(conventionalCommit.type(), is(equalTo("feat")));
        assertThat(conventionalCommit.scope().get(), is(equalTo("feature-scope")));
        assertThat(conventionalCommit.exclamation(), is(true));
        assertThat(conventionalCommit.description(), is(equalTo("My feature description")));
        assertThat(conventionalCommit.body().get(), is(equalTo("Full feature description")));
        assertThat(conventionalCommit.footer(), is(equalTo(expectedFooter)));
    }

    @Test
    public void should_create_simple_conventional_commit() {
        ConventionalCommit conventionalCommit = new ConventionalCommit("feat", "My feature description");

        assertThat(conventionalCommit.type(), is(equalTo("feat")));
        assertThat(conventionalCommit.scope().isPresent(), is(false));
        assertThat(conventionalCommit.exclamation(), is(false));
        assertThat(conventionalCommit.description(), is(equalTo("My feature description")));
        assertThat(conventionalCommit.body().isPresent(), is(false));
        assertThat(conventionalCommit.footer(), is(equalTo(new HashMap<>())));
    }

}
