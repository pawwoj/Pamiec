package ppp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

//a

public class Podpis extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -146801908957696881L;
	HTTPSConnection httpsConn = new HTTPSConnection();
	FTPConnection ftpClient = new FTPConnection();
	JPanel panEkranStartowy = new JPanel();
	JFrame fraOknoZObrazkiem = new JFrame("Dokument dla pacjenta");
	LabWczytanyObrazek2 labWczytanyObrazek = new LabWczytanyObrazek2();
	JTextField jTFimie = new JTextField();
	JTextField jTFnazwisko = new JTextField();
	JTextField jTFpesel = new JTextField();
	JTextField jTFkodKreskowy = new JTextField();
	JTextArea jTAwypisInformacji = new JTextArea("Info");
	JScrollPane jspScrollWypisInformacji = new JScrollPane(jTAwypisInformacji);
	JButton butWybierzObrazek = new JButton("Wybierz dokument");
	JButton butZapiszJakoPDF = new JButton("Zapisz dokument");
	JButton butWstawTlo = new JButton("t³o");
	JButton butResetWpisu = new JButton("reset");
	JButton butDokA = new JButton("dok A");
	JLabel labImie = new JLabel("Imiê:");
	JLabel labNazwisko = new JLabel("Nazwisko:");
	JLabel labPesel = new JLabel("PESEL:");
	JLabel labKodKreskowy = new JLabel("Kod kreskowy:");
	JLabel labCzas = new JLabel("");

	JLabel labGoraczka = new JLabel("Gor¹czka");
	JLabel labKatar = new JLabel("Katar");
	JLabel labKaszel = new JLabel("Kaszel");
	JLabel labDusznosci = new JLabel("Dusznoœci");
	JLabel labUtrataSmaku = new JLabel("Utrata Sm/W");
	JLabel labInne = new JLabel("Inne");
	JLabel labKontakt = new JLabel("Kontakt");

	JCheckBox jCBGoraczkaTak = new JCheckBox("TAK");
	JCheckBox jCBKatarTak = new JCheckBox("TAK");
	JCheckBox jCBKaszelTak = new JCheckBox("TAK");
	JCheckBox jCBDusznosciTak = new JCheckBox("TAK");
	JCheckBox jCBUtrataSmakuTak = new JCheckBox("TAK");
	JCheckBox jCBKontaktTak = new JCheckBox("TAK");

	JCheckBox jCBGoraczkaNie = new JCheckBox("NIE");
	JCheckBox jCBKatarNie = new JCheckBox("NIE");
	JCheckBox jCBKaszelNie = new JCheckBox("NIE");
	JCheckBox jCBDusznosciNie = new JCheckBox("NIE");
	JCheckBox jCBUtrataSmakuNie = new JCheckBox("NIE");
	JCheckBox jCBKontaktNie = new JCheckBox("NIE");

	JTextField jTFInne = new JTextField();

	int intOpuszczenie = 90;

	int czasDoAutozapisu = 60;
	int elapsedTime = 0;
	int seconds = czasDoAutozapisu;
	boolean tloAktywne = true;
	String dataPelna;
	String dataData;
	File filWybranyPlik;
	Image imageWybranyObrazek;
	String seconds_string = String.format("%02d", seconds);

	String[] tabLewaKodKreskowyBin = { "0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111",
			"0111011", "0110111", "0001011" };
	String[] tabPrawaKodKreskowyBin = { "1110010", "1100110", "1101100", "1000010", "1011100", "1001110", "1010000",
			"1000100", "1001000", "1110100" };
	String startStopKodKreskowyBin = "101";
	String srodekKodKreskowyBin = "01010";
	String kodKreskowyBin = "";
