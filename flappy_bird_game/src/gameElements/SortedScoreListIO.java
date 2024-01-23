package gameElements;

import java.io.*;
import java.util.Scanner;

/**

 The SortedScoreListIO class provides methods to save and load SortedScoreList objects from files.
 */
public class SortedScoreListIO {

    /**

     Saves a SortedScoreList object to a file with the specified filename.
     @param scores the SortedScoreList object to save
     @param filename the filename to save the SortedScoreList object to
     */
    public static void saveToFile(SortedScoreList scores, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < scores.size(); i++) {
                writer.println(scores.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error saving scores to file: " + e.getMessage());
        }
    }
    /**

     Loads a SortedScoreList object from a file with the specified filename.
     @param filename the filename to load the SortedScoreList object from
     @return the SortedScoreList object loaded from the file
     */
    public static SortedScoreList readFromFile(String filename) {
        SortedScoreList scores = new SortedScoreList();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                Integer score = Integer.parseInt(scanner.nextLine());
                scores.add(score);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading scores from file: " + e.getMessage());
        }
        return scores;
    }
}