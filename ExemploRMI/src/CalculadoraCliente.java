import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class CalculadoraCliente {
	
	public static void main(String[] args) {
		// Vari�veis de Programa
		Registry reg = null; // Tabela de registros
		ICalculadora calc = null; // Calculadora
		Scanner scan = new Scanner(System.in); // Entrada
		boolean isRunning = true; // Verifica se o programa est� rodando

		// Captura do objeto remoto
		try {
			reg = LocateRegistry.getRegistry(1099); // Procura pelo registro
			calc = (ICalculadora) reg.lookup("calculadora"); // Pega a calculadora na tabela de registros
		} catch (RemoteException | NotBoundException e) {
				System.out.println(e);
				System.exit(0);
		}
		
		// Loop para o console
		while (isRunning) {
			int operation = getoperationID(calc, scan); // Entrada da opera��o
			int valueA = inputinteger(scan, "Valor A: "); // Entrada do Primeiro Valor
			int valueB = inputinteger(scan, "Valor B: "); // Entrada do Segundo Valor
			try {
				calculate(calc, operation, valueA, valueB); // Pede o resultado da opera��o
			} catch (RemoteException e) {
				System.out.println(e); // Informa o erro ao usu�rio
			}
			
			// Verifica se o usu�rio quer continuar fazendo opera��es
			if (shouldend(scan)) {
				break;
			}
		}
		
		System.out.println("Programa Encerrado!");
	}
	
	private static void calculate(ICalculadora calc, int operationID, int valueA, int valueB) throws RemoteException {
		int result = 0;
		switch (operationID) {
		case 1: // Soma
			result = calc.soma(valueA, valueB);
			break;
		case 2: // Subtra��o
			result = calc.subtrai(valueA, valueB);
			break;
		case 3: // Multiplica��o
			result = calc.multiplica(valueA, valueB);
			break;
		case 4: // Divis�o
			result = calc.divide(valueA, valueB);
			break;
		default: // Opera��o n�o implementada
			System.out.println("Opera��o inv�lida!");
			return;
		}
		
		// Exibe resultado
		System.out.println("Resultado: "+result);
	}

	private static int getoperationID(ICalculadora calc, Scanner scan) {
		// Lista de Opera��es
		System.out.println("Calculadora: ");
		System.out.println("1 - Somar");
		System.out.println("2 - Subtrair");
		System.out.println("3 - Multiplicar");
		System.out.println("4 - Dividir");
		
		// Retorna o c�digo escolhido pelo usu�rio
		return inputinteger(scan, "Opera��o: ");
	}
	
	private static int inputinteger(Scanner scan, String message) {
		// Exibe uma mensagem
		System.out.print(message);
		
		// Captura o input do usu�rio
		return scan.nextInt();
	}
	
	private static boolean shouldend(Scanner scan) {
		// Fun��o para permitir o loop do console
		System.out.println("Fazer mais opera��es? Digite S para continuar...");
		String command = scan.next();
		switch (command) {
		case "S": // Continuar
			return false;
		default: // Encerrar
			return true;
		}
	}
}
