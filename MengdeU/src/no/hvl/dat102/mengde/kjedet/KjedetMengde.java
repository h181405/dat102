package no.hvl.dat102.mengde.kjedet;

//********************************************************************
// Kjedet implementasjon av en mengde. 
//********************************************************************
import java.util.*;

import no.hvl.dat102.exception.EmptyCollectionException;
import no.hvl.dat102.mengde.adt.*;

public class KjedetMengde<T> implements MengdeADT<T> {
	private static Random rand = new Random();
	private int antall; // antall elementer i mengden
	private LinearNode<T> start;

	/**
	 * Oppretter en tom mengde.
	 */
	public KjedetMengde() {
		antall = 0;
		start = null;
	}//

	@Override
	public void leggTil(T element) {
		if (!(inneholder(element))) {
			LinearNode<T> node = new LinearNode<T>(element);
			node.setNeste(start);
			start = node;
			antall++;
		}
	}

	@Override
	public void leggTilAlle(MengdeADT<T> m2) {
		Iterator<T> teller = m2.oppramser();
		while (teller.hasNext()) {
			leggTil(teller.next());
		}
	}

	@Override
	public T fjernTilfeldig() {
		if (erTom())
			throw new EmptyCollectionException("mengde");

		LinearNode<T> forgjenger, aktuell;
		T resultat = null;

		int valg = rand.nextInt(antall) + 1;
		if (valg == 1) {
			resultat = start.getElement();
			start = start.getNeste();
		} else {
			forgjenger = start;
			for (int nr = 2; nr < valg; nr++) {
				forgjenger = forgjenger.getNeste();
			}
			aktuell = forgjenger.getNeste();
			resultat = aktuell.getElement();
			forgjenger.setNeste(aktuell.getNeste());
		}
		antall--;

		return resultat;

	}//

	@Override
	public T fjern(T element) {

		if (erTom())
			throw new EmptyCollectionException("mengde");

		boolean funnet = false;
		LinearNode<T> forgjenger, aktuell;
		T resultat = null;
		
		//sjekker om elementet er første i kjeden
		if(start.getElement().equals(element)) {
			//sletter første element ved å sette start = neste element
			resultat = start.getElement();
			start = start.getNeste();
			//minker antall med 1
			antall--;
		}	else {
			//setter forgjenger = start node og aktuell = neste node
			forgjenger = start;
			aktuell = start.getNeste();
			//lager en for loop som sjekker hver node i mengden og stopper om vi finner noden som skal slettes
			for(int i = 1;i<antall && !funnet;i++) {
				//sjekker om noden er lik den vi søker etter 
				if(aktuell.getElement().equals(element))
					funnet = true;
				else {
					//henter neste node som skal sjekkes
					forgjenger = aktuell;
					aktuell = aktuell.getNeste();
				}
			}
			//om noden er funnet sletter vi den ved å sette forgjenger.neste = aktuell.getNeste og minker antall med 1
			if(funnet) {
				aktuell = forgjenger.getNeste();
				resultat = aktuell.getElement();
				forgjenger.setNeste(aktuell.getNeste());
				antall--;
			}
		}
		//returnerer true om en node ble slettet, false ellers
		return resultat;
	}//

	@Override
	public boolean inneholder(T element) {
		boolean funnet = false;
		LinearNode<T> aktuell = start;
		for (int soek = 0; soek < antall && !funnet; soek++) {
			if (aktuell.getElement().equals(element)) {
				funnet = true;
			} else {
				aktuell = aktuell.getNeste();
			}
		}
		return funnet;
	}

