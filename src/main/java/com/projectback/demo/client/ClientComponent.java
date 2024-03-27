package com.projectback.demo.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClientComponent {
    public String getValues () {
        try {
	    Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();

	    Elements paragraphs = doc.select("p");

	    StringBuilder result = new StringBuilder();
	    for (org.jsoup.nodes.Element paragraph : paragraphs) {
	        result.append(paragraph.text()).append("\n");
	    }
	    return result.toString();
        } catch (IOException e) {
	    e.printStackTrace();
	    return null;
        }
    }
}