package rocks.cleancode.conventionalcommit;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class ConventionalCommit {

    public static final String EMPTY_STRING = "";

    public static final String NEWLINE = format("%n");

    public static final String DOUBLE_NEWLINE = NEWLINE + NEWLINE;

    private final String type;

    private final String scope;

    private final boolean exclamation;

    private final String description;

    private final String body;

    private final Map<String, String> footer;

    public ConventionalCommit(
        String type,
        String scope,
        boolean exclamation,
        String description,
        String body,
        Map<String, String> footer
    ) {
        this.type = type;
        this.scope = scope;
        this.exclamation = exclamation;
        this.description = description;
        this.body = body;
        this.footer = footer;
    }

    public String type() {
        return type;
    }

    public Optional<String> scope() {
        return Optional.ofNullable(scope);
    }

    public boolean exclamation() {
        return exclamation;
    }

    public String description() {
        return description;
    }

    public Optional<String> body() {
        return Optional.ofNullable(body)
            .filter(this::isNotBlank);
    }

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

    public boolean breakingChange() {
        return exclamation || footer.containsKey("BREAKING CHANGE");
    }

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
            .map(surrounded("(", ")"))
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
            .map(joinFooterEntry())
            .collect(joining(NEWLINE, DOUBLE_NEWLINE, EMPTY_STRING));
    }

    private Function<Map.Entry<String, String>, String> joinFooterEntry() {
        return entry -> format("%s%s%s", entry.getKey(), ": ", entry.getValue());
    }

    private Function<String, String> surrounded(String prefix, String suffix) {
        return input -> format("%s%s%s", prefix, input, suffix);
    }

}
