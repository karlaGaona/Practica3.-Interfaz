package interfazJavaArduino;

/* La clase se implementa SerialPortEventListener para comunicación con el puerto serial,
permitiendo la conexión entre java y arduino y logrando interactuar entre ellas*/

// Importar las librerías necesarías
// Se utiliza la librería RXTX para realizar la conexión correctamente y el envio de datos
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import static java.awt.image.ImageObserver.ERROR;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class conectar implements SerialPortEventListener {
    
     // Variables de conexión.
    private OutputStream output = null;         // Variable ouuput para obtener el canal de salida 
    private InputStream input = null;           // Variable para obtener el canal de entrada

    SerialPort serialPort;                      // Variable de tipo serialport
    private final String PUERTO = "COM10";      // Variable para asignar el puerto a utilizar
    private static final int TIMEOUT = 2000;    // Variable para asignar un determinado tiempo, 2 segundos.
    private static final int DATA_RATE = 9600;  // Variable para asignar los Baudios a utilizar. 
    
    public InputStream getInput(){
        return input;
    }
    
    public OutputStream getOutput(){
        return output;
    }
                  
    // Método para realizar la conexión entre java y arduino
    public void iniciarConexión(){
        CommPortIdentifier puertoID = null;     // Variable de tipo commportidentifier para almacenar el puerto a utilizar
        Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();   // Varible para guardar el puerto obtenido en puertoID
 
        // Se recorren los elementos del puertoEnum
        while (puertoEnum.hasMoreElements()){   
            // Variable de tipo commportidentifier para almacenar el puerto actual
            CommPortIdentifier actualPuertoID = (CommPortIdentifier) puertoEnum.nextElement();  
            
            // Se compara si el puerto a utilizar es igual al puerto actual
            if (PUERTO.equals(actualPuertoID.getName())){
                puertoID = actualPuertoID;      // Si es igual entonces se guarda el puerto en la variable puertoID
                break;
            }
        }
 
        // Se compara si el puertoID es nulo
        if (puertoID == null){
            // Si lo es, manda un error de conexión y cierra la ventana
            mostrarError("No se puede conectar al puerto");
            System.exit(ERROR);
        }

        /* Se asigna el canal de salida del puerto serie a la variable output, 
        con sus debido capturador de error. */
        try{
            // se inicializa la variable serialPort para abrir el puerto serie en el tiempo determinado
            serialPort = (SerialPort) puertoID.open(this.getClass().getName(), TIMEOUT);
            
            // Se declaran los parámetros del puerto serie
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
            
            // Se obtiene el canal de salida
            output = serialPort.getOutputStream();
        } catch (Exception e){
            // Si ocurre algún error manda un mensaje 
            mostrarError(e.getMessage());
            System.exit(ERROR);
        }

        /* Se asigna el canal de entrada del puerto serie a la variable input, 
        con sus debido capturador de error. */
        try {
            // Se obtiene el canal de entrada
            input = serialPort.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Se agrega el evento de escucha del puerto serial a esta misma clase 
        (recordamos que nuestra clase implementa SerialPortEventListener), el método que 
        sobreescibiremos será serialevent. */
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (TooManyListenersException ex) {
            Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
    /* Método que obtiene los datos que serán enviados al arduino a traves del puerto serie,
    con su debido capturador de error */
    void enviarDatos(String datos, InputStream input, OutputStream output){
        try{
            // Los datos se mandan por el canal de salida en Bytes
            output.write(datos.getBytes());
        } catch (Exception e){
            mostrarError("ERROR");
            System.exit(ERROR);
        }
    }
 
    // Mensaje que se muetra cuando ocurre un error
    public void mostrarError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);    
    }
    
    @Override
    public void serialEvent(SerialPortEvent spe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
