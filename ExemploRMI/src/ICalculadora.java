import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculadora extends Remote {

	public int soma(int a, int b) throws RemoteException;
	// Novas operações a serem implementadas abaixo
	public int subtrai(int a, int b) throws RemoteException; // a-b
	public int multiplica(int a, int b) throws RemoteException; // a*b
	public int divide(int a, int b) throws RemoteException; // a/b
}
