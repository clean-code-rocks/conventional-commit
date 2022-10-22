package rocks.cleancode.conventionalcommit;

import java.util.Optional;

class ConventionalCommitParser {

    public ConventionalCommit parse(String fullCommitMessage) {
        ConventionalCommitMessage message = new ConventionalCommitMessage(fullCommitMessage);

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
