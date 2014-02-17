package com.example.homepractice;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlParser {
	private InputStream instream = null;
	private String errorCode = null;
	private XmlPullParserFactory factory = null;
	private XmlPullParser parser = null;
	private boolean initem = false;
	
	public XmlParser(InputStream instream) {
		this.instream = instream;
	}
	
	public String xmlParse() {
		try {
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
		
			parser.setInput(instream, "utf-8");
		
			int eventType = parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				switch(eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("error")) {
						initem = true;
					}
					break;
				case XmlPullParser.TEXT:
					if(initem) {
						errorCode = parser.getText();
						initem = false;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errorCode;
	}
}
