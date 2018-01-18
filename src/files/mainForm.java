package files;

//import external libraries used
//these include mainly the Swing components for the window components
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.util.*;
import java.security.CodeSource;
import java.text.*;
import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLDecoder;



public class mainForm extends JFrame {

	String[] args = {};
	
	
	//variables for windows components
	private JPanel contentPane;
	public static String strName;
	public static String strLimit;
	public static String lang;
	public static int langint;
	
	//constant variables
	public static String filename = "name.txt";
	static LinkedList transactions = new LinkedList();
	static String fileName = "transactions.txt";
	static double total = 0;
	
	// String variables for localization
	public static String welcome;
	public static String searchlbl;
	public static String newlbl;
	public static String summarylbl;
	public static String settinglbl;
	public static String usedlbl;
	public static String ballbl;
	public static Font fontbig;
	public static Font fontsmall;
	public static Font fontdate;
	static Locale loc = new Locale("en", "TH");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", loc);
	
	
	
	/**
	 * Launch the application.
	 * @throws URISyntaxException 
	 * @throws UnsupportedEncodingException 
	 */
	
	public static void main(String[] args) throws URISyntaxException{
	
		//obtaining data file path in which JAR is located in
		File path =  new File(ClassLoader.getSystemClassLoader().getResource(".").toURI().getPath());
		File parent = new File((path).getParentFile().toURI().getPath());
		String decoded = path.toString();
	
		filename = decoded + "/name.txt";
		System.out.println(filename);
		fileName = decoded + "/transactions.txt";
	
		//try-catch for reading from data file
		try {

			BufferedReader in = new BufferedReader(new FileReader(fileName));

			String line = in.readLine();
			String transactionInfo[];

			//traversing data file, creating transaction objects and adding to the transaction linkedlist
			while (line != null) // continue until end of file
			{
				transactionInfo = line.split(", ", 5);
				transaction x = new transaction(transactionInfo[0],
						transactionInfo[1], transactionInfo[2],
						transactionInfo[3], transactionInfo[4]);
				transactions.add(x);
				line = in.readLine();
			}
		} catch (IOException iox) {
			System.out.println(decoded);

		}
		
		//loop to traverse the transactions linkedlist
		for (int i = 1; i <= transactions.listCount; i++) {
			transaction a = transactions.get(i);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy",
					new Locale("th", "TH"));
			
			//comparing whether the date of each transaction is in the same month of the current month
			try {
				Date infoDate = (Date) dateFormat.parse(a.DATE);
				Date today = Calendar.getInstance().getTime();
				int month = infoDate.getMonth();
				int year = infoDate.getYear();
				int realmonth = today.getMonth();
				int realyear = today.getYear();
				
				//if this is true, it will be added to the quick glance balance
				if(month == realmonth && year == realyear){
					total= total + a.VALUE;
				}
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainForm frame = new mainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//try-catch statement for reading settings data file
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));

