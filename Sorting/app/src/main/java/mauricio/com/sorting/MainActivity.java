/*
 *  Copyright (C) 2019 Mauricio Andrada
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mauricio.com.sorting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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

        //Parses out all the words; notice how we invoked toLowerCase() to make the sentence case insensitive
        StringTokenizer stringTokenizer = new StringTokenizer(sentence.toLowerCase()," ,;:'!?.");
        HashMap<String, Word> map = new HashMap<>();

        /*
        Using a HashMap to get the list of words along with the count for each word
        The idea here is to use the word as an index to an instance of Word so we can quickly find it and increment the count
         */
        while (stringTokenizer.hasMoreTokens()) {

            String word = stringTokenizer.nextToken();

            Word item = map.get(word);

            //First time we find a word, put it in the HashMap along with the instance
            if (item == null) {

                item = new Word();
                item.word = word;
            }

            item.count++;

            map.put(word,item);

        } //done with populating the list with the words and their amounts

        /*
        On this section we'll setup the JSON with the nested sorting rules.
         */
        JSONObject rulesRoot = new JSONObject();
        JSONObject rules1 = new JSONObject();
        JSONObject rules2 = new JSONObject();

        try {
            /**
             * rules are: first sort by count in descending order; if same count then sort by word (alphabetically) in ascending order
             * The column names must match the names for the fields in Word class.
             * In the Comparator we'll use reflection to get values for each column.
             */
            rules1.put("column","count");
            rules1.put("order","desc");
            rules2.put("column","word");
            rules2.put("order","asc");

            rules1.put("rule",rules2);
            rulesRoot.put("rule",rules1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GenericComparator genericComparator = new GenericComparator(rulesRoot);

        Word[] wordList = map.values().toArray(new Word[]{});

        Arrays.sort(wordList,genericComparator);

        for (int i = 0; i < num_words; i++) {

            result[i] = wordList[i].word;
        }

        return result;

    }

    private class Word {


        private String word;
        private int count;

    }

    class GenericComparator implements Comparator<Word> {

        private JSONObject sortingRules;

        public GenericComparator(JSONObject sortingRules) {
            this.sortingRules = sortingRules;
        }

        private Object getValue(Word w1, String column) {

            try {
                /*
                Using reflection to get the field names.
                The Word class is not Comparable but the fields are assumed to be.
                 */
                Field f = w1.getClass().getField(column);
                return f.get(w1);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int compare(Word w1, Word w2) {

            int result = 0;

            try {

                JSONObject rule = sortingRules;

                //loop through the nested rules
                while (rule.has("rule")) {

                    rule = rule.getJSONObject("rule");

                    String column = rule.getString("column");
                    String order = rule.getString("order");

                    /**
                     * Word class is not comparable but its fields are assumed to be.
                     */
                    Comparable obj1 = (Comparable) getValue(w1,column);
                    Comparable obj2 = (Comparable) getValue(w2,column);

                    boolean ascending = order.toLowerCase().equals("asc");

                    int aux = obj1.compareTo(obj2);

                    if (aux == 0) {

                      continue;

                    }

                    if (ascending)
                        result = aux;
                    else
                        result = -aux;

                    break;

                };

            } catch (JSONException e) {
                return result;
            }

            return result;
        }
    }
}
