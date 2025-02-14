package com.walidzaaneun;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public final class ULID implements Comparable<ULID> {

    private static final char[] ENCODING_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
            'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
            'Y', 'Z'
    };

    // Precompiled regex for valid ULIDs: 26 characters from the allowed set.
    private static final Pattern ULID_PATTERN = Pattern.compile("^[0-9A-HJKMNP-TV-Z]{26}$");

    private final String value;

    /**
     * Generates a new ULID using the current timestamp and a random component.
     */
    public ULID() {
        this.value = generate();
    }

    /**
     * Constructs a ULID instance from an existing ULID string.
     *
     * @param value A valid ULID string.
     * @throws IllegalArgumentException if the given string is not a valid ULID.
     */
    public ULID(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid ULID string: " + value);
        }
        this.value = value;
    }

    private static String generate() {
        long timestamp = Instant.now().toEpochMilli();
        StringBuilder builder = new StringBuilder(26);

        // Timestamp component (first 10 characters)
        String timestampPart = encodeTimestamp(timestamp);
        builder.append(timestampPart);

        // Random component (last 16 characters)
        for (int i = 0; i < 16; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(32);
            builder.append(ENCODING_CHARS[randomIndex]);
        }

        return builder.toString();
    }

    /**
     * Encodes the given timestamp (in milliseconds) as a 10-character string.
     */
    private static String encodeTimestamp(long timestamp) {
        char[] encoded = new char[10];
        // Encode timestamp in base-32 from the least significant digit.
        for (int i = 9; i >= 0; i--) {
            encoded[i] = ENCODING_CHARS[(int) (timestamp % 32)];
            timestamp /= 32;
        }
        return new String(encoded);
    }

    /**
     * Checks if the provided string is a valid ULID.
     *
     * @param value the string to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        return ULID_PATTERN.matcher(value).matches();
    }

    /**
     * Returns the ULID value as a string.
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ULID ulid = (ULID) o;
        return Objects.equals(value, ulid.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Returns the ULID string representation.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares two ULID instances lexicographically.
     *
     * @throws NullPointerException if the given ULID is null.
     */
    @Override
    public int compareTo(ULID other) {
        if (other == null) {
            throw new NullPointerException("ULID cannot be compared to null");
        }
        return this.value.compareTo(other.value);
    }
}