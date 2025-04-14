package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClickHouseClientTest {

    static ClickHouseClient clickHouseClient;

    @BeforeAll
    static void setup() {
        clickHouseClient = new ClickHouseClient();
    }

    @Test
    void testInsertData() {
        try {
            clickHouseClient.insertData(4, 300000, "2023-04-01", "Flat", "London");
            assertTrue(true); // if no exception, test passed
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false, "Insert failed with exception: " + e.getMessage());
        }
    }
}
