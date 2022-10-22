# Conventional commit

[![codecov](https://codecov.io/gh/clean-code-rocks/conventional-commit/branch/main/graph/badge.svg?token=QQXYU45M13)](https://codecov.io/gh/clean-code-rocks/conventional-commit)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fconventional-commit.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fclean-code-rocks%2Fconventional-commit?ref=badge_shield)

Tool to manage conventional commit messages.

## Requirement

Java 8+

## Installation

```xml
<dependency>
    <groupId>rocks.cleancode</groupId>
    <artifactId>conventional-commit</artifactId>
    <version>{{VERSION}}</version>
</dependency>
```

## Usage

```java
import rocks.cleancode.conventionalcommit.ConventionalCommitParser;

ConventionalCommitParser parser = new ConventionalCommitParser();

ConventionalCommit conventionalCommit = parser.parse("feat: My new feature");

String type = conventionalCommit.type(); // is equal to "feat"
String description = conventionalCommit.description(); // is equal to "My new feature"
```
