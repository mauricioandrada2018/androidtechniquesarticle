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

        /**
         * This Hashmap makes it easier to find out if a word has been countet yet
         */
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

            /**
             * Compares the counts and if they are the same sort alphabetically in ascending order
             */
            int result = word2.count;

            if (result == 0)
                return this.word.compareTo(word2.word);

            return result;
        }
    }
}