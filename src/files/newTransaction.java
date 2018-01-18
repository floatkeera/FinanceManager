package files;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.JLabel;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;

import com.toedter.calendar.JMonthChooser;

public class newTransaction extends JFrame {

	// variables for window components
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtValue;

	String[] args = {};

	//declaration of linkedlists
	static LinkedList transactions = new LinkedList();
	static LinkedListCat categories = new LinkedListCat();


	//constant variables
	private String currentcat = "";
	private double currentvalue = 0;
	private String currentdate = "";
	static String fileName = "transactions.txt";
	static String fileName2 = "categories.txt";

	//variables for localization
	public static String datelbl;
	public static String categorylbl;
	public static String namelbl;
	public static String valuelbl;
	public static String noteslbl;
	public static Font fontbig;
	public static Font fontsmall;
	public static Font fontdate;
	public static int langint;
	static Locale loc = new Locale("en", "TH");

	/**
	 * Launch the application.
	 * @throws UnsupportedEncodingException 
	 * @throws URISyntaxException 
	 */
	
	public static void main(String[] args) throws URISyntaxException {
		
		//obtaining data file path in which JAR is located in
		File path =  new File(ClassLoader.getSystemClassLoader().getResource(".").toURI().getPath());
		String decoded = path.toString();
		fileName = decoded + "/transactions.txt";
		fileName2 = decoded + "/categories.txt";
		
		//try-catch for reading from transaction data file
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
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
			System.out.println("Error");

		}

