package interfazJavaArduino;

/* Muestra la ventana para seleccionar e interactuar con el LED multicolor
logrando cambiar los colores que debe mostrar dependiendo el botón que se elija */

// Importar librerias a utilizar
import java.awt.Color;
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

public class colorLed extends JFrame implements ActionListener {
    // Se declaran componentes de la ventana
    JLabel fondo;
    JLabel titulo;
    JButton rojo;
    JButton verde;
    JButton azul;
    JButton blanco;
    JButton magenta;
    JButton naranja;
    JButton regresar;
    conectar c = new conectar();
    
    // Variables.
    private static final String colorRojo = "rojo";
    private static final String colorVerde = "verde";
    private static final String colorAzul = "azul";
    private static final String colorBlanco = "blanco";
    private static final String colorMagenta = "magenta";
    private static final String colorNaranja = "naranja";
    
    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada
    
    // Inicializamos los componentes de la interfaz
    colorLed(InputStream input, OutputStream output){
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;
        
        fondo= new JLabel(im);   
        titulo = new JLabel("Selecciona el color que deseas para el LED RGB");
        rojo = new JButton("Rojo");
        verde = new JButton("Verde");
        azul = new JButton("Azul");
        blanco = new JButton("Blanco");
        magenta = new JButton("Magenta");
        naranja = new JButton("Naranja");
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
        fondo.add(titulo); // agrega etiqueta1 a JFrame
        fondo.add(rojo); // agrega etiqueta1 a JFrame
        fondo.add(verde);
        fondo.add(azul);
        fondo.add(blanco);
        fondo.add(magenta);
        fondo.add(naranja);
        fondo.add(regresar);
         
        // Se establecen las características de cada componente
        fondo.setBounds(0,0,500,400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(50,30,400,50);
        rojo.setForeground(Color.red);
        rojo.setBounds(100,100,100,30);
        verde.setForeground(Color.green);
        verde.setBounds(100,150,100,30);
        azul.setForeground(Color.blue);
        azul.setBounds(100,200,100,30);
        blanco.setBounds(300,150,100,30);
        blanco.setForeground(Color.gray);
        magenta.setBounds(300,200,100,30);
        magenta.setForeground(Color.magenta);
        naranja.setBounds(300,250,100,30);
        naranja.setForeground(Color.orange);
        regresar.setBounds(370,320,100,25);
        
        rojo.addActionListener(this);
        verde.addActionListener(this);
        azul.addActionListener(this);
        blanco.addActionListener(this);
        magenta.addActionListener(this);
        naranja.addActionListener(this);
        regresar.addActionListener(this);
    }
               
    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == rojo){
            c.enviarDatos(colorRojo, input, output); 
        } else if(e.getSource() == verde){
            c.enviarDatos(colorVerde, input, output); 
        } else if(e.getSource() == azul){
            c.enviarDatos(colorAzul, input, output); 
        } else if(e.getSource() == blanco){
            c.enviarDatos(colorBlanco, input, output); 
        } else if(e.getSource() == magenta){
            c.enviarDatos(colorMagenta, input, output); 
        } else if(e.getSource() == naranja){
            c.enviarDatos(colorNaranja, input, output);  
        } else if(e.getSource() == regresar){
            new menu(input, output);
            this.dispose();
        } else {
            System.exit(0);
        }
    }
}
