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

//--------------------------------------------------
//Declara librerías a utilizar
//--------------------------------------------------
#include <RTClib.h>                 // Incluye la Libreria RTClib para utilizar el RTC DS1307
#include <Wire.h>                   // Incluye la Libreria Wire se utiliza para comunicar la placa arduino con otros dispositivos con el protocolo 12C/TWI
#include <LiquidCrystal_I2C.h>      // Incluye la Libreria LiquidCrystal_I2C para utilizar la Pantalla LCD con el controlador LCD I2C


//--------------------------------------------------
//Declara puertos de entradas y salidas y variables
//--------------------------------------------------
//Pantalla LCD y Controlador LCD I2C
LiquidCrystal_I2C lcd(0x3F,16,2);   // Crear el objeto lcd dirección 0x3F y 16 columnas x 2 filas

// Sensor de Temperatura
float sensorTemp=A0;                // Pin asignado al sensor de temperatura

// RTC DS1307
RTC_DS1307 rtc;                     // Declaramos un RTC DS1307
int segundo,minuto,hora,dia,mes;    // Almacenan los segundos, minutos y horas, al igual que el dia y el mes del RTC DS1307
long anio;                          // Almacena el año de tipo long
DateTime HoraFecha;                 // Almacena una fecha y hora

// Buzzer
int buzzer = 3;                     // Pin asignado al buzzer
int duracion=250;                   // Duración del sonido
int fMin=2000;                      // Frecuencia más baja que queremos emitir
int fMax=4000;                      // Frecuencia más alta que queremos emitir
int i=0;                            // Contador
int numTones = 10;                  // Definimos una variable con el número de tonos que va a reproducir
int tones[] = {261, 277, 294, 311, 330, 349, 370, 392, 415, 440};
//                  mid C  C#   D     D#    E     F     F#    G     G#   A
// Arriba se muestran las equivalencias entre frecuencias y Notas de la escala natural, no están todas declaradas pero existen.

// LED Multicolor
int ledRojo=9;                      // Pin asignado a rojo
int ledVerde=10;                    // Pin asignado a verde
int ledAzul=11;                     // Pin asignado a azul

// Diferentes LEDs
int led1=5;                         // Pin para el LED amarillo
int led2=6;                         // Pin para el LED verde
int led3=7;                         // Pin para el LED rojo

int tiempo;                         // Almacena diferentes valores para asignar el tiempo de espera

// Monitor Serie
char caracter;                      // Almacena cada caracter detectado en el monitor 
String comando;                     // Almacena la cadena completa detectada en el monitor

//------------------------------------
//Funcion principal
//------------------------------------
void setup() {
  // Declarar pines de entrada o salida
  pinMode(ledRojo, OUTPUT);
  pinMode(ledVerde, OUTPUT);
  pinMode(ledAzul, OUTPUT);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
  pinMode(buzzer, OUTPUT);
  
  Serial.begin(9600);               // Iniciamos el puerto serie a 9600 baudios. 
  
  lcd.init();                       // Inicializar el LCD
  lcd.backlight();                  // Encender la luz de fondo
  rtc.begin();                      //Inicializamos el RTC
  // Ponemos en hora, solo la primera vez, luego comentar y volver a cargar.
  // Ponemos en hora con los valores de la fecha y la hora en que el sketch ha sido compilado.
  rtc.adjust(DateTime(__DATE__, __TIME__));
}

