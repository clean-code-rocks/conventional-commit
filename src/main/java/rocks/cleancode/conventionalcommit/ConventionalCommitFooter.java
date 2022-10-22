package rocks.cleancode.conventionalcommit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

class ConventionalCommitFooter {

    private static final String NEWLINE_REGEX = "\\r?\\n";

    private static final String KEY_REGEX = "(?:[^ :]+|BREAKING CHANGE)";

    private static final String FOOTER_SEPARATOR = ": ";

    private static final String FOOTER_REGEX = format(
            "((?:%s){2}(%s%s.+(?:%s%s%s.+)*))$",
            NEWLINE_REGEX,
            KEY_REGEX,
            FOOTER_SEPARATOR,
            NEWLINE_REGEX,
            KEY_REGEX,
            FOOTER_SEPARATOR
    );

    private static final Pattern FOOTER_PATTERN = Pattern.compile(FOOTER_REGEX);

    private final String raw;

    private final Map<String, String> footer;

    ConventionalCommitFooter(String fullCommitMessage) {
        Matcher matcher = FOOTER_PATTERN.matcher(fullCommitMessage);

        if (matcher.find()) {
            raw = matcher.group(1);
            footer = parseFooters(matcher.group(2));
        } else {
            raw = "";
            footer = new HashMap<>();
        }
    }

    public String raw() {
        return raw;
    }

    public Map<String, String> footer() {
        return footer;
    }

    private Map<String, String> parseFooters(String rawFooter) {
        String[] split = rawFooter.split("\\r?\\n");

        return Arrays.stream(split)
                .map(this::parseFooter)
                .collect(toMap(
                        array -> array[0],
                        array -> array[1]
                ));
    }

    private String[] parseFooter(String rawFooter) {
        int separatorIndex = rawFooter.indexOf(": ");

        return new String[] {
                rawFooter.substring(0, separatorIndex),
                rawFooter.substring(separatorIndex + 2)
        };
    }

}
