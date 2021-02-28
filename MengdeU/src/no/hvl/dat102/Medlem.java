package no.hvl.dat102;

import no.hvl.dat102.mengde.adt.MengdeADT;
import no.hvl.dat102.mengde.kjedet.KjedetMengde;

public class Medlem {
	private String navn;
	private KjedetMengde<Hobby> hobbyer;
	private int statusIndeks;
	
	public Medlem(String navn) {
		this.navn = navn;
		hobbyer = new KjedetMengde<Hobby>();
		statusIndeks = -1;
	}
	public void leggTilHobby(Hobby hobby) {
		hobbyer.leggTil(hobby);
	}
	public boolean fjernHobby(Hobby hobby) {
		if(hobby == hobbyer.fjern(hobby))
				return true;
		return false;
	}
	public KjedetMengde<Hobby> getHobbyer(){
		return hobbyer;
	}
	
	public boolean passerTil(Medlem medlem2) {
		return hobbyer.equals(medlem2.getHobbyer());
	}
	
	public String getNavn() {
		return navn;
	}
	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public int getStatusIndeks() {
		return statusIndeks;
	}

	public void setStatusIndeks(int statusIndeks) {
		this.statusIndeks = statusIndeks;
	}
	
	public String toString() {
		String resultat;
		resultat = "<" + hobbyer.toString() + ">";
		return resultat;
	}
}
