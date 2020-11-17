import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas.
 * De alguna manera tendrá que poder acceder a la ventana principal.
 * Se puede lograr pasando en el constructor la referencia a la ventana.
 * Recuerda que desde la ventana, se puede acceder a la variable de tipo ControlJuego
 * @author Alexandro Rodríguez Parrón
 **
 */
public class ActionBoton implements ActionListener{

	private VentanaPrincipal ventanaPrincipal;
	private int i;
	private int j;

	//Constructor de ActionBoton
	public ActionBoton(VentanaPrincipal ventanaPrincipal, int i, int j) {
		this.ventanaPrincipal = ventanaPrincipal;
		this.i = i;
		this.j = j;
		
	}

	
	/**
	 * Al pulsar en botones:
	 * Si al abrir la casilla no es una mina se indica el numero de minas que hay alrededor,
	 * y si no es una mina y la puntuación ha llegado a 80 habrás ganado
	 * Si al abrir la casilla es una mina, habrás perdido
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if(ventanaPrincipal.juego.abrirCasilla(i, j)){
			ventanaPrincipal.mostrarNumMinasAlrededor(i, j);
			if(ventanaPrincipal.juego.getPuntuacion() == 80){
				ventanaPrincipal.mostrarFinJuego(false);
			}
		}
		else{
			ventanaPrincipal.mostrarFinJuego(true);
		}
		ventanaPrincipal.refrescarPantalla();
	}

}
