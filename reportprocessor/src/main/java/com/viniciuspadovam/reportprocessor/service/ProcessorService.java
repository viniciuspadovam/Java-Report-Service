package com.viniciuspadovam.reportprocessor.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.viniciuspadovam.reportprocessor.dto.ReportPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessorService {
	
	@KafkaListener(topics = "test-topic", groupId = "group_id")
	public void listen(ReportPayload message) {
		processReport(message);
	}
	
	private void processReport(ReportPayload payload) {
		log.info("Criando PDF...");
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
			document.open();
			
			document.add(createTitle(payload.title()));
			document.add(createTable(payload.body()));
			
			document.close();		
		} catch(FileNotFoundException | DocumentException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			log.info("PDF criado.");
		}
	}
	
	private Paragraph createTitle(String title) {
		Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
		font.setStyle(1); // BOLD
		Chunk chunk = new Chunk(title, font);
		Paragraph documentTitle = new Paragraph(chunk);
		documentTitle.setSpacingAfter(65f);
		return documentTitle;
	}
	
	private PdfPTable createTable(Map<String, ArrayList<String>> body) {
		PdfPTable table = new PdfPTable(body.size());
		addTableHeader(table, body.keySet());
		addRows(table, body);
		return table;
	}
	
	private void addTableHeader(PdfPTable table, Set<String> keys) {
	    keys.forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table, Map<String, ArrayList<String>> body) {
		int maxRows = body.entrySet().stream()
                .mapToInt(entry -> entry.getValue().size())
                .max()
                .orElse(0);
		
		for(int i = 0; i < maxRows; i++) {
            for(String header : body.keySet()) {
                var array = body.get(header);
                if(i < array.size()) {
                    table.addCell(array.get(i));
                } else {
                    table.addCell("");
                }
            }
		}
	}
	
}
