package mx.hashCode.printerTest;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Hello world!
 *
 */
public class App implements Printable, ActionListener {

    public static void main(String[] args) {

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame ventanaPrincipal = new JFrame("Hello World Printer");
        ventanaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JButton botonImprimir = new JButton("Imprimir recibo");
        botonImprimir.addActionListener(new App());
        ventanaPrincipal.add("Center", botonImprimir);
        ventanaPrincipal.pack();
        ventanaPrincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();

        PageFormat pf = job.defaultPage();
        Paper paper = pf.getPaper();
        paper.setSize(3.5 * 72, 11 * 72);
        paper.setImageableArea(0.5 * 72, 0.0 * 72, 3.0 * 72, 10.5 * 72);
        
        pf.setPaper(paper);

        Book book = new Book();//java.awt.print.Book
        book.append(this, pf);
        job.setPageable(book);

        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
                ex.printStackTrace();
            }
        }
    }

    private ArrayList<String> datosTicket() {
        ArrayList<String> datos = new ArrayList<>();

        datos.add("GRUPO GASOLINERO");
        datos.add("www.grupo.com.mx");
        datos.add("VALE SERVICIOS GASOLINEROS SA DE CV");
        datos.add("AV SALVADOR No 100");
        datos.add("RFC: V***********");
        datos.add("CLAVE SIIC: 0000000000 ESTACION: E11111");
        datos.add("CUENTA");
        datos.add("OPERADORA S.A. DE C.V.");
        datos.add("SUBCUENTA:");
        datos.add("TSURU");
        datos.add("----------------------------------------");
        datos.add("TRANSACCION: 37452");
        datos.add("POSICION: 08  PRODUCTO: MAGNA(32011)");
        datos.add("HORA: 16:05:20   FECHA: 29/12/2017");
        datos.add("RECIBO: ORIGINAL   MOP: CREDITO");
        datos.add("CORTE: #1811");
        datos.add("----------------------------------------");
        datos.add("Cant.    Cod.   Prod.   PU $   importe $");
        datos.add("17.160   32011  MAGNA   16.300    289.49");
        datos.add("1       351 BARDAHL SUPER RA 65.00 65.00");
        datos.add("         VENTA TOTAL: $354.49");
        datos.add("Gracias por su preferencia");
        datos.add("Vuelva pronto");
        datos.add("");
        datos.add("");
        datos.add("");
        datos.add("");
        datos.add("RECIBIO                      DESPACHO");
        datos.add("----------------------------------------");
        datos.add("");
        datos.add("");
        datos.add("");

        return datos;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */

            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        ArrayList<String> datos = this.datosTicket();
        
        g.setFont(new Font("Monospaced", Font.PLAIN, 8));
        
        for (int i = 0; i < datos.size(); i++) {
            g.drawString(datos.get(i), 10, 100 + (10 * i));
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

}