//------------------------------------
//Funcion ciclicla
//------------------------------------
void loop() {  
  // Ciclo para que se muestre el mensaje 1 y también se pueda realizar cualquier otra opción
  for(int m=0; m < 3; m++){
    for (int x=0; x < 100; x++) {             
      tiempo = 250;                   // Valor del tiempo
  
      // Elegir el mensaje a mostrar secuencialmente en un determinado tiempo
      if(m == 0){
        mensaje1();                   // Se llama al método para desplegar el mensaje 1
      } else if (m == 1){
        mensaje2();                   // Se llama al método para desplegar el mensaje 2
      } else
        mensaje3();                   // Se llama al método para desplegar el mensaje 3
  
      delay(tiempo);                  // Se da un tiempo de espera
      if (Serial.available() != 0) {      // Se comprueba si el monitor serial tiene algún caracter
        // Si contiene caracteres los recorre uno por uno para obtener la cadena completa
        while (Serial.available()>0){     
          caracter= Serial.read();        // Lee los caracteres del monitor serial
          comando.concat(caracter);       // Concatena cada caracter para obtener la cadena completa
          delay(10); 
        }
        
        /* Cuando tengamos la cadena acabada, comprobamos el valor aquí abajo. Si no se encuentra, lo ignorará. Ya podemos encender los leds, sensores, pantalla, etc */ 
        tiempo = 1000;

        // Navegar entre los mensajes para mostrarlos en la pantalla LCD
        // obtener el mensaje de bienvenida ----------------------
        if (comando.equals("mensajes") == true){ 
          mensaje1();
        }
        // Obtener la temperatura
        if (comando.equals("temperatura") == true){ 
          mensaje2();
        } 
        // Obtener hora y fecha
        if (comando.equals("hora y fecha")== true){ 
          mensaje3();
        } 
        //-------------------------------------------------------

        // Cambiar el color del LED multicolor ------------------
        if (comando.equals("rojo") == true){ 
          color(255,0,0);     //Rojo
          Serial.println("LED color rojo."); 
        } 
        if (comando.equals("verde")== true){ 
          color(0,255,0);     //Verde
          Serial.println("LED color verde.."); 
        }         
        if (comando.equals("azul") == true){ 
          color(0,0,255);     //Azul 
          Serial.println("LED color azul."); 
        }
        if (comando.equals("blanco") == true){ 
          color(255,255,255); //Blanco
          Serial.println("LED color blanco."); 
        }  
        if (comando.equals("magenta")== true){ 
          color(255,0,255);   //Magenta
          Serial.println("LED color magenta."); 
        }  
        if (comando.equals("naranja") == true){ 
          color(255,128,0);   //Naranja
          Serial.println("LED color naranja."); 
        }
        //-----------------------------------------------------   

        // Probar diferentes secuencias de LEDs ---------------
        if (comando.equals("secuencia 1") == true){
          secuencia1();
        }
        if (comando.equals("secuencia 2") == true){
          secuencia2();
        }
        if (comando.equals("secuencia 3") == true){
          secuencia3();
        }
        //-------------------------------------------------------

        // Probar  diferentes sonidos con el buzzer -------------
        if (comando.equals("sonido 1") == true){
          sonido1();
        }
        if (comando.equals("sonido 2") == true){
          sonido2();
        }
        if (comando.equals("sonido 3") == true){
          sonido3();
        }
        // ------------------------------------------------------

        comando="";           // Limpiamos la cadena para volver a recibir el siguiente comando. 
      }
    }
  }  
}

// ---------------------------------------------------------------------
// Método para mostrar el mensaje 1 que da la bienvenida al usuario
// ----------------------------------------------------------------------
void mensaje1(){
  lcd.clear();                    // Limpia la pantalla
  lcd.setCursor(0,0);             // setea el cursor a la columna 0, fila 0
  lcd.print("Hola, Bienvenido");  // imprime mensaje en la pantalla LCD
  lcd.setCursor(0,1);             // setea el cursor a la columna 0, fila 1
  //lcd.autoscroll();               // establece la pantalla para desplazarse automáticamente:
  lcd.print("Interfaz Java-Arduino");   // imprime mensaje en la pantalla LCD
  Serial.println("Mensaje");      // Imprime en el monitor serie
  delay(tiempo);                  // Tiempo de espera
}

// ---------------------------------------------------------------------
// Método para mostrar el mensaje 2 que muestra la temperatura que detecta
// el sensor de temperatura
// ----------------------------------------------------------------------
void mensaje2(){
  lcd.clear();                    // Limpia la pantalla
    float analogico =analogRead(sensorTemp);
    float temCentigrado=(analogico*500)/1024;
    lcd.setCursor(0,0);               // setea el cursor a la columna 0, fila 0
    lcd.noAutoscroll();               // apaga el desplazamiento automático
    lcd.print("Temperatura");         // Imprime mensaje en la pantalla LCD
    lcd.setCursor(0,1);               // setea el cursor a la columna 0, fila 1
    lcd.print(temCentigrado);         // Imprime temperatura en la pantalla LCD
    Serial.println("Temperatura");    // Imprime en el monitor serie
    delay(tiempo);                    // Tiempo de espera
}

