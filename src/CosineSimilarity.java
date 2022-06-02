import java.util.HashMap;
import java.util.Map;

public class CosineSimilarity {
    Map<String, int[]> index = new HashMap<String, int[]>();

    // Build index for all documents and store it for later computation
    public static void buildIndex(String[] files)
    {

    }

    // Calculate Cosine Similarity Score between 2 documents
    public static float getCosineScore(String doc1, String doc2)
    {
        return 0;
    }
    public static void main(String[] args)
    {
        String[] files = {"doc1.txt", "doc2.txt"};
        buildIndex(files);
    }
}
