package rocks.cleancode.conventionalcommit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

class ConventionalCommitMessage {

    private static final String SCOPE_REGEX = "(?:\\((.+)\\))";

    private static final String EXCLAMATION_REGEX = "(!)";

    private static final String DESCRIPTION_REGEX = "(.+)";

    private final String raw;

    private final String type;

    private final String scope;

    private final boolean exclamation;

    private final String description;

    ConventionalCommitMessage(List<String> types, String fullCommitMessage) {
        Pattern pattern = Pattern.compile(regex(types));

        Matcher matcher = pattern.matcher(fullCommitMessage);

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

    private String regex(List<String> types) {
        return format(
            "^(%s%s?%s?: %s)",
            typesRegex(types),
            SCOPE_REGEX,
            EXCLAMATION_REGEX,
            DESCRIPTION_REGEX
        );
    }

    private String typesRegex(List<String> types) {
        return types.stream()
            .collect(joining("|", "(", ")"));
    }

}
