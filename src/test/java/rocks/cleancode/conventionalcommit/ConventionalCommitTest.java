package rocks.cleancode.conventionalcommit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

}
