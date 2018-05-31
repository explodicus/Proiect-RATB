package proiect_ratb;

import java.io.Serializable;

public class Request implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int ID;
	private String nume;
	private String prenume;
	private int linia;
	private int bani;
	private int tip;    // 0 = incarcare pe zi 1 = incarcare pe luna 2 = cu bani
	private int reqTip; // 1 = newCard 2 = validare 3 = verificare 4 = incarcare 5 = terminare conexiune
	
	public Request(int ID, String nume, String prenume, int linia, int bani, int tip, int reqTip) {
		this.ID = ID;
		this.nume = nume;
		this.prenume = prenume;
		this.linia = linia;
		this.bani = bani;
		this.tip = tip;
		this.reqTip = reqTip;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public int getLinia() {
		return linia;
	}

	public void setLinia(int linia) {
		this.linia = linia;
	}

	public int getBani() {
		return bani;
	}

	public void setBani(int bani) {
		this.bani = bani;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public int getReqTip() {
		return reqTip;
	}

	public void setReqTip(int reqTip) {
		this.reqTip = reqTip;
	}

}
