package rocks.cleancode.conventionalcommit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

/**
 * <p>Conventional commit message representation.</p>
 *
 * <p>Based on specification <a href="https://www.conventionalcommits.org/en/v1.0.0/">Conventional Commits v1.0.0</a>.</p>
 *
 * @since 1.0.0
 */
public class ConventionalCommit {

    private static final String EMPTY_STRING = "";

    private static final String NEWLINE = format("%n");

    private static final String DOUBLE_NEWLINE = NEWLINE + NEWLINE;

    /**
     * Builder to ease conventional commit creation.
     *
     * @since 1.1.0
     */
    public static class Builder {

        private String type;

        private String scope;

        private boolean exclamation;

        private String description;

        private String body;

        private final Map<String, String> footer;

        /**
         * Default constructor.
         *
         * @since 1.1.0
         */
        public Builder() {
            this.footer = new HashMap<>();
        }

        /**
         * Message type.
         *
         * @param type Message type
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder type(String type) {
            this.type = type;

            return this;
        }

        /**
         * Message scope.
         *
         * @param scope Message scope
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder scope(String scope) {
            this.scope = scope;

            return this;
        }

        /**
         * Message with exclamation mark.
         *
         * @param exclamation {@code true} to set exclamation mark in message, {@code false} otherwise
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder exclamation(boolean exclamation) {
            this.exclamation = exclamation;

            return this;
        }

        /**
         * Message description.
         *
         * @param description Message description
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder description(String description) {
            this.description = description;

            return this;
        }

        /**
         * Message body.
         *
         * @param body Message body
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder body(String body) {
            this.body = body;

            return this;
        }

        /**
         * Add footer to message.
         *
         * @param key Key of the footer entry
         * @param value Value of the footer entry
         * @return Current builder instance
         *
         * @since 1.1.0
         */
        public Builder footer(String key, String value) {
            this.footer.put(key, value);

            return this;
        }

        /**
         * Build the conventional commit with the given values.
         *
         * @return Conventional commit
         *
         * @since 1.1.0
         */
        public ConventionalCommit build() {
            return new ConventionalCommit(
                type,
                scope,
                exclamation,
                description,
                body,
                footer
            );
        }

    }

    private final String type;

    private final String scope;

    private final boolean exclamation;

    private final String description;

    private final String body;

    private final Map<String, String> footer;

    /**
     * All arguments constructor.
     *
     * @param type Message type; recommended values: {@code fix}, {@code feat}, {@code build}, {@code chore},
     *             {@code ci}, {@code docs}, {@code style}, {@code refactor}, {@code perf} or {@code test}
     * @param scope Custom message scope; might be {@code null}
     * @param exclamation {@code true} if exclamation mark appears in the message, {@code false} otherwise
     * @param description Message description
     * @param body Full message body, might be multiline
     * @param footer Key/value list of additional data
     * @throws IllegalStateException If type or description is {@code null} or empty
     *
     * @since 1.0.0
     */
    public ConventionalCommit(
        String type,
        String scope,
        boolean exclamation,
        String description,
        String body,
        Map<String, String> footer
    ) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type is missing or empty");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description is missing or empty");
        }

        this.type = type;
        this.scope = scope;
        this.exclamation = exclamation;
        this.description = description;
        this.body = body;
        this.footer = footer;
    }

    /**
     * Simple constructor.
     *
     * @param type Message type; recommended values: {@code fix}, {@code feat}, {@code build}, {@code chore},
     *             {@code ci}, {@code docs}, {@code style}, {@code refactor}, {@code perf} or {@code test}
     * @param description Message description
     * @throws IllegalStateException If type or description is {@code null} or empty
     *
     * @since 1.1.0
     */
    public ConventionalCommit(
        String type,
        String description
    ) {
        this(type, null, false, description, null, new HashMap<>());
    }

    /**
     * Message type.
     *
     * @return Message type
     *
     * @since 1.0.0
     */
    public String type() {
        return type;
    }

    /**
     * Message scope.
     *
     * @return {@link Optional} with message scope; empty if it does not exist
     *
     * @since 1.0.0
     */
    public Optional<String> scope() {
        return Optional.ofNullable(scope);
    }

    /**
     * Message with exclamation mark.
     *
     * @return {@code true} if exclamation mark exists in message, {@code false} otherwise
     *
     * @since 1.0.0
     */
    public boolean exclamation() {
        return exclamation;
    }

    /**
     * Message description.
     *
     * @return Message description
     *
     * @since 1.0.0
     */
    public String description() {
        return description;
    }

    /**
     * Message body.
     *
     * @return {@link Optional} with message body; empty if it does not exist
     *
     * @since 1.0.0
     */
    public Optional<String> body() {
        return Optional.ofNullable(body)
            .filter(this::isNotBlank);
    }

    /**
     * Key/value footer list.
     *
     * @return {@link Map} representing footer key/value
     *
     * @since 1.0.0
     */
    public Map<String, String> footer() {
        return footer;
    }

    private Predicate<String> isEmpty() {
        return String::isEmpty;
    }

    private boolean isNotBlank(String string) {
        return Optional.ofNullable(string)
            .map(String::trim)
            .filter(isEmpty().negate())
            .isPresent();
    }

    /**
     * <p>Indicator of breaking change.</p>
     *
     * A breaking change is identified by a "!" after the type/scope of the message,
     * or by a footer entry "BREAKING CHANGE"
     *
     * @return {@code true} if breaking change, {@code false} otherwise
     *
     * @since 1.0.0
     */
    public boolean breakingChange() {
        return exclamation || footer.containsKey("BREAKING CHANGE");
    }

    /**
     * <p>Generate conventional commit message.</p>
     *
     * For example:
     *
     * <pre><code>
     * feat: This is my new feature
     *
     * Some more details to describe the newly added
     * functionality.
     *
     * Reviewed-by: Me
     * Refs: #123
     * </code></pre>
     *
     * @return Conventional commit message
     *
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return format(
                "%s%s%s: %s%s%s",
                type,
                scopeToString(),
                exclamationToString(),
                description,
                bodyToString(),
                footerToString()
            )
            .trim();
    }

    private String scopeToString() {
        return scope()
            .map(this::surroundScope)
            .orElse(EMPTY_STRING);
    }

    private String exclamationToString() {
        if (exclamation) {
            return "!";
        }

        return EMPTY_STRING;
    }

    private String bodyToString() {
        return body()
            .map(DOUBLE_NEWLINE::concat)
            .orElse(EMPTY_STRING);
    }

    private String footerToString() {
        return footer().entrySet()
            .stream()
            .map(this::joinFooterEntry)
            .collect(joining(NEWLINE, DOUBLE_NEWLINE, EMPTY_STRING));
    }

    private String joinFooterEntry(Map.Entry<String, String> entry) {
        return format("%s%s%s", entry.getKey(), ": ", entry.getValue());
    }

    private String surroundScope(String scope) {
        return format("(%s)", scope);
    }

}
