package JMeter.plugins.functional.samplers.socketsamplerGui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.JTextArea;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class MYtcpsampler extends JPanel {
	public static JTextField textField;
	public static JTextField textField_1;

	public static JTextField server;
	public static JTextField port;
	public static JTextField timeout;
	public static JTextField connecttimeout;

	/**
	 * Create the panel.
	 */
	public MYtcpsampler() {
        setLayout(null);



 
  

        
        server = new JTextField();
        server.setBounds(35, 19, 94, 21);
        add(server);
        server.setColumns(10);
        
        port = new JTextField();
        port.setBounds(177, 19, 27, 21);
        add(port);
        port.setColumns(10);
        
        timeout = new JTextField();
        timeout.setBounds(385, 19, 48, 21);
        add(timeout);
        timeout.setColumns(10);
        
        connecttimeout = new JTextField();
        connecttimeout.setBounds(530, 19, 66, 21);
        add(connecttimeout);
        connecttimeout.setColumns(10);
        
        JLabel lblNewLabel = new JLabel("发送间隔(ms)|需要大于10");
        lblNewLabel.setBounds(231, 22, 144, 15);
        add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("connettimeout");
        lblNewLabel_1.setBounds(446, 22, 78, 15);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("IP :");
        lblNewLabel_2.setBounds(10, 22, 54, 15);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("port:");
        lblNewLabel_3.setBounds(139, 22, 54, 15);
        add(lblNewLabel_3);

        
	}
}
