package no.hvl.dat102.mengde.tabell;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import no.hvl.dat102.exception.EmptyCollectionException;
import no.hvl.dat102.mengde.adt.MengdeADT;
import no.hvl.dat102.mengde.kjedet.KjedetMengde;

public class TabellMengde<T> implements MengdeADT<T> {
	// ADT-en Mengde implementert som tabell
	//
	private final static Random tilf = new Random();
	private final static int STDK = 100;
	private int antall;
	private T[] tab;

	public TabellMengde() {
		this(STDK);
	}

	public TabellMengde(int start) {
		antall = 0;
		tab = (T[]) (new Object[start]);
	}

	@Override
	public int antall() {
		return antall;
	}

	@Override
	public boolean erTom() {
		return (antall == 0);
	}

	@Override
	public void leggTil(T element) {
		if (!inneholder(element)) {
			if (antall == tab.length) {
				utvidKapasitet();
			}
			tab[antall] = element;
			antall++;
		}
	}

	private void utvidKapasitet() {
		T[] hjelpetabell = (T[]) (new Object[2 * tab.length]);
		for (int i = 0; i < tab.length; i++) {
			hjelpetabell[i] = tab[i];
		}
		tab = hjelpetabell;
	}

	@Override
	public T fjernTilfeldig() {
		if (erTom())
			throw new EmptyCollectionException("mengde");

		T svar = null;
		int indeks = tilf.nextInt(antall);
		svar = tab[indeks];
		tab[indeks] = tab[antall - 1];
		antall--;

		return svar;
	}

	@Override
	public T fjern(T element) {
		
		if (erTom())
			throw new EmptyCollectionException("mengde");

		boolean funnet = false;
		T svar = null;
		
		int index = -1;
		
		//lager en while loop som enter går gjennom alle elementer i tab, eller til funnet er lik true.
		while(index < antall && !funnet) {
			//sjekker om elementet i tab[index] er lik element,
			//om den er lik sittes funnet = true og minker antall med 1
			if(tab[index].equals(element)) {
				funnet = true;
				antall--;
			} else {
				//øker index med 1 hver gang while loopen kjøres og ikke elementet er funnet.
				index++;
			}
		}
		//setter elementet som skal slettes lik siste elementet i mengden.
		//setter så siste elementet lik null.
		if(funnet) {
			tab[index] = tab[antall];	//skriver tab[antall] pga vi har allerede minket antallet med 1
			tab[antall] = null;			//ellers måtte vi ha skrevet tab[antall - 1]
		}
		//returnerer den nye mengden
		return svar;
	}

	@Override
	public boolean inneholder(T element) {
		boolean funnet = false;
		for (int i = 0; (i < antall) && (!funnet); i++) {
			if (tab[i].equals(element)) {
				funnet = true;
			}
		}
		return (funnet);
	}
	
	/*
	 * Når vi overkjører (override) equals- meteoden er det anbefalt at vi også
	 * overkjører hascode-metoden da en del biblioterker burker hascode sammen med
	 * equals. Vi kommer tilbake til forklaring og bruk av hascode senere i faget.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + antall;
		result = prime * result + Arrays.deepHashCode(tab);
		return result;
	}

	@Override
	public boolean equals(Object m2) {
		
		boolean likeMengder = true;
		T element;
		//sjekker om mengden er identisk med denne mengden, hvis ja returnerer true
		if (this == m2) {
			return true;
		}
		//sjekker om mengden er tom, hvis ja returnerer false
		if (m2 == null) {
			return false;
		}
		//sjekker om klassene er like, returnerer false hvis ikke
		if (getClass() != m2.getClass()) {
			return false;
		} else {	//om klassene er like, sjekker vi at de inneholder like mange elementer, og returnerer false hvis ikke
			MengdeADT<T> mengde2 = (TabellMengde<T>) m2;
			if (this.antall != mengde2.antall()) {
				likeMengder = false;
			} else {	// om antallet er likt lager vi en iterator for å gå gjennom alle elementene i mengde2
						//og sjekker om det eksisterer i denne mengden, returnerer false om ikke alle elementene er funnet og true ellers.
				likeMengder = true;
				
				Iterator<T> teller = mengde2.oppramser();
				while(teller.hasNext()) {
					if(!this.inneholder(teller.next())) {
						likeMengder = false;
					}
				}
			}
		}
		//returnerer likeMengder
		return likeMengder;
			
	}

	@Override
	public void leggTilAlle(MengdeADT<T> m2) {
		Iterator<T> teller = m2.oppramser();
		while (teller.hasNext())
			leggTil(teller.next());
	}

	/*
	 * Denne versjonen av unionen er lite effekktiv
	 * 
	 * @Override public MengdeADT<T> union(MengdeADT<T> m2) { TabellMengde<T> begge
	 * = new TabellMengde<T>(); for (int i = 0; i < antall; i++) {
	 * begge.leggTil(tab[i]); } Iterator<T> teller = m2.oppramser();
	 * 
	 * while (teller.hasNext()) { begge.leggTil(teller.next()); } return
	 * (MengdeADT<T>)begge; }
	 */
	@Override

