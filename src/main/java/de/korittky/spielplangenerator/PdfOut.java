package de.korittky.spielplangenerator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;

public class PdfOut {
    //public static final String dest= "F://IdeaProjects//Facharbeit//spielplangenerator/test.pdf";
    private String dest;
    public PdfOut(String dest){
        File file = new File(dest);
        this.dest=dest;
        file.getParentFile().mkdirs();
    }


    public void printPlanPdf(Plan plan,Spielfest spielfest)throws Exception{
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        int spielnummer = 1;
        Table layoutTable = new Table(UnitValue.createPercentArray(2))
                .setVerticalBorderSpacing(100).useAllAvailableWidth();

        for(Runde runde : plan.getRunden()){
            Table rundenTable = new Table(UnitValue
                    .createPercentArray(new float[]{1,3,1}))
                    .setFontSize(9)
                    .setAutoLayout();
            rundenTable.addCell("Runde"+(runde.getRundennummer()+1));
            rundenTable.startNewRow();
            int platz=1;
            for(Spiel spiel : runde.getSpiele()){
                rundenTable.addCell(""+spielnummer ++);
                rundenTable.addCell(spiel.toString());
                rundenTable.addCell("platz"+platz++);
               //table.addCell(""+spiel.getPlatz());
            }
            Cell cell=new Cell()
                    .add(rundenTable).setPaddingBottom(10)
                    .setBorder(Border.NO_BORDER);
            layoutTable.addCell(cell);



        }
        doc.add(new Paragraph(spielfest.getGastgeber()+" "+spielfest.getDatum()+" Beginnt um:"+spielfest.getBeginnzeit()).setFontSize(20));
        doc.add(layoutTable);

        doc.close();
    }

}
