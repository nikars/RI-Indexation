package com.ri;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Lector del texto plano
 */
public class PlainTextReader {

    public Document readDocument(int id, File file) {
        return new Document(id, parseDocument(file));
    }

    private String parseDocument(File file) {
        Tika tika = new Tika();

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Metadata metadata = new Metadata();
        ContentHandler ch = new BodyContentHandler(10 * 1024 * 1024); // Incrementar el límite de caracteres
        ParseContext parseContext = new ParseContext();

        AutoDetectParser parser = new AutoDetectParser();

        try {
            parser.parse(is, ch, metadata, parseContext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }

        return ch.toString();
    }
}
