package com.example.rabbitdocs.utils;

import com.example.rabbitdocs.models.User;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.UUID;

@Component
public class PdfGenerator {
    private final static String HOKAGE = "C:\\Users\\Марат Шигабутдинов\\Desktop\\JavaLab\\rabbitdocs\\hokage";
    private final static String SHINOBI_WAR = "C:\\Users\\Марат Шигабутдинов\\Desktop\\JavaLab\\rabbitdocs\\SHINOBI_WAR";

    public static void createPdf(User user, String title) throws FileNotFoundException {
        String filePath = "";
        if (title.equals("Hokage") || title.equals("Hokage Order"))
            filePath = HOKAGE;
        else if (title.equals("Shinobi war"))
            filePath = SHINOBI_WAR;
        String fp = filePath + title + "_" + user.getLastName() + UUID.randomUUID().toString() + ".pdf";
        System.out.println("Full filepath: " + fp);
        PdfDocument pdf = new PdfDocument(new PdfWriter(fp));
        Document document = new Document(pdf);

        Style style = new Style().setFontSize(20);

        document.add(new Paragraph(title).addStyle(style));
        document.add(new Paragraph("Name: " + user.getFirstName()));
        document.add(new Paragraph("Surname: " + user.getLastName()));
        document.add(new Paragraph("Age: " + user.getAge()));
        document.close();
    }
}