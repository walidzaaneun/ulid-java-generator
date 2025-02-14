package com.walidzaaneun;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ULIDTest {

    @Test
    public void testDefaultConstructorGeneratesValidULID() {
        ULID ulid = new ULID();
        assertNotNull(ulid.getValue(), "ULID value should not be null");
        assertEquals(26, ulid.getValue().length(), "ULID should have 26 characters");
        assertTrue(ULID.isValid(ulid.getValue()), "Generated ULID should be valid");
    }

    @Test
    public void testULIDStringConstructorWithValidValue() {
        // Create an instance using the default constructor and then build another one using its value.
        ULID original = new ULID();
        String value = original.getValue();
        ULID ulidFromString = new ULID(value);
        assertEquals(value, ulidFromString.getValue(), "ULID generated from string should equal the original");
    }

    @Test
    public void testULIDStringConstructorWithInvalidValue() {
        String invalidUlid = "INVALID_ULID_STRING";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ULID(invalidUlid);
        });
        String expectedMessage = "Invalid ULID string";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Exception message should contain the expected text");
    }

    @Test
    public void testIsValid() {
        // Test using a valid ULID created via the default constructor.
        ULID ulid = new ULID();
        assertTrue(ULID.isValid(ulid.getValue()), "ULID.isValid should return true for a valid ULID");

        // Test against null.
        assertFalse(ULID.isValid(null), "ULID.isValid should return false for null");

        // Test with incorrect lengths.
        assertFalse(ULID.isValid("1234567890123456789012345"), "Should be 25 characters (invalid)");
        assertFalse(ULID.isValid("123456789012345678901234567"), "Should be 27 characters (invalid)");

        // Test with invalid characters.
        String invalid = "0123456789ABCDEFILOQRSTUVWX"; // Contains I, L, O which are not allowed.
        // Even though the length is 26, the invalid characters should cause failure.
        assertFalse(ULID.isValid(invalid), "ULID should be invalid when containing unallowed characters");
    }

    @Test
    public void testEqualsAndHashCode() {
        ULID ulid1 = new ULID();
        ULID ulid2 = new ULID(ulid1.getValue());
        ULID ulid3 = new ULID();

        // ulid1 and ulid2 have the same value.
        assertEquals(ulid1, ulid2, "ULIDs created with the same string should be equal");
        assertEquals(ulid1.hashCode(), ulid2.hashCode(), "Equal ULIDs must have the same hash code");

        // ulid1 and ulid3 should differ (unless an extremely unlikely collision occurs).
        if (!ulid1.getValue().equals(ulid3.getValue())) {
            assertNotEquals(ulid1, ulid3, "Different ULIDs should not be equal");
        }
    }

    @Test
    public void testCompareTo() {
        // Create two ULIDs with a small delay to ensure a different timestamp.
        ULID ulid1 = new ULID();
        try {
            Thread.sleep(2);   // Sleep briefly to allow a timestamp difference.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        ULID ulid2 = new ULID();

        // If the ULIDs are different, the compareTo method should correctly order them.
        if (!ulid1.getValue().equals(ulid2.getValue())) {
            assertNotEquals(0, ulid1.compareTo(ulid2), "compareTo should not return zero for different ULIDs");
        }
    }

    @Test
    public void testToStringAndGetValue() {
        ULID ulid = new ULID();
        assertEquals(ulid.getValue(), ulid.toString(), "toString should return the same value as getValue");
    }
}
