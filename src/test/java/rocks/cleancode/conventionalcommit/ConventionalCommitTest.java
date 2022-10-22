package rocks.cleancode.conventionalcommit;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

class ConventionalCommitTest {

    @Test
    public void should_get_simple_commit_message() {
        ConventionalCommit conventionalCommit = new ConventionalCommit(
            "feat",
            null,
            false,
            "My new feature",
            null,
            null
        );

        assertThat(conventionalCommit.toString(), is(equalTo("feat: My new feature")));
    }

    @Test
    public void should_get_simple_commit_message_with_scope() {
        ConventionalCommit conventionalCommit = new ConventionalCommit(
            "feat",
            "feature-scope",
            false,
            "My new feature",
            null,
            null
        );

        assertThat(conventionalCommit.toString(), is(equalTo("feat(feature-scope): My new feature")));
    }

}
