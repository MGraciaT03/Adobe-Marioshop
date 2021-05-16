import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author Mario Gracia
 * @version 1.0
 * @since 1.0
 * @see Esta clase se encarga principalmente de crear la interfaz completa del
 *      editor Marioshop
 */
// Clase Ventana Principal de la interfaz Marioshop
public class VentanaPrincipal {
    // Atributos de la clase Ventana Principal
    // Constituye un
    // frame,paneles,labels,botones,buffers,imagenes,graficos,radioBotones,JFileChooser
    // Este conjunto de componentes representa el lienzo y las herramientas a pintar
    // y tambien se encarga de poenr imagenes en el lienzo de la interfaz
    private JFrame ventana;
    private Color color = Color.BLACK;
    private JPanel panelIzquierda, panelLienzo, panelExtra, panelRadio;
    private JButton botonCuadrado, botonCirculo, botonColor, botonLimpiar, cargar, cambiarFondoColor, pintar, tiralineas,
            bordeCircular, bordeCuadrado;
    private JLabel labelX, labelY, lienzo, linea, labelTamano, labelBorde, labelTitulo;
    private BufferedImage canvasPintado, base, canvasSuperior;
    private int x, y, tamano, xAnt, yAnt;
    private JTextField textLabelX, textLabelY, textTamano;
    private ImageIcon iconCanvas;
    private Graphics graficos, graficosFiguras;
    private String[] colores = { "Negro", "Azul", "Naranja", "Verde", "Rojo", "Cyan", "Gris Oscuro" };
    private JRadioButton[] radios;
    private ButtonGroup grupo;

    // Atributo File que permite guardar un directorio marcando que la ruta es el
    // Escritorio
    private File ultimaRuta = new File(System.getProperty("user.home") + "./Desktop");
    JFileChooser chooser;

    /**
     * Constructor, marca el tamaño y el cierre del frame
     */
    public VentanaPrincipal() {
        ventana = new JFrame("Adobe Marioshop");
        ventana.setBounds(100, 100, 1100, 700);
        ventana.getContentPane();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {

        // Componentes de la interfaz
        // A la ventana se la marca un GridBagLayout
        ventana.setLayout(new GridBagLayout());

        // El panel de la izquierda esta en posicion 0,0.
        // Le añadimos peso vertical 1 y padding horizontal de 80,relleno a ambos lados
        // Se añade al frame
        panelIzquierda = new JPanel(new GridBagLayout());
        GridBagConstraints settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.weighty = 1;
        settings.ipadx = 80;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelIzquierda, settings);

        // El panel Lienzo esta en la posicion 1,0
        // le añadimos peso horizontal y vertical 1 , relleno a ambos lados
        // Se añade al frame
        panelLienzo = new JPanel(new GridBagLayout());
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelLienzo, settings);

        // El panel de las herramientas esta en posicion 2,0.
        // Le añadimos peso vertical 1 y padding horizontal de 80,relleno a ambos lados
        // Se le pone un borde titulo al panel y se añade al frame
        panelExtra = new JPanel(new GridBagLayout());
        settings = new GridBagConstraints();
        settings.gridx = 2;
        settings.gridy = 0;
        settings.ipadx = 80;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        panelExtra.setBorder(BorderFactory.createTitledBorder("Extra"));
        ventana.add(panelExtra, settings);

        // Boton Cuadrado en posicion 0,0
        // cargamos el icono con un metodo,pasandole el tamaño que es 50,50
        // separacion de 10 arriba y 10 abajo
        // Se añade a panelIzquierda
        botonCuadrado = new JButton(cargarIcono("Imagenes/cuadrado.png", 50, 50));
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.insets = new Insets(10, 0, 10, 0);
        settings.fill = GridBagConstraints.BOTH;
        panelIzquierda.add(botonCuadrado, settings);

