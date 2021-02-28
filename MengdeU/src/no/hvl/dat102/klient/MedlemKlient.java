package no.hvl.dat102.klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import no.hvl.dat102.Hobby;
import no.hvl.dat102.Medlem;
import no.hvl.dat102.Tekstgrensesnitt;

public class MedlemKlient {
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("velg antall medlemmer som skal lages:");
		try {
			Medlem medlem = new Medlem("ola Normann");
			String hobby = null;
			do {
				if(hobby != null)
					medlem.leggTilHobby(new Hobby(hobby));
				System.out.println("skriv en hobby, avslutt med zzz");
				hobby = reader.readLine();
			} while (!hobby.equals("zzz"));
			
			Tekstgrensesnitt.skrivHobbyListe(medlem);
			
			
			
		} catch (NumberFormatException e) {
			System.out.println("ulovlig tall");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
