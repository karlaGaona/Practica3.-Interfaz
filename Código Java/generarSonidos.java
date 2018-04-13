package interfazJavaArduino;

/* Muestra la ventana para que el usuario pueda seleccionar diferentes sonidos
que produce el buzzer, logrando interactuar con ello */

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

public class generarSonidos extends JFrame implements ActionListener {
    // Se declaran componentes de la ventana
    JLabel fondo;
    JLabel titulo;
    JButton son1;
    JButton son2;
    JButton son3;
    JButton regresar;
    conectar c = new conectar();
    
     // Variables.
    private static final String sonido1 = "sonido 1";
    private static final String sonido2 = "sonido 2";
    private static final String sonido3 = "sonido 3";
    
    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada
            
    // Inicializamos los componentes de la interfaz
    generarSonidos(InputStream input, OutputStream output){
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;
        
        fondo= new JLabel(im);  
        titulo = new JLabel("Selecciona el sonido que deseas reproducir");
        son1 = new JButton("Sonido 1 (Alarma)");
        son2 = new JButton("Sonido 2 (Melodía)");
        son3 = new JButton("Sonido 3 (Beeper)");
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
        fondo.add(son1);
        fondo.add(son2);
        fondo.add(son3); // agrega etiqueta1 a JFrame
        fondo.add(regresar); // agrega etiqueta1 a JFrame
         
        // Se establecen las características de cada componente
        fondo.setBounds(0,0,500,400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(50,50,400,50);
        son1.setBounds(150,120,200,30);
        son2.setBounds(150,170,200,30);
        son3.setBounds(150,220,200,30);
        regresar.setBounds(370,320,100,25);
        
        son1.addActionListener(this);
        son2.addActionListener(this);
        son3.addActionListener(this);
        regresar.addActionListener(this);
    }
              
    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == son1){
            c.enviarDatos(sonido1, input, output);
        } else if(e.getSource() == son2){
            c.enviarDatos(sonido2, input, output);
        } else if(e.getSource() == son3){
            c.enviarDatos(sonido3, input, output);
        } else {
            new menu(input, output);
            this.dispose();
        }
    }

}
