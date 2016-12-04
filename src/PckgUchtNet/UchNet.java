package PckgUchtNet;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

public class UchNet extends JFrame implements Runnable {
	static private Socket connection;
	static private ObjectOutputStream output;
	static private ObjectInputStream input;

	public static void main(String[] args) {
		new Thread(new UchNet("GUI")).start();
	}
	public UchNet(String name){
		super(name);
		setLayout(new FlowLayout());
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		final JTextField t1 = new JTextField(50);
		final JButton b1=new JButton("Send");
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendData(t1.getText());				
			}
		});
		add(t1);
		add(b1);
	}

	@Override
	public void run() {
		try {
			
			while(true){
				connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
				output = new ObjectOutputStream(connection.getOutputStream());
				input = new ObjectInputStream(connection.getInputStream());
				JOptionPane.showMessageDialog(null, (String)input.readObject());
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ошибка соединения");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Не найдено");
		}
		
	}
	private static void sendData(Object obj){
		try {
			output.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ошибка");
		}
	}

}
