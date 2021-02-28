package no.hvl.dat102;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tekstgrensesnitt {

	public static Medlem lesMedlem() {
		Medlem m = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Skriv inn navn på medlem:");

		try {
			m = new Medlem(reader.readLine());
			String hobby = null;
			do {
				if (hobby != null)
					m.leggTilHobby(new Hobby(hobby));
				System.out.println("skriv en hobby, avslutt med zzz");
				hobby = reader.readLine();
			} while (!hobby.equals("zzz"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}

	public static void skrivHobbyListe(Medlem medlem) {
		System.out.println("Alle hobbyene:");
		System.out.println(medlem.toString());
	}

	public static void skrivParListe(Datakontakt arkiv) {
		System.out.println("PARNAVN \t\t HOBBYER");
		Medlem[] medlemmer = arkiv.getMedlemmer();
		int partner = -1;
		int total = 0;
		
		for(int i = 0; i < medlemmer.length; i++) {
			if(medlemmer[i]!=null) {
				partner = medlemmer[i].getStatusIndeks();
				if(partner > i) {
					System.out.print(medlemmer[i].getNavn() + " og " + medlemmer[partner].getNavn());
					System.out.println("\t\t" + medlemmer[i].toString());
					total++;
				}
			}
		}
		System.out.println("------------------------");
		System.out.println("Antall par funnet: " + total);
		
	}
}
