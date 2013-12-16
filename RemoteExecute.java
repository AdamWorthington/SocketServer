import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteExecute {
	public RemoteExecute(String s){
		
	}
	private static void printLines(String name, InputStream ins)
			throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
	}

	private static void runProcess(String command) throws Exception {
		Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream());
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		System.out.println(command + " exitValue() " + pro.exitValue());
	}

	public static void main(String[] args) {
		try {
			runProcess("javac C:\\Users\\Adam\\Desktop\\Main.java");
			runProcess("java -cp C:\\Users\\Adam\\Desktop Main");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
