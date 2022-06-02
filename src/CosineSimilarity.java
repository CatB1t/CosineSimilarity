import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Math;
public class CosineSimilarity {

    static String filesDir = "files/";
    static Map<Integer, String> docsMap;
    static Map<String, Integer[]> index;

    // Constructs a HashMap with N words X M distinct words,
    // Can be viewed as NxM Boolean matrix, rows are documents, columns are words, 1 if word M exist in N document.
    public static void buildIndex(String[] files) throws FileNotFoundException {
        File file;
        Scanner sc;
        Map<String, Integer[]> wordsSet = new HashMap<String, Integer[]>();
        docsMap = new HashMap<>(files.length);

        // Create a table where Key is Word, Values is Documents
        for (int i = 0; i < files.length; i++) {
            docsMap.put(i, files[i]);
            file = new File(filesDir + files[i]);
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] lineWords = sc.nextLine().split("\\W+");
                for (String word : lineWords) {
                    word = word.toLowerCase();
                    if (!wordsSet.containsKey(word)) {
                        Integer[] tmp = new Integer[files.length];
                        Arrays.fill(tmp, 0);
                        wordsSet.put(word, tmp);
                    }
                    wordsSet.get(word)[i]++;
                }
            }
        }


        index = new HashMap<>(files.length);
        for (int i = 0; i < files.length; i++) {
            Integer[] arr = new Integer[wordsSet.size()];
            Arrays.fill(arr, 0);
            index.put(docsMap.get(i), arr);
        }

        int c = 0;


        // Transform To Word Table, Where Key is Document, Value is Boolean Words
        for (Map.Entry mapElement : wordsSet.entrySet()) {
            for (int i =0; i < files.length; i++) {
                Integer[] documentValue = index.get(docsMap.get(i));
                documentValue[c] =  ((Integer[]) mapElement.getValue())[i];
            }
            c++;
        }

        System.out.println("===== TABLE =====");
        for (Map.Entry mapElement : index.entrySet())
            System.out.println(mapElement.getKey() + " => " + Arrays.toString((Integer[]) mapElement.getValue()));
    }

    // Calculate Cosine Similarity Score between 2 documents
    public static double getCosineScore(String doc1, String doc2)
    {
        Integer[] doc1Entries = index.get(doc1);
        Integer[] doc2Entries = index.get(doc2);

        int dotSum = 0, d1SqrSum = 0, d2SqrSum = 0;

        for(int i=0; i < doc1Entries.length; i++) {
            dotSum += doc1Entries[i] * doc2Entries[i];
            d1SqrSum += doc1Entries[i] * doc1Entries[i];
            d2SqrSum += doc2Entries[i] * doc2Entries[i];
        }

        return dotSum / (Math.sqrt(d1SqrSum) * Math.sqrt(d2SqrSum));
    }
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {
                "doc1.txt",
                "doc2.txt",
                "doc3.txt",
                "doc4.txt"
        };
        buildIndex(files);

        double score = getCosineScore("doc1.txt", "doc2.txt");
        System.out.println("Cosine Score for (doc1.txt and doc2.txt): " + String.format("%.3f", score));
    }
}
