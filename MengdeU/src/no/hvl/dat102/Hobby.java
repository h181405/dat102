package no.hvl.dat102;

public class Hobby {
	private String hobbyNavn;
	
	public Hobby(String hobby) {
		hobbyNavn = hobby;
	}
	@Override
	public String toString() {
		return hobbyNavn;
	}

	public String getHobbyNavn() {
		return hobbyNavn;
	}
	public void setHobbyNavn(String hobbyNavn) {
		this.hobbyNavn = hobbyNavn;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hobby hobbyDenAndre = (Hobby) obj;
		return (hobbyNavn.equals(hobbyDenAndre.getHobbyNavn()));
	}
}
