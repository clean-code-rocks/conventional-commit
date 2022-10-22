package rocks.cleancode.conventionalcommit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

class ConventionalCommitMessage {

    private static final String TYPE_REGEX = "(fix|feat|build|chore|ci|docs|style|refactor|perf|test)";

    private static final String SCOPE_REGEX = "(?:\\((.+)\\))";

    private static final String EXCLAMATION_REGEX = "(!)";

    private static final String DESCRIPTION_REGEX = "(.+)";

    private static final String CONVENTIONAL_COMMIT_REGEX = format(
            "^(%s%s?%s?: %s)",
            TYPE_REGEX,
            SCOPE_REGEX,
            EXCLAMATION_REGEX,
            DESCRIPTION_REGEX
    );

    private static final Pattern CONVENTIONAL_COMMIT_PATTERN = Pattern.compile(CONVENTIONAL_COMMIT_REGEX);

    private final String raw;

    private final String type;

    private final String scope;

    private final boolean exclamation;

    private final String description;

    ConventionalCommitMessage(String fullCommitMessage) {
        Matcher matcher = CONVENTIONAL_COMMIT_PATTERN.matcher(fullCommitMessage);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Malformed conventional commit message");
        }

        raw = matcher.group(1);
        type = matcher.group(2);
        scope = matcher.group(3);
        exclamation = matcher.group(4) != null;
        description = matcher.group(5);
    }

    public String raw() {
        return raw;
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

    public String description() {
        return description;
    }


}