	public MengdeADT<T> union(MengdeADT<T> m2) {
		//TODO
		MengdeADT<T> begge = new TabellMengde<T>();
		T element = null;
		
		//setter inn alle elementer fra denne mengden til begge mengden.
		for(int i=0;i<antall;i++) {
			((TabellMengde<T>) begge).settInn(tab[i]);
		}
		//lager en iterator som går gjennom alle elementene i m2 og setter de inn hvis de ikke allerede eksisterer i denne mengden
		Iterator<T> teller = m2.oppramser();
		while (teller.hasNext()) {
			element = teller.next();
			if(!this.inneholder(element))
				((TabellMengde<T>) begge).settInn(element);
		}
		
		//returnerer mengden begge
		return begge;
	}//

	@Override
	public MengdeADT<T> snitt(MengdeADT<T> m2) {
		MengdeADT<T> snittM = new TabellMengde<T>();
		T element = null;
		
		//bruker en iterator for å gå gjennom alle elementer i m2 sjekker om de eksisterer i denne mengden,
		//de som eksisterer blir lagt til i snittM mengden
		Iterator<T> teller = m2.oppramser();
		while(teller.hasNext()) {
			element = teller.next();
			if(this.inneholder(element)) {
				((TabellMengde<T>) snittM).settInn(element);
			}
		}
		//returnerer mengden snittM
		return snittM;
	}

	@Override
	public MengdeADT<T> differens(MengdeADT<T> m2) {
		//TODO
		MengdeADT<T> differensM = new TabellMengde<T>();
		T element;
		
		//bruker en iterator for å gå igjennom alle elementer i denne mengden og sammenligner de med m2 mengden
		//om elementene ikke eksisterer i m2 blir de lagt til i differensM mengden
		Iterator<T> teller = this.oppramser();
		while(teller.hasNext()) {
			element = teller.next();
			if(!m2.inneholder(element)) {
				((TabellMengde<T>) differensM).settInn(element);
			}
		}
		//returnerer differensM mengden
		return differensM;
	}

	@Override
	public boolean undermengde(MengdeADT<T> m2) {
		//TODO
		boolean erUnderMengde = true;
		//bruker en iterator for å gå gjennom alle elementer i m2
		//sjekker at denne mengden inneholder alle elementer i m2 og setter erUnderMengde lik false hvis ikke
		Iterator<T> teller = m2.oppramser();
		while(teller.hasNext()) {
			if(!this.inneholder(teller.next())) {
				erUnderMengde = false;
			}
		}
		//returnerer erUnderMengde mengden
		return erUnderMengde;
	}

	@Override
	public Iterator<T> oppramser() {
		return new TabellIterator<T>(tab, antall);
	}

	private void settInn(T element) {
		if (antall == tab.length) {
			utvidKapasitet();
		}
		tab[antall] = element;
		antall++;
	}
	//returnerer mengden som String
	public String toString() {
		String resultat = "";
		for(int i = 0; i < antall; i++) {
			resultat += tab[i].toString() + "\t";
		}
		return resultat;
	}

}// class
