import java.io.*;



public class Path {
	public static void main(String[] args) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\aawor_000\\workspace\\TCPServer\\src\\Users\\Adam.txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        String everything = sb.toString();
	        System.out.println(everything);
	    } finally {
	        br.close();
	    }
	    
	   
	}
}
