import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ScrisoareAnonima {

	public static int citesteNumarIntreg(String text) {
		Scanner scan = new Scanner(System.in);
		System.out.println(text);
		int numar = scan.nextInt();
		return numar;
	}

	public static int meniu() {
		System.out.println();
		System.out.println("|*********************MENIU*********************|");
		System.out.println("|                                               |");
		System.out.println("|1. Adauga o propozitie.                        |");
		System.out.println("|2. Verifica daca mesajul tau poate fi formulat.|");
		System.out.println("|3. Trimite mesajul.                            |");
		System.out.println("|0. Paraseste programul.                        |");
		System.out.println("|                                               |");
		System.out.println("*************************************************");
		return citesteNumarIntreg("Optiunea ta este: ");
	}

	public static boolean verificaString(String input) {
		String caractereNepermise = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
		String[] test = input.split("");
		for (int i = 0; i < test.length; i++) {
			if (caractereNepermise.contains(test[i])) {
				return false;
			}
		}
		return true;
	}

	public static String citestePropozitie(String text) {
		Scanner scan = new Scanner(System.in);
		System.out.println(text);
		String propozitie = scan.nextLine();
		if (verificaString(propozitie) == false) {
			System.out.println("Propozitia contine numere sau caractere speciale. Te rog sa reintroduci!");
			return citestePropozitie("Reintroduceti o propozitie: ");
		}
		return propozitie;
	}

	public static void adaugaInDictionar(String input, Map<Character, Integer> dictionar) {
		input = input.toLowerCase();
		input = input.replaceAll("\\s", "");
		for (int i = 0; i < input.length(); i++) {
			if (dictionar.containsKey(input.charAt(i))) {
				int count = dictionar.get(input.charAt(i));
				dictionar.put(input.charAt(i), count + 1);
			} else {
				dictionar.put(input.charAt(i), 1);
			}
		}
	}

	public static boolean verificaDisponibilitateLitere(String propozitieDeVerificat,
			Map<Character, Integer> dictionar) {
		propozitieDeVerificat = propozitieDeVerificat.toLowerCase();
		propozitieDeVerificat = propozitieDeVerificat.replaceAll("\\s", "");
		if (dictionar == null) {
			return false;
		} else {
			Map<Character, Integer> dictionarVerificari = new HashMap<Character, Integer>();
			dictionarVerificari.putAll(dictionar);
			for (int i = 0; i < propozitieDeVerificat.length(); i++) {
				if (dictionarVerificari.containsKey(propozitieDeVerificat.charAt(i))) {
					int count = dictionarVerificari.get(propozitieDeVerificat.charAt(i));
					if (count <= 0) {
						dictionarVerificari.remove(propozitieDeVerificat.charAt(i));
						return false;
					} else {
						dictionarVerificari.put(propozitieDeVerificat.charAt(i), count - 1);
					}
				} else {
					return false;
				}
			}
			return true;
		}
	}

	public static void trimiteMesaj(String propozitieDeTrimis, Map<Character, Integer> dictionar) {
		propozitieDeTrimis = propozitieDeTrimis.toLowerCase();
		propozitieDeTrimis = propozitieDeTrimis.replaceAll("\\s", "");
		for (int i = 0; i < propozitieDeTrimis.length(); i++) {
			int count = dictionar.get(propozitieDeTrimis.charAt(i));
			dictionar.put(propozitieDeTrimis.charAt(i), --count);
			if (count == 0) {
				dictionar.remove(propozitieDeTrimis.charAt(i));
			}
		}
	}

	public static void main(String[] args) {
		Map<Character, Integer> stocLitere = new HashMap<Character, Integer>();
		String propozitieDinRevista = null;
		String propozitieVerificata = null;
		int alegere = meniu();

		while (alegere != 0) {
			switch (alegere) {
			case 1:
				propozitieDinRevista = citestePropozitie("Introduceti propozitia din revista:");
				adaugaInDictionar(propozitieDinRevista, stocLitere);
				System.out.println(stocLitere);
				break;
			case 2:
				propozitieVerificata = citestePropozitie("Introduceti propozitia pe care doriti sa o verificati:");
				if (verificaDisponibilitateLitere(propozitieVerificata, stocLitere)) {
					System.out.println("Mesajul poate fi formulat.");
				} else {
					System.out.println("Mesajul nu poate fi formulat. Mai adauga propozitii.");
				}
				break;
			case 3:
				try {
					if (verificaDisponibilitateLitere(propozitieVerificata, stocLitere)) {
						trimiteMesaj(propozitieVerificata, stocLitere);
						System.out.println("Mesajul a fost trimis!");
						if (!stocLitere.isEmpty()) {
							System.out.println("Mai ai urmatoarele litere ramase: ");
							System.out.println(stocLitere);
						} else {
							System.out.println("Nu mai sunt alte litere disponibile");
						}
					} else {
						System.out.println("Hmm, nu ai toate literele necesare pentru a formula acest mesaj.");
					}
				} catch (Exception e) {
					System.out.println("Aceasta optiune nu este disponibila momentan");
				}
				break;
			default:
				System.out.println("Ati introdus o valoare invalida! Va rog reincercati!");
			}
			alegere = meniu();
		}
		System.out.println("Program incheiat.");

	}
}
