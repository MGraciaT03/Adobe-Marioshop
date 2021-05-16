import java.awt.EventQueue;

/**
 * @author Mario Gracia
*/
//Main que se encarga de llamar a la clase VentanaPrincipal
public class Principal {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try {
                    VentanaPrincipal ventanaPrincipal=new VentanaPrincipal();
                    ventanaPrincipal.inicializar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}