import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Calculadora  implements ICalculadora {

	private static final long serialVersionUID = 1L;
	
	private static int chamadas = 0;

	public int soma(int a, int b) throws RemoteException {
		System.out.println("Método soma chamado " + chamadas++); // Adiciona ao número de chamadas
		return a + b; // Retorna o resultado
	}
	
	public int subtrai(int a, int b) throws RemoteException {
		System.out.println("Método subtrai chamado " + chamadas++); // Adiciona ao número de chamadas
		return a - b; // Retorna o resultado
	}
	
	public int multiplica(int a, int b) throws RemoteException {
		System.out.println("Método multiplica chamado " + chamadas++); // Adiciona ao número de chamadas
		return a * b; // Retorna o resultado
	}
	
	public int divide(int a, int b) throws RemoteException {
		System.out.println("Método divide chamado " + chamadas++); // Adiciona ao número de chamadas
		return a / b; // Retorna o resultado
	}

	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException  {
		Calculadora calculadora = new Calculadora(); // Inicia a calculadora		
		Registry reg = null; // Tabela de registros
		ICalculadora stub = (ICalculadora) UnicastRemoteObject.
				exportObject(calculadora, 1100); // Cria o stub da calculadora
		try {
			System.out.println("Creating registry...");
			reg = LocateRegistry.createRegistry(1099); // Inicializa o registro
		} catch (Exception e) {
			try {
				reg = LocateRegistry.getRegistry(1099); // Pega o registro caso já tenha sido criado
			} catch (Exception e1) {
				System.exit(0);
			}
		}
		reg.rebind("calculadora", stub);
	}
}