        // Boton Circulo en posicion 0,1
        // cargamos el icono con un metodo,pasandole el tamaño que es 50,50
        // separacion de 10 arriba y 10 abajo
        // Se añade a panelIzquierda
        botonCirculo = new JButton(cargarIcono("Imagenes/circulo.png", 50, 50));
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 1;
        settings.insets = new Insets(10, 0, 10, 0);
        settings.fill = GridBagConstraints.BOTH;
        panelIzquierda.add(botonCirculo, settings);

        // LabelX en posicion 0,3
        // Separacion de arriba de 10
        // Se añade a panelIzquierda
        labelX = new JLabel("Posicion X");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 3;
        settings.insets = new Insets(10, 0, 0, 0);
        panelIzquierda.add(labelX, settings);

        // Texto Label X en posicion 0,4
        // separacion de arriba de 10, relleno a ambos lados
        // Se añade a panelIzquierda
        textLabelX = new JTextField("");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 4;
        settings.insets = new Insets(10, 0, 0, 0);
        settings.fill = GridBagConstraints.BOTH;
        panelIzquierda.add(textLabelX, settings);

        // LabelY en posicion 0,5
        // Separacion de arriba de 10
        // Se añade a panelIzquierda
        labelY = new JLabel("Posicion Y");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 5;
        settings.insets = new Insets(10, 0, 0, 0);
        panelIzquierda.add(labelY, settings);

        // Texto Label Y en posicion 0,6
        // separacion de arriba de 10, relleno a ambos lados
        // Se añade a panelIzquierda
        textLabelY = new JTextField("");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 6;
        settings.insets = new Insets(10, 0, 0, 0);
        settings.fill = GridBagConstraints.BOTH;
        panelIzquierda.add(textLabelY, settings);

        // Convertimos paneLienzo en un GridBagLayout
        panelLienzo.setLayout(new GridBagLayout());

        // LabelTitulo en posicion 0,0
        // separacion de arriba de 5,componente horizontalmente centrado
        // cargamos el icono con un metodo,pasandole el tamaño que es 80,80
        // Le añadimos una fuente que sea Arial,Negrita tamaño 30 y el color de la letra rojo
        // Se añade a panelLienzo
        labelTitulo = new JLabel("ADOBE MARIOSHOP");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.insets = new Insets(5, 0, 0, 0);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setIcon(cargarIcono("Imagenes/gorra.png", 80, 80));
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        labelTitulo.setForeground(Color.RED);
        panelLienzo.add(labelTitulo, settings);

        // JLabel Lienzo en posicion 0,1 ,horizontalmente centrado
        // con un borde lineal de color negro
        // Se añade al panelLienzo
        lienzo = new JLabel();
        settings.gridx = 0;
        settings.gridy = 1;
        lienzo.setHorizontalAlignment(SwingConstants.CENTER);
        lienzo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelLienzo.add(lienzo, settings);

        // Creamos los tres BufferedImages con tamaño de 500x500 cada uno
        base = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        canvasSuperior = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        canvasPintado = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        // Se crea un Graphics con el canvas creado mediante getGraphics,
        // se rellena con un fill rect el canvas,se refresca
        // Se actualiza un ImageIcon con el canvas nuevo y se actualiza el icon
        graficos = base.getGraphics();
        graficos.fillRect(0, 0, base.getWidth(), base.getHeight());
        graficos.dispose();
        iconCanvas = new ImageIcon(base);
        lienzo.setIcon(iconCanvas);

        // Boton Cargar posicion 0,0 con relleno Horizontal,separacion de 10 en todo
        // Se carga el icono tamaño 15,15 con un metodo y se posiciona el componente a la izquierda
        // Se añáde a panelExtra
        cargar = new JButton("Cargar");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.insets = new Insets(10, 10, 10, 10);
        cargar.setIcon(cargarIcono("Imagenes/load.png", 15, 15));
        cargar.setHorizontalAlignment(SwingConstants.LEFT);
        panelExtra.add(cargar, settings);

