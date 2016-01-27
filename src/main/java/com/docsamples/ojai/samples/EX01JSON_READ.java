package com.docsamples.ojai.samples;

/**
 * Created by khahn on 10/5/15.
 */

import org.ojai.*;

import org.ojai.DocumentReader.EventType;
import org.ojai.beans.BeanCodec;
import org.ojai.exceptions.DecodingException;
import org.ojai.json.Json;
import org.ojai.json.JsonOptions;
import org.ojai.store.DocumentMutation;
import org.ojai.util.Values;    //for DocumentBuilder
// import org.ojai.types.Interval; //for DocumentBuilder


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal; //for DocumentBuilder
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer; //for DocumentBuilder
import java.nio.file.*;
import java.sql.Date; //for DocumentBuilder
import java.sql.Timestamp; //for DocumentBuilder
import java.util.*;

public class EX01JSON_READ {

    public static void main(String[] args) {

        EX01JSON_READ app = new EX01JSON_READ();
        app.run();
    }

    /***********************************
     * Byte array to use DocumentBuilder
     ***********************************/
/*
    private byte[] getByteArray(int size) {
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) i;
        }
        return bytes;
    }
    */
    private void run() {

/*******************************
 * Read the donuts resource file
 ********************************/

        URI fileLocation;
        Path path = null;
        String donutString = null;

        try {
            fileLocation = this.getClass().getClassLoader().getResource("donuts.json").toURI();
            path = Paths.get(fileLocation);
        } catch (URISyntaxException e) {
        }

        try {
            donutString = new String(java.nio.file.Files.readAllBytes(path));
        } catch (IOException e) {
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
            System.out.println(donutDocument);
            System.out.println("unformattedstring: " + Json.toJsonString(donutDocument));
            System.out.println("prettystring: " + Json.toJsonString(donutDocument, new JsonOptions().pretty()));
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
            System.out.println("batters are: " + donutDocument.getMap("batters"));
            System.out.println("doc size: " + newDonutDoc.size());
            String jsonString = Json.toJsonString(newDonutDoc.asReader());
            System.out.println("string from reader: " + jsonString);

            /************************************************************************
             *  Create a new record from a bean
             *************************************************************************/

            User bean = new User();
            bean.setId("mcjou");
            bean.setFirstName("Marie-Claude");
            bean.setLastName("Jousset");
            bean.setDob(Date.valueOf("1980-10-13"));
            bean.addInterest("html");
            bean.addInterest("css");
            bean.addInterest("js");

            Document userDoc = Json.newDocument(bean);
            System.out.println("doc from bean: " + Json.newDocument(bean));
            System.out.println("untagged string: " + Json.toJsonString(userDoc, new JsonOptions().withoutTags()));

       //     User mybean = new User().toString();
            Document doc = Json.newDocument(bean);
            User newBean = doc.toJavaBean(User.class);
            System.out.println("converted to bean: " + newBean);
                /* USED to work
                User myUser = userDoc.toJavaBean(User.class);
                System.out.println("converted to bean: " + myUser); */

            /************************************************************************
             *  Decode/encode bean
             *************************************************************************/
/*used to work
                BeanCodec bc = new BeanCodec();
                DocumentBuilder db = Json.newDocumentBuilder();
                System.out.println("decoded bean: " + bc.decode(db, bean));

                String mcjouString = Json.toJsonString(userDoc);
                DocumentReader dr = Json.newDocumentReader(mcjouString);
                System.out.println("encoded bean: " + bc.encode(dr, User.class));*/


            /************************************************************************
             *  Create a new record using Document
             *************************************************************************/

            Document record = Json.newDocument();
            record.set("id", "0001");
            record.set("type", "donut");
            record.set("name", "Cake");
            record.set("ppu", 0.55d);
            record.set("sales", 35d);

            Document batters = Json.newDocument();
            List<Object> batter = new ArrayList<Object>();

            Map<String, Object> b1 = new LinkedHashMap<String, Object>();
            b1.put("id", "1001");
            b1.put("type", "Regular");
            batter.add(b1);
            Map<String, Object> b2 = new LinkedHashMap<String, Object>();
            b2.put("id", "1002");
            b2.put("type", "Chocolate");
            batter.add(b2);
            batters.set("batter", batter);

            // add it to record
            record.set("batters", batters);

            List<Object> topping = new ArrayList<Object>();
            Map<String, Object> m2 = new LinkedHashMap<String, Object>();
            m2.put("id", "5001");
            m2.put("type", "None");
            topping.add(m2);
            Map<String, Object> m3 = new LinkedHashMap<String, Object>();
            m3.put("id", "5002");
            m3.put("type", "Glazed");
            topping.add(m3);

            // add it to record
            record.set("topping", topping);


            System.out.println(record);
            // Document rec = documentBuilder.getDocument();
            //  System.out.println(rec + Json.toJsonString(rec, new JsonOptions().pretty()));

            /************************************************************************
             *  Create a new record using DocumentBuilder
             *************************************************************************/

            DocumentBuilder jsonBuilder = Json.newDocumentBuilder();
            jsonBuilder.addNewMap();
            jsonBuilder.put("id", "0001");
            jsonBuilder.put("type", "donut");
            jsonBuilder.put("name", "Cake");
            jsonBuilder.put("ppu", 0.55d);
            jsonBuilder.put("sales", 35d);

            // build array
            jsonBuilder.putNewArray("topping");
            jsonBuilder.add("None");
            jsonBuilder.add("Glazed");
            jsonBuilder.add("Sugar");
            jsonBuilder.add("Powdered Sugar");
            jsonBuilder.add("Chocolate with Sprinkles");
            jsonBuilder.add("Chocolate");
            jsonBuilder.add("Maple");
            jsonBuilder.endArray();
            jsonBuilder.endMap(); //end of document

            System.out.println(jsonBuilder);

            System.out.println("get third topping: " + jsonBuilder.getDocument().getString("topping[3]"));


/************************************************************************
 *  Streaming
 *************************************************************************/

            InputStream in = getClass().getClassLoader().getResourceAsStream("donuts.json");
            DocumentStream<Document> stream = Json.newDocumentStream(in);

            int documentCount = 0;
            for (DocumentReader reader : stream.documentReaders()) {
                documentCount++;
                readStream(reader);
            }
            System.out.println("document count: " + documentCount);
        } catch (DecodingException e) {
            System.out.println("Invalid JSON string: " + e);
        }
    }

    private void readStream(DocumentReader reader) {
        EventType et = null;
    //    String name_field = null; //explore
        String fieldName = null;
        while ((et = reader.next()) != null) {
            //  System.out.println("event type: " + et);
        //    reader.inMap();
            if (et == EventType.STRING) {
                fieldName = reader.getFieldName();

          //      System.out.println("event field name: " + fieldName); // explore

                if (fieldName.equals("name")) {

                    System.out.println("NAME FIELD: " + reader.getString());
                }
            }
             //   }
       //     }
            // else {
            //    if ((et == DocumentReader.EventType.DOUBLE) && (fieldName.equals("ppu"))
            //           && (name_field.equals("Cake")))
            //      System.out.println("NAME FIELD: " + name_field);
            // }
        }
        //  System.out.println("read a doc: ");
    }
}

