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
	    Elements rows = doc.select("tr.linhaImparCentralizada, tr.linhaParCentralizada");
	    StringBuilder result = new StringBuilder();
            for (Element row : rows) {
	        Element firstCell = row.selectFirst("td");
                String state = firstCell.text();
                Element fifthCellImage = row.select("td:nth-child(5) img").first();
                String srcAttribute = fifthCellImage.attr("src");
                String status;
                if (srcAttribute.equals("imagens/bola_verde_P.png")) {
	            status = "positivo";
                } else if (srcAttribute.equals("imagens/bola_amarela_P.png")) {
	            status = "alerta";
                } else if (srcAttribute.equals("imagens/bola_vermelha_P.png")) {
	            status = "negativo";
                } else {
	            status = "desconhecido";
                }
                result.append(", Estado: ").append(state).append("Status: ").append(status).append(System.lineSeparator());

            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}