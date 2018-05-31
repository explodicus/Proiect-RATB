package proiect_ratb;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Launcher {

	public static void main(String[] args) throws ClassNotFoundException, UnknownHostException, IOException {

		Socket socket = new Socket("localhost", 1342);
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		//Creeaza un vector de linii pentru combobox
		String s = (String) ois.readObject();
		String[] linii = s.split(" ");

		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		//Functie de inchidere
		WindowListener exitListener = new WindowAdapter() {

			public void windowClosing(WindowEvent w) {
				try {
					oos.writeObject(new Request(-1, "", "", -1, 0, -1, 5));
					ois.close();
					oos.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.exit(0);
			}
		};
		frame.addWindowListener(exitListener);

		//Dialog Emitere Card
		JDialog dialogCard = new JDialog(frame, "Emitere Card", true);
		JPanel panelCard = new JPanel();
		panelCard.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelCard.setLayout(new GridLayout(4, 2));
		panelCard.add(new JLabel("Nume"));
		JTextField textNume = new JTextField();
		panelCard.add(textNume);
		panelCard.add(new JLabel("Prenume"));
		JTextField textPrenume = new JTextField();
		panelCard.add(textPrenume);
		panelCard.add(new JLabel("Cod Card"));
		JTextField textCodE = new JTextField();
		panelCard.add(textCodE);
		JButton addC = new JButton("Salveaza");
		panelCard.add(addC);
		JButton cancelC = new JButton("Anuleaza");
		panelCard.add(cancelC);
		addC.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int ID = 0;
				try {
					ID = Integer.parseInt(textCodE.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Input gresit (ID-ul trebuie sa fie numar intreg)");
					return;
				}
				Request r = new Request(ID, textNume.getText(), textPrenume.getText(), -1, 0, -1, 1);
				try {
					oos.writeObject(r);
					String response = (String) ois.readObject();
					JOptionPane.showMessageDialog(frame, response);

				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}

				textCodE.setText("");
				textNume.setText("");
				textPrenume.setText("");

				dialogCard.setVisible(false);
				panelCard.revalidate();
				panelCard.repaint();
			}

		});
		cancelC.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textCodE.setText("");
				textNume.setText("");
				textPrenume.setText("");

				dialogCard.setVisible(false);
				panelCard.revalidate();
				panelCard.repaint();
			}

		});
		dialogCard.setBounds(200, 200, 280, 280);
		dialogCard.setContentPane(panelCard);

		//Dialog Incarcare Card
		JDialog dialogIncarcare = new JDialog(frame, "Incarcare Card", true);
		JPanel panelIncarcare = new JPanel();
		panelIncarcare.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelIncarcare.setLayout(new GridLayout(4, 2));// nr randuri si nr col
		panelIncarcare.add(new JLabel("Abonament"));
		JComboBox<String> comboAbonament = new JComboBox<String>(
				new String[] { "Lunar pe toate liniile", "Lunar pe o linie", "O zi pe toate liniile" });
		panelIncarcare.add(comboAbonament);
		panelIncarcare.add(new JLabel("Bani"));
		JTextField textBani = new JTextField();
		textBani.setText("0");
		panelIncarcare.add(textBani);
		panelIncarcare.add(new JLabel("Cod Card"));
		JTextField textCodI = new JTextField();
		panelIncarcare.add(textCodI);
		JButton incarca = new JButton("Salveaza");
		panelIncarcare.add(incarca);
		JButton cancelI = new JButton("Anuleaza");
		panelIncarcare.add(cancelI);
		incarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ID = 0;
				int bani = 0;
				try {
					ID = Integer.parseInt(textCodI.getText());
					if (textBani.getText() != "")
						bani = Integer.parseInt(textBani.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Input gresit (ID-ul si banii trebuie sa fie numare intregi)");
					return;
				}
				if (bani < 0) {
					JOptionPane.showMessageDialog(frame, "Input gresit (Banii trebuie sa fie >= 0)");
					return;
				}
				if (bani > 0) {
					Request r = new Request(ID, "", "", -1, bani, 2, 4);
					try {
						oos.writeObject(r);
						String response = (String) ois.readObject();
						JOptionPane.showMessageDialog(frame, response);
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else if (comboAbonament.getSelectedIndex() == 0) {
					Request r = new Request(ID, "", "", -1, 0, 1, 4);
					try {
						oos.writeObject(r);
						String response = (String) ois.readObject();
						JOptionPane.showMessageDialog(frame, response);
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else if (comboAbonament.getSelectedIndex() == 1) {
					String s = (String) JOptionPane.showInputDialog(frame, "Selecteaza Linia", "Select",
							JOptionPane.PLAIN_MESSAGE, null, linii, "601");
					Request r = new Request(ID, "", "", Integer.parseInt(s), 0, 1, 4);
					try {
						oos.writeObject(r);
						String response = (String) ois.readObject();
						JOptionPane.showMessageDialog(frame, response);
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else if (comboAbonament.getSelectedIndex() == 2) {
					Request r = new Request(ID, "", "", -1, 0, 0, 4);
					try {
						oos.writeObject(r);
						String response = (String) ois.readObject();
						JOptionPane.showMessageDialog(frame, response);
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				textBani.setText("");

				dialogIncarcare.setVisible(false);
				panelIncarcare.revalidate();
				panelIncarcare.repaint();
			}

		});
		cancelI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textBani.setText("");

				dialogIncarcare.setVisible(false);
				panelIncarcare.revalidate();
				panelIncarcare.repaint();
			}

		});
		dialogIncarcare.setBounds(200, 200, 280, 280);
		dialogIncarcare.setContentPane(panelIncarcare);

		//Dialog Validare Card
		JDialog dialogValidare = new JDialog(frame, "Validare Card", true);
		JPanel panelValidare = new JPanel();
		panelValidare.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelValidare.setLayout(new GridLayout(3, 2));// nr randuri si nr col
		panelValidare.add(new JLabel("ID Card"));
		JTextField textIdVal = new JTextField();
		panelValidare.add(textIdVal);
		panelValidare.add(new JLabel("Linia"));
		JComboBox<String> comboLiniaVal = new JComboBox<String>(linii);
		panelValidare.add(comboLiniaVal);
		JButton valideaza = new JButton("Valideaza");
		panelValidare.add(valideaza);
		JButton cancelVal = new JButton("Anuleaza");
		panelValidare.add(cancelVal);
		valideaza.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ID = 0;
				try {
					ID = Integer.parseInt(textIdVal.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Input gresit (ID-ul trebuie sa fie numar intreg)");
					return;
				}
				Request r = new Request(ID, "", "", Integer.parseInt((String) comboLiniaVal.getSelectedItem()), 0, -1,
						2);
				try {
					oos.writeObject(r);
					String response = (String) ois.readObject();
					JOptionPane.showMessageDialog(frame, response);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				textIdVal.setText("");

				dialogValidare.setVisible(false);
				panelValidare.revalidate();
				panelValidare.repaint();
			}

		});
		cancelVal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textIdVal.setText("");

				dialogValidare.setVisible(false);
				panelValidare.revalidate();
				panelValidare.repaint();
			}

		});
		dialogValidare.setBounds(200, 200, 280, 280);
		dialogValidare.setContentPane(panelValidare);

		JMenuBar menuBar = new JMenuBar();
		JMenu administrare = new JMenu("Administrare");
		menuBar.add(administrare);

		frame.setJMenuBar(menuBar);

		JMenuItem menuItem = new JMenuItem("Emitere Card", KeyEvent.VK_E);
		menuItem.addActionListener(event -> dialogCard.setVisible(true));
		administrare.add(menuItem);
		menuItem = new JMenuItem("Incarcare Card", KeyEvent.VK_I);
		menuItem.addActionListener(event -> dialogIncarcare.setVisible(true));
		administrare.add(menuItem);
		menuItem = new JMenuItem("Validare Card", KeyEvent.VK_V);
		menuItem.addActionListener(event -> dialogValidare.setVisible(true));
		administrare.add(menuItem);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(4, 2));
		panel.add(new JLabel("ID Card"));
		JTextField textIdVer = new JTextField();
		panel.add(textIdVer);
		panel.add(new JLabel("Linia"));
		JComboBox<String> comboLiniaVer = new JComboBox<String>(linii);
		panel.add(comboLiniaVer);
		JButton verifica = new JButton("Verifica");
		panel.add(verifica);
		JButton exit = new JButton("Iesire");
		panel.add(exit);
		verifica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ID = 0;
				try {
					ID = Integer.parseInt(textIdVer.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "Input gresit (ID-ul trebuie sa fie numar intreg)");
					return;
				}
				Request r = new Request(ID, "", "", Integer.parseInt((String) comboLiniaVer.getSelectedItem()), 0, -1,
						3);
				try {
					oos.writeObject(r);
					String response = (String) ois.readObject();
					JOptionPane.showMessageDialog(frame, response);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

		});
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					oos.writeObject(new Request(-1, "", "", -1, 0, -1, 5));
					ois.close();
					oos.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}

		});
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
