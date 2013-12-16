
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server extends Thread {
	private Socket socket = null;
	private String currentUser;

	public Server(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}

	public void run() {

		try {
			if (socket.isConnected()) {
				if (authenticate()) {
					sendMessage("Access Granted");
					access();
				} else {
					sendMessage("Access Denied");
				}
			}
		} catch (IOException e1) {
			System.out.println("User gave up at login...");

		}
	}

	private void commands(String input) throws IOException {
		String help = "<Hello - say hi to your friendly server\n"
				+ "Bye - attempt to leave with grace.\n"
				+ "whoami - find out who you trully are on the inside... of the server.\n"
				+ "help - What is the number for 911 again?>";
		input = input.toLowerCase();
		switch (input) {
		case "/portcheck":{
			//portCheck();
			break;
		}
		case "/hello": {
			sendMessage("<Hey>");
			break;
		}
		case "/bye": {
			sendMessage("<No don't leave.>");
			break;
		}
		case "/whoami": {
			String p = socket.getRemoteSocketAddress().toString();
			sendMessage("<You are " + currentUser + ". Connected on IP: " + p + ".>");
			break;
		}
		case "/help": {
			sendMessage(help);
			break;

		}
		case "/randomnum": {
			double p = Math.random() * 100;
			String s = "<" + String.format("%f", p) + ">";
			sendMessage(s);
			break;
		}
		case "/miniwatt":{
			
		}
		case "/remoteexecute": {
			
			RemoteExecute b = new RemoteExecute("yo");
		}
		default: {
			if (input.startsWith("/miniwatt")){
				
			}
			sendMessage("<Please enter a valid command.>");
			break;
		}
		}

	}

	public void sendMessage(String message) {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(message);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed  at METHOD: SENDMESSAGE");
		}
	}

	public boolean authenticate() throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		String user = in.readLine();
		String pass = in.readLine();

		BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\aawor_000\\workspace\\TCPServer\\src\\Users\\"
						+ user + ".txt"));
		try {

			String line = br.readLine();
			if (line.equals(pass)) {
				currentUser = user;

				System.out.println(currentUser + " has logged in. ");
				return true;
			} else {
				return false;
			}
		} finally {
			br.close();
		}

	}

	public void access() throws IOException {

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String inputLine;
			addOnline();
			while (socket.isConnected()) {

				inputLine = in.readLine();
				if (inputLine != null) {
					if (inputLine.equalsIgnoreCase("Exit")) {
						break;
					} else {
						commands(inputLine);
					}

				} else {
					socket.close();
					System.out.print("(Break Character) ");
					break;
				}
			}
			removeOnline();
			in.close();
			socket.close();

		} catch (IOException e) {
			System.out.print("(Manual Close) ");
			removeOnline();
			
			
		}

	}

	public void addOnline() throws IOException {
		String path = "C:\\Users\\aawor_000\\workspace\\TCPServer\\src\\Status\\Online.txt";
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
		    out.println(currentUser);
		    out.close();
		} catch (IOException e) {
		    System.out.println("Failed on METHOD: addOnline.");
		}
	}

	public void removeOnline() {
		String file = "C:\\Users\\aawor_000\\workspace\\TCPServer\\src\\Status\\Online.txt";
		try {

			File inFile = new File(file);
			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}
			// Construct the new file that will later be renamed to the original
			// filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			// Read from the original file and write to the new
			// unless content matches data to be removed.
			while ((line = br.readLine()) != null) {

				if (!line.trim().equals(currentUser)) {

					pw.println(line);
					pw.flush();
				}
			}

			System.out.println(currentUser
					+ " has disconnected. (Exit Command)");
			pw.close();
			br.close();

			// Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out
					.println("Failed at finding file in METHODD: removeOnline ");
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Failed at IO in METHOD: removeOnline ");
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<String> onlineList() throws IOException {
		String path = "C:\\Users\\aawor_000\\workspace\\TCPServer\\src\\Status\\Online.txt";
		ArrayList<String> names = new ArrayList<String>();

		BufferedReader in = new BufferedReader(new FileReader(path));
		try {
			while (true) {
				names.add(in.readLine());
			}
		} finally {
			in.close();
			return names;

		}
	}
	public void portCheck(){
		String p = socket.getRemoteSocketAddress().toString();
		int i = p.indexOf(':');
		p = p.substring(0, i);
		for(int j = 100; j <= 65535; j++){
			try (ServerSocket mal = new ServerSocket(j)) { 
	            System.out.println(currentUser + " has a vulenerable port on: " + j);
	            mal.close();
	        } catch (IOException e) {
	            
	        }
			
		}
	}
	public void externalExec(String name, String input){
		
	}
}