// ---------------------------------------------------------------------
// Método para mostrar el mensaje 3 que muestra la hora y fecha que detecta
// el RTC DS1307
// ----------------------------------------------------------------------
void mensaje3(){
  HoraFecha = rtc.now();         //obtenemos la hora y fecha actual
    
  segundo=HoraFecha.second();   // guardamos los segundos
  minuto=HoraFecha.minute();    // guardamos los minutos
  hora=HoraFecha.hour();        // guardamos la hora
  dia=HoraFecha.day();          // guardamos el día
  mes=HoraFecha.month();        // guardamos el mes
  anio=HoraFecha.year();        // guardamos el año

  String menFecha = "Fecha=";   // Declaramos variable de mensaje de fecha
  menFecha += dia;              // Se concatena el mensaje
  menFecha += "/";
  menFecha += mes;
  menFecha += "/";
  menFecha += anio;
  
  String menHora = "Hora = ";   // Declaramos variable de mensaje de hora
  menHora += hora;              // Se concatena el mensaje
  menHora += ":";
  menHora += minuto;
  menHora += ":";
  menHora += segundo;
    
  lcd.clear();                    // Limpiar la pantalla
  lcd.setCursor(0,0);           // setea el cursor a la columna 0, fila 0
  lcd.noAutoscroll();             // apaga el desplazamiento automático
  lcd.print(menHora);           // imprime la hora en la pantalla LCD
  lcd.setCursor(0,1);           // setea el cursor a la columna 0, fila 1
  lcd.print(menFecha);          // imprime la fecha en la pantalla LCD
  Serial.println("Hora");       // imprime el el monitor serie
  delay(tiempo);                // tiempo de espera
}

// ---------------------------------------------------------------------
// Método para dar un determinado color al LED multicolor
// ----------------------------------------------------------------------
void color(int rojo, int verde, int azul){
  // Escritura PWM del color rojo
  analogWrite(ledRojo, 255-rojo);
  // Escritura PWM del color verde
  analogWrite(ledVerde, 255-verde);
  // Escritura PWM del color azul
  analogWrite(ledAzul, 255-azul);
}

// ---------------------------------------------------------------------
// Método para mostrar una determinada secuencia de LEDs
// ----------------------------------------------------------------------
void secuencia1(){
  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

   digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);
}

// ---------------------------------------------------------------------
// Método para mostrar una determinada secuencia de LEDs
// ----------------------------------------------------------------------
void secuencia2(){
  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
   delay(250);
   
  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);
}

// ---------------------------------------------------------------------
// Método para mostrar una determinada secuencia de LEDs
// ----------------------------------------------------------------------
void secuencia3(){
  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);

  digitalWrite(led1,HIGH);
  digitalWrite(led2,HIGH);
  digitalWrite(led3,HIGH);
  delay(250);

  digitalWrite(led1,LOW);
  digitalWrite(led2,LOW);
  digitalWrite(led3,LOW);
  delay(250);
}

// ---------------------------------------------------------------------
// Método para reproducir el sonido de una alarma mediante las variables de frecuencia más baja y más alta,
// reproduciendose durante un tiempo determinado
// ----------------------------------------------------------------------
void sonido1(){
  int contador = 0;
    while(contador < 3){
      //sonido más agudo
      for (i=fMin;i<=fMax; i++)
        tone(buzzer, i, duracion);
      //sonido más grave
      for (i=fMax;i>=fMin; i--)
        tone(buzzer, i, duracion);  
      delay(2000);
      contador ++;
    }
}

// ---------------------------------------------------------------------
// Método para reproducir una melodía mediante un bucle que recorra el vector. 
// Este será el encargado de introducir una determinada frecuencia al zumbador cada vez, conforme hayamos declarado el vector de tonos.
// ----------------------------------------------------------------------
void sonido2(){
  for (int i = 0; i < numTones; i++) {
    tone(buzzer, tones[i]);
    delay(500);
  }
  noTone(buzzer); 
}

// ---------------------------------------------------------------------
// Método para reproducir un beep mediante el sonido del buzzer con una pausa
// ----------------------------------------------------------------------
void sonido3(){
  beep(50);
  beep(50);
  beep(50);
  delay(1000);
  beep(200);
  delay(1000);
  beep(200);
  delay(1000);
  beep(200);
  delay(1000);            
}

// ---------------------------------------------------------------------
// Método para asignar el valor del sonido del buzzer
// ----------------------------------------------------------------------
void beep(unsigned char pausa) {
  analogWrite(buzzer, 20);
  delay(pausa);                 // Espera
  analogWrite(buzzer, 0);       // Apaga
  delay(pausa);                 // Espera
}
