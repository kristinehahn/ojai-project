package com.docsamples.ojai.samples;

/**
 * Created by Kristine Hahn on 10/5/15.
 */

import org.ojai.Document;

import org.ojai.exceptions.DecodingException;
import org.ojai.json.Json;
import org.ojai.json.JsonOptions;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;

public class EX01JSON_READ {

    public static void main(String[] args) {

        EX01JSON_READ app = new EX01JSON_READ();
        app.run();
    }

    private void run()  {

/*******************************
* Read the donuts resource file
********************************/

        URI fileLocation;
        Path path = null;
        String donutString =  null;

        try {
            fileLocation = this.getClass().getClassLoader().getResource("donuts.json").toURI();
            path = Paths.get(fileLocation);
        }
        catch (URISyntaxException e) {
        }

        try {
            donutString = new String(java.nio.file.Files.readAllBytes(path));
        }
        catch (IOException e) {
            System.out.println("Failed to read donut file.");
        }

        if (donutString != null)

            System.out.println("Donut String: " + donutString);

/************************************************************************
*  Convert the JSON string read from the resource file to a Document
*************************************************************************/
            try {
                Document donutDocument = Json.newDocument(donutString);
                System.out.println("Donut Document: " + donutDocument.toString());
                java.lang.String mystring = Json.toJsonString(donutDocument, new JsonOptions().pretty());
                System.out.println("mystring: " + mystring);
                Document newDonutDoc = Json.newDocument(

                        "{\n" +
                                "\"id\" : \"0001\",\n" +
                                "\"type\" : \"donut\",\n" +
                                "\"name\" : \"Cake\",\n" +
                                "\"ppu\" : 0.55,\n" +
                                "\"sales\" : 35,\n" +
                                "\"batters\" : {\n" +
                                "  \"batter\" : [\n" +
                                "{\n" +
                                " \"id\" : \"1001\",\n" +
                                "    \"type\" : \"Regular\"\n" +
                                "  },\n" +
                                "{\n" +
                                "    \"id\" : \"1002\",\n" +
                                "    \"type\" : \"Chocolate\"\n" +
                                "  },\n" +
                                "{\n" +
                                "    \"id\" : \"1003\",\n" +
                                "    \"type\" : \"Blueberry\"\n" +
                                "  },\n" +
                                "{\n" +
                                "    \"id\" : \"1004\",\n" +
                                "    \"type\" : \"Devil's Food\"\n" +
                                "  } ]\n" +
                                "},\n" +
                                "\"topping\" : [\n" +
                                "{\n" +
                                "  \"id\" : \"5001\",\n" +
                                "  \"type\" : \"None\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5002\",\n" +
                                "  \"type\" : \"Glazed\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5005\",\n" +
                                "  \"type\" : \"Sugar\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5007\",\n" +
                                "  \"type\" : \"Powdered Sugar\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5006\",\n" +
                                "  \"type\" : \"Chocolate with Sprinkles\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5003\",\n" +
                                "  \"type\" : \"Chocolate\"\n" +
                                "},\n" +
                                "{\n" +
                                "  \"id\" : \"5004\",\n" +
                                "  \"type\" : \"Maple\"\n" +
                                "} ]\n" +
                                "}");
                System.out.println("New Donut Document: " + newDonutDoc);
                System.out.println("topping is: " + donutDocument.getString("topping[3].type"));
                System.out.println("batters are: " + donutDocument.getString("batters.batter.type"));
                System.out.println("doc size: " + newDonutDoc.size());
                String jsonString = Json.toJsonString(newDonutDoc.asReader());
                System.out.println("string from reader: " + jsonString);


            }
            catch (DecodingException e) {
                System.out.println("Invalid JSON string: " + e);
            }
    }
}
