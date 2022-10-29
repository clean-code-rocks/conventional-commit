package rocks.cleancode.conventionalcommit;

import java.util.Optional;

/**
 * <p>Conventional commit message parser.</p>
 *
 * <p>Based on specification <a href="https://www.conventionalcommits.org/en/v1.0.0/">Conventional Commits v1.0.0</a>.</p>
 *
 * @since 1.0.0
 */
public class ConventionalCommitParser {

    private final String[] types;

    /**
     * Default constructor.
     *
     * @since 1.0.0
     */
    public ConventionalCommitParser() {
        this("fix", "feat", "build", "chore", "ci", "docs", "style", "refactor", "perf", "test");
    }

    public ConventionalCommitParser(String... types) {
        this.types = types;
    }

    /**
     * Parse raw conventional commit message.
     *
     * @param fullCommitMessage Raw conventional commit message
     * @return Parsed conventional commit message
     *
     * @since 1.0.0
     */
    public ConventionalCommit parse(String fullCommitMessage) {
        ConventionalCommitMessage message = new ConventionalCommitMessage(types, fullCommitMessage);

        ConventionalCommitFooter footer = new ConventionalCommitFooter(fullCommitMessage);

        String body = body(fullCommitMessage, message, footer);

        return conventionalCommit(message, body, footer);
    }

    private String body(String fullCommitMessage, ConventionalCommitMessage message, ConventionalCommitFooter footer) {
        String rawMessage = Optional.ofNullable(message.raw()).orElse("");
        String rawFooter = Optional.ofNullable(footer.raw()).orElse("");

        return fullCommitMessage
                .substring(rawMessage.length(), fullCommitMessage.length() - rawFooter.length())
                .trim();
    }

    private ConventionalCommit conventionalCommit(ConventionalCommitMessage message, String body, ConventionalCommitFooter footer) {
        return new ConventionalCommit(
                message.type(),
                message.scope(),
                message.exclamation(),
                message.description(),
                body,
                footer.footer()
        );
    }

}
