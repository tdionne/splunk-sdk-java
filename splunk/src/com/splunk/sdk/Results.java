/*
 * Copyright 2011 Splunk, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"): you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.splunk.sdk;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;

/**
 * Low level splunk sdk http incremental reader and data converter
 */

public class Results {

    public String getContents(HttpURLConnection urlconn) throws IOException, XMLStreamException {
        StringBuilder composite = new StringBuilder();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);   // default true

        XMLEventReader ereader = factory.createXMLEventReader(new InputStreamReader(urlconn.getInputStream()));

        // TODO: break out XML processing
        while(ereader.hasNext()) {
            XMLEvent event = ereader.nextEvent();
            if (event.getEventType() != XMLEvent.END_DOCUMENT) {
                composite.append(event.toString());
            }
        }

        return composite.toString();
    }


    //TODO: add other methods like getHeaders(), getStatusCode(), etc...
    // int response = urlconn.getResponseCode();

}
