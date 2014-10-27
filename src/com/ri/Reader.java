package com.ri;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.FilenameUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;


public class Reader {


    public Document readDocument(int id, File file){
        Document document = new Document(id, parseDocument(file));
        return document;
    }



private String parseDocument(File file){
        Tika tika=new Tika();

        Metadata metadata = new Metadata();
        ContentHandler ch = new BodyContentHandler();
        ParseContext parseContext = new ParseContext();

        // Introducction of Documents

            InputStream is = null;
            try {
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



            // Recieve Data
            AutoDetectParser parser = new AutoDetectParser();



            // Parser
            String parse = null;
            try {
                parse = tika.parseToString(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }

            try {
                parser.parse(is,ch,metadata,parseContext);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            }

            return parse;


        }




}
