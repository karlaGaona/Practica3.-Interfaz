package interfazJavaArduino;

/*
----------------------------------------------------------------------
Interacción de usuario con Java-Arduino 
-----------------------------------------------------------------------
Programa que permite la interacción con el usuario realizando diferentes 
actividades como: 
  -Navegar entre mensajes para ser mostrados como temperatura,
  hora y fecha, y otro predeterminado.
  -Elegir el color que tomará el LED Multicolor.
  -Elegir entre varias opciones de secuencia de LEDs para ser realizada.
  -Elegir entre varias opciones de sonido para ser reproducidad.
Esto se realiza utilizando la Pantalla LCD, Controlador LCD I2C, RTC DS1307,
sensor de temperatura, LED Multicolor, LEDs y buzzer.

Sistemas Programables
Karla Paola Gaona Delgado
12 de abril del 2018
*/

/**
 *
 * @author Karla Paola
 */

// Importar librerias a utilizar
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

public class interfaz extends JFrame implements ActionListener {

    // Se declaran componentes de la ventana
    JLabel fondo;
    JButton continuar;
    JButton salir;
    JLabel titulo;

    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada

    // Inicializamos los componentes de la interfaz
    interfaz(InputStream input, OutputStream output) {
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;

        fondo = new JLabel(im);
        titulo = new JLabel("<html><body> BIENVENIDO <br>"
                + "Interfaz en Java para interactuar con Arduino </body></html>");
        continuar = new JButton("Continuar");
        salir = new JButton("Salir");

        prepararGUI();
    }

    // Método para preparar la ventana
    public void prepararGUI() {
        setSize(500, 400);
        setLocation(380, 100);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Se añaden los omponentes a la ventana
        add(fondo);
        fondo.add(titulo); 
        fondo.add(continuar);
        fondo.add(salir);

        // Se establecen las características de cada componente
        fondo.setBounds(0, 0, 500, 400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.ITALIC, 28));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(100, 50, 300, 200);
        continuar.setBounds(260, 320, 100, 25);
        continuar.addActionListener(this);
        salir.setBounds(370, 320, 100, 25);
        
        salir.addActionListener(this);
    }

    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continuar) {
            this.dispose();
            new menu(input, output);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        conectar c = new conectar();     
        c.iniciarConexión();        // LLamada a método para realizar la conexión desde java con el arduino

        new interfaz(c.getInput(), c.getOutput());      // Despliega la interfaz
    }

}
