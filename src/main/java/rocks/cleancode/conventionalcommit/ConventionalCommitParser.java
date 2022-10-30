package rocks.cleancode.conventionalcommit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * <p>Conventional commit message parser.</p>
 *
 * Based on specification <a href="https://www.conventionalcommits.org/en/v1.0.0/">Conventional Commits v1.0.0</a>.
 *
 * @since 1.0.0
 */
public class ConventionalCommitParser {

    /**
     * Recommended types in <a href="https://www.conventionalcommits.org/en/v1.0.0/">Conventional Commits v1.0.0</a>:
     * {@code fix}, {@code feat}, {@code build}, {@code chore}, {@code ci}, {@code docs}, {@code style},
     * {@code refactor}, {@code perf} or {@code test}
     *
     * @since 1.2.0
     */
    public static final List<String> RECOMMENDED_TYPES = unmodifiableList(asList(
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
    ));

    private final List<String> types;

    /**
     * Default constructor with types defined with {@link #RECOMMENDED_TYPES}.
     *
     * @since 1.0.0
     */
    public ConventionalCommitParser() {
        this(RECOMMENDED_TYPES);
    }

    /**
     * Constructor with allowed types.
     *
     * @param types List of allowed types
     *
     * @since 1.2.0
     */
    public ConventionalCommitParser(String... types) {
        this.types = unmodifiableList(asList(types));
    }

    private ConventionalCommitParser(List<String> types) {
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

    private ConventionalCommit conventionalCommit(
        ConventionalCommitMessage message,
        String body,
        ConventionalCommitFooter footer
    ) {
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
