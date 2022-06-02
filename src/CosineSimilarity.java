import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class CosineSimilarity {

    static String filesDir = "files/";
    static Map<Integer, String> docsMap;
    static Map<String, Integer[]> index;

    // Constructs a HashMap with N words X M distinct words,
    // Can be viewed as NxM Boolean matrix, rows are documents, columns are words, 1 if word M exist in N document.
    public static void buildIndex(String[] files) throws FileNotFoundException {
        File file;
        Scanner sc;
        Map<String, List<Integer>> wordsSet = new HashMap<String, List<Integer>>();
        docsMap = new HashMap<>(files.length);

        // Create a table where Key is Word, Values is Documents
        for (int i = 0; i < files.length; i++) {
            docsMap.put(i, files[i]);
            file = new File(filesDir + files[i]);
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] lineWords = sc.nextLine().split("\\W+");
                for (String word : lineWords) {
                    if (!wordsSet.containsKey(word))
                        wordsSet.put(word, new ArrayList<Integer>());
                    wordsSet.get(word).add(i);
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
            List<Integer> indices = (List<Integer>) mapElement.getValue();
            for (int i : indices)
                index.get(docsMap.get(i))[c] = 1;
            c++;
        }

        // Print
        for (Map.Entry mapElement : index.entrySet())
            System.out.println(mapElement.getKey() + " " + Arrays.toString((Integer[]) mapElement.getValue()));
    }

    // Calculate Cosine Similarity Score between 2 documents
    public static float getCosineScore(String doc1, String doc2)
    {
        return 0;
    }
    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"doc1.txt", "doc2.txt"};
        buildIndex(files);
    }
}
