package rocks.cleancode.conventionalcommit;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ConventionalCommit {

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

}
