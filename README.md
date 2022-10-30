# Conventional commit

[![Maven Central][Maven Central - badge]][Maven Central - link]
[![Javadoc][Javadoc - badge]][Javadoc - link]
[![Codecov][Codecov - badge]][Codecov - link]
[![License: GPL v3][Licence - badge]][Licence - link]
[![Fossa][Fossa - badge]][Fossa - link]

Tool to manage conventional commit messages, based on [Conventional Commits v1.0.0](https://www.conventionalcommits.org/en/v1.0.0/).

## Requirement

Java 8+

## Installation

```xml
<dependency>
    <groupId>rocks.cleancode</groupId>
    <artifactId>conventional-commit</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Usage

### Parse conventional commit message

```java
import rocks.cleancode.conventionalcommit.ConventionalCommit;
import rocks.cleancode.conventionalcommit.ConventionalCommitParser;

ConventionalCommitParser parser = new ConventionalCommitParser();

ConventionalCommit conventionalCommit = parser.parse("feat: My new feature");

String type = conventionalCommit.type(); // is equal to "feat"
String description = conventionalCommit.description(); // is equal to "My new feature"
```

### Generate conventional commit message

#### Simple constructor

```java
import rocks.cleancode.conventionalcommit.ConventionalCommit;

ConventionalCommit conventionalCommit = new ConventionalCommit(
    "feat",
    "My feature description"
);

String conventionalCommitMessage = conventionalCommit.toString();
```

#### All arguments constructor

```java
import java.util.HashMap;

import rocks.cleancode.conventionalcommit.ConventionalCommit;

ConventionalCommit conventionalCommit = new ConventionalCommit(
    "feat",
    null,
    false,
    "My new feature",
    null,
    new HashMap<>()
);

String conventionalCommitMessage = conventionalCommit.toString();
```

#### Builder

```java
import rocks.cleancode.conventionalcommit.ConventionalCommit;

ConventionalCommit conventionalCommit = new ConventionalCommit.Builder()
        .type("feat")
        .scope("feature-scope")
        .exclamation(true)
        .description("My feature description")
        .body("Full feature description")
        .footer("Reviewed-by", "John DOE")
        .footer("Refs", "#123")
        .build();

String conventionalCommitMessage = conventionalCommit.toString();
```

[Maven Central - badge]: https://img.shields.io/maven-central/v/rocks.cleancode/conventional-commit?color=brightgreen
[Maven Central - link]: https://search.maven.org/artifact/rocks.cleancode/conventional-commit
[Javadoc - badge]: https://javadoc.io/badge2/rocks.cleancode/conventional-commit/javadoc.svg
[Javadoc - link]: https://javadoc.io/doc/rocks.cleancode/conventional-commit
[Codecov - badge]: https://codecov.io/gh/clean-code-rocks/conventional-commit/branch/main/graph/badge.svg?token=QQXYU45M13
[Codecov - link]: https://codecov.io/gh/clean-code-rocks/conventional-commit
[Licence - badge]: https://img.shields.io/badge/License-GPLv3-blue.svg
[Licence - link]: https://www.gnu.org/licenses/gpl-3.0
[Fossa - badge]: https://app.fossa.com/api/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fconventional-commit.svg?type=shield
[Fossa - link]: https://app.fossa.com/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fconventional-commit?ref=badge_shield
