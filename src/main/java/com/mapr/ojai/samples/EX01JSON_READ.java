package com.mapr.ojai.samples;

/**
 * Created by Kristine Hahn on 10/5/15.
 */

import org.ojai.Document;

import org.ojai.DocumentReader;
import org.ojai.FieldPath;
import org.ojai.Value;
import org.ojai.exceptions.DecodingException;
import org.ojai.json.Json;
import org.ojai.json.JsonOptions;
import org.ojai.types.Interval;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
*  Convert the JSON string read from the resource file to a mapR Document
*************************************************************************/
            try {
                Document donutDocument = Json.newDocument(donutString);
                System.out.println("Donut Document: " + donutDocument.toString());
            }
            catch (DecodingException e) {
                System.out.println("Invalid JSON string: " + e);
            }
    }
}
