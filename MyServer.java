import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

class MyServer{
	ServerSocket ss;
	Socket cs;
	private static final int port = 57968;
	private static final int backlog = 10;
	private static final String addr = "127.0.0.1";

	public MyServer(){
		build_ServerSocket_and_list_ServerInfo();
		listenClient();
		close_socket();
	}

	private void build_ServerSocket_and_list_ServerInfo(){
		try{
			ss = new ServerSocket(port,backlog,InetAddress.getByName(addr));

			System.out.println("Local Information");
			System.out.println(" Local Host："+InetAddress.getLocalHost());
			System.out.println(" Host Name："+InetAddress.getLocalHost().getHostName());
			System.out.println(" Host Address："+ InetAddress.getLocalHost().getHostAddress());
			System.out.println();

			System.out.println("Socket Server Information");
			System.out.println(" IP Address：" + ss.getInetAddress().getHostAddress());
			System.out.println(" Host Name：" + ss.getLocalPort());
			System.out.println();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	private void listenClient(){
		System.out.println("Listen for clients...");
		while(true){
			try{
				cs = ss.accept();
				Thread thread = new Thread(new listenThread(cs));
				thread.start();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}

	private void close_socket(){
		try{
			ss.close();
			System.out.println("closed!");
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println("\nI'm server!!!!");
		new MyServer();
	}
}

class listenThread implements Runnable{
	Socket cs;
	Scanner scanner;

	public listenThread(Socket clientSocket){
		cs = clientSocket;
	}

	public void run(){
		try{
			if(cs.isConnected()){
				System.out.println("Client from IP：" + cs.getInetAddress().getHostAddress());

				String inData = "";
				DataInputStream in = new DataInputStream(cs.getInputStream());
				DataOutputStream out = new DataOutputStream(cs.getOutputStream());

				inData = in.readUTF();
				System.out.println("Message from client "+cs.getInetAddress().getHostAddress()+" :"+inData);
				System.out.print("Please type messages to client：");
				scanner = new Scanner(System.in);
				String outData = scanner.nextLine();
				out.writeUTF(outData);

				out.close();
				in.close();
				cs.close();
			}
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
}