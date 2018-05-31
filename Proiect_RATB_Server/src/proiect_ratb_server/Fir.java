package proiect_ratb_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;

import proiect_ratb.Request;

public class Fir implements Runnable {
	Socket socket;
	private Connection conn = null;
	private Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

	public Fir() {
	}

	public Fir(Socket s) {
		socket = s;
	}

	public String newCard(int ID, String nume, String prenume) {
		try (PreparedStatement ps = conn.prepareStatement("insert into carduri values(?,?,?,0)")) {
			ps.setString(2, nume);
			ps.setString(3, prenume);
			ps.setInt(1, ID);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Adaugare nereusita";
		}
		return "Adaugare reusita";
	}

	public String validare(int ID, int linia) {
		try (PreparedStatement ps = conn.prepareStatement("select * from abonamente where id_card = ?")) {
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				GregorianCalendar calM = new GregorianCalendar();
				GregorianCalendar calD = new GregorianCalendar();
				calD.setTime(rs.getDate("data_inceput"));
				calM.setTime(rs.getDate("data_inceput"));
				calM.add(Calendar.MONTH, 1);
				calD.add(Calendar.HOUR, 24);
				Date dateM = new Date(calM.getTime().getTime());
				Date dateD = new Date(calD.getTime().getTime());
				if (rs.getInt("linia") == linia && currentDate.before(dateM))
					return "Card validat cu succes";
				else if (rs.getInt("linia") == 0 && rs.getInt("Tip") == 1 && currentDate.before(dateM))
					return "Card validat cu succes";
				else if (rs.getInt("linia") == 0 && currentDate.before(dateD))
					return "Card validat cu succes";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Validare nereusita";
		}
		try (PreparedStatement ps = conn.prepareStatement("select * from carduri where id = ?")) {
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("bani") > 0) {
				try (PreparedStatement ps1 = conn.prepareStatement("update carduri set bani = bani - 1 where id = ?")) {
					ps1.setInt(1, ID);
					ps1.executeUpdate();
					try (PreparedStatement ps2 = conn.prepareStatement("insert into validari values(?,?,?)")) {
						ps2.setInt(1, ID);
						ps2.setInt(2, linia);
						ps2.setDate(3, currentDate);
						ps2.execute();

					} catch (SQLException e) {
						e.printStackTrace();
						return "Validare nereusita";
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return "Validare nereusita";
				}
				return "Card Validat cu succes";
			} else
				return "Insuficienti Bani";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Validare nereusita";
		}
	}

	public String verificare(int ID, int linia) {
		try (PreparedStatement ps = conn.prepareStatement("select * from abonamente where id_card = ?")) {
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				GregorianCalendar calM = new GregorianCalendar();
				GregorianCalendar calD = new GregorianCalendar();
				calD.setTime(rs.getDate("data_inceput"));
				calM.setTime(rs.getDate("data_inceput"));
				calM.add(Calendar.MONTH, 1);
				calD.add(Calendar.HOUR, 24);
				Date dateM = new Date(calM.getTime().getTime());
				Date dateD = new Date(calD.getTime().getTime());
				if (rs.getInt("linia") == linia && currentDate.before(dateM))
					return "Card verificat cu succes";
				else if (rs.getInt("linia") == 0 && rs.getInt("Tip") == 1 && currentDate.before(dateM))
					return "Card verificat cu succes";
				else if (rs.getInt("linia") == 0 && currentDate.before(dateD))
					return "Card verificat cu succes";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Verificare nereusita";
		}
		try (PreparedStatement ps = conn.prepareStatement("select * from validari where id_card = ? and data = ?")) {
			ps.setInt(1, ID);
			ps.setDate(2, currentDate);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				return "Card verificat cu succes";
			return "Verificare Nereusita";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Verificare nereusita";
		}
	}

	public String incarcare(int ID, int tip, int bani, int linia) {
		if (tip == 2) {
			try (PreparedStatement ps = conn.prepareStatement("update carduri set bani = bani + ? where id = ?")) {
				ps.setInt(1, bani);
				ps.setInt(2, ID);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return "Incarcare nereusita";
			}
		} else if (tip == 0) {
			try (PreparedStatement ps = conn.prepareStatement("insert into abonamente values(?,?,?,null)")) {
				ps.setInt(1, ID);
				ps.setInt(2, 0);
				ps.setDate(3, currentDate);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return "Incarcare nereusita";
			}
		} else
			try (PreparedStatement ps = conn.prepareStatement("insert into abonamente values(?,?,?,?)")) {
				ps.setInt(1, ID);
				ps.setInt(2, 1);
				ps.setDate(3, currentDate);
				if (linia != -1)
					ps.setInt(4, linia);
				else
					ps.setNull(4, Types.INTEGER);
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return "Incarcare nereusita";
			}
		return "Incarcare reusita";
	}

	public boolean checkID(int ID) {
		try (PreparedStatement ps = conn.prepareStatement("select * from carduri where id = ?")) {
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	// Gutu Dan 10-9

	public void run() {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		final String DB_URL = "jdbc:mysql://localhost:3306/proiect_ratb";
		final String DB_USER = "root";
		final String DB_PASS = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		String linii = "";

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (PreparedStatement ps = conn.prepareStatement("select * from linii"); ResultSet rs = ps.executeQuery();) {
			rs.next();
			linii = "" + rs.getInt("id");
			while (rs.next()) {
				linii = linii + " " + rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			oos.writeObject(linii);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean wait = true;

		while (wait) {
			String response = "";
			Request r = null;
			try {
				r = (Request) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if (r.getReqTip() == 5)
				wait = false;
			else {
				if (checkID(r.getID()) && r.getReqTip() != 1) {
					switch (r.getReqTip()) {
					case 2:
						response = validare(r.getID(), r.getLinia());
						break;
					case 3:
						response = verificare(r.getID(), r.getLinia());
						break;
					case 4:
						response = incarcare(r.getID(), r.getTip(), r.getBani(), r.getLinia());
						break;
					}
					try {
						oos.writeObject(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (r.getReqTip() == 1) {
					response = newCard(r.getID(), r.getNume(), r.getPrenume());
					try {
						oos.writeObject(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					try {
						oos.writeObject("Date introduse gresit");
					} catch (IOException e) {
						e.printStackTrace();
					}
			}

		}

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {

		}

		try {
			ois.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