        // Boton Nuevo en posicion 1,0 con relleno Horizontal,separacion de 10 en todo
        // Se carga el icono tamaño 15,15 con un metodo y se posiciona el componente a la izquierda
        // Se añáde a panelExtra
        cambiarFondoColor = new JButton("Fondo Color");
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 0;
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.insets = new Insets(10, 10, 10, 10);
        cambiarFondoColor.setIcon(cargarIcono("Imagenes/new.png", 15, 15));
        cambiarFondoColor.setHorizontalAlignment(SwingConstants.LEFT);
        panelExtra.add(cambiarFondoColor, settings);

        // Boton Nuevo en posicion 0,1 con relleno Horizontal,separacion de 10 en todo
        // Se carga el icono tamaño 15,15 con un metodo y se posiciona el componente a la izquierda
        // Esta desactivado y se añáde a panelExtra
        pintar = new JButton("Pintar");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 1;
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.insets = new Insets(10, 10, 10, 10);
        pintar.setIcon(cargarIcono("Imagenes/pincel.png", 15, 15));
        pintar.setHorizontalAlignment(SwingConstants.LEFT);
        pintar.setEnabled(false);
        panelExtra.add(pintar, settings);

        // Boton Nuevo en posicion 1,1 con relleno Horizontal,separacion de 10 en todo
        // Se carga el icono tamaño 15,15 con un metodo y se posiciona el componente a  la izquierda
        // Se añade a panelExtra
        tiralineas = new JButton("Tiralineas");
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 1;
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.insets = new Insets(10, 10, 10, 10);
        tiralineas.setIcon(cargarIcono("Imagenes/linea.png", 15, 15));
        tiralineas.setHorizontalAlignment(SwingConstants.LEFT);
        panelExtra.add(tiralineas, settings);

        // LabelBorde en posicion 0,2. Ocupa 2 filas horizontal,separacion de 10 arriba
        // Se posiciona el componente al centro Y se añade a panelExtra
        labelBorde = new JLabel("Tipo de Borde");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 2;
        settings.gridwidth = 2;
        settings.insets = new Insets(10, 0, 0, 0);
        labelBorde.setHorizontalAlignment(SwingConstants.CENTER);
        panelExtra.add(labelBorde, settings);

        // BotonCuadrado en posicion 0,3.Relleno horizontal 5 y vertical 4,separacion de
        // 10 en todo menos a la derecha que es 8
        // se posiciona el componente al centro y Se añade a panelExtra
        bordeCuadrado = new JButton("Cuadrado");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 3;
        settings.ipadx = 5;
        settings.ipady = 4;
        settings.insets = new Insets(10, 10, 10, 8);
        bordeCuadrado.setHorizontalAlignment(SwingConstants.CENTER);
        panelExtra.add(bordeCuadrado, settings);

        // BotonCirculo en posicion 0,3.Relleno horizontal 5 y vertical 4,separacion de
        // 10 en todo menos a la izquierda que es 8
        // se posiciona el componente al centro,esta desactivado Se añade a panelExtra
        bordeCircular = new JButton("Circular");
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 3;
        settings.ipadx = 5;
        settings.ipady = 4;
        settings.insets = new Insets(10, 8, 10, 10);
        bordeCircular.setHorizontalAlignment(SwingConstants.CENTER);
        bordeCircular.setEnabled(false);
        panelExtra.add(bordeCircular, settings);

        // BotonColor en posicion 0,4.Relleno horizontal 10 y vertical 5,ocupa 2 celdas horizontales,
        // Separacion de 5 arriba
        // Se añade a panelExtra
        botonColor = new JButton("Color");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 4;
        settings.ipadx = 10;
        settings.ipady = 5;
        settings.gridwidth = 2;
        settings.insets = new Insets(5, 0, 0, 0);
        panelExtra.add(botonColor, settings);

