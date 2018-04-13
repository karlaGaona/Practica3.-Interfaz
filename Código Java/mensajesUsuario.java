package interfazJavaArduino;

/* Muestra la ventana para interactuar con los mensajes que se encuentran
predeterminados como hora y fecha, temperatura y bienvenida, y que se muestren
en la pantalla LCD */

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

public class mensajesUsuario extends JFrame implements ActionListener {
    // Se declaran componentes de la ventana
    JLabel fondo;
    JLabel titulo;
    JButton sig;
    JButton ant;
    JButton regresar;
    conectar c = new conectar();
    int contador=1, contador2=1;
    
    // Variables.
    private static final String temperatura = "temperatura";
    private static final String horayFecha = "hora y fecha";
    private static final String mensajes = "mensajes";
    
    // Variables de conexión.
    OutputStream output;    // Variable paraobtener el canal de salida 
    InputStream input;      // variable para obtener el canal de entrada

    // Inicializamos los componentes de la interfaz
    mensajesUsuario(InputStream input, OutputStream output){
        super("Interacción de Usuario con Java y Arduino");
        ImageIcon im = new ImageIcon("fondo2.jpg");
        this.input = input;
        this.output = output;
        
        fondo= new JLabel(im);
        titulo = new JLabel("Mostrar mensajes en la Pantalla LCD");
        sig = new JButton("Siguiente");
        ant = new JButton("Anterior");
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
        fondo.add(sig); // agrega etiqueta1 a JFrame
        fondo.add(ant); // agrega etiqueta1 a JFrame
        fondo.add(regresar);
         
        // Se establecen las características de cada componente
        fondo.setBounds(0,0,500,400);
        titulo.setFont(new java.awt.Font("Tahoma", Font.BOLD, 16));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setBounds(100,60,300,30);
        sig.setBounds(150,120,200,30);
        ant.setBounds(150,180,200,30);
        regresar.setBounds(370,320,100,25);
        
        sig.addActionListener(this);
        ant.addActionListener(this);
        regresar.addActionListener(this);
    }
               
    // Eventos que se realizan con los botones
    public void actionPerformed(ActionEvent e) {   
        if(e.getSource() == sig){            
            if(contador == 1){
                c.enviarDatos(mensajes, input, output);
            } else if (contador == 2){
                c.enviarDatos(temperatura, input, output);
            } else if (contador == 3){
                c.enviarDatos(horayFecha, input, output); 
            } else {
                contador = 0;
                c.enviarDatos(mensajes, input, output);
            }
            contador ++;
        } else if(e.getSource() == ant){
            if(contador2 == 1){
                c.enviarDatos(horayFecha, input, output);
            } else if (contador2 == 2){
                c.enviarDatos(temperatura, input, output);
            } else if (contador2 == 3){
                c.enviarDatos(mensajes, input, output);  
            } else {
                contador2=0;
                c.enviarDatos(horayFecha, input, output); 
            }
            contador2 ++;
        } else {
            new menu(input,output);
            this.dispose();
        }
    }

}
