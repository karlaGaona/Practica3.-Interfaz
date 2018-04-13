package interfazJavaArduino;

/* Muestra la ventana para que el usuario pueda seleccionar entre varias secuencias 
de leds */

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

public class secuenciaLeds extends JFrame implements ActionListener{
    // Se declaran componentes de la ventana
    JLabel fondo;
    JLabel titulo;
    JButton sec1;
    JButton sec2;
    JButton sec3;
    JButton regresar;
    conectar c = new conectar();
    
     // Variables.
    private static final String secuencia1 = "secuencia 1";
    private static final String secuencia2 = "secuencia 2";
    private static final String secuencia3 = "secuencia 3";
    
    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada
    
    // Inicializamos los componentes de la interfaz        
    secuenciaLeds(InputStream input, OutputStream output){
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;
        
        fondo= new JLabel(im);  
        titulo = new JLabel("Selecciona la secuencia de LEDs que desees");
        sec1 = new JButton("Secuencia 1");
        sec2 = new JButton("Secuencia 2");
        sec3 = new JButton("Secuencia 3");
        regresar = new JButton("Regresar");
        
        prepararGUI();
    }
    
    // Método para preparar la ventana
    public void prepararGUI(){
        setSize(500,400);
        setLocation(380,100);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        // Se añaden los omponentes a la ventana
        add(fondo);
        fondo.add(titulo);
        fondo.add(sec1);
        fondo.add(sec2);
        fondo.add(sec3); // agrega etiqueta a JFrame
        fondo.add(regresar); // agrega etiqueta a JFrame
         
        // Se establecen las características de cada componente
        fondo.setBounds(0,0,500,400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(50,50,400,50);
        sec1.setBounds(150,120,200,30);
        sec2.setBounds(150,170,200,30);
        sec3.setBounds(150,220,200,30);
        regresar.setBounds(370,320,100,25);
        
        sec1.addActionListener(this);
        sec2.addActionListener(this);
        sec3.addActionListener(this);
        regresar.addActionListener(this);
    }
            
    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == sec1){
            c.enviarDatos(secuencia1, input, output);
        } else if(e.getSource() == sec2){
            c.enviarDatos(secuencia2, input, output);
        } else if(e.getSource() == sec3){
            c.enviarDatos(secuencia3, input, output);
        } else {
            new menu(input, output);
            this.dispose();
        }
    }
    
}
