package utils.documentGeneration;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import services.ElectrodeService;
import utils.enums.Paths;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TheBlank {


    public void theDoc(String elNumberFrom,String elNumberTo, String cableLength, String empoyerPosition, String fullName, String createDate){
        XWPFDocument document = new XWPFDocument(); // делаем наш документ

        XWPFParagraph paragraph = document.createParagraph(); // делаем первый параграф нашего дока
        XWPFRun run = paragraph.createRun();

        run.setText("10. Свидетельство об упаковывании");

        run.setFontSize(14);
        run.setFontFamily("Times New Roman");
        run.setBold(true);
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun run2 = paragraph2.createRun();
        run2.setText("Электрод сравнения неполяризующийся медно-сульфатный гелевый");
        run2.setFontSize(13);
        run2.setFontFamily("Times New Roman");
        run2.setBold(true);
        run2.setUnderline(UnderlinePatterns.SINGLE);
        run2.addBreak();

        XWPFRun run3 = paragraph2.createRun();
        run3.setText("наименование изделия");
        run3.setFontSize(11);
        run3.setFontFamily("Times New Roman");
        run3.addBreak();

        XWPFParagraph paragraph3 = document.createParagraph();

        XWPFRun run4 = paragraph3.createRun();
        run4.setText("ЭСМГ-АКТИВ");
        run4.setFontSize(13);
        run4.setFontFamily("Times New Roman");
        run4.setBold(true);
        run4.setUnderline(UnderlinePatterns.SINGLE);
        run4.addBreak();

        XWPFRun run5 = paragraph3.createRun();
        run5.setText("   обозначение");
        run5.addBreak();
        run5.setText("");
        run5.setFontSize(11);
        run5.setFontFamily("Times New Roman");
        run5.addBreak();

        XWPFRun run6 = paragraph3.createRun();
        run6.setText("ТУ 28.99.39 – 005 – 37064207 – 2017");
        run6.setFontSize(13);
        run6.setFontFamily("Times New Roman");
        run6.setBold(true);
        run6.setUnderline(UnderlinePatterns.SINGLE);
        run6.addBreak();

        XWPFParagraph paragraph4 = document.createParagraph();

        XWPFRun run7 = paragraph4.createRun();

        elNumberFrom = ElectrodeService.formatElectrodeNumber(elNumberFrom);
        if (elNumberTo.isEmpty()) {
            run7.setText("№" + " " + elNumberFrom);
        } else {
            elNumberTo = ElectrodeService.formatElectrodeNumber(elNumberTo);
            run7.setText("№ " + elNumberFrom + " - " + elNumberTo);
        }
        run7.setFontSize(12);
        run7.setFontFamily("Times New Roman");
        run7.setUnderline(UnderlinePatterns.SINGLE);
        run7.addBreak();

        XWPFRun run8 = paragraph4.createRun();
        run8.setText("заводской номер");
        run8.setFontSize(11);
        run8.setFontFamily("Times New Roman");


        XWPFParagraph paragraph5 = document.createParagraph();

        XWPFRun run9 = paragraph5.createRun();
        run9.setText("    Длина кабеля:" + " " + cableLength + " " + "м.");
        run9.setFontSize(12);
        run9.setFontFamily("Times New Roman");
        run9.addBreak();

        XWPFRun run10 = paragraph5.createRun();
        run10.setText("     Упакован на");
        run10.setFontSize(13);
        run10.setFontFamily("Times New Roman");

        XWPFParagraph paragraph6 = document.createParagraph();

        XWPFRun run11 = paragraph6.createRun();
        run11.setText("ООО НПО \"Активация\"");
        run11.setFontSize(14);
        run11.setFontFamily("Times New Roman");
        run11.setBold(true);
        run11.setUnderline(UnderlinePatterns.SINGLE);
        run11.addBreak();

        XWPFRun run12 = paragraph6.createRun();
        run12.setText("наименование или код изготовителя");
        run12.setFontSize(11);
        run12.setFontFamily("Times New Roman");
        run12.addBreak();

        XWPFRun run13 = paragraph6.createRun();
        run13.setText("согласно требованиям, предусмотренным в действующей технической документации");
        run13.setFontSize(12);
        run13.setFontFamily("Times New Roman");
        run13.addBreak();

        XWPFParagraph paragraph7 = document.createParagraph();

        XWPFRun run14 = paragraph7.createRun();
        run14.setText(" " + empoyerPosition);
        run14.setFontSize(13);
        run14.setFontFamily("Times New Roman");
        run14.setItalic(true);
        run14.setUnderline(UnderlinePatterns.SINGLE);
        run14.addBreak();

        XWPFRun run15 = paragraph7.createRun();
        run15.setText(" " + "должность");
        run15.addBreak();
        run15.setText(" ");
        run15.setFontSize(11);
        run15.setFontFamily("Times New Roman");
        run15.addBreak();

        XWPFRun run16 = paragraph7.createRun();
        run16.setText(" " + "_____________");
        run16.setFontSize(13);
        run16.setFontFamily("Times New Roman");
        run16.addBreak();

        XWPFRun run17 = paragraph7.createRun();
        run17.setText(" " + "личная подпись");
        run17.addBreak();
        run17.setText(" ");
        run17.setFontSize(11);
        run17.setFontFamily("Times New Roman");
        run17.addBreak();

        XWPFRun run18 = paragraph7.createRun();
        run18.setText(" " + fullName);
        run18.setFontSize(13);
        run18.setFontFamily("Times New Roman");
        run18.setItalic(true);
        run18.setUnderline(UnderlinePatterns.SINGLE);
        run18.addBreak();

        XWPFRun run19 = paragraph7.createRun();
        run19.setText(" " + "расшифровка подписи");
        run19.addBreak();
        run19.setText(" ");
        run19.setFontSize(11);
        run19.setFontFamily("Times New Roman");
        run19.addBreak();

        XWPFRun run20 = paragraph7.createRun();
        run20.setText(" " + createDate);
        run20.setFontSize(13);
        run20.setFontFamily("Times New Roman");
        run20.setItalic(true);
        run20.setUnderline(UnderlinePatterns.SINGLE);
        run20.addBreak();

        XWPFRun run21 = paragraph7.createRun();
        run21.setText(" " + "число, месяц, год");
        run21.setFontSize(11);
        run21.setFontFamily("Times New Roman");

        paragraph2.setAlignment(ParagraphAlignment.CENTER);
        paragraph2.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph2.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph2.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph2.setBorderTop(Borders.BASIC_THIN_LINES);

        paragraph3.setAlignment(ParagraphAlignment.LEFT);
        paragraph3.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph3.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph3.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph3.setBorderTop(Borders.BASIC_THIN_LINES);

        paragraph4.setAlignment(ParagraphAlignment.CENTER);
        paragraph4.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph4.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph4.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph4.setBorderTop(Borders.BASIC_THIN_LINES);

        paragraph5.setAlignment(ParagraphAlignment.LEFT);
        paragraph5.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph5.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph5.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph5.setBorderTop(Borders.BASIC_THIN_LINES);

        paragraph6.setAlignment(ParagraphAlignment.CENTER);
        paragraph6.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph6.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph6.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph6.setBorderTop(Borders.BASIC_THIN_LINES);

        paragraph7.setAlignment(ParagraphAlignment.LEFT);
        paragraph7.setBorderBottom(Borders.BASIC_THIN_LINES);
        paragraph7.setBorderLeft(Borders.BASIC_THIN_LINES);
        paragraph7.setBorderRight(Borders.BASIC_THIN_LINES);
        paragraph7.setBorderTop(Borders.BASIC_THIN_LINES);

        File file = crateFile();
        try(FileOutputStream output = new FileOutputStream(file)) {
            document.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private File crateFile(){
        String path = Paths.C.get()+Paths.ACCOUNTING_SYSTEM.get()+Paths.DOCUMENTS.get();
        String prefix = "passport_";
        String postfix = ".doc";
        String genericTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fullname = prefix+genericTime+postfix;
        return new File(path,fullname);
    }
}