			//input of the settings
			String line = in.readLine();
			strName = line;
			String line2 = in.readLine();
			strLimit = line2;
			String line3 = in.readLine();
			lang = line3;
			langint = Integer.parseInt(lang);
		}
			catch (IOException iox) {
				System.out.println(decoded);
			}
	}



	/**
	 * Create the frame.
	 */
	public mainForm() {
		System.out.println(strName);
		System.out.println(strLimit);
		System.out.println(lang);
		
		
		//perform localization
		locale();
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("th", "TH"));
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//set date label as today's date with localization
		Date today = Calendar.getInstance().getTime();
		String d = dateFormat.format(today);
		contentPane.setLayout(null);
		JLabel lblDay = new JLabel(d);
		lblDay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDay.setBounds(213, 9, 211, 32);
		lblDay.setFont(fontdate);
		contentPane.add(lblDay);
		
		//set welcome label with user name
		JLabel lblWelcome = new JLabel("");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(fontbig);
		lblWelcome.setBounds(18, 47, 406, 32);
		lblWelcome.setText(welcome + strName);
		contentPane.add(lblWelcome);
		
		JLabel lblSearch = new JLabel(searchlbl);
		lblSearch.setFont(fontsmall);
		lblSearch.setBounds(24, 90, 244, 30);
		contentPane.add(lblSearch);
		
		//button to open search window
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears the linkedlist to avoid repeated inputs
				total = 0;
				for (int i = transactions.listCount; i >= 1; i--) {
					boolean a = transactions.remove(i);
					System.out.println(a);
				}
				try {
					search.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
		});
		btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSearch.setBounds(313, 90, 117, 35);
		contentPane.add(btnSearch);
		
		JLabel lblAddTransactions = new JLabel(newlbl);
		lblAddTransactions.setFont(fontsmall);
		lblAddTransactions.setBounds(24, 132, 213, 30);
		contentPane.add(lblAddTransactions);
		
		//button to open add new transaction window
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//clears the linkedlist to avoid repeated inputs
				total = 0;
				System.out.println(total);
				for (int i = transactions.listCount; i >= 1; i--) {
					boolean a = transactions.remove(i);
					System.out.println(a);
				}

				try {
					newTransaction.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		btnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnAdd.setBounds(313, 132, 117, 35);
		contentPane.add(btnAdd);
		
		JLabel lblLookAtSummary = new JLabel(summarylbl);
		lblLookAtSummary.setFont(fontsmall);
		lblLookAtSummary.setBounds(24, 174, 277, 30);
		contentPane.add(lblLookAtSummary);
		
		//button to open summary window
		JButton btnSummary = new JButton("Summary");
		btnSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears the linkedlist to avoid repeated inputs
				total = 0;
				for (int i = transactions.listCount; i >= 1; i--) {
					boolean a = transactions.remove(i);
					System.out.println(a);
				}

				try {
					summary.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
		});
		btnSummary.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSummary.setBounds(313, 174, 117, 35);
		contentPane.add(btnSummary);
		
		JLabel lblLimitAdjustmentAnd = new JLabel(settinglbl);
		lblLimitAdjustmentAnd.setFont(fontsmall);
		lblLimitAdjustmentAnd.setBounds(24, 216, 244, 30);		
		contentPane.add(lblLimitAdjustmentAnd);
		
		//button to open settings window
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSettings.setBounds(313, 216, 117, 35);
		contentPane.add(btnSettings);
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears the linkedlist to avoid repeated inputs
				total = 0;
				for (int i = transactions.listCount; i >= 1; i--) {
					boolean a = transactions.remove(i);
					System.out.println(a);
				}

				try {
					settings.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			
				
				
			}
		});
		
		JLabel lblFinancialManager = new JLabel("Financial Manager");
		lblFinancialManager.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblFinancialManager.setBounds(24, 9, 255, 32);
		contentPane.add(lblFinancialManager);
		
		JLabel lblQuickGlance = new JLabel("Quick Glance");
		lblQuickGlance.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblQuickGlance.setBounds(24, 272, 213, 30);
		contentPane.add(lblQuickGlance);
		
		//quick glance menu components
		
		//amount used
		JLabel lblQuickG1 = new JLabel(usedlbl);
		lblQuickG1.setFont(fontsmall);
		lblQuickG1.setBounds(24, 300, 406, 30);
		contentPane.add(lblQuickG1);
		String money = currencyFormatter.format(total);
		lblQuickG1.setText(usedlbl + money);
		
		//balance left from limit
		JLabel lblLimit = new JLabel(ballbl);
		lblLimit.setFont(fontsmall);
		lblLimit.setBounds(24, 329, 406, 30);
		contentPane.add(lblLimit);
		double intlimit = Double.parseDouble(strLimit);
		String limitleft = currencyFormatter.format(intlimit - total);
		lblLimit.setText(ballbl + limitleft );
		
		//separator
		JLabel label = new JLabel("__________________________________________________________");
		label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		label.setBounds(24, 244, 406, 30);
		contentPane.add(label);
		

		
		
		
		
	}
	
	//localization function
	public void locale(){
		//for English menu,  the following descriptions and buttons texts are used
		if(langint == 0){
			loc = new Locale("en", "US");
		    welcome = "Welcome, ";
			searchlbl = "Search, Edit, View, Delete";
			dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", loc);
			newlbl = "Add new transaction";
			summarylbl = "View Summary";
			settinglbl = "Limit adjustment and settings";
			usedlbl = "Used this month: ";
			ballbl = "Balance left from limit: ";
			fontbig = new Font("Segoe UI", Font.PLAIN, 20);
			fontsmall = new Font ("Segoe UI Symbol", Font.PLAIN, 16);
			fontdate = new Font("Segoe UI", Font.PLAIN, 14);
			
		}
		
		//for Thai menu, the following descriptions and buttons texts are used
		if(langint == 1){
			loc = new Locale("th", "TH");
		    welcome = "ยินดีต้อนรับ ";
			searchlbl = "ค้นหาเพื่อ ดู แก้ไข ลบ";
			newlbl = "เพิ่มรายการใหม่";
			summarylbl = "ดูยอดสรุปประจำเดือน";
			settinglbl = "แก้ไขวงเงินและการตั้งค่าอื่นๆ";
			usedlbl = "เดือนนี้ใช้ไป: ";
			ballbl = "คงเหลือจากวงเงิน: ";
			fontbig = new Font("Sukhumvit Set", Font.PLAIN, 20);
			fontsmall = new Font ("Sukhumvit Set", Font.PLAIN, 16);
			fontdate = new Font("Sukhumvit Set"
					+ "", Font.PLAIN, 14);
			dateFormat = new SimpleDateFormat("EEEE d MMMM yyyy", loc);
			
		}
	}
	
}
