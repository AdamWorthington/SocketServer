
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	private String hostName;
	@SuppressWarnings("unused")
	private int portNumber;
	private PrintWriter out;
	private Socket echoSocket;
	private BufferedReader in;
	private BufferedReader stdIn;

	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err
					.println("Incorrect Syntax. Correct: java Client <host name> <port number>");
			System.exit(1);
		}
		Client client = new Client();
		client.assignVariables(args[0], Integer.parseInt(args[1]));
		while (true) {

			if (client.logIn() == true) {

			} else {

				break;
			}
		}
		client.closeAll();
	}

	public boolean logIn() throws UnknownHostException, IOException {
		String message;
		String total = "";
		@SuppressWarnings("resource")
		Scanner us = new Scanner(System.in);

		try {

			String userInput;
			System.out.print("Username: ");
			String userSend = us.next();
			out.println(userSend);
			System.out.print("Password: ");
			String passSend = us.next();
			out.println(passSend);
			String pop = "";
			pop = in.readLine();

			if (pop.equals("Access Granted")) {

				System.out.print("Access Granted\n> ");
				while ((userInput = stdIn.readLine()) != null) {
					out.println(userInput);
					total = "";
					do {
						message = in.readLine();
						if (message.endsWith(">")) {
							total += message + "\n> ";
						}
						else{
							total += message + "\n";
						}
					} while (!(message.endsWith(">")));
					System.out.print(total);
				}
				return false;
			} else if (pop.equals("Access Denied")) {
				System.out
						.println("Access Denied, Incorrect Username or Password.");
				return true;
			} else {
				System.out.println("Couldn't find Access level... ");
				return true;
			}

		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
			return true;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);

			return true;
		}
	}

	public void assignVariables(String host, int port) {
		hostName = host;
		portNumber = port;
		try {
			echoSocket = new Socket(host, port);
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			out = new PrintWriter(echoSocket.getOutputStream(), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stdIn = new BufferedReader(new InputStreamReader(System.in));
	}

	public void closeAll() throws IOException {
		echoSocket.close();
	}

	public void printValues() {
		System.out.println(echoSocket + " " + in + " " + stdIn);
	}

}