//	570, 220
	int wspKoduKreskowegoX = 520;
	int wspKoduKreskowegoY = 150;
	int wspKoduKreskowegoWysokosc = 25;
	int wspKoduKreskowegoGrubosc = 3;

	public static void main(String[] args) {
		new Podpis().setVisible(true);
	}

	public Podpis() {
		this.setTitle("Podpis dokumentu");
		this.setBounds(1100, 0, 415, intOpuszczenie + 475);
		initComponents();
		this.setDefaultCloseOperation(3);
	}

	public void initComponents() {
		this.getContentPane().add(panEkranStartowy);

		panEkranStartowy.setLayout(null);
		panEkranStartowy.setBackground(Color.WHITE);
		// panEkranStartowy.add(butWybierzObrazek);
		panEkranStartowy.add(butZapiszJakoPDF);
		panEkranStartowy.add(butResetWpisu);
		panEkranStartowy.add(butWstawTlo);
		panEkranStartowy.add(labImie);
		panEkranStartowy.add(labNazwisko);
		panEkranStartowy.add(labPesel);
		panEkranStartowy.add(labKodKreskowy);
		panEkranStartowy.add(butDokA);
		panEkranStartowy.add(labCzas);

		panEkranStartowy.add(labGoraczka);
		panEkranStartowy.add(labKatar);
		panEkranStartowy.add(labKaszel);
		panEkranStartowy.add(labDusznosci);
		panEkranStartowy.add(labUtrataSmaku);
		panEkranStartowy.add(labInne);
		panEkranStartowy.add(labKontakt);
//	panEkranStartowy.add(labTak);
//	panEkranStartowy.add(labNie);

		panEkranStartowy.add(jCBGoraczkaTak);
		panEkranStartowy.add(jCBKatarTak);
		panEkranStartowy.add(jCBKaszelTak);
		panEkranStartowy.add(jCBDusznosciTak);
		panEkranStartowy.add(jCBUtrataSmakuTak);
		panEkranStartowy.add(jCBKontaktTak);

		panEkranStartowy.add(jCBGoraczkaNie);
		panEkranStartowy.add(jCBKatarNie);
		panEkranStartowy.add(jCBKaszelNie);
		panEkranStartowy.add(jCBDusznosciNie);
		panEkranStartowy.add(jCBUtrataSmakuNie);
		panEkranStartowy.add(jCBKontaktNie);

		panEkranStartowy.add(jTFInne);

//	jCBGoraczkaTak
//	jCBKatarTak
//	jCBKaszelTak
//	jCBDusznosciTak
//	jCBUtrataSmakuTak
//	jCBKontaktTak
//	
//	jCBGoraczkaNie
//	jCBKatarNie
//	jCBKaszelNie
//	jCBDusznosciNie
//	jCBUtrataSmakuNie
//	jCBKontaktNie

		labGoraczka.setBounds(10, 200, 70, 30);
		labKatar.setBounds(10, 230, 70, 30);
		labKaszel.setBounds(10, 260, 70, 30);
		labDusznosci.setBounds(10, 290, 70, 30);
		labUtrataSmaku.setBounds(10, 320, 70, 30);
		labInne.setBounds(10, 350, 70, 30);
		labKontakt.setBounds(10, 380, 70, 30);

		jTFInne.setBounds(90, 350, 300, 30);

		jCBGoraczkaTak.setBounds(90, 200, 50, 30);
		jCBKatarTak.setBounds(90, 230, 50, 30);
		jCBKaszelTak.setBounds(90, 260, 50, 30);
		jCBDusznosciTak.setBounds(90, 290, 50, 30);
		jCBUtrataSmakuTak.setBounds(90, 320, 50, 30);
		jCBKontaktTak.setBounds(90, 380, 50, 30);

		jCBGoraczkaNie.setBounds(170, 200, 50, 30);
		jCBKatarNie.setBounds(170, 230, 50, 30);
		jCBKaszelNie.setBounds(170, 260, 50, 30);
		jCBDusznosciNie.setBounds(170, 290, 50, 30);
		jCBUtrataSmakuNie.setBounds(170, 320, 50, 30);
		jCBKontaktNie.setBounds(170, 380, 50, 30);

		butResetWpisu.setBounds(320, intOpuszczenie + 110, 70, 30);
		butWstawTlo.setBounds(320, intOpuszczenie + 150, 70, 30);
		// butWybierzObrazek.setBounds(10, 10, 185, 30);
		butDokA.setBounds(10, 10, 185, 30);
		butZapiszJakoPDF.setBounds(205, 10, 185, 30);

		labImie.setBounds(10, intOpuszczenie, 185, 20);
		labNazwisko.setBounds(205, intOpuszczenie, 185, 20);
		labPesel.setBounds(10, intOpuszczenie + 60, 185, 20);
		labKodKreskowy.setBounds(205, intOpuszczenie + 60, 185, 20);
		labCzas.setBounds(10, intOpuszczenie + -30, 395, 20);
		labCzas.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		labCzas.setForeground(Color.GREEN);
		// labCzas.setBackground(Color.RED);

		panEkranStartowy.add(jTFimie);
		panEkranStartowy.add(jTFnazwisko);
		panEkranStartowy.add(jTFpesel);
		panEkranStartowy.add(jTFkodKreskowy);
		panEkranStartowy.add(jspScrollWypisInformacji);

		jTAwypisInformacji.setLineWrap(true);

		jspScrollWypisInformacji.setBounds(10, intOpuszczenie + 330, 380, 100);

		jTFimie.setBounds(10, intOpuszczenie + 20, 185, 30);
		jTFimie.setToolTipText("Wpisz imiê pacjenta");

		jTFnazwisko.setBounds(205, intOpuszczenie + 20, 185, 30);
		jTFnazwisko.setToolTipText("Wpisz nazwisko pacjenta");

		jTFpesel.setBounds(10, intOpuszczenie + 80, 185, 30);
		jTFpesel.setToolTipText("Wpisz PESEL pacjenta");

		jTFkodKreskowy.setBounds(205, intOpuszczenie + 80, 185, 30);
		jTFkodKreskowy.setToolTipText("Wpisz numer kodu kreskowego");

		jTAwypisInformacji.setText("");
		jTAwypisInformacji.setEditable(false);
//	focus(jTFimie);
//	focus(jTFnazwisko);
//	focus(jTFpesel);

		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatPelny = DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss");
		DateTimeFormatter formatData = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		// System.out.println();
		dataPelna = localDate.format(formatPelny).toString();
		dataData = localDate.format(formatData).toString();

		wstawTlo();
		otworzLabelZObrazkiem();
		fraOknoZObrazkiem.add(labWczytanyObrazek);
		


		butZapiszJakoPDF.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				labWczytanyObrazek.zapisDokumentu(labWczytanyObrazek.getBuffImage(), ".jpg", 2);

			}
		});

		butResetWpisu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				resetRysika("klik: Reset");
			}
		});

		butWybierzObrazek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wczytajObrazek();
			}
		});

		butWstawTlo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// sprawdzCheckBox();
		//		 System.out.println(httpsConn.executeGet());
		//		 httpsConn.conTest1();
			//	String nazwaplikuNaFTP2 = jTFpesel.getText().toString() + "_" + jTFkodKreskowy.getText().toString() + "_" + dataPelna + ".jpg";
			//	System.out.println(nazwaplikuNaFTP2);
				wstawTlo();

			}

		});

		butDokA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sprawdzenieKoduKreskowego() && sprawdzeniePESEL()) {
					try {

						czasRestart();
						// imageWybranyObrazek =ImageIO.read(new File("C:\\graficzne\\ankieta.jpg"));
						imageWybranyObrazek = ImageIO.read(ResourceLoader.load("/ankieta.jpg"));
						kodKreskowyBin = wypelnijKodKreskowyBin();

						tloAktywne = false;

						labWczytanyObrazek.czysciArrayList();
						sprawdzCheckBox();
						otworzLabelZObrazkiem();
						fraOknoZObrazkiem.repaint();
						jTAwypisInformacji.append("\n" + dataPelna + ": Ankieta aktywna do wype³nienia");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d: " + e1);
						System.out.println("b³¹d: " + e1);
					}
				}
			}
		});

	}
	
	public void setjTAwypisInformacji(String text) {
		jTAwypisInformacji.append("\n" + dataPelna + ": " + text);
	}

	public void sprawdzCheckBox() {

		if (jCBGoraczkaTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(435, 358);
		if (jCBGoraczkaNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(650, 358);

		if (jCBKatarTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(435, 418);
		if (jCBKatarNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(650, 418);

		if (jCBKaszelTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(435, 477);
		if (jCBKaszelNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(650, 477);

		if (jCBDusznosciTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(435, 538);
		if (jCBDusznosciNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(650, 538);

		if (jCBUtrataSmakuTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(435, 595);
		if (jCBUtrataSmakuNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(650, 595);

		if (jCBKontaktTak.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(210, 870);
		if (jCBKontaktNie.isSelected())
			labWczytanyObrazek.wypelnianieOdCheckBox(580, 870);
	}

	public void reserCheckBox() {

		jCBGoraczkaTak.setSelected(false);
		jCBGoraczkaNie.setSelected(false);

		jCBKatarTak.setSelected(false);
		jCBKatarNie.setSelected(false);

		jCBKaszelTak.setSelected(false);
		jCBKaszelNie.setSelected(false);

		jCBDusznosciTak.setSelected(false);
		jCBDusznosciNie.setSelected(false);

		jCBUtrataSmakuTak.setSelected(false);
		jCBUtrataSmakuNie.setSelected(false);

		jCBKontaktTak.setSelected(false);
		jCBKontaktNie.setSelected(false);
	}

	public String wypelnijKodKreskowyBin() {
		String kodKBin = "";
		StringBuilder sB = new StringBuilder(startStopKodKreskowyBin);
		kodKBin = sB.toString();
//		System.out.println(kodKBin);

		for (int i = 0; i < 4; i++) {
			sB.append(tabLewaKodKreskowyBin[Character.getNumericValue(jTFkodKreskowy.getText().charAt(i))]);
			kodKBin = sB.toString();
			// System.out.println(kodKBin);
		}
		sB.append(srodekKodKreskowyBin);

		for (int j = 4; j < 8; j++) {
			sB.append(tabPrawaKodKreskowyBin[Character.getNumericValue(jTFkodKreskowy.getText().charAt(j))]);
			kodKBin = sB.toString();
			// System.out.println(kodKBin);
		}

		sB.append(startStopKodKreskowyBin);
		kodKBin = sB.toString();
		// System.out.println(kodKBin);

		return kodKBin;
	}

	static void focus(JTextField nazwa) {
		nazwa.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				nazwa.selectAll();
			}

			public void focusLost(FocusEvent e) {
			}
		});
	}

	public void resetRysika(String info) {
		czasRestart();
		labWczytanyObrazek.czysciArrayList();
		fraOknoZObrazkiem.repaint();

		jTAwypisInformacji.append("\n" + dataPelna + ": " + info);
		System.out.println(info);
	}

	public void wstawTlo() {

		try {

			// imageWybranyObrazek = ImageIO.read(new File("C:\\graficzne\\tlo.jpg"));
			imageWybranyObrazek = ImageIO.read(ResourceLoader.load("/tlo.jpg"));
			jTAwypisInformacji.append("\n" + dataPelna + ": t³o jest aktywne");
			tloAktywne = true;
			System.out.println("t³o jest aktywne");
			jTFimie.setText("");
			jTFnazwisko.setText("");
			jTFpesel.setText("");
			jTFkodKreskowy.setText("");
			jTFInne.setText("");
			reserCheckBox();
			labWczytanyObrazek.czysciArrayList();
			fraOknoZObrazkiem.repaint();
			czasStop();
			labCzas.setText("ANKIETA NIEAKTYWNA - T£O");

		} catch (IOException e2) {
			System.out.println("brak obrazka");
			jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d wstawTlo: " + e2);
			System.out.println("b³¹d wstawTlo: " + e2);
		}
	}

	public void wczytajObrazek() {

		JFileChooser wyborObrazka = new JFileChooser();
		wyborObrazka.setCurrentDirectory(new File("C:\\graficzne"));
		wyborObrazka.showOpenDialog(null);
		filWybranyPlik = wyborObrazka.getSelectedFile();
		if (filWybranyPlik != null) {
			try {
				imageWybranyObrazek = ImageIO.read(filWybranyPlik);
				jTAwypisInformacji.append("\n" + dataPelna + ": wczytano dokument: " + filWybranyPlik);
				tloAktywne = false;
			} catch (IOException e1) {
				System.out.println("b³¹d wczytajObrazek: " + e1);
				e1.printStackTrace();
				jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d wczytajObrazek: " + e1);
			}
			otworzLabelZObrazkiem();
			try {
				fraOknoZObrazkiem.add(labWczytanyObrazek);
			} catch (Exception e8) {
				System.out.println("b³¹d e8: " + e8);
				e8.printStackTrace();
				jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d wczytajObrazek e8: " + e8);
			}
			fraOknoZObrazkiem.repaint();
		}
	}

	public void otworzLabelZObrazkiem() {

		try {
			if (fraOknoZObrazkiem.isVisible() == false) {
				fraOknoZObrazkiem.setUndecorated(true);
				fraOknoZObrazkiem.setLocation(zwracaSzerokoscEkranu(), 0);
				fraOknoZObrazkiem.setExtendedState(JFrame.MAXIMIZED_BOTH);
				fraOknoZObrazkiem.setVisible(true);
			}

		} catch (Exception e0) {
			System.out.println("b³¹d otworzLabelZObrazkiem: " + e0);
			jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d otworzLabelZObrazkiem: " + e0);
			e0.printStackTrace();
		}
	}

	public int zwracaSzerokoscEkranu() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// System.out.println(screenSize.getWidth());
		// System.out.println(Toolkit.getDefaultToolkit().getScreenResolution());

		int intSzerokoscEkranuGlownego = 0;

		double doubleSzerokoscEkranuGlownego = screenSize.getWidth();

		double mnoznikSkaliEkranu = 1.00;
		int pikselaNaCale = Toolkit.getDefaultToolkit().getScreenResolution();

		if (pikselaNaCale == 120)
			mnoznikSkaliEkranu = 1.25;
		if (pikselaNaCale == 144)
			mnoznikSkaliEkranu = 1.50;
		if (pikselaNaCale == 168)
			mnoznikSkaliEkranu = 1.751;

		intSzerokoscEkranuGlownego = (int) (doubleSzerokoscEkranuGlownego * mnoznikSkaliEkranu);
		// System.out.println(intSzerokoscEkranuGlownego + " int");

		return intSzerokoscEkranuGlownego;
	}

	Timer timer = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			elapsedTime = elapsedTime + 1000;

			seconds = czasDoAutozapisu - (elapsedTime / 1000);
			seconds_string = String.format("%02d", seconds);

			labCzas.setText("ANKIETA AKTYWNA :" + seconds_string);
			labCzas.setForeground(Color.RED);
			if (seconds == 00 || seconds < 0) {
				czasStop();
				// labCzas.setForeground(Color.GREEN);
				if (labWczytanyObrazek.getBuffImage() == null) {
					wstawTlo();
					jTAwypisInformacji.append("\n" + dataPelna + ": Ankieta niewype³niona - brak zapisu");
					labCzas.setText("ANKIETA NIEAKTYWNA - NIEWYPE£NIONA");
				}
				if (labWczytanyObrazek.getBuffImage() != null) {
					labWczytanyObrazek.zapisDokumentu(labWczytanyObrazek.getBuffImage(), ".jpg", 1);
					jTAwypisInformacji.append("\n" + dataPelna + ": Zapis automatyczny");
					labCzas.setText("ANKIETA NIEAKTYWNA - ZAPISANA");
				}
			}
		}
	});

	void czasStart() {
		// labCzas.setBackground(Color.RED);

		timer.start();
	}

	void czasStop() {
		timer.stop();
		labCzas.setForeground(Color.GREEN);
//		labCzas.setText("ANKIETA NIEAKTYWNA");
		// labCzas.setBackground(Color.GREEN);
	}

	void czasRestart() {
		timer.stop();
		timer.start();
		elapsedTime = 0;
		seconds = czasDoAutozapisu;

		seconds_string = String.format("%02d", seconds);

		labCzas.setText("ANKIETA AKTYWNA :" + seconds_string);
	}

	public boolean sprawdzenieKoduKreskowego() {
		if (!czyInt(jTFkodKreskowy.getText())) {
			labCzas.setText("B³¹d kodu kreskowego - b³êdne znaki");
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d kodu kreskowego - b³êdne znaki");
			return false;
		}
		if (jTFkodKreskowy.getText().length() != 8) {
			labCzas.setText("B³¹d kodu kreskowego - iloœæ cyfr");
			// System.out.println("ostatnia: " + jTFkodKreskowy.getText().charAt(7));
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d kodu kreskowego - iloœæ cyfr");
			return false;
		} else if (sprawdzenieSumyKoduKreskowego() != Character.getNumericValue(jTFkodKreskowy.getText().charAt(7))) {
			// System.out.println("ostatnia: " +
			// Character.getNumericValue(jTFkodKreskowy.getText().charAt(7)));
			labCzas.setText("B³¹d kodu kreskowego - cyfry");
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d kodu kreskowego - cyfry");
			return false;
		}

		return true;
	}

	public int sprawdzenieSumyKoduKreskowego() {
		int sp = Character.getNumericValue(jTFkodKreskowy.getText().charAt(1))
				+ Character.getNumericValue(jTFkodKreskowy.getText().charAt(3))
				+ Character.getNumericValue(jTFkodKreskowy.getText().charAt(5));
		int snp = 3 * (Character.getNumericValue(jTFkodKreskowy.getText().charAt(0))
				+ Character.getNumericValue(jTFkodKreskowy.getText().charAt(2))
				+ Character.getNumericValue(jTFkodKreskowy.getText().charAt(4))
				+ Character.getNumericValue(jTFkodKreskowy.getText().charAt(6)));
		int suma = 10 - ((sp + snp) % 10);
		if (suma == 10) {
			suma = 0;
		}
//		System.out.println("suma kontrolana: " + suma);
		return suma;

	}

	public boolean sprawdzeniePESEL() {
		if (!czyInt(jTFpesel.getText())) {
			labCzas.setText("B³¹d PESEL - b³êdne znaki");
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d PESEL - b³êdne znaki");
			return false;
		} else if (jTFpesel.getText().length() != 11) {
			labCzas.setText("B³¹d PESEL - iloœæ cyfr");
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d PESEL - iloœæ cyfr");
			return false;
		} else if (!sprawdzenieSumyPESEL(jTFpesel.getText())) {
			labCzas.setText("B³¹d PESEL - cyfry");
			jTAwypisInformacji.append("\n" + dataPelna + ": B³¹d PESEL - cyfry");
			// System.out.println("suma pesel : " + sumaPesel);
			return false;
		}
		return true;
	}

	public boolean czyInt(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public boolean sprawdzenieSumyPESEL(String pesel) {
		int sumaPesel = Character.getNumericValue(pesel.charAt(0)) * 1 + Character.getNumericValue(pesel.charAt(1)) * 3
				+ Character.getNumericValue(pesel.charAt(2)) * 7 + Character.getNumericValue(pesel.charAt(3)) * 9
				+ Character.getNumericValue(pesel.charAt(4)) * 1 + Character.getNumericValue(pesel.charAt(5)) * 3
				+ Character.getNumericValue(pesel.charAt(6)) * 7 + Character.getNumericValue(pesel.charAt(7)) * 9
				+ Character.getNumericValue(pesel.charAt(8)) * 1 + Character.getNumericValue(pesel.charAt(9)) * 3;
//		System.out.println("suma pesel1 : " + sumaPesel);
		sumaPesel = sumaPesel % 10;
		// System.out.println("suma peselMod : " + sumaPesel);

		sumaPesel = 10 - sumaPesel;
		// System.out.println("suma pesel-10 : " + sumaPesel);
		if (sumaPesel == 10) {
			sumaPesel = 0;
		}
		// System.out.println("suma pesel : " + sumaPesel);
		// System.out.println("ostatnia pesel : " +
		// Character.getNumericValue(pesel.charAt(10)));
		// System.out.println("suma pesel : " + (sumaPesel ==
		// Character.getNumericValue(pesel.charAt(10))));

		return (sumaPesel == Character.getNumericValue(pesel.charAt(10)));

	}

	public class LabWczytanyObrazek2 extends JLabel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6186028188940826695L;
		private List<List<Point>> curves = new ArrayList<>();
		private List<List<Point>> odCheckBox = new ArrayList<>();

		public void czysciArrayList() {
			this.curves.clear();
			this.odCheckBox.clear();
		}
		String nazwaplikuNaFTP2 = "";
		BufferedImage buffImage;

		public BufferedImage getBuffImage() {
			return buffImage;
		}

		public void setBuffImage(BufferedImage img) {
			this.buffImage = img;
		}

		public LabWczytanyObrazek2() {
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					List<Point> newCurve = new ArrayList<Point>();
					newCurve.add(new Point(e.getX(), e.getY()));
					curves.add(newCurve);
				}
			});

			labWczytanyObrazek = this;
			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					curves.get(curves.size() - 1).add(new Point(e.getX(), e.getY()));
					repaint(0, 0, getWidth(), getHeight());
				}
			});
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (tloAktywne == false) {
						czasRestart();

						buffImage = new BufferedImage(labWczytanyObrazek.getWidth(), labWczytanyObrazek.getHeight(),
								BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = buffImage.createGraphics();
						labWczytanyObrazek.printAll(g2d);
						g2d.dispose();

						zapisDokumentu(buffImage, "robocze.jpg", 0);
						setBuffImage(buffImage);
					} else {
						System.out.println("T³o aktywne");
					}
				}
			});
		}

		public void wypelnianieOdCheckBox(int x, int y) {
			List<Point> punktOdCheckBox = new ArrayList<Point>();
			punktOdCheckBox.add(new Point(x, y));
			odCheckBox.add(punktOdCheckBox);
		}

		public void zapisDokumentu(BufferedImage bImg, String nazwaPliku, int temp0Automat1Przycisk2) {
			String jakiZapis = "";
			try {
				if (temp0Automat1Przycisk2 == 0) {
					ImageIO.write(bImg, "jpg", new File("C:\\podglad\\" + nazwaPliku));
					jakiZapis = "roboczy";
				} else if (temp0Automat1Przycisk2 == 1) {
					ImageIO.write(bImg, "jpg", new File("C:\\automat\\" + jTFpesel.getText() + "_"
							+ jTFkodKreskowy.getText() + "_" + dataPelna + " " + nazwaPliku));
					wstawTlo();
					jakiZapis = "auto60s";
					labCzas.setText("ANKIETA NIEAKTYWNA - ZAPISANA");

				} else if (temp0Automat1Przycisk2 == 2) {
					ImageIO.write(bImg, "jpg", new File("C:\\zapisane\\" + jTFpesel.getText() + "_"
							+ jTFkodKreskowy.getText() + "_" + dataPelna + " " + nazwaPliku));
						nazwaplikuNaFTP2 = jTFpesel.getText().toString() + "_" + jTFkodKreskowy.getText().toString() + "_" + dataPelna + ".jpg";
						System.out.println(nazwaplikuNaFTP2);
					ftpClient.zapisNaFTP(nazwaplikuNaFTP2);
					jTAwypisInformacji.append("\n" + dataPelna + ": " + ftpClient.getKomunikatFTP());
					jakiZapis = "przycisk";
					wstawTlo();

					labCzas.setText("ANKIETA NIEAKTYWNA - ZAPISANA");
				}
				jTAwypisInformacji.append("\n" + dataPelna + ": Plik zapisany :" + jakiZapis);
				System.out.println("Plik zapisany :" + jakiZapis);

			} catch (Exception i1) {
				jTAwypisInformacji.append("\n" + dataPelna + ": b³¹d zapisDokumentu: " + i1);
				System.out.println("b³¹d zapisDokumentu: " + i1);

				i1.printStackTrace();
				wstawTlo();

			}
			
			if (ftpClient.isZapisNaFTPUdany()) {
			//	String nazwaplikuNaFTP = "";
			//	nazwaplikuNaFTP = jTFpesel.getText().toString() + "_" + jTFkodKreskowy.getText().toString() + "_" + dataPelna + nazwaPliku;
				System.out.println("nazwa =" +  nazwaplikuNaFTP2);
				httpsConn.conTest1(nazwaplikuNaFTP2);
				jTAwypisInformacji.append("\n" + dataPelna + ": " + httpsConn.getKomunikatHTTPS());
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imageWybranyObrazek, 0, 0, null);

			if (tloAktywne == false) {
				g.setFont(new Font("TimesRoman", Font.BOLD, 24));
				g.drawString(jTFimie.getText() + " " + jTFnazwisko.getText(), 320, 120);
				g.drawString(jTFpesel.getText(), 170, 180);
				g.drawString(jTFkodKreskowy.getText(), 570, 200);
				g.drawString(jTFInne.getText(), 120, 667);

				g.drawString(dataData, 590, 1246);
				// kodKreskowyBin = wypelnijKodKreskowyBin();
				int grubosc = 0;
				for (int ix = 0; ix < kodKreskowyBin.length(); ix++) {
					int c = Character.getNumericValue(kodKreskowyBin.charAt(ix));
					for (int jx = 0; jx < wspKoduKreskowegoGrubosc; jx++) {
						if (c == 1) {
							g.drawLine(wspKoduKreskowegoX + grubosc, wspKoduKreskowegoY, wspKoduKreskowegoX + grubosc,
									wspKoduKreskowegoY + wspKoduKreskowegoWysokosc);
							grubosc++;
							// System.out.println("1");
						} else if (c == 0) {
							grubosc++;
							// System.out.println("0");
						}

					}
				}
				for (List<Point> curve : curves) {
					Point previousPoint = curve.get(0);
					for (Point point : curve) {
						g.drawLine(previousPoint.x, previousPoint.y, point.x, point.y);
						// System.out.println(point);
						previousPoint = point;
					}
				}

				for (List<Point> odCB : odCheckBox) {

					for (Point point : odCB) {
						g.drawLine(point.x - 105, point.y - 25, point.x + 105, point.y + 25);
						g.drawLine(point.x - 105, point.y + 25, point.x + 105, point.y - 25);

					}
				}

			}

		}

	}

}
