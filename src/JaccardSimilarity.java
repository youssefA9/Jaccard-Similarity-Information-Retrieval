import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


class FileTokenizer {
    ArrayList<String> sources;  // store the words
    String filename;

    FileTokenizer() {
        sources = new ArrayList<String>();
    }

    public void buildSources(String file) {
        filename = file;
        try (BufferedReader f = new BufferedReader(new FileReader(file))) {
            String ln;
            while ((ln = f.readLine()) != null) {
                String[] words = ln.split("\\W+");
                for (String word : words) {
                    word = word.toLowerCase();
                    // check to see if the word is not in the dictionary
                    if (!sources.contains(word)) {
                        sources.add(word);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("File " + file + " not found. Skip it");
        }
    }

}

class Jaccard {
    ArrayList<String> union(ArrayList<String> s1, ArrayList<String> s2) {
        ArrayList<String> answer = new ArrayList<String>();
        for (String s : s1) {
            answer.add(s.toLowerCase());
        }
        for (String s : s2) {
            if (!answer.contains(s)) {
                answer.add(s.toLowerCase());
            }
        }
        return answer;
    }

    ArrayList<String> intersect(ArrayList<String> s1, ArrayList<String> s2) {
        ArrayList<String> answer = new ArrayList<String>();

        for (String s : s1) {
            for (String ss : s2) {
                if (s.toLowerCase().equals(ss.toLowerCase())) {
                    if (!answer.contains(s))
                        answer.add(s.toLowerCase());
                }
            }
        }
        return answer;
    }

    public double jaccard(String phrase, FileTokenizer f) {
        String words1[] = phrase.split(" ");
        ArrayList<String> words = new ArrayList<String>();
        for (int i = 0; i < words1.length; i++) {
            words.add(words1[i]);
        }
        ArrayList<String> intersection = intersect(words, f.sources);
        ArrayList<String> union = union(words, f.sources);
        double uSize = union.size();
        double iSize = intersection.size();

        return iSize / uSize;
    }

}


public class JaccardSimilarity {
    public static void main(String args[]) throws IOException {
        Jaccard j = new Jaccard();
        FileTokenizer f1 = new FileTokenizer();
        f1.buildSources("src/doc1.txt");

        FileTokenizer f2 = new FileTokenizer();
        f2.buildSources("src/doc2.txt");

        String Query = "idea of March";
        double j1 = j.jaccard(Query, f1);
        double j2 = j.jaccard(Query, f2);
        System.out.println("\n" + "====================== Jaccard Similarity of the two files (sorted descendingly) =================" + "\n");
        if (j1 > j2) {
            System.out.println(f1.filename + "      Jaccard similarity= " + j1);
            System.out.println(f2.filename + "      Jaccard similarity= " + j2);
        } else {
            System.out.println(f2.filename + "    Jaccard similarity= " + j2);
            System.out.println(f1.filename + "    Jaccard similarity= " + j1);
        }


    }
}

