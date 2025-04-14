package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClickHouseController {

    private final ClickHouseClient clickHouseClient;

    // Constructor with dependency injection for ClickHouseClient
    @Autowired
    public ClickHouseController(ClickHouseClient clickHouseClient) {
        this.clickHouseClient = clickHouseClient;
    }

    // POST endpoint to insert data
    @PostMapping("/insertData")
    public String insertData(@RequestBody DataInput data) {
        try {
            // Log data before insertion
            System.out.println("Inserting data: " + data.getId() + ", " + data.getPrice() + ", " + data.getDate() 
                + ", " + data.getPropertyType() + ", " + data.getTown());
            
            // Call the ClickHouseClient to insert data
            clickHouseClient.insertData(data.getId(), data.getPrice(), data.getDate(), data.getPropertyType(), data.getTown());
            
            return "Data inserted successfully!";
        } catch (Exception e) {
            // Log error and return failure message
            e.printStackTrace();
            return "Failed to insert data: " + e.getMessage();
        }
    }

    // GET endpoint to retrieve data based on town
    @GetMapping("/getData/{town}")
    public List<String> getData(@PathVariable String town) {
        List<String> data = clickHouseClient.getData(town);
        System.out.println("Fetched Data: " + data);  // Log the fetched data to debug
        return data;
    }



    // Helper class to map JSON data to Java object
    public static class DataInput {
        private int id;
        private int price;
        private String date;
        private String propertyType;
        private String town;

        // Getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }
    }
}
