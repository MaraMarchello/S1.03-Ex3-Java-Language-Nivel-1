package tascaS103Ex3;

import java.io.*;
import java.util.*;

public class GeographyGame {
	private static HashMap<String, String> countryCapitals = new HashMap<>();
	private static final int TOTAL_QUESTIONS = 10;

	public static void main(String[] args) {
		loadCountries();
		playGame();
	}

	private static void loadCountries() {
		try (BufferedReader reader = new BufferedReader(new FileReader("countries.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					countryCapitals.put(parts[0].trim(), parts[1].trim());
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading countries file: " + e.getMessage());
			System.exit(1);
		}
	}

	private static void playGame() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Please enter your name: ");
			String playerName = scanner.nextLine();

			List<String> countries = new ArrayList<>(countryCapitals.keySet());
			Random random = new Random();
			int score = 0;
			Set<String> usedCountries = new HashSet<>();

			for (int i = 0; i < TOTAL_QUESTIONS && countries.size() > usedCountries.size(); i++) {

				String country;
				do {
					country = countries.get(random.nextInt(countries.size()));
				} while (usedCountries.contains(country));
				usedCountries.add(country);

				System.out.printf("\nWhat is the capital of %s? ", country);
				String userAnswer = scanner.nextLine().trim();

				String correctCapital = countryCapitals.get(country);
				if (userAnswer.equalsIgnoreCase(correctCapital)) {
					System.out.println("Correct!");
					score++;
				} else {
					System.out.printf("Wrong! The capital of %s is %s\n", country, correctCapital);
				}
			}

			System.out.printf("\nGame Over! Your score: %d out of %d\n", score, TOTAL_QUESTIONS);
			saveScore(playerName, score);
		}
	}

	private static void saveScore(String playerName, int score) {
		try (FileWriter writer = new FileWriter("classificacio.txt", true);
				BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
			bufferedWriter.write(String.format("%s: %d points%n", playerName, score));
			System.out.println("Score saved successfully!");
		} catch (IOException e) {
			System.out.println("Error saving score: " + e.getMessage());
		}
	}
}