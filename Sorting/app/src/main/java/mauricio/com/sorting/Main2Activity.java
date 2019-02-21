package mauricio.com.sorting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sentence = "The dog and the cat are friends. The dog is brown and the dog has many friends.";
        String[] list = get_word_list(sentence,4);
        if (list != null) {
            for (String item:list)
                Log.v("TestSort",item);
        }
    }

    String[] get_word_list(String sentence,int num_words) {
        if (num_words < 0 )
            return null;
        if (num_words == 0)
            return new String[0];
        if (sentence == null)
            return null;
        String[] result = new String[num_words];
        StringTokenizer stringTokenizer = new StringTokenizer(sentence.toLowerCase()," ,;:'!?.");
        HashMap<String, Word> map = new HashMap<>();
        while (stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            Word item = map.get(word);
            if (item == null) {
                item = new Word(word);
            }
            item.count++;
            map.put(word,item);
        }
        Word[] wordList = map.values().toArray(new Word[]{});
        Arrays.sort(wordList);
        for (int i = 0; i < num_words; i++) {
            result[i] = wordList[i].word;
        }
        return result;
    }

    private class Word implements Comparable<Word> {


        private final String word;
        private int count;


        public Word(String word) {
            this.word = word;
        }

        @Override
        public int compareTo(@NonNull Word word2) {

            int result = word2.count;

            if (result == 0)
                return this.word.compareTo(word2.word);

            return result;
        }
    }
}