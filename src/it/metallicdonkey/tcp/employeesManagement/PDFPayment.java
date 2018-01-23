package it.metallicdonkey.tcp.employeesManagement;

import java.io.FileOutputStream;
import java.time.LocalDate;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import it.metallicdonkey.tcp.models.Check;
import it.metallicdonkey.tcp.models.Match;
import it.metallicdonkey.tcp.models.Payment;

public class PDFPayment {

  public String print(Payment payment) {
    System.out.println("INizio stampa");
    Document output = null;

    LocalDate d = LocalDate.now();
    String date = d.getDayOfMonth()+"-"+d.getMonth()+"-"+d.getYear();
    String path = "./payment-"+date+".pdf";
    
    try {
      // text file to convert to pdf as args[0]
      // letter 8.5x11
      //    see com.lowagie.text.PageSize for a complete list of page-size constants.
      output = new Document(PageSize.LETTER, 40, 40, 40, 40);
      // pdf file as args[1]
      PdfWriter.getInstance(output, new FileOutputStream(path));

      output.open();
      output.addAuthor("TCP Project");
      output.addSubject("Payment");
      output.addTitle("Payment");

      String line = "Mandato di pagamento "+date.replaceAll("-", "/")+":\n";
      
      line += "Matricola: "+payment.getIdEmployee()+"\n";
      line += "Quota: "+payment.getNetSalary()+"\n";
      line += "Da prelevare presso: Monte dei paschi di Siena \n";
      line += "Codice a barre:\n";
      line += "|| || ||| || | | | | ||| | |\n";
      line += "|| || ||| || | | | | ||| | |\n";
      line += "12 34 523 21 43 54 32 3 93 2";
      line += "\n";
      
      
      
      Paragraph p = new Paragraph(line);
      p.setAlignment(Element.ALIGN_JUSTIFIED);
      output.add(p);
    
      System.out.println("Done.");
      output.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return path;
  }
}