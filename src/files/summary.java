package files;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JComboBox;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JToolTip;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.FontUIResource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JLabel;

public class summary extends JFrame {

	private JPanel contentPane;
	private static JPanel panel = new JPanel();
	static String args[] = {};

	// Linked list variables
	static LinkedListCat categories = new LinkedListCat();
	static String fileName2 = "categories.txt";
	static double total = 0;
	static String totaltext = "";

	// window list components
	static DefaultListModel listModel = new DefaultListModel();
	static JList list = new JList(listModel);

	// String variables for localization
	static Locale loc = new Locale("en", "TH");
	static int langint;

	/**
	 * Launch the application.
	 * 
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException {

		UIManager.put("ToolTip.font", new FontUIResource("Leelawadee",
				Font.PLAIN, 12));

		File path = new File(ClassLoader.getSystemClassLoader()
				.getResource(".").toURI().getPath());
		File parent = new File((path).getParentFile().toURI().getPath());

		String decoded = path.toString();

		fileName2 = decoded + "/categories.txt";

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName2), "UTF8"));

			String line = in.readLine();
			String categoryInfo[];

			while (line != null) // continue until end of file
			{
				categoryInfo = line.split(", ", 3);
				category x = new category(categoryInfo[0], categoryInfo[1],
						categoryInfo[2]);
				categories.add(x);

				line = in.readLine();

			}
		} catch (IOException iox) {
			System.out.println("มึงโง่");

		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					summary frame = new summary();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		langint = mainForm.langint;

	}

	/**
	 * Create the frame.
	 */
	public summary() {

		// perform localization
		locale();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton button = new JButton("Main Menu");
		button.setBounds(31, 358, 117, 35);

		// action listener for mainmenu button
		button.addActionListener(new ActionListener() {
			// if button has been clicked
			public void actionPerformed(ActionEvent e) {

				// clears listbox
				listModel.removeAllElements();
				// reverse for loop from number of category entries until 0
				for (int i = categories.listCount; i >= 1; i--) {
					boolean a = categories.remove(i);

				}

				// clears the graph panel
				panel.removeAll();
				try {
					mainForm.main(args);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();

			}
		});
		contentPane.setLayout(null);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		contentPane.add(button);

		// window components
		JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.setBounds(31, 68, 137, 28);
		monthChooser.setFont(new Font("Sukhumvit Set", Font.PLAIN, 14));
		monthChooser.setLocale(loc);
		contentPane.add(monthChooser);

		JYearChooser yearChooser = new JYearChooser();
		yearChooser.getSpinner().setFont(new Font("Segoe UI", Font.PLAIN, 16));
		yearChooser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		yearChooser.setMinimum(2540);
		yearChooser.setBounds(170, 68, 57, 28);
		yearChooser.setFont(new Font("Leelawadee", Font.PLAIN, 14));
		// yearChooser set localization
		yearChooser.setLocale(new Locale("th"));
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", new Locale(
				"th", "TH"));
		int maxyear = Integer.parseInt(dateFormat.format(today));
		yearChooser.setValue(maxyear);
		yearChooser.setMaximum(maxyear);
		yearChooser.setFocusable(false);
		contentPane.add(yearChooser);

		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setFont(new Font("Sukhumvit Set", Font.PLAIN, 14));
		list.setEnabled(true);
		list.setVisible(true);
		list.setBounds(31, 108, 281, 236);
		contentPane.add(list);

		// adds the graph panel to the window frame
		panel.setBounds(324, 20, 350, 330);
		contentPane.add(panel);

		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//receiving the month from the user
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM",
						new Locale("en", "TH"));
				listModel.removeAllElements();
				total = 0;

				int b = monthChooser.getMonth();
				String month = "";

				switch (b) {
				case 0:
					month = "01";
					break;
				case 1:
					month = "02";
					break;
				case 2:
					month = "03";
					break;
				case 3:
					month = "04";
					break;
				case 4:
					month = "05";
					break;
				case 5:
					month = "06";
					break;
				case 6:
					month = "07";
					break;
				case 7:
					month = "08";
					break;
				case 8:
					month = "09";
					break;
				case 9:
					month = "10";
					break;
				case 10:
					month = "11";
					break;
				case 11:
					month = "12";
					break;
				}
				
				//getting the year from the user
				String year = String.valueOf(yearChooser.getValue());
				
				//forming the search criteria
				String criteria = month + "/" + year;
				
				//creating the data set
				DefaultPieDataset dataset = new DefaultPieDataset();

				//for loop searching the categories linkedlist for the criteria
				for (int i = 1; i <= categories.listCount; i++) {

			
					System.out.println(categories.get(i).month);
					System.out.println(criteria);
					if (categories.get(i).month.contains(criteria)) {

						String a = "";
						a = a + categories.get(i).NAME + ": ";

						NumberFormat currencyFormatter = NumberFormat
								.getCurrencyInstance(new Locale("th", "TH"));

						a = a
								+ currencyFormatter.format(categories.get(i).VALUE);
						total = total + categories.get(i).VALUE;

						if (categories.get(i).VALUE != 0) {
							dataset.setValue(categories.get(i).NAME,
									new Double(categories.get(i).VALUE));
						}

						listModel.addElement(a);

					}
				}

				NumberFormat currencyFormatter = NumberFormat
						.getCurrencyInstance(new Locale("th", "TH"));
				totaltext = "Total for " + month + "/" + year + ": "
						+ currencyFormatter.format(total);
				listModel.addElement(totaltext);

				//creates the chart using the data from the search
				JFreeChart chart = createChart(dataset, "Summary for " + month
						+ "/" + year);
				panel.removeAll();
				//adds the chart panel to the right hand side panel
				panel.add(createDemoPanel(chart));
				panel.updateUI();

			}
		});

		// lets the user view more detail when the mouse is hovered onto the
		// listbox
		list.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// no-op
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				JList l = (JList) e.getSource();
				ListModel m = l.getModel();
				int index = l.locationToIndex(e.getPoint());
				if (index > -1) {
					l.setToolTipText(m.getElementAt(index).toString());
				}
			}
		});
		
		
		//further window components
		btnView.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		btnView.setBounds(236, 64, 76, 35);
		contentPane.add(btnView);
		contentPane.getRootPane().setDefaultButton(btnView);

		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblSummary.setBounds(31, 20, 255, 32);
		contentPane.add(lblSummary);

	}

	// localization function
	public void locale() {
		if (langint == 0) {
			loc = new Locale("en", "US");

		}

		if (langint == 1) {
			loc = new Locale("th", "TH");

		}
	}

	// function to generate chart using JFreeChart library
	private static JFreeChart createChart(PieDataset dataset, String title) {
		NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance(new Locale("th", "TH"));

		JFreeChart chart = ChartFactory.createPieChart(title, // chart title
				dataset, // data
				true, // include legend
				true, false);

		// sets font of text to a Thai-compatible font
		chart.getLegend().setItemFont(new Font("Leelawadee", Font.PLAIN, 13));
		PiePlot pp = (PiePlot) chart.getPlot();
		pp.setLabelFont(new Font("Leelawadee", Font.PLAIN, 13));
		pp.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}"));
		chart.getTitle().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		return chart;
	}

	// creates the panel that contains the chart
	public static JPanel createDemoPanel(JFreeChart chart) {

		ChartPanel cp = new ChartPanel(chart);
		cp.setPreferredSize(new Dimension(350, 315));
		return cp;
	}
}