        // LabelLinea en posicion 0,5.Relleno horizontal 70 y vertical 2,ocupa 2 celdas horizontales,
        // fondo de color negro,es opaco,Separacion de 10 arriba y abajo
        // Se añade a panelExtra
        linea = new JLabel();
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 5;
        settings.ipadx = 70;
        settings.ipady = 2;
        settings.gridwidth = 2;
        linea.setBackground(Color.BLACK);
        linea.setOpaque(true);
        settings.insets = new Insets(10, 0, 10, 0);
        panelExtra.add(linea, settings);

        // LabelTamaño en posicion 0,6,ocupa 2 celdas horizontales,
        // Separacion de 10 arriba,se alinea al centro
        // Se añade a panelExtra
        labelTamano = new JLabel("Tamaño");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 6;
        settings.insets = new Insets(10, 0, 0, 0);
        settings.gridwidth = 2;
        labelTamano.setHorizontalAlignment(SwingConstants.CENTER);
        panelExtra.add(labelTamano, settings);

        // texto Tamaño en posicion 0,7,ocupa 2 celdas horizontales,
        // Relleno horizontal 90 y vertical 3
        // Separacion de 10 arriba
        // Se añade a panelExtra
        textTamano = new JTextField("");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 7;
        settings.gridwidth = 2;
        settings.ipadx = 90;
        settings.ipady = 3;
        settings.insets = new Insets(10, 0, 0, 0);
        panelExtra.add(textTamano, settings);

        // panelRadio con Borde lineal Azul con titulo en el centro y above_arriba.
        // panelRadio es un GridLayout de 1 columna y ocupacion de 20 columnas verticales
        // posicion 0,8, ocupa 3 celdas horizontales
        // Separacion de 10 arriba y izquierda,20 a la derecha
        // Se añade a panelExtra
        panelRadio = new JPanel();
        panelRadio.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Colores",
                TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        panelRadio.setLayout(new GridLayout(0, 1, 0, 20));
        settings.gridx = 0;
        settings.gridy = 8;
        settings.gridwidth = 3;
        settings.fill = GridBagConstraints.HORIZONTAL;
        settings.insets = new Insets(10, 10, 0, 20);
        panelExtra.add(panelRadio, settings);

        // Crea un vector de JRadioButtons y un ButtonGroup
        // Los va añadiendo a cada radioButton al grupo y al panelRadio
        radios = new JRadioButton[colores.length];
        grupo = new ButtonGroup();
        for (int i = 0; i < colores.length; i++) {
            radios[i] = new JRadioButton(colores[i]);
            grupo.add(radios[i]);
            panelRadio.add(radios[i]);
        }

        // BotonColor en posicion 0,9.Relleno horizontal y vertical 5,ocupa 2 celdas horizontales,
        // Separacion de 5 arriba y abajo
        // Se añade a panelExtra

