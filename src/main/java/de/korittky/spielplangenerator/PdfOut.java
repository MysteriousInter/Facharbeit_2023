package de.korittky.spielplangenerator;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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

  /*  protected void manipulatePdf(String dest,String[][] plan) throws Exception {

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        Table table = new Table(UnitValue.createPercentArray(plan[1].length)).useAllAvailableWidth();

        for (int i = 0; i < plan[1].length; i++) {
            for (int j = 0; j < plan.length; j++) {
                table.addCell(plan[i][j]);
            }
        }


        doc.add(table);

        doc.close();
    }

   */
    public void printPlanPdf(Plan plan,Spielfest spielfest)throws Exception{
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        int i=1;
        Table table1 = new Table(UnitValue
                .createPercentArray(new float[]{1,3,1}))
                .setFontSize(7)
                .setAutoLayout();//.useAllAvailableWidth();
        for(Runde runde : plan.getRunden()){
            table1.addCell("Runde"+(runde.getRundennummer()+1));
            table1.startNewRow();
            int platz=1;
            for(Spiel spiel : runde.getSpiele()){
                table1.addCell(""+i++);
                table1.addCell(spiel.getTeam1() + " : " + spiel.getTeam2());
                table1.addCell("platz"+platz++);
               //table.addCell(""+spiel.getPlatz());
            }
            table1.startNewRow();


        }
        doc.add(new Paragraph(spielfest.getGastgeber()+" "+spielfest.getDatum()));
        doc.add(table1);

        doc.close();
    }


}
