# ULID Java Generator

![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)
![License](https://img.shields.io/github/license/walidzaaneun/ulid-java-generator)

A Java library for generating ULIDs (Universally Unique Lexicographically Sortable Identifiers). ULIDs offer a unique identifier that is lexicographically sortable and more human-friendly than traditional UUIDs.

## Features

- Generates ULIDs in a lexicographically sortable manner
- Provides timestamp-based ordering
- Simple and lightweight implementation

## Installation

You can import this library through a Maven dependency to your own project. Since it is not distributed to MavenCentral, you have to add a custom repository section:
### Maven
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub walidzaaneun/ulid-java-generator Apache Maven Packages</name>
        <url>https://maven.pkg.github.com/walidzaaneun/ulid-java-generator</url>
    </repository>
</repositories>
```
Then you can import it with the following dependency:

```xml
<dependency>
    <groupId>com.walidzaaneun</groupId>
    <artifactId>ulid-java-generator</artifactId>
    <version>1.0.2</version>
</dependency>
```

## Usage

### Generating a ULID

```java
import com.walidzaaneun.ULID;
import com.yourorg.ulid.ULIDGenerator;

public class Main {
    public static void main(String[] args) {
        ULID ulid = new ULID();
        System.out.println("Generated ULID: " + ulid.getValue());
    }
}
```

## Why ULID?

- **Sortable**: ULIDs preserve order based on the timestamp.
- **UUID Alternative**: Unlike UUIDs, ULIDs are more human-readable and sortable.
- **128-bit**: Provides enough randomness and uniqueness for distributed systems.
- **URL Safe**: Uses a Base32 encoding scheme, making it URL-friendly.


## Acknowledgments

Inspired by :
- [ulid/spec](https://github.com/ulid/spec)
- [jaspeen/ulid-java](https://github.com/jaspeen/ulid-java)
- [f4b6a3/ulid-creator](https://github.com/f4b6a3/ulid-creator)