package interfazJavaArduino;

/* Muestra el menú de actividades a realizar, desplegando las opciones que se
tienen y realizando la acción correspondiente */

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

public class menu extends JFrame implements ActionListener{
    // Se declaran componentes de la ventana
    JLabel fondo;
    JLabel titulo;
    JButton secLeds;
    JButton sonidos;
    JButton mensaje;
    JButton leds;
    JButton salir;
    
    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada
            
    // Inicializamos los componentes de la interfaz
    menu(InputStream input, OutputStream output){
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;
        
        fondo= new JLabel(im);  
        titulo = new JLabel("Selecciona lo que deseas realizar");
        mensaje = new JButton("Mensaje");
        leds = new JButton("Color del LED");
        secLeds = new JButton("Secuencia de LEDs");
        sonidos = new JButton("Sonidos");
        salir = new JButton("Salir");
        
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
        fondo.add(mensaje);
        fondo.add(leds);
        fondo.add(secLeds); 
        fondo.add(sonidos); 
        fondo.add(salir);
         
        // Se establecen las características de cada componente
        fondo.setBounds(0,0,500,400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(100,30,300,50);
        mensaje.setBounds(150,100,200,30);
        leds.setBounds(150,150,200,30);
        secLeds.setBounds(150,200,200,30);
        sonidos.setBounds(150,250,200,30);
        salir.setBounds(370,320,100,25);
        
        
        secLeds.addActionListener(this);
        sonidos.addActionListener(this);
        mensaje.addActionListener(this);
        leds.addActionListener(this);
        salir.addActionListener(this);
    }
        
    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == mensaje){
            new mensajesUsuario(input, output);
            this.dispose();
        } else if(e.getSource() == leds){
            new colorLed(input, output);
            this.dispose();
        } else if(e.getSource() == secLeds){
            new secuenciaLeds(input, output);
            this.dispose();
        } else if(e.getSource() == sonidos){
            new generarSonidos(input, output);
            this.dispose();
        } else {
            System.exit(0);
        }
    }

}
