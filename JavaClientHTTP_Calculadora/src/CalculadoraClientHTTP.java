import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class CalculadoraClientHTTP {

	public static void main(String[] args) {
		// Inicializa��o das Vari�veis
		String urlPath = "https://double-nirvana-273602.appspot.com/?hl=pt-BR"; // URL onde est� o servi�o
		Scanner scan = new Scanner(System.in); // Entrada
		boolean isRunning = true; // Verifica se o programa est� rodando

		while (isRunning) {
			int operation = getoperationID(scan); // Entrada da opera��o
			int valueA = inputinteger(scan, "Valor A: "); // Entrada do Primeiro Valor
			int valueB = inputinteger(scan, "Valor B: "); // Entrada do Segundo Valor
			try {
		    	HttpsURLConnection conn = sendRequest(urlPath, operation, valueA, valueB); // Manda requisi��o de opera��o
		    	printResponse(conn); // Imprime a resposta para a requisi��o
			} catch (IOException e) {
				System.out.println(e); // Informa o erro ao usu�rio
			}
			
			// Verifica se o usu�rio quer continuar fazendo opera��es
			if (shouldend(scan)) {
				break;
			}
		}
		
		System.out.println("Programa Encerrado!");
	}
	
	private static HttpsURLConnection sendRequest(String urlPath, int operationID, int valueA, int valueB) throws IOException {
		URL url = new URL(urlPath);
		
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); // Abre a conex�o
        conn.setReadTimeout(10000); // Timeout de leitura
        conn.setConnectTimeout(15000); // Timeout para a conex�o
        conn.setRequestMethod("POST"); // M�todo de requisi��o
        conn.setDoInput(true); // Conex�o envia dados
        conn.setDoOutput(true); // Conex�o recebe dados

        //ENVIO DOS PARAMETROS
        OutputStream os = conn.getOutputStream(); // Abre o caminho de sa�da
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8")); // Cria um BufferedWriter para escrever os dados
        //1-somar 2-subtrair 3-multiplicar 4-dividir
        writer.write("oper1="+valueA+"&oper2="+valueB+"&operacao="+operationID); // Escreve os dados
        writer.flush(); // Envia os dados
        writer.close(); // Fecha o BufferedWriter
        os.close(); // Fecha o caminho de sa�da
        return conn;
	}
	
	private static void printResponse(HttpsURLConnection conn) throws IOException {
		int responseCode = conn.getResponseCode(); // C�digo de Resposta � Requisi��o
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            //RECBIMENTO DOS PARAMETROS
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8")); // Cria um leitor
            StringBuilder response = new StringBuilder(); // Inicia o StringBuilder
            String responseLine = null; // Vari�vel tempor�ria para receber as linhas
            while ((responseLine = br.readLine()) != null) { // L� todas as linhas do inputStream
                response.append(responseLine.trim()); // Adiciona a resposta
            }
            String result = response.toString(); // Monta a string 
            System.out.println("Resposta do Servidor PHP = "+result);
        }
	}
	
	private static int getoperationID(Scanner scan) {
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
