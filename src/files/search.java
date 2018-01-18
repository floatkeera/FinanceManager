package files;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.toedter.calendar.JDateChooser;

import java.util.*;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class search extends JFrame {

	public static String[] args = {};

	// Linked list variables
	static LinkedList transactions = new LinkedList();
	static LinkedListCat categories = new LinkedListCat();
	static String fileName = "transactions.txt";
	static String fileName2 = "categories.txt";

	// Misc variables
	static boolean dateOrName = true;
	static int edited;

	// variables for window components
	private JPanel contentPane;
	static DefaultListModel listModel = new DefaultListModel();
	static JList list = new JList(listModel);
	static DefaultListModel listModel1 = new DefaultListModel();
	static JList list1 = new JList(listModel1);
	private JTextField txtName;
	private JTextField txtValue;
	private JTextField txtSearchName;

	// String variables for localization
	static Locale loc = new Locale("en", "TH");
	public static String searchdate;
	public static String searchkeyword;
	public static String datelbl;
	public static String categorylbl;
	public static String namelbl;
	public static String valuelbl;
	public static String noteslbl;
	public static Font fontbig;
	public static Font fontsmall;
	public static Font fontdate;
	public static int langint;

	/**
	 * 
	 * Launch the application.
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException {

		// obtaining data file path in which JAR is located in
		File path = new File(ClassLoader.getSystemClassLoader()
				.getResource(".").toURI().getPath());
		File parent = new File((path).getParentFile().toURI().getPath());
		String decoded = path.toString();
		fileName = decoded + "/transactions.txt";
		fileName2 = decoded + "/categories.txt";

		// try-catch for reading from transaction data file
		dateOrName = true;
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), "UTF-8"));

			String line = in.readLine();
			String transactionInfo[];

			// traversing data file, creating transaction objects and adding to
			// the transaction linkedlist
			while (line != null) // continue until end of file
			{
				transactionInfo = line.split(", ", 5);
				transaction x = new transaction(transactionInfo[0],
						transactionInfo[1], transactionInfo[2],
						transactionInfo[3], transactionInfo[4]);
				transactions.add(x);
				System.out.println(transactions.listCount);
				line = in.readLine();

			}
		} catch (IOException iox) {
			System.out.println("มึงโง่");

		}

		// try-catch for reading from categories data file

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName2), "UTF-8"));

			String line = in.readLine();
			String categoryInfo[];

			// traversing data file, creating category objects and adding to the
			// categories linkedlist
			while (line != null) // continue until end of file
			{
				categoryInfo = line.split(", ", 3);
				category x = new category(categoryInfo[0], categoryInfo[1],
						categoryInfo[2]);
				categories.add(x);
				System.out.println(categories.listCount);
				line = in.readLine();

			}
		} catch (IOException iox) {
			System.out.println("มึงโง่");

		}

		// default language is English
		langint = 0;
		// setting the language indicator to the same as that read from the
		// settings data file in the main menu
		langint = mainForm.langint;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					search frame = new search();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the frame.
	 */
	public search() {

		// performs localization
		locale();
		UIManager.put("ToolTip.font", new FontUIResource("Leelawadee",
				Font.PLAIN, 12));

		// sets frames and declares frame object components
		// declaration of labels, and making them display the localized
		// descriptions
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblSearch.setBounds(16, 6, 255, 25);
		contentPane.add(lblSearch);

		JButton btnMainMenu = new JButton("Main Menu");

		// action listener for mainmenu button
		btnMainMenu.addActionListener(new ActionListener() {
			// if button has been clicked
			public void actionPerformed(ActionEvent e) {

				// reverse for loop from the number of transactions until 0
				for (int i = transactions.listCount; i >= 0; i--) {
					// transaction is removed from linked list
					boolean a = transactions.remove(i);

				}

				// reverse for loop from number of category entries until 0
				for (int i = categories.listCount; i >= 0; i--) {
					// remove entry from the linked list
					boolean a = categories.remove(i);

				}

				try {
					mainForm.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				listModel.removeAllElements();
				listModel1.removeAllElements();
				dispose();

			}
		});
		btnMainMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnMainMenu.setBounds(42, 375, 117, 35);
		contentPane.add(btnMainMenu);

		JButton btnSearch = new JButton("Search");

		btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSearch.setBounds(308, 111, 117, 35);
		contentPane.add(btnSearch);

		// declares new dateChooser object
		JDateChooser dateChooser = new JDateChooser();
		// sets the location of the object and adds it to the panel
		dateChooser.setBounds(185, 43, 240, 28);
		contentPane.add(dateChooser);
		// sets the locale of the object to the one the user chooses
		dateChooser.setLocale(loc);
		// sets the date of the object to today's date
		dateChooser.setDate(Calendar.getInstance().getTime());
		// sets font to Leelawadee - a Thai-compatible font
		dateChooser.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		// disables the text editor for the object
		dateChooser.getDateEditor().setEnabled(false);
		// sets the max date of the object to the max VALUE
		dateChooser.setMaxSelectableDate(new Date(Long.MAX_VALUE));

		// setting a scrollable multi-line notes field
		list.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setEnabled(false);
		listScroller.setBounds(16, 150, 409, 200);
		list.setEnabled(false);
		contentPane.add(listScroller);

		JButton btnEdit = new JButton("View/Edit");
		btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnEdit.setBounds(165, 375, 117, 35);
		contentPane.add(btnEdit);
		btnEdit.setEnabled(false);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnDelete.setBounds(287, 375, 117, 35);
		contentPane.add(btnDelete);
		btnDelete.setEnabled(false);

		// setting right pane for displaying show/edit window pane
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(437, 6, 356, 404);
		contentPane.add(panel);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(fontsmall);
		lblName.setBounds(18, 129, 81, 30);
		lblName.setText(namelbl);
		panel.add(lblName);

		JLabel lblValue = new JLabel("Value");
		lblValue.setFont(fontsmall);
		lblValue.setBounds(18, 171, 81, 30);
		lblValue.setText(valuelbl);
		panel.add(lblValue);

		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setFont(fontsmall);
		lblNotes.setBounds(18, 213, 47, 30);
		lblNotes.setText(noteslbl);
		panel.add(lblNotes);

		txtName = new JTextField();
		txtName.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(101, 131, 234, 28);
		panel.add(txtName);

		txtValue = new JTextField();
		txtValue.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		txtValue.setColumns(10);
		txtValue.setBounds(101, 173, 234, 28);
		panel.add(txtValue);

		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnSave.setBounds(218, 324, 117, 35);
		panel.add(btnSave);

		JLabel lblEdit = new JLabel("View/Edit");
		lblEdit.setHorizontalAlignment(SwingConstants.LEFT);
		lblEdit.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblEdit.setBounds(18, 0, 255, 25);
		panel.add(lblEdit);

		JTextArea txtNotes = new JTextArea();
		txtNotes.setWrapStyleWord(true);
		txtNotes.setFont(new Font("Leelawadee", Font.PLAIN, 13));
		txtNotes.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(txtNotes);
		jsp.setBounds(101, 213, 234, 70);
		panel.add(jsp);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(fontsmall);
		lblCategory.setBounds(18, 86, 71, 30);
		lblCategory.setText(categorylbl);
		panel.add(lblCategory);

		// combobox with preset values of categories, as demanded by the user
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "อาหาร",
				"ค่าใช้จ่ายประจำเดือน", "ค่าเรียนพิเศษ", "เสื้อผ้าและอุปกรณ์",
				"บริจาค", "ค่าเดินทาง", "ค่ายาและรักษาพยาบาล", "อื่นๆ" }));
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		comboBox.setBounds(101, 90, 234, 27);
		panel.add(comboBox);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(fontsmall);
		lblDate.setBounds(18, 44, 47, 30);
		lblDate.setText(datelbl);
		panel.add(lblDate);

		// dateChooser follows localization
		JDateChooser dateChooser1 = new JDateChooser();
		dateChooser1.getDateEditor().setEnabled(false);
		dateChooser1.setLocale(loc);
		dateChooser1.setFont(new Font("Leelawadee", Font.PLAIN, 16));
		dateChooser1.setBounds(101, 44, 234, 28);
		dateChooser1.setMaxSelectableDate(new Date(Long.MAX_VALUE));
		panel.add(dateChooser1);

		// hidden listbox to display linkedlist indexes of search results
		list1.setEnabled(false);
		list1.setVisible(false);
		list1.setBounds(16, 338, 148, 44);
		contentPane.add(list1);

		// function to see if the user has clicked on a search result
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				btnEdit.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		});

		JRadioButton rdbtndate = new JRadioButton("Search by date");
		rdbtndate.setSelected(true);
		rdbtndate.setFont(fontsmall);
		rdbtndate.setBounds(6, 48, 141, 23);
		rdbtndate.setText(searchdate);
		contentPane.add(rdbtndate);

		// radio button tooltip
		JRadioButton rdbtnName = new JRadioButton("Search by Keyword");
		rdbtnName.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				JRadioButton b = (JRadioButton) e.getSource();
				if (langint == 0) {
					b.setToolTipText("You can search for all items using a keyword.");
				} else {
					b.setToolTipText("คุณสามารถค้นหารายการทั้งหมดโดยใช้ keyword.");
				}

			}
		});

		rdbtnName.setFont(fontsmall);
		rdbtnName.setBounds(6, 83, 165, 23);
		rdbtnName.setText(searchkeyword);
		contentPane.add(rdbtnName);

		txtSearchName = new JTextField();
		txtSearchName.setFont(new Font("Leelawadee", Font.PLAIN, 16));
		txtSearchName.setBounds(185, 77, 240, 28);
		contentPane.add(txtSearchName);
		txtSearchName.setColumns(10);

		JLabel txtResultsFound = new JLabel("");
		txtResultsFound.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtResultsFound.setBounds(16, 111, 200, 30);
		contentPane.add(txtResultsFound);
		txtSearchName.enable(false);

		panel.setVisible(false);
		panel.enable(false);

		// if the search by date transaction is selected, the following will be
		// performed
		rdbtndate.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// clears all existing results
					listModel.removeAllElements();
					listModel1.removeAllElements();
					// deselects the search by date radio button
					rdbtnName.setSelected(false);
					dateChooser.setEnabled(true);
					dateChooser.getDateEditor().setEnabled(false);
					txtSearchName.enable(false);
					txtSearchName.setText(null);
					dateOrName = true;
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					txtResultsFound.setText("");
				}

			}
		});

		// if the search by name transaction is selected, the following will be
		// performed
		rdbtnName.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// clears all existing results
					listModel.removeAllElements();
					listModel1.removeAllElements();
					// deselects the search by date radio button
					rdbtndate.setSelected(false);
					txtSearchName.enable();
					dateChooser.setEnabled(false);
					dateOrName = false;
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
					txtResultsFound.setText("");
				}

			}
		});

		// when the search button is cicked
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtName.setText(null);
				txtValue.setText(null);
				txtNotes.setText(null);
				listModel.removeAllElements();
				listModel1.removeAllElements();
				int howmany = 0;
				boolean x = true;

				// new date object by using VALUE from dateChooser
				Date date = dateChooser.getDate();
				// declares new dateFormat based on Buddhist Era Thai format
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy",
						new Locale("th", "TH"));
				// Formats the date VALUE and puts it into string
				// this is the search criteria for the date
				String d = dateFormat.format(date);

				// gets search criteria for name
				String name = txtSearchName.getText().toLowerCase();

				// traverses the transactions linkedlist
				for (int i = 1; i <= transactions.listCount; i++) {

					// creates a temporary object for each entry int he
					// linkedlist
					transaction a = transactions.get(i);
					double money = a.VALUE;
					NumberFormat currencyFormatter = NumberFormat
							.getCurrencyInstance(new Locale("th", "TH"));

					// if it is searching by date
					if (dateOrName == true) {
						// if the date matches, add it to the listbox
						if (a.DATE.contains(d) == true) {
							listModel.addElement(a.CATEGORY + ": " + a.NAME
									+ " on " + a.DATE + " @ "
									+ currencyFormatter.format(money));
							listModel1.addElement(i);

							x = false;
							list.setEnabled(true);
							listScroller.setEnabled(true);

							howmany++;
						}
					}

					// if it is searching by name
					if (dateOrName == false) {
						// if the name, category, or notes matches the universal
						// search criteria entered,
						// add result to the linked list
						if (a.NAME.toLowerCase().contains(name) == true
								|| a.CATEGORY.contains(name) == true
								|| a.NOTES.toLowerCase().contains(name)) {
							listModel.addElement(a.CATEGORY + ": " + a.NAME
									+ " on " + a.DATE + " @ "
									+ currencyFormatter.format(money));
							listModel1.addElement(i);

							x = false;
							list.setEnabled(true);
							listScroller.setEnabled(true);

							howmany++;

						}

					}
				}

				// if no results have been found then a message will be
				// displayed
				if (x == true) {

					// dialog to show message that no transactions found based
					// on criteria given
					JLabel dialog = new JLabel(
							"No transactions found. ไม่พบรายการ");
					// sets font to Thai-compatible font
					dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));
					// shows dialog with the message above and error message
					// styling
					JOptionPane.showMessageDialog(null, dialog, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				txtResultsFound.setText("Found " + howmany + " transactions");

			}

		});

		// when the edit button is clicked
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// displays the right hand side panel
				if (list.isSelectionEmpty() == false) {
					panel.setVisible(true);
					panel.enable(true);
				}

				// gets the index in the linkedlist of the selected result
				int a = list.getSelectedIndex();
				int b = Integer.parseInt(listModel1.getElementAt(a).toString());
				edited = b;

				// creates a new transaction object of the selected search
				// result
				transaction test = transactions.get(b);
				txtName.setText(test.NAME);
				double money = test.VALUE;
				DecimalFormat formatter = new DecimalFormat("#0.00");
				txtValue.setText(String.valueOf(formatter.format(money)));
				txtNotes.setText(test.NOTES);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy",
						new Locale("th", "TH"));
				try {
					Date infoDate = (Date) dateFormat.parse(test.DATE);
					dateChooser1.setDate(infoDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				comboBox.setSelectedItem(test.CATEGORY);

			}
		});

		//when the delete button is clicked
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean z;

				try {
					//tries to ask user if they confirm the deletion

					JLabel dialog1 = new JLabel("Confirm delete? ยีนยันการลบ?");
					dialog1.setFont(new Font("Leelawadee", Font.PLAIN, 14));
					int result = JOptionPane.showConfirmDialog(
							(Component) null, dialog1, "Confirmation",
							JOptionPane.OK_CANCEL_OPTION);
					//if the user is confirmed

					if (result == 0) {

						// gets the index at which the user selects
						int x = list.getSelectedIndex();
						// uses the VALUE from x and parses it into an integer
						// to get the VALUE of the index the transaction exists
						// in the linkedlist
						int y = Integer.parseInt(listModel1.getElementAt(x)
								.toString());

						//creates a temporary transaction object
						SimpleDateFormat dateFormat1 = new SimpleDateFormat(
								"MM/yyyy", new Locale("th", "TH"));
						transaction sample = transactions.get(y);
						String currentcat = sample.CATEGORY;
						double currentvalue = sample.VALUE;
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"dd/MM/yy", new Locale("th", "TH"));
						Date currentd;
						currentd = dateFormat.parse(sample.DATE);
						String currentdate = dateFormat1.format(currentd);

						//traversing the categories linkedlist
						//for the category of this transaction to be deleted
						for (int i = 1; i <= categories.listCount; i++) {
							//creates temporary category object
							category a = categories.get(i);
							//if the category matches the one of the transaction to be deleted
							if (a.NAME.equals(currentcat)
									&& a.month.equals(currentdate)) {
								//the VALUE will be decreased
								a.VALUE = a.VALUE - currentvalue;
								//removes old object, and replaces it
								categories.remove(i);
								categories.add(a, i);
							}
						}
						
						//traverses the categories linkedlist once again to write to data file
						for (int i = 1; i <= categories.listCount; i++) {
							if (i == 1) {
								z = false;

							} else {
								z = true;
							}

							Object c = categories.get(i);
							String b = c.toString();
							System.out.println(b);

							try {
								Writer writer = new BufferedWriter(
										new OutputStreamWriter(
												new FileOutputStream(fileName2,
														z), "UTF-8"));
								writer.write(b + "\n");
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}
						}

						// for transactions linkedlist

						//removes the transaction to be deleted
						transactions.remove(y);
					

						//if there are no more transactions, writes a blank line to the data file
						if (transactions.listCount == 0) {
							try {
								Writer writer = new BufferedWriter(
										new OutputStreamWriter(
												new FileOutputStream(fileName,
														false), "UTF-8"));
								writer.write("");
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}
						}

						//if not blank, traverses the transactions linkedlist to write to the data file with the
						//desired transaction deleted
						for (int d = 1; d <= transactions.listCount; d++) {

							if (d == 1) {
								z = false;

							} else {
								z = true;
							}

							Object a = transactions.get(d);
							String b = a.toString();
							System.out.println(b);

							try {
								Writer writer = new BufferedWriter(
										new OutputStreamWriter(
												new FileOutputStream(fileName,
														z), "UTF-8"));
								writer.write(b + "\n");
								writer.close();

							} catch (IOException iox) {
								System.out.println("error");
							}

						}

						//sets the text fields to null
						txtName.setText(null);
						txtValue.setText(null);
						txtNotes.setText(null);

						//removes all search results
						listModel.removeAllElements();
						listModel1.removeAllElements();
						boolean abd = true;
						Date date = dateChooser.getDate();
						String d = dateFormat.format(date);
						String name = txtSearchName.getText().toLowerCase();

						//refreshes the search box
						for (int j = 1; j <= transactions.listCount; j++) {
							transaction e1 = transactions.get(j);

							NumberFormat currencyFormatter = NumberFormat
									.getCurrencyInstance(new Locale("th", "TH"));

							if (dateOrName == true) {
								if (e1.DATE.contains(d) == true) {
									listModel.addElement(e1.CATEGORY
											+ ": "
											+ e1.NAME
											+ " on "
											+ e1.DATE
											+ " @ "
											+ currencyFormatter
													.format(e1.VALUE));
									listModel1.addElement(j);

									abd = false;

									list.setEnabled(true);
									listScroller.setEnabled(true);

								}
							}

							if (dateOrName == false) {
								if (e1.NAME.toLowerCase().contains(name) == true) {
									listModel.addElement(e1.CATEGORY
											+ ": "
											+ e1.NAME
											+ " on "
											+ e1.DATE
											+ " @ "
											+ currencyFormatter
													.format(e1.VALUE));
									listModel1.addElement(j);

									abd = false;
									list.setEnabled(true);
									listScroller.setEnabled(true);
								}
							}

							txtResultsFound.setText("");

						}

					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		);
		
		//action for the save button for editing being performed
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//asks for confirmation from the user
				JLabel dialog = new JLabel("Save Edits? บันทักการเปลี่ยนแปลง?");
				dialog.setFont(new Font("Leelawadee", Font.PLAIN, 14));

				int result = JOptionPane.showConfirmDialog((Component) null,
						dialog, "alert", JOptionPane.OK_CANCEL_OPTION);

				//if the user confirms
				if (result == 0) {

					//checks if the text fields are blank
					if (txtName.getText() == null || txtValue.getText() == null) {
						JLabel dialog1 = new JLabel(
								"Enter all fields. โปรดใส่ข้อมูลให้ครบถ้วน");
						dialog1.setFont(new Font("Leelawadee", Font.PLAIN, 14));

						JOptionPane.showMessageDialog(null, dialog1, "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						//checks if the VALUE fields are indeed of a double type
						//or if any of the text fields contain a comma
						if (isDouble(txtValue.getText()) == false
								|| txtName.getText().contains(",")
								|| txtNotes.getText().contains(",")) {
							JLabel dialog1 = new JLabel(
									"Check that there are only numbers in VALUE field and don't use comma sign. โปรดใส่แต่หมายเลขในช่องมูลค่า และอย่าใส่เครื่องหมาย ','");
							dialog1.setFont(new Font("Leelawadee", Font.PLAIN,
									14));

							JOptionPane.showMessageDialog(null, dialog1,
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {

							//date format for transaction
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"dd/MM/yy", new Locale("th", "TH"));
							//date format for categories
							SimpleDateFormat dateFormat1 = new SimpleDateFormat(
									"MM/yyyy", new Locale("th", "TH"));
							//creates temporary transaction of the unedited transaction
							transaction sample = transactions.get(edited);

							
							//getting previous date to edit the categories data file
							String previousdate = null;
							try {
								Date sampledate = dateFormat.parse(sample.DATE);
								previousdate = dateFormat1.format(sampledate);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//getting the previous values to edit the categories data file
							String currentcat = sample.CATEGORY;
							double currentvalue = sample.VALUE;

							//removing the old transaction from the linkedlist
							transactions.remove(edited);

							//getting new values
							String name = txtName.getText();
							String VALUE = txtValue.getText();
							System.out.println(VALUE);
							String notes = txtNotes.getText();
							Date date = dateChooser1.getDate();
							String d = dateFormat.format(date);
							String currentdate = dateFormat1.format(date);
							String category = comboBox.getSelectedItem()
									.toString();
							boolean dowriteagain = true;

							//creating new transaction from these new values
							transaction temp = new transaction(name, VALUE,
									notes, d, category);
							transactions.add(temp, edited);

							//traversing categories linkedlist and see if there are categories in the data file
							//for the new date
							for (int i = 1; i <= categories.listCount; i++) {
								category a = categories.get(i);

								if (a.month.equals(currentdate)) {

									dowriteagain = false;

								}
							}
							
							
							//if categories are not in the data file
							//new categories are written to the data file
							if (dowriteagain == true) {
								String b = "อาหาร, 0.0, " + currentdate + "\n"
										+ "ค่าใช้จ่ายประจำเดือน, 0.0, "
										+ currentdate + "\n"
										+ "ค่าเรียนพิเศษ, 0.0, " + currentdate
										+ "\n" + "เสื้อผ้าและอุปกรณ์, 0.0, "
										+ currentdate + "\n" + "บริจาค, 0.0, "
										+ currentdate + "\n"
										+ "ค่าเดินทาง, 0.0, " + currentdate
										+ "\n" + "ค่ายาและรักษาพยาบาล, 0.0, "
										+ currentdate + "\n" + "อื่นๆ, 0.0, "
										+ currentdate + "\n";

								try {
									Writer writer = new BufferedWriter(
											new OutputStreamWriter(
													new FileOutputStream(
															fileName2, true),
													"UTF-8"));
									writer.write(b);
									writer.close();

								} catch (IOException iox) {
									System.out.println("error");
								}
							}

							//removes all existing category objects from the categories linkedlist
							for (int i = categories.listCount; i >= 1; i--) {
								boolean a = categories.remove(i);
							}

							//reads from the categories data file with the new data
							try {
								BufferedReader in = new BufferedReader(
										new InputStreamReader(
												new FileInputStream(fileName2),
												"UTF-8"));

								String line = in.readLine();
								String categoryinfo[];

								while (line != null) // continue until end of
														// file
								{
									categoryinfo = line.split(", ", 3);
									category x = new category(categoryinfo[0],
											categoryinfo[1], categoryinfo[2]);

									categories.add(x);
									line = in.readLine();
								}
							} catch (IOException iox) {
								
							}

							//traverses the categories linkedlist with the new values
							
							for (int i = 1; i <= categories.listCount; i++) {
								category a = categories.get(i);
								System.out.println(a.NAME + currentcat);

								//if the category and the date of the old transaction matches
								//the VALUE of the old transaction will be deducted
								if (a.NAME.equals(currentcat)
										&& a.month.equals(previousdate)) {
									System.out.println("YES");
									a.VALUE = a.VALUE - currentvalue;
									categories.remove(i);
									categories.add(a, i);
								}

								//if the category and the date of the new transaction matches
								//the VALUE of the new transaction will be added
								if (a.NAME.equals(temp.CATEGORY)
										&& a.month.equals(currentdate)) {
									System.out.println("YES");
									a.VALUE = a.VALUE + temp.VALUE;
									;
									categories.remove(i);
									categories.add(a, i);
								}
							}

							
							//traverses the categories linkedlist and writes it to the transactions datafile
							for (int i = 1; i <= categories.listCount; i++) {
								boolean z;

								if (i == 1) {
									z = false;

								} else {
									z = true;
								}

								Object c = categories.get(i);
								String b = c.toString();
								System.out.println(b);

								//uses unicode UTF-8 encoding
								try {
									Writer writer = new BufferedWriter(
											new OutputStreamWriter(
													new FileOutputStream(
															fileName2, z),
													"UTF-8"));
									writer.write(b + "\n");
									writer.close();

								} catch (IOException iox) {
									System.out.println("error");
								}

							}

							//traverses the transactions linkedlist and writes to transaction data file
							for (int i = 1; i <= transactions.listCount; i++) {
								boolean z;

								if (i == 1) {
									z = false;

								} else {
									z = true;
								}

								Object a = transactions.get(i);
								String b = a.toString();
								System.out.println(b);

								try {
									Writer writer = new BufferedWriter(
											new OutputStreamWriter(
													new FileOutputStream(
															fileName, z),
													"UTF-8"));
									writer.write(b + "\n");
									writer.close();

								} catch (IOException iox) {
									System.out.println("error");
								}

							}
							
							
							//shows dialogs to confirm
							JLabel dialog2 = new JLabel(
									"Data saved. บันทึกข้อมูลเรียบร้อย");
							dialog2.setFont(new Font("Leelawadee", Font.PLAIN,
									14));

							JOptionPane.showMessageDialog(null, dialog2,
									"Success", JOptionPane.INFORMATION_MESSAGE);

						}

					}
				}
			}
		});

		contentPane.getRootPane().setDefaultButton(btnSearch);

	}

	public void locale() {
		// langint obtained from the setting stored in the User data file
		// if langint is 0, set the String values of labels to English
		if (langint == 0) {
			// sets loc, the locale variable to English U.S.
			loc = new Locale("en", "US");
			searchdate = "Search by date";
			searchkeyword = "Universal search";
			categorylbl = "Category";
			datelbl = "Date";
			namelbl = "Name";
			valuelbl = "Value";
			noteslbl = "Notes";
			// sets fonts to Segoe UI
			fontbig = new Font("Segoe UI", Font.PLAIN, 20);
			fontsmall = new Font("Segoe UI", Font.PLAIN, 16);
			fontdate = new Font("Segoe UI", Font.PLAIN, 14);
		}

		// if langint is 1, set the String values of labels to Thai
		if (langint == 1) {
			// set the locale object to Thai
			loc = new Locale("th", "TH");
			searchdate = "ค้นหาตามวันที่";
			searchkeyword = "Universal search";
			datelbl = "วันที่";
			categorylbl = "ประเภท";
			namelbl = "ชื่อรายการ";
			valuelbl = "มูลค่า";
			noteslbl = "โน๊ต";
			// sets fonts to Thai-compatible fonts
			fontbig = new Font("Sukhumvit Set", Font.PLAIN, 20);
			fontsmall = new Font("Sukhumvit Set", Font.PLAIN, 16);
			fontdate = new Font("Sukhumvit Set", Font.PLAIN, 14);

		}
	}

	public boolean isDouble(String a) {
		try {
			Double.parseDouble(a);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
