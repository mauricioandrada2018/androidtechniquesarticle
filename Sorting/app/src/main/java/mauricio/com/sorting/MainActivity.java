package mauricio.com.sorting;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String sentence = "The dog and the cat are friends. The dog is brown and the dog has many friends.";
        String[] list = get_word_list(sentence,3);

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

        //Parses out all the words; notice how we invoked toLowerCase() to make the sentence case insensitive
        StringTokenizer stringTokenizer = new StringTokenizer(sentence.toLowerCase()," ,;:'!?.");
        HashMap<String, Word> map = new HashMap<>();

        //Using a HashMap to get the list of words along with the count for each word
        while (stringTokenizer.hasMoreTokens()) {

            String word = stringTokenizer.nextToken();

            Word item = map.get(word);

            if (item == null) {

                item = new Word();
            }

            Integer count = (Integer) item.getValue("count");

            if (count == null)
                count = 0;

            count++;

            item.putValue("word",word);
            item.putValue("count",count);

            map.put(word,item);

        }

        JSONObject rulesRoot = new JSONObject();
        JSONObject rules1 = new JSONObject();
        JSONObject rules2 = new JSONObject();

        try {
            rules1.put("column","count");
            rules1.put("order","desc");
            rules2.put("column","word");
            rules2.put("order","desc");

            rules1.put("rule",rules2);
            rulesRoot.put("rule",rules1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GenericComparator genericComparator = new GenericComparator(rulesRoot);

        Word[] wordList = map.values().toArray(new Word[]{});

        Arrays.sort(wordList,genericComparator);

        for (int i = 0; i < num_words; i++) {

            result[i] = (String) wordList[i].getValue("word");
        }

        return result;

    }

    class Word {

        private HashMap<String,Object> fields;

        public Word() {
            this.fields = new HashMap<>();
        }

        public void putValue(String column,Comparable value) {

            fields.put(column,value);
        }

        public Comparable getValue(String column) {

            return (Comparable) fields.get(column);
        }

    }

    class GenericComparator implements Comparator<Word> {

        private JSONObject sortingRules;

        public GenericComparator(JSONObject sortingRules) {
            this.sortingRules = sortingRules;
        }

        private int sort(JSONObject rules, Word w1, Word w2) {

            int result = 0;

            try {
                JSONObject rule = rules.getJSONObject("rule");
                String column = rule.getString("column");
                String order = rule.getString("order");

                Comparable obj1 = w1.getValue(column);
                Comparable obj2 = w2.getValue(column);

                boolean ascending = order.toLowerCase().equals("asc");

                int aux = obj1.compareTo(obj2);

                if (aux == 0)
                    return sort(rule,w1,w2);

                if (ascending)
                    result = aux;
                else
                    result = -aux;

            } catch (JSONException e) {
                return result;
            }

            return result;
        }

        @Override
        public int compare(Word w1, Word w2) {

            return sort(sortingRules,w1,w2);
        }
    }

}