	@Override
	public boolean equals(Object ny) {
		//sjekker om mengden er identisk med denne mengden, hvis ja returnerer true
		if (this == ny) {
			return true;
		}
		//sjekker om mengden er tom, hvis ja returnerer false
		if (ny == null) {
			return false;
		}
		//sjekker om klassene er like, returnerer false hvis ikke
		if (getClass() != ny.getClass()) {
			return false;
		} else {	//om klassene er like, sjekker vi at de inneholder like mange noder, og returnerer false hvis ikke
			boolean likeMengder = true;
			MengdeADT<T> m2 = (KjedetMengde<T>) ny;
			if (this.antall != m2.antall()) {
				likeMengder = false;
			} else {	// om antallet er likt lager vi en iterator for å gå gjennom alle elementene i m2
						//og sjekker om det eksisterer i denne mengden, returnerer false om ikke alle elementene er funnet og true ellers.
				likeMengder = true;
				
				Iterator<T> teller = m2.oppramser();
				while(teller.hasNext()) {
					if(!this.inneholder(teller.next())) {
						likeMengder = false;
					}
				}
				
				return likeMengder;
			}
		}
		return false;

	}

	@Override
	public boolean erTom() {
		return antall == 0;
	}

	@Override
	public int antall() {
		return antall;
	}

	@Override
	public MengdeADT<T> union(MengdeADT<T> m2) {
		MengdeADT<T> begge = new KjedetMengde<T>();
		LinearNode<T> aktuell = start;
		T element = null;

		//setter inn alle elementer fra denne mengden til begge mengden.
		while(aktuell != null) {
			((KjedetMengde<T>)begge).settInn(aktuell.getElement());
			aktuell = aktuell.getNeste();
		}
		//lager en iterator som går gjennom alle elementene i m2 og setter de inn hvis de ikke allerede eksisterer i denne mengden
		Iterator<T> teller = m2.oppramser();
		while(teller.hasNext()) {
			element = teller.next();
			if(!this.inneholder(element)) {
				((KjedetMengde<T>)begge).settInn(element);
			}
		}
		//returnerer resultatet
		return begge;
	}//

	@Override
	public MengdeADT<T> snitt(MengdeADT<T> m2) {
		MengdeADT<T> snittM = new KjedetMengde<T>();
		T element;
		
		//bruker en iterator for å gå gjennom alle elementer i m2 sjekker om de eksisterer i denne mengden,
		//de som eksisterer blir lagt til i snittM mengden
		Iterator<T> teller = m2.oppramser();
		while(teller.hasNext()) {
			element = teller.next();
			if(this.inneholder(element)) {
				((KjedetMengde<T>)snittM).settInn(element);
			}
		}
		//returnerer snittM mengden
		return snittM;
	}

	@Override
	public MengdeADT<T> differens(MengdeADT<T> m2) {
		MengdeADT<T> differensM = new KjedetMengde<T>();
		T element;
		
		//bruker en iterator for å gå igjennom alle elementer i denne mengden og sammenligner de med m2 mengden
		//om elementene ikke eksisterer i m2 blir de lagt til i differensM mengden
		Iterator<T> teller = this.oppramser();
		while(teller.hasNext()) {
			element = teller.next();
			if(!m2.inneholder(element)) {
				((KjedetMengde<T>)differensM).settInn(element);
			}
		}
		//returnerer differensM mengden
		return differensM;
	}

	@Override
	public boolean undermengde(MengdeADT<T> m2) {
		boolean erUnderMengde = true;
		
		//bruker en iterator for å gå gjennom alle elementer i m2
		//sjekker at denne mengden inneholder alle elementer i m2 og setter erUnderMengde lik false hvis ikke
		Iterator<T> teller = m2.oppramser();
		while(teller.hasNext()) {
			if(!this.inneholder(teller.next()))
				erUnderMengde = false;
		}
		//returnerer erUnderMengde
		return erUnderMengde;
	}

	@Override
	public Iterator<T> oppramser() {
		return new KjedetIterator<T>(start);
	}

	private void settInn(T element) {
		LinearNode<T> nyNode = new LinearNode<T>(element);
		nyNode.setNeste(start);
		start = nyNode;
		antall++;
	}
	
	//returnerer mengden som String
	public String toString() {
		String resultat = "";
		LinearNode<T> aktuell = start;
		while(aktuell != null) {
			if(aktuell != start)
				resultat += ", ";
			resultat += aktuell.getElement().toString();
			aktuell = aktuell.getNeste();
		}
		return resultat;
	}

}// class
