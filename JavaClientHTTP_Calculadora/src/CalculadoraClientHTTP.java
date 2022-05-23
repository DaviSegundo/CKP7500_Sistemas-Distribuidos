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
		// Inicialização das Variáveis
		String urlPath = "https://double-nirvana-273602.appspot.com/?hl=pt-BR"; // URL onde está o serviço
		Scanner scan = new Scanner(System.in); // Entrada
		boolean isRunning = true; // Verifica se o programa está rodando

		while (isRunning) {
			int operation = getoperationID(scan); // Entrada da operação
			int valueA = inputinteger(scan, "Valor A: "); // Entrada do Primeiro Valor
			int valueB = inputinteger(scan, "Valor B: "); // Entrada do Segundo Valor
			try {
		    	HttpsURLConnection conn = sendRequest(urlPath, operation, valueA, valueB); // Manda requisição de operação
		    	printResponse(conn); // Imprime a resposta para a requisição
			} catch (IOException e) {
				System.out.println(e); // Informa o erro ao usuário
			}
			
			// Verifica se o usuário quer continuar fazendo operações
			if (shouldend(scan)) {
				break;
			}
		}
		
		System.out.println("Programa Encerrado!");
	}
	
	private static HttpsURLConnection sendRequest(String urlPath, int operationID, int valueA, int valueB) throws IOException {
		URL url = new URL(urlPath);
		
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); // Abre a conexão
        conn.setReadTimeout(10000); // Timeout de leitura
        conn.setConnectTimeout(15000); // Timeout para a conexão
        conn.setRequestMethod("POST"); // Método de requisição
        conn.setDoInput(true); // Conexão envia dados
        conn.setDoOutput(true); // Conexão recebe dados

        //ENVIO DOS PARAMETROS
        OutputStream os = conn.getOutputStream(); // Abre o caminho de saída
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8")); // Cria um BufferedWriter para escrever os dados
        //1-somar 2-subtrair 3-multiplicar 4-dividir
        writer.write("oper1="+valueA+"&oper2="+valueB+"&operacao="+operationID); // Escreve os dados
        writer.flush(); // Envia os dados
        writer.close(); // Fecha o BufferedWriter
        os.close(); // Fecha o caminho de saída
        return conn;
	}
	
	private static void printResponse(HttpsURLConnection conn) throws IOException {
		int responseCode = conn.getResponseCode(); // Código de Resposta à Requisição
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            //RECBIMENTO DOS PARAMETROS
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8")); // Cria um leitor
            StringBuilder response = new StringBuilder(); // Inicia o StringBuilder
            String responseLine = null; // Variável temporária para receber as linhas
            while ((responseLine = br.readLine()) != null) { // Lê todas as linhas do inputStream
                response.append(responseLine.trim()); // Adiciona a resposta
            }
            String result = response.toString(); // Monta a string 
            System.out.println("Resposta do Servidor PHP = "+result);
        }
	}
	
	private static int getoperationID(Scanner scan) {
		// Lista de Operações
		System.out.println("Calculadora: ");
		System.out.println("1 - Somar");
		System.out.println("2 - Subtrair");
		System.out.println("3 - Multiplicar");
		System.out.println("4 - Dividir");
		
		// Retorna o código escolhido pelo usuário
		return inputinteger(scan, "Operação: ");
	}
	
	private static int inputinteger(Scanner scan, String message) {
		// Exibe uma mensagem
		System.out.print(message);
		
		// Captura o input do usuário
		return scan.nextInt();
	}
	
	private static boolean shouldend(Scanner scan) {
		// Função para permitir o loop do console
		System.out.println("Fazer mais operações? Digite S para continuar...");
		String command = scan.next();
		switch (command) {
		case "S": // Continuar
			return false;
		default: // Encerrar
			return true;
		}
	}
}