		//try-catch for reading from categories data file
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName2), "UTF-8"));
			String line = in.readLine();
			String categoryinfo[];
			
			//traversing data file, creating category objects and adding to the categories linkedlist
			while (line != null) // continue until end of file
			{
				categoryinfo = line.split(", ", 3);
				category x = new category(categoryinfo[0], categoryinfo[1],
						categoryinfo[2]);

				categories.add(x);
				line = in.readLine();
			}
		} catch (IOException iox) {
			System.out.println("Error");
		}
	
		//Default language is English
		langint = 0;
		//setting the language indicator to the same as that read from the settings data file in the main menu
		langint = mainForm.langint;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newTransaction frame = new newTransaction();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//changing the font of the message boxes
		javax.swing.UIManager.put("OptionPane.font", new Font("Leelawadee",
				Font.PLAIN, 14));
	}

	/**
	 * Create the frame.
	 */

	public boolean isDouble(String a) {
		try {
			Double.parseDouble(a);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public newTransaction() {

		//perform localization
		locale();
		
		//set frames and declares frame object components
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//declaration of labels, and making them display the localized descriptions
		JLabel lblAddTransaction = new JLabel("Add Transaction");
		lblAddTransaction.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblAddTransaction.setBounds(18, 6, 255, 25);
		contentPane.add(lblAddTransaction);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(fontsmall);
		lblName.setBounds(18, 157, 81, 30);
		lblName.setText(namelbl);
		contentPane.add(lblName);

		JLabel lblValue = new JLabel("Value");
		lblValue.setFont(fontsmall);
		lblValue.setBounds(18, 199, 81, 30);
		lblValue.setText(valuelbl);
		contentPane.add(lblValue);

		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setFont(fontsmall);
		lblNotes.setBounds(18, 241, 47, 30);
		lblNotes.setText(noteslbl);
		contentPane.add(lblNotes);

		txtName = new JTextField();
		txtName.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		txtName.setBounds(130, 157, 263, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtValue = new JTextField();
		txtValue.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		txtValue.setColumns(10);
		txtValue.setBounds(130, 201, 263, 28);
		contentPane.add(txtValue);

		//setting a scrollable multi-line notes field
		JTextArea txtNotes = new JTextArea();
		txtNotes.setWrapStyleWord(true);
		txtNotes.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		txtNotes.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(txtNotes);
		jsp.setBounds(130, 249, 263, 65);
		contentPane.add(jsp);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSave.setBounds(276, 356, 117, 35);
		contentPane.add(btnSave);

		//cancel button will return the user to the main menu
		JButton btnCancel = new JButton("Main Menu");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//clears the transaction linkedlist to avoid repeated inputs
				for (int i = transactions.listCount; i >= 1; i--) {
					boolean a = transactions.remove(i);
				}
				
				//clears the categories linkedlist to avoid repeated inputs
				for (int i = categories.listCount; i >= 1; i--) {
					boolean a = categories.remove(i);
				}

				//goes to main menu and disposes the current window
				try {
					mainForm.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();

			}
		});
		btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnCancel.setBounds(60, 356, 117, 35);
		contentPane.add(btnCancel);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(fontsmall);
		lblDate.setBounds(18, 60, 47, 30);
		lblDate.setText(datelbl);
		contentPane.add(lblDate);

		//dateChooser follows localization
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setLocale(loc);
		dateChooser.setFont(new Font("Sukhumvit Set", Font.PLAIN, 16));
		dateChooser.setBounds(130, 62, 263, 28);
		dateChooser.getDateEditor().setEnabled(false);
		contentPane.add(dateChooser);
		Date convert = Calendar.getInstance().getTime();
		dateChooser.setDate(Calendar.getInstance().getTime());
		dateChooser.setMaxSelectableDate(new Date(Long.MAX_VALUE));
		
		JLabel lblCateogry = new JLabel("Category");
		lblCateogry.setFont(fontsmall);
		lblCateogry.setBounds(18, 114, 81, 30);
		lblCateogry.setText(categorylbl);
		contentPane.add(lblCateogry);

		//comboBox with the following choices pre-set as requested by the user
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "อาหาร",
				"ค่าใช้จ่ายประจำเดือน", "ค่าเรียนพิเศษ", "เสื้อผ้าและอุปกรณ์",
				"บริจาค", "ค่าเดินทาง", "ค่ายาและรักษาพยาบาล", "อื่นๆ" }));
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		comboBox.setBounds(130, 114, 263, 31);
		contentPane.add(comboBox);

		//save button performs the following
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//validation if any of the required fields are blank
				if (txtName.getText() == null || txtValue.getText() == null
						|| dateChooser.getDate() == null) {
					JLabel dialog = new JLabel("Enter all fields โปรดใส่ข้อมูลทุกช่อง");
					dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));
					JOptionPane.showMessageDialog(null, dialog,
							"Error", JOptionPane.ERROR_MESSAGE);
				} else {

					//putting textbox, combobox and datechooser values into placeholder variables
					String name = txtName.getText();
					String VALUE = txtValue.getText();
					String notes = txtNotes.getText();
					Date date = dateChooser.getDate();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yy", new Locale("th", "TH"));
					String d = dateFormat.format(date);
					String category = comboBox.getSelectedItem().toString();

					//validation to check if any of the inputs contain a comma
					if (isDouble(VALUE) == false || txtName.getText().contains(",") || txtNotes.getText().contains(",")){
						JLabel dialog = new JLabel("Check that there are only numbers in VALUE field and don't use comma sign. \n โปรดใส่แต่หมายเลขในช่องมูลค่า และอย่าใส่เครื่องหมาย ','");
						dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));
						JOptionPane
								.showMessageDialog(
										null,
										dialog,
										"Error", JOptionPane.ERROR_MESSAGE);
					} else {

						SimpleDateFormat dateFormat1 = new SimpleDateFormat(
								"MM/yyyy", new Locale("th", "TH"));

						//creates new transaction object
						transaction newTransaction = new transaction(name,
								VALUE, notes, d, category);

						try {
							//program checks for the current date
							currentdate = dateFormat1.format(dateFormat.parse(newTransaction.DATE));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						
						currentcat = newTransaction.CATEGORY;
						currentvalue = newTransaction.VALUE;
						boolean dowriteagain = true;
						
						//adding the transaction object to the transactions linkedlist
						transactions.add(newTransaction);

						//traversing the categories linkedlist to see if category data has been created for the month
						//the user has inputed for the transaction
						for (int i = 1; i <= categories.listCount; i++) {
							
							category a = categories.get(i);
							//if the category data for that month already exists, the boolean VALUE = false
							if (a.month.equals(currentdate)) {
								dowriteagain = false;
							}
						}

						//if boolean VALUE is true then the new category data is entered into the data file
						if (dowriteagain == true) {
							String b = "อาหาร, 0.0, " + currentdate + "\n"
									+ "ค่าใช้จ่ายประจำเดือน, 0.0, "
									+ currentdate + "\n"
									+ "ค่าเรียนพิเศษ, 0.0, " + currentdate
									+ "\n" + "เสื้อผ้าและอุปกรณ์, 0.0, "
									+ currentdate + "\n" + "บริจาค, 0.0, "
									+ currentdate + "\n" + "ค่าเดินทาง, 0.0, "
									+ currentdate + "\n"
									+ "ค่ายาและรักษาพยาบาล, 0.0, "
									+ currentdate + "\n" + "อื่นๆ, 0.0, "
									+ currentdate + "\n";

							//writes to the data file
							try {
								Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName2, true), "UTF-8"));
								writer.write(b);
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}
						}

						//removes existing values from the categories linkedlist
						for (int i = categories.listCount; i >= 1; i--) {
							boolean a = categories.remove(i);

						}

						//reads from the data file again to gain access to new data
						try {

							BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName2), "UTF-8"));
							String line = in.readLine();
							String categoryinfo[];

							//traversing data file, creating category objects and adding to the categories linkedlist
							while (line != null) // continue until end of file
							{
								categoryinfo = line.split(", ", 3);
								category x = new category(categoryinfo[0],
										categoryinfo[1], categoryinfo[2]);

								categories.add(x);
								line = in.readLine();
							}
						} catch (IOException iox) {
							
						}

						//checks for the category object that matches the date for the current transaction
						for (int i = 1; i <= categories.listCount; i++) {
							category a = categories.get(i);

							if (a.NAME.equals(currentcat)
									&& a.month.equals(currentdate)) {
								System.out.println("YES");
								//adds the transaction VALUE to the category object VALUE
								a.VALUE = a.VALUE + currentvalue;
								//removes the old category object and adds the new category object
								categories.remove(i);
								categories.add(a, i);
							}
						}

						//for loop to traverse the categories linkedlist
						for (int i = 1; i <= categories.listCount; i++) {
							boolean x;

							if (i == 1) {
								x = false;

							} else {
								x = true;
							}

							
							Object c = categories.get(i);
							String b = c.toString();
							System.out.println(b);

							//try-catch to write the new data to the data file
							try {
								Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName2, x), "UTF-8"));;
								writer.write(b + "\n");
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}

						}

						//traverses the transactions linkedlist
						for (int i = 1; i <= transactions.listCount; i++) {

							boolean x;

							if (i == 1) {
								x = false;

							} else {
								x = true;
							}

							Object a = transactions.get(i);
							String b = a.toString();

							//try-catch to write to the new data file
							try {
								Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, x), "UTF-8"));;
								writer.write(b + "\n");
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}

						}

						//displays confirmation message to the user
						JLabel dialog = new JLabel("Data saved. ข้อมูลบันทึกเรียบร้อย");
						dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));
	

						JOptionPane.showMessageDialog(null, dialog, "Success",
								JOptionPane.INFORMATION_MESSAGE);

						//clears the text fields
						txtName.setText(null);
						txtNotes.setText(null);
						txtValue.setText(null);

					}

				}
			}
		});

	}

	//localization function to change descriptions and text language in this window
	public void locale() {
		
		//for English menu,  the following descriptions and buttons texts are used
		if (langint == 0) {
			loc = new Locale("en", "TH");
			datelbl = "Date";
			categorylbl = "Category";
			namelbl = "Name";
			valuelbl = "Value";
			noteslbl = "Notes";
			fontbig = new Font("Segoe UI", Font.PLAIN, 20);
			fontsmall = new Font("Segoe UI", Font.PLAIN, 16);
			fontdate = new Font("Segoe UI", Font.PLAIN, 14);

		}
		
		//for Thai menu, the following descriptions and buttons texts are used
		if (langint == 1) {
			loc = new Locale("th", "TH");
			datelbl = "วันที่";
			categorylbl = "ประเภท";
			namelbl = "ชื่อรายการ";
			valuelbl = "มูลค่า";
			noteslbl = "โน๊ต";
			fontbig = new Font("Sukhumvit Set", Font.PLAIN, 20);
			fontsmall = new Font("Sukhumvit Set", Font.PLAIN, 16);
			fontdate = new Font("Sukhumvit Set", Font.PLAIN, 14);

		}
	}

}
