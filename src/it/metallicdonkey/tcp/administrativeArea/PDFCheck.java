package it.metallicdonkey.tcp.administrativeArea;

import java.io.FileOutputStream;
import java.time.LocalDate;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Match;

public class PDFCheck {

  public String print(Check morning, Check afternoon, Check evening) {
    System.out.println("INizio stampa");
    Document output = null;

    LocalDate d = LocalDate.now();
    String path = "./check-"+d.getDayOfMonth()+"-"+d.getMonth()+"-"+d.getYear()+".pdf";
    
    try {
      // text file to convert to pdf as args[0]
      // letter 8.5x11
      //    see com.lowagie.text.PageSize for a complete list of page-size constants.
      output = new Document(PageSize.LETTER, 40, 40, 40, 40);
      // pdf file as args[1]
      PdfWriter.getInstance(output, new FileOutputStream(path));

      output.open();
      output.addAuthor("TCP Project");
      output.addSubject("Check");
      output.addTitle("Check");

      String line = "Mattina:\n";
      
      for(int i=0; i<morning.getMatches().size(); i++) {
      	Match m = morning.getMatches().get(i);
      	line+="Autista: "+m.getEmployee().getId()+" - "+m.getEmployee().getFirstName()+" "+m.getEmployee().getLastName()+"\n";
      	line+="Mezzo: "+m.getVehicle().getId() +" - "+m.getVehicle().getBrand()+"\n";
      	line+="Linea: "+m.getLine().getName()+" - "+m.getLine().getStartTerminal()+" - "+m.getLine().getEndTerminal()+"\n";
      	if(morning.getMatches().size()-1 != i)
      		line+="______________________________\n\n";
      }
      
      line+="\n\n\n\n";

      Paragraph p = new Paragraph(line);
      p.setAlignment(Element.ALIGN_JUSTIFIED);
      output.add(p);
      
      String line2 = "Pomeriggio:\n";
      
      for(int i=0; i<afternoon.getMatches().size(); i++) {
      	Match m = afternoon.getMatches().get(i);
      	line2+="Autista: "+m.getEmployee().getId()+" - "+m.getEmployee().getFirstName()+" "+m.getEmployee().getLastName()+"\n";
      	line2+="Mezzo: "+m.getVehicle().getId() +" - "+m.getVehicle().getBrand()+"\n";
      	line2+="Linea: "+m.getLine().getName()+" - "+m.getLine().getStartTerminal()+" - "+m.getLine().getEndTerminal()+"\n";
      	if(afternoon.getMatches().size()-1 != i)
      		line2+="______________________________\n\n";
      }
    
      line2+="\n\n\n\n";

      Paragraph p2 = new Paragraph(line2);
      p2.setAlignment(Element.ALIGN_JUSTIFIED);
      output.add(p2);
      
      String line3 = "Sera:\n";
      
      for(int i=0; i<evening.getMatches().size(); i++) {
      	Match m = evening.getMatches().get(i);
      	line3+="Autista: "+m.getEmployee().getId()+" - "+m.getEmployee().getFirstName()+" "+m.getEmployee().getLastName()+"\n";
      	line3+="Mezzo: "+m.getVehicle().getId() +" - "+m.getVehicle().getBrand()+"\n";
      	line3+="Linea: "+m.getLine().getName()+" - "+m.getLine().getStartTerminal()+" - "+m.getLine().getEndTerminal()+"\n";
      	if(evening.getMatches().size()-1 != i)
      		line3+="______________________________\n\n";
	    }
	    
	    line3+="\n\n\n\n";

      Paragraph p3 = new Paragraph(line3);
      p3.setAlignment(Element.ALIGN_JUSTIFIED);
      output.add(p3);
    
      System.out.println("Done.");
      output.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return path;
  }
}