package no.hvl.dat102.klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import no.hvl.dat102.Datakontakt;
import no.hvl.dat102.Hobby;
import no.hvl.dat102.Medlem;
import no.hvl.dat102.Tekstgrensesnitt;

public class DatakontaktKlient {
	
	public static Scanner reader = null;

	public static void main(String[] args) {
		
		reader = new Scanner(System.in);
		int antall = 0;
		Medlem medlem;
		String temp;
		boolean run = true;
		System.out.println("Skriv inn antall medlemmer:");
		int indeks = -1;
		Datakontakt arkiv = null;
		try {
			antall = reader.nextInt();
			arkiv = new Datakontakt(antall);
			System.out.println("skriv inn nummeret foran kommandoen du vil utføre:");
			System.out.println("1 - legg til medlem");
			System.out.println("2 - vis antall medlemmer i listen");
			System.out.println("3 - vis hobbyer fra medlem");
			System.out.println("4 - vis alle par");
			System.out.println("5 - tilbakestill par");
			System.out.println("6 - finn medlems indeks");
			System.out.println("7 - finn medlem som passer");
			System.out.println("8 - match resterende medlemmer");
			System.out.println("9 - vis alle par");
			
			System.out.println("-1 - for å avslutte");
			while(run) {
				System.out.println("skriv inn nummeret foran kommandoen du vil utføre:");
				switch(reader.nextLine()) {
				case "1":
					medlem = Tekstgrensesnitt.lesMedlem();
					arkiv.leggTilMedlem(medlem);
					break;
				case "2":
					System.out.println("antall medlemmer:");
					System.out.println(arkiv.getMedlemmer().length);
					break;
				case "3":
					System.out.println("skriv et nummer mellom 0 og " + (arkiv.getMedlemmer().length));
					indeks = reader.nextInt();
					medlem = arkiv.getMedlem(indeks);
					if(medlem != null)
						Tekstgrensesnitt.skrivHobbyListe(medlem);
					break;
				case "4":
					Tekstgrensesnitt.skrivParListe(arkiv);
					break;
				case "5":
					System.out.println("skriv navnet på medlemet du vil tilbakestille");
					temp = reader.nextLine();
					arkiv.tilbakestillStatusIndeks(temp);
					break;
				case "6":
					System.out.println("skriv navnet på medlemmet du vil ha indeks til");
					temp = reader.nextLine();
					System.out.println(arkiv.finnMedlemsIndeks(temp));
					break;
				case "7":
					System.out.println("skriv navnet på medlemmet");
					temp = reader.nextLine();
					System.out.print(arkiv.finnPartnerFor(temp));
					break;
				case "8":
					int partner;
					for(int j = 0; j<antall;j++) {
						partner = -1;
						medlem = arkiv.getMedlem(j);
						partner = arkiv.finnPartnerFor(medlem.getNavn());
						if(partner > j) {
							arkiv.partnerOpp(j, partner);
							partner = -1;
						}
					}
					break;
				case "9":
					Tekstgrensesnitt.skrivParListe(arkiv);
					break;
				case "-1":
					run = false;
					break;
				default:
					System.out.println("vennligst skriv et lovig tall");
				}
			}
		}	catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
		
	}
}
