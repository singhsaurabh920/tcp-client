package org.tcp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TcpClientApplication {
	public static final Logger logger = LoggerFactory.getLogger(TcpClientApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TcpClientApplication.class, args);
		String SOCKET_IP = args[0];
		int SOCKET_PORT = Integer.parseInt(args[1]);
		logger.info("Client application  started");
		createConnection(SOCKET_IP,SOCKET_PORT);
	}
	
	
	public static void createConnection(String ip,int port){
		Socket socket = null;
		try {
			logger.info("Socket connecting- " + ip+":"+port);
			socket = new Socket(ip, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader systemBR = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader socketBR = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));
			while (true) {
				try {
					String message = systemBR.readLine();
					out.println(message);
					out.flush();
					String response = socketBR.readLine();
					logger.info(response);
				} catch (Exception e) {
					logger.error("Exception - " + e);
					break;
				}
			}
			socket.close();
		} catch (IOException e) {
			logger.error("IOException - ", e);
		}	
	}

}
