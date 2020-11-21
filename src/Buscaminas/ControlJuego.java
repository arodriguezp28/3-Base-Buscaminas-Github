package Buscaminas;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author Alexandro Rodríguez Parrón
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
		depurarTablero();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){

		//Repartir minas e inicializar puntación. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.
		//Poner  todas las posiciones a 0
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				tablero[i][j] = 0;
			}
		}
		//Poner la puntuacion a 0
		puntuacion = 0;
		//Colocar las minas: (while).
		Random aleatorio = new Random();
		int minas = MINAS_INICIALES;
		int ale1=0, ale2=0;
		
		//minas tiene el valor de Minas_Iniciales, mientras que no llegue a 1, va comprobando si en la posicion marcada no hay mina introduce la mina y va restando las minas.
		while(minas > 0){
			ale1 = aleatorio.nextInt(LADO_TABLERO);
			ale2 = aleatorio.nextInt(LADO_TABLERO);
			if(tablero[ale1][ale2] == 0){
				tablero[ale1][ale2] = MINA;
				minas--;
			}
		}
		
		//Al final del método hay que guardar el número de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}
			}
		} 
	}
	
	/**Cálculo de las minas adjuntas: 
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		int contMinas = 0;
		int iPrincipio = Math.max(0, i-1);
		
		int iFinal = Math.min(LADO_TABLERO-1, i+1);
		int jPrincipio = Math.max(0, j-1);
		
		int jFinal = Math.min(LADO_TABLERO-1, j+1);

		for (int vert = iPrincipio; vert <= iFinal; vert++) {
			for (int hor = jPrincipio; hor <= jFinal; hor++) {
				if(tablero[vert][hor] == MINA){
					contMinas++;
				}
			}
		}


		return contMinas;
	}
	
	/**
	 * Método que nos permite abrir una casilla. Devuelve verdadero y suma un punto si no hay mina
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		boolean noExplota;

		if(tablero[i][j] == MINA){
			noExplota = false;
		}
		else{
			noExplota = true;
		}

		return noExplota;
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		boolean fin = false;
		for (int i = 0; i < LADO_TABLERO; i++) {
			for (int j = 0; j < LADO_TABLERO; j++) {
				if(!abrirCasilla(i, j)){
					fin = true;
				}
				else{
					fin = false;
				}
			}
		}

		return fin;
	}
	
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

	/**
	 * Método que controlará la puntuación, se irá sumando la puntuación, si cae en una mina, no dará puntos
	 * Asi controlamos que si cae en una mina se restablezca la puntuación a 0.
	 * @param puntuacion
	 */
	public void setPuntuacion(int puntuacion){
		if(puntuacion == 0){
			this.puntuacion = 0;
		}else{
			this.puntuacion += puntuacion;
		}
	}
	
}
