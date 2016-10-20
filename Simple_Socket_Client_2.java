import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Simple_Socket_Client_2{
	private Socket cs;
	private Scanner scanner;
	private static final int port = 57968;
	private static final String addr = "127.0.0.1";
	
	public Simple_Socket_Client_2(){
		build_client_socket();
	}

	private void build_client_socket(){
		try{
			cs = new Socket(InetAddress.getByName(addr), port);
			System.out.println("Connection to server IP: " + 
					cs.getInetAddress().getHostAddress() + "\n");
			
			String inData = "";
			DataOutputStream out = new DataOutputStream(cs.getOutputStream());
			DataInputStream in = new DataInputStream(cs.getInputStream());

			while(true){
				System.out.print("Please type messages to server：");
				scanner = new Scanner(System.in);
				String outData = scanner.nextLine();
				if(outData.equals("321"))
					break;
				out.writeUTF(outData);
				inData = in.readUTF();
				System.out.println("Message from server "+cs.getInetAddress().getHostAddress()+" :"+inData);
			}

			in.close();
			out.close();
			cs.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private void close_socket(){
		try{
			cs.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println("\nI'm Client!!!!");
		new Simple_Socket_Client_2();
	}
}