        botonLimpiar = new JButton("Limpiar");
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 9;
        settings.ipadx = 5;
        settings.ipady = 5;
        settings.gridwidth = 2;
        settings.insets = new Insets(5, 0, 5, 0);
        panelExtra.add(botonLimpiar, settings);
    }

    // Metodo que se encarga de crear un BufferedImage Aparte,pasandole por parametros una ruta y un tamaño a cargar
    // comprueba que la ruta es correcto la que se le pasa,y si no hay errores
    // retorna un ImageIcon, escalando en BufferImage que ocupe de ancho y alto el
    // tamaño que se le pasa por parametros
    public ImageIcon cargarIcono(String ruta, int tamanoX, int tamanoY) {
        BufferedImage buffAux = null;
        try {
            buffAux = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(buffAux.getScaledInstance(tamanoX, tamanoY, Image.SCALE_SMOOTH));
    }

    // Metodo que comprobara las posiciones xy e tamaño, mediante un try catch,si se cumple.
    // Retornara verdadero ,en caso contrario saltara una cuadro de error y
    // retornara false
    public boolean comprobarNumeros(String cadena1, String cadena2, String cadena3) {
        boolean sePuede = false;
        try {
            x = Integer.parseInt(cadena1);
            y = Integer.parseInt(cadena2);
            tamano = Integer.parseInt(cadena3);
            sePuede = true;
        } catch (NullPointerException | NumberFormatException err) {
            JOptionPane.showMessageDialog(ventana, "Debes rellenar todos los campos", "Error Campos Vacios",
                    JOptionPane.ERROR_MESSAGE);
        }
        return sePuede;
    }

    // Metodo que comprobara el tamaño, mediante un try catch,si se cumple.
    // Retornara verdadero ,en caso contrario saltara una cuadro de error y
    // retornara false
    public boolean comprobarTamano(String cadena) {
        boolean sePuede = false;
        try {
            tamano = Integer.parseInt(cadena);
            sePuede = true;
        } catch (NullPointerException | NumberFormatException err) {
            JOptionPane.showMessageDialog(ventana, "Debes rellenar el tamaño", "Error Campo Vacio",
                    JOptionPane.ERROR_MESSAGE);

        }
        return sePuede;

    }

    // Metodo que se encargara de crear graficos,usando los graphics del BufferedImage del canvas base
    // se crea el canvas Base con la suma del canvas Pintado y el canvasSuperior transparente
    // que se utiliza para el tiralineas , se refresca los graficos
    // se repinta el lienzo para que aparezcan los cambios
    public void superPonerCanvas() {
        Graphics graficosBase = base.getGraphics();
        graficosBase.fillRect(0, 0, base.getWidth(), base.getHeight());
        graficosBase.drawImage(canvasPintado, 0, 0, null);
        graficosBase.drawImage(canvasSuperior, 0, 0, null);
        graficosBase.dispose();
        lienzo.repaint();
    }

    // Metodo para inicializar los listeners
    public void inicializarListeners() {
        // Al pulsar el botonCuadrado, se comprueba con un metodo los campos posicionx,posiciony, tamaño.
        // Si son correctos,se recogeran esos valores.
        // Se implementa a graficosFiguras,usando los graphics del BufferedImage del canvas
        // Dependiendo del color se escogera el color que tiene escogido el selector
        // se crea el cuadrado con FillRect, se refresca los graficos y se repinta el
        // JLabel lienzo para que aparezca el cambio
        botonCuadrado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (comprobarNumeros(textLabelX.getText(), textLabelY.getText(), textTamano.getText())) {
                    x = Integer.parseInt(textLabelX.getText());
                    y = Integer.parseInt(textLabelY.getText());
                    tamano = Integer.parseInt(textTamano.getText());
                    graficosFiguras = canvasPintado.getGraphics();
                    graficosFiguras.setColor(color);
                    graficosFiguras.fillRect(x, y, tamano, tamano);
                    graficosFiguras.dispose();
                    superPonerCanvas();
                    lienzo.repaint();
                }

            }

        });
        // Al pulsar el botonCirculo, se comprueba con un metodo los campos posicionx,posiciony, tamaño.
        // Si son correctos,se recogeran esos valores.
        // Se implementa a graficosFiguras,usando los graphics del BufferedImage del canvas
        // Dependiendo del color se escogera el color que tiene escogido el selector
        // se crea el cuadrado con FillOval, se refresca los graficos y se repinta el
        // JLabel para que aparezca el cambio
        botonCirculo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (comprobarNumeros(textLabelX.getText(), textLabelY.getText(), textTamano.getText())) {
                    x = Integer.parseInt(textLabelX.getText());
                    y = Integer.parseInt(textLabelY.getText());
                    tamano = Integer.parseInt(textTamano.getText());
                    graficosFiguras = canvasPintado.getGraphics();
                    graficosFiguras.setColor(color);
                    graficosFiguras.fillOval(x, y, tamano, tamano);
                    graficosFiguras.dispose();
                    superPonerCanvas();
                    lienzo.repaint();
                }
            }
        });
      // Utilizo el file Chooser para determinar qué imagen cargar:
        // Creo el fileChooser asignandola a la ruta de Escritorio
        // Creo un filtro exclusivo solo para imagenes (archivos jpg,png y jpeg).
        // Pongo el filtro en el chooser y abro el dialogo en la ventana para escoger las imagenes
        // Si he escogido una imagen, creo una variable que almacene el fichero escogido
        // Actualizo la ruta,en vez de que se abra la del Escritorio,se abrira la de la imagen que he escogido
        // si no hay errores en abrir la imagen. Creo un canvas auxiliar
        // y obtengo los graficos del canvasPintado,utilizo drawImage para recrear la imagen en el lienzo
        //Usando el tamaño del lienzo,refreco los graficos,,lamo al metodo superPonerCanvas y repinto el lienzo
        cargar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser(ultimaRuta);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Imagenes", "jpg", "png",
                        "jpeg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(ventana);
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = chooser.getSelectedFile();
                    ultimaRuta = selectedFile;
                    try {
                        BufferedImage aux = ImageIO.read(selectedFile);
                        graficos = canvasPintado.getGraphics();
                        graficos.drawImage(
                                aux.getScaledInstance(lienzo.getWidth(), lienzo.getHeight(), Image.SCALE_SMOOTH), 0, 0,
                                null);
                        graficos.dispose();
                        superPonerCanvas();
                        lienzo.repaint();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(ventana, "No se puede abrir la imagen", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    }

                }

            }

        });
        //Cuando pulso el boton Nuevo. recorro el vector de colores 
        //Recogo la cadena del elemento que he pulsado del radioGroup 
        // compruebo que el elemento seleccionado coincide un color
        //Si coincide actualizo la variable Color correspondiente a la seleccion dada
        cambiarFondoColor.addActionListener(new ActionListener() {
            Color colorARellenar = Color.GRAY;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < colores.length; i++) {
                    String cadena = radios[i].getText();
                    if (radios[i].isSelected() && cadena.equalsIgnoreCase("Negro")) {
                        colorARellenar = Color.BLACK;
                    } else {
                        if (radios[i].isSelected() && cadena.equalsIgnoreCase("Azul")) {
                            colorARellenar = Color.BLUE;
                        } else {
                            if (radios[i].isSelected() && cadena.equalsIgnoreCase("Naranja")) {
                                colorARellenar = Color.ORANGE;
                            } else {
                                if (radios[i].isSelected() && cadena.equalsIgnoreCase("Verde")) {
                                    colorARellenar = Color.GREEN;
                                } else {
                                    if (radios[i].isSelected() && cadena.equalsIgnoreCase("Rojo")) {
                                        colorARellenar = Color.RED;
                                    } else {
                                        if (radios[i].isSelected() && cadena.equalsIgnoreCase("Cyan")) {
                                            colorARellenar = Color.CYAN;
                                        } else {
                                            if (radios[i].isSelected() && cadena.equalsIgnoreCase("Gris Oscuro")) {
                                                colorARellenar = Color.DARK_GRAY;
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }
                }
                // Se crea el bufferImage con el tamaño del lienzo
                // Creamos unos Graphics obtenidos del Buffer del lienzo
                // Actualizamos el color en los Graphics
                // rellenamos un cuadrado con el tamaño del canvas
                // Y lo refescamos y actualizamos el Buffer del lienzo
                graficosFiguras = canvasPintado.getGraphics();
                graficosFiguras.setColor(colorARellenar);
                graficosFiguras.fillRect(0, 0, canvasPintado.getWidth(), canvasPintado.getHeight());
                graficosFiguras.dispose();
                superPonerCanvas();
                lienzo.repaint();
            }

        });
        //Boton pintar cada vez que se pulsa,se desactiva
        //Y se activa el boton tiraLineas
        pintar.addActionListener((e) -> {
            pintar.setEnabled(false);
            tiralineas.setEnabled(true);
            tiralineas.requestFocusInWindow();
        });
        //Boton tiralineas cada vez que se pulsa,se desactiva
        //Y se activa el boton pintar
        tiralineas.addActionListener((e) -> {
            tiralineas.setEnabled(false);
            pintar.setEnabled(true);
            pintar.requestFocusInWindow();
        });
        //Boton bordeCircular cada vez que se pulsa,se desactiva
        //Y se activa el boton  bordeCuadrado
        bordeCircular.addActionListener((e) -> {
            bordeCircular.setEnabled(false);
            bordeCuadrado.setEnabled(true);
            bordeCuadrado.requestFocusInWindow();
        });
        //Boton bordeCuadrado cada vez que se pulsa,se desactiva
        //Y se activa el boton  bordeCircular
        bordeCuadrado.addActionListener((e) -> {
            bordeCuadrado.setEnabled(false);
            bordeCircular.setEnabled(true);
            bordeCircular.requestFocusInWindow();
        });

        //Cada vez que se pulse al boton Selector,se abrira un JColorChooser mostrando un dialogo
        //Si se escoge el color en el JColorChooser, se actualiza,sino,se dejara igual el color anteriormente escogido
        botonColor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color aux = JColorChooser.showDialog(ventana, "Elige un color", color);
                if (aux != null) {
                    color = aux;
                    botonColor.setBackground(color);
                    botonColor.setText("");
                }
            }

        });
         //Comprueba el tamaño para cambiar el alto del JLabel Linea,sin sobrepasarse de 12
        //Si se sobrepasa,se seguira quedando su alto en 12, solo se cambiara cuando sea menor o igual a 12
        textTamano.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (comprobarTamano(textTamano.getText())) {
                    tamano = Integer.parseInt(textTamano.getText());
                    if (tamano <= 22) {
                        linea.setSize(linea.getWidth(), tamano);
                    } else {
                        linea.setSize(linea.getWidth(), 22);
                    }
                }

            }

        });
        //Al pulsar el boton Limpiar,se cambia el color Blanco
        //Se implementa a graficosFiguras,usando los graphics del BufferedImage del canvas
    //se crea el cuadrado con FillRect en la posicion 0,0 y se usa el tamaño del lienzo
    //se refresca los graficos y se repinta el JLabel para que aparezca el cambio
        botonLimpiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                graficosFiguras = canvasPintado.getGraphics();
                graficosFiguras.setColor(Color.WHITE);
                graficosFiguras.fillRect(0, 0, canvasPintado.getWidth(), canvasPintado.getHeight());
                graficosFiguras.dispose();
                superPonerCanvas();
                lienzo.repaint();

            }

        });
           //Listeners al raton, utilizando cuando el usuario presiona el raton
        //recogimos la posicon xy del evento que hemos pulsado
        lienzo.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                xAnt = e.getX();
                yAnt = e.getY();
            }
        //Con el MouseMotionListener, si hago click en el raton y despues lo suelto comprobamos el tamaño que hemos asignado
        //Obtenemos los graficos del canvasPintando y creamos un Graphics2D a partir de los graficos creados
        //creamos un trazo con el tamaño que hemos escrito,si el botonCuadrado esta activado,pintamos un trazo circular,sino se pinta un trazo rectangular
        //usamos el color que tenemos selecionado,dibujamos la linea con las posiciones que hemos pulsado
        //refrescamos los Graphics2D ,llamamos a superPonerCanvas repintamos el lienzo
        
            @Override
            public void mouseReleased(MouseEvent e) {
                if (comprobarTamano(textTamano.getText())) {
                graficos = canvasPintado.getGraphics();
                Graphics2D g = (Graphics2D) graficos;
                if (bordeCuadrado.isEnabled()) {
                    g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                } else {
                    g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                }
                g.setColor(color);
                g.drawLine(xAnt, yAnt, x, y);
                g.dispose();
                canvasSuperior = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
                superPonerCanvas();

                lienzo.repaint();
              }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });

        //Con el MouseMotionListener, si arrastramos el raton
        //Comprobamos que estamos pulsando con el boton izquierdo del raton,
        //y se comprueba que esta el botonTiralineas activado
        //Si se cumple y el tamaño que hemos recogido
        //La posicion x e y se obtienen a partir del MouseEvent y se recoge el tamaño
        //Obtenemos los graficos del BufferImage y creamos un Graphics2D a partir de ls Graphics creados
        //creamos un trazo con el tamaño que hemos escrito,si el botonCuadrado esta activado
        //hacemos un trazo con bordeado circular,sino lo hacemos rectangular
        //usamos el color que tenemos selecionado,dibujamos la linea con las posiciones que hemos pulsado
        //refrescamos los Graphics2D,llamamos al metodo superPoner, repintamos el lienzo
        //y volvemos a refrescar las posicionex XY mediante el Mouse Event
        

        //Si no esta activado el botonTiralineas
        //La posicion x e y se obtienen a partir del MouseEvent y se recoge el tamaño
        //Se crea de cero el canvasSuperior con tamaño 500x500
        //se crean los graphics2D del canvasSuperior
        //si el botonCuadrado esta activado hacemos un trazo con bordeado circular,sino lo hacemos rectangular
        //usamos el color que tenemos selecionado,dibujamos la linea con las posiciones que hemos pulsado
        //refrescamos los Graphics2D,llamamos al metodo superPoner y  repintamos el lienzo

        lienzo.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (tiralineas.isEnabled()) {
                        if (comprobarTamano(textTamano.getText())) {
                            x = e.getX();
                            y = e.getY();
                            tamano = Integer.parseInt(textTamano.getText());
                            graficos = canvasPintado.getGraphics();
                            Graphics2D g = (Graphics2D) graficos;
                            if (bordeCuadrado.isEnabled()) {
                                g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                            } else {
                                g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                            }
                            g.setColor(color);
                            g.drawLine(xAnt, yAnt, x, y);
                            g.dispose();
                            superPonerCanvas();
                            lienzo.repaint();
                            xAnt = e.getX();
                            yAnt = e.getY();
                        }
                    } else {
                        if (comprobarTamano(textTamano.getText())) {
                            x = e.getX();
                            y = e.getY();
                            tamano = Integer.parseInt(textTamano.getText());
                            canvasSuperior = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
                            graficos = canvasSuperior.getGraphics();
                            Graphics2D g = (Graphics2D) graficos;
                            if (bordeCuadrado.isEnabled()) {
                                g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                            } else {
                                g.setStroke(new BasicStroke(tamano, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
                            }
                            g.setColor(color);
                            g.drawLine(xAnt, yAnt, x, y);
                            g.dispose();
                            superPonerCanvas();
                            lienzo.repaint();
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }
    
    //Metodo que se encrga de emitir un fichero de sonido
    //Creando un AudioInputStream al fichero pasado por parametros
    //y creando un objeto Clip para emitirlo,se controla tambien las excepciones en caso que no cargue
    //Mientras se esta reproduciendo,tardara un segundo al finalizar
    // y se volvera a reproducir de forma infinita
    public void ReproducirSonido(String nombreSonido) {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            while (clip.isRunning()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error al reproducir el sonido.");
                }
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            JOptionPane.showMessageDialog(ventana, "No se puede reproducir la cancion", "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodoa para inicializar 
    //los componentes de la interfaz
    //los listeners de los componentes
    // la cancion de fondo del programa
    public void inicializar() {
        ventana.setVisible(true);
        inicializarComponentes();
        inicializarListeners();
        ReproducirSonido("Sonido/super mario maker theme.wav");
    }

}
