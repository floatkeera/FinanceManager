package files;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class settings extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;

	public static String userName;
	public static String limit;
	public static String lang;
	public static int langint;
	public static String filename = "name.txt";
	public static String[] args = {};

	public static PrintWriter writer = null;
	private JTextField textField;
	
	//text var
	static String usernamelbl;
	static String limitlbl;
	static String languagelbl;
	
	public static Font fontbig;
	public static Font fontsmall;
	public static Font fontdate;

	/**
	 * Launch the application.
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					settings frame = new settings();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//obtaining data file path in which JAR is located in
		
		File path =  new File(ClassLoader.getSystemClassLoader().getResource(".").toURI().getPath());
		File parent = new File((path).getParentFile().toURI().getPath());
		String decoded = path.toString();
		filename = decoded + "/name.txt";

		try {
			
			//reading the data file with each line representing each settings value
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line = in.readLine();
			userName = line;
			String line2 = in.readLine();
			limit = line2;
			String line3 = in.readLine();
			lang = line3;
			langint = Integer.parseInt(lang);
			
		} catch (IOException iox) {
			System.out.println("Problem reading " + filename);
		}
	}

	/**
	 * Create the frame.
	 */
	public settings() {
		//performs localization
		locale();
		
		
		//window components
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblSettings.setBounds(17, 6, 255, 30);
		contentPane.add(lblSettings);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(fontsmall);
		lblUsername.setBounds(17, 54, 78, 30);
		lblUsername.setText(usernamelbl);
		contentPane.add(lblUsername);

		txtName = new JTextField();
		txtName.setFont(new Font("Leelawadee", Font.PLAIN, 13));
		txtName.setBounds(105, 56, 255, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtName.setText(userName);

	
		//combobox to display the language setting options
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "ภาษาไทย"}));
		comboBox.setSelectedIndex(langint);
		comboBox.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		comboBox.setBounds(105, 143, 255, 27);
		contentPane.add(comboBox);
		
		
		JButton btnSave = new JButton("Save");
		//action listener for save button
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				//asks the user for confirmation
				JLabel dialog = new JLabel("Save Settings? บันทึกการตั้งค่า?");
				dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));
				
				int result = JOptionPane
						.showConfirmDialog((Component) null, dialog,
								"Confirmation", JOptionPane.OK_CANCEL_OPTION);
				
				//if the user confirms
				if (result == 0) {
					//tries to write the new settings to the settings data file
					userName = txtName.getText();
					limit = textField.getText();
					lang = String.valueOf(comboBox.getSelectedIndex());

					try {

						writer = new PrintWriter(new BufferedWriter(
								new FileWriter(filename)), true);
					} catch (IOException iox) {
						System.out.println("Error in creating file");
						return;

					}

				}

				writer.println(userName);
				writer.println(limit);
				writer.println(lang);
				
				//returns to the main menu
				try {
					mainForm.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		//further window components
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSave.setBounds(235, 223, 117, 35);
		contentPane.add(btnSave);

		JButton btnCancel = new JButton("Main Menu");
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnCancel.setBounds(40, 223, 117, 35);
		contentPane.add(btnCancel);
		
		JLabel lblSpendlingLimit = new JLabel("Limit");
		lblSpendlingLimit.setFont(fontsmall);
		lblSpendlingLimit.setBounds(17, 96, 78, 30);
		lblSpendlingLimit.setText(limitlbl);
		contentPane.add(lblSpendlingLimit);
		
		textField = new JTextField();
		textField.setText((String) null);
		textField.setFont(new Font("Leelawadee", Font.PLAIN, 13));
		textField.setColumns(10);
		textField.setBounds(105, 98, 255, 28);
		contentPane.add(textField);
		textField.setText(limit);

		//option for user to return to main menu
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					mainForm.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		contentPane.getRootPane().setDefaultButton(btnSave);
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setFont(fontsmall);
		lblLanguage.setBounds(17, 138, 78, 30);
		lblLanguage.setText(languagelbl);
		contentPane.add(lblLanguage);
		
		
	}
	
	//localization function which sets the descriptions to the language the user desires
	public void locale(){
		if (langint == 0) {
		
			usernamelbl = "User name";
			limitlbl = "Credit limit";
			languagelbl = "Language";
			fontbig = new Font("Segoe UI", Font.PLAIN, 20);
			fontsmall = new Font("Segoe UI", Font.PLAIN, 16);
			fontdate = new Font("Segoe UI", Font.PLAIN, 14);

		}

		if (langint == 1) {
			
			usernamelbl = "ชื่อผู้ใช้";
			limitlbl = "วงเงินใช้จ่าย";
			languagelbl = "ภาษา";
			
			fontbig = new Font("Leelawadee", Font.PLAIN, 20);
			fontsmall = new Font("Leelawadee", Font.PLAIN, 16);
			fontdate = new Font("Leelawadee", Font.PLAIN, 14);

		}
	}
	
}
