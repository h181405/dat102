package no.hvl.dat102;

import java.util.Iterator;

import no.hvl.dat102.mengde.tabell.TabellMengde;

public class Datakontakt {
	private int antall;
	private Medlem[] medlemTab;
	
	public Datakontakt(int antall) {
		this.antall = 0;
		medlemTab = new Medlem[antall];
	}
	public void leggTilMedlem(Medlem person) {
		if(medlemTab.length <= antall) {
			utvidKapasitet();
		}
		
		medlemTab[antall] = person;
		antall++;
	}
	
	public int finnMedlemsIndeks(String mn) {
		int indeks = -1;
		for(int i = 0; i < antall; i++) {
			if(medlemTab[i].getNavn().matches(mn)) {
				indeks = i;
				break;
			}
		}
		return indeks;
	}
	
	
	public Medlem getMedlem(int indeks) {
		return medlemTab[indeks];
	}
	
	
	public int finnPartnerFor(String mn) {
		int indeks = finnMedlemsIndeks(mn);
		boolean funnet = false;
		
		
		for(int i = 0; i < antall; i++) {
			if(i != indeks) {
				if(medlemTab[i].getStatusIndeks() == -1) {
					if(medlemTab[i].passerTil(medlemTab[indeks])) {
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	public Medlem[] getMedlemmer() {
		return medlemTab;
	}
	
	public void partnerOpp(int m1, int m2) {
		medlemTab[m1].setStatusIndeks(m2);
		medlemTab[m2].setStatusIndeks(m1);
	}
	
	public int tilbakestillStatusIndeks(String mn) {
		int indeks = finnMedlemsIndeks(mn);
		int antallTilbakestilt = 0;
		
		for(int i = 0; i < antall; i++){
			if(medlemTab[i].getStatusIndeks()==indeks) {
				medlemTab[i].setStatusIndeks(-1);
				antallTilbakestilt++;
			}
		}
		
		return antallTilbakestilt;
	}
	
	private void utvidKapasitet() {
		Medlem[] hjelpetabell = new Medlem[medlemTab.length+5];
		for (int i = 0; i < medlemTab.length; i++) {
			hjelpetabell[i] = medlemTab[i];
		}
		medlemTab = hjelpetabell;
	}
}
