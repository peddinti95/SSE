// Venkata Sandeep Peddinti
// a1758476


import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.io.IOException;

public class Decrypt {

    private static int num = 4; //number of letters
    private static char[] arr = {0, 0, 0, 0};
    private static HashSet<String> K = new HashSet<>();
    private static String[] week = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    private static char[] letterFrq = {'e','t', 'a', 'o', 'i', 'n', 's', 'r', 'h', 'd', 'l', 'u', 'c',
            'm', 'f', 'y', 'w', 'g', 'p', 'b', 'v' , 'k', 'x', 'q', 'j', 'z'};
    public static void main(String[] args) {
        ex1();
        ex2();

    }

    private static void ex1() {
        System.out.println("\nEx1 for Assignment 3\n");
        String cipher = readFile("ex1.enc");
        System.out.println("Ciphertext:\n" + cipher);
        char[] frq = getFrequencySort(cipher);
        sortFrequency(frq);

        // try for the frequency
        String plainText = getPlainMono(cipher, frq);
        System.out.println("\nTry with frequency:");
        System.out.println("Alphabet: " + Arrays.toString(letterFrq));
        System.out.println("Try key:  " + Arrays.toString(frq));
        System.out.println("\n" + plainText);

        // modify the key from observation
        // guess 1: fc=hi, do=to, n=a, ngx=are, qoa=you, tgg=see, 't='s, kfggxt=cheers
        // a-n, b-h, c-k, e-g, h-f, i-c, o-o, r-x, s-t, t-d, u-a, y-q
        char[] key1 = {'n','h', 'k', 'w', 'g', 'p', 'j', 'f', 'c', 'y', 'v', 'z', 'i',
                'b', 'o', 's', 'e', 'x', 't', 'd', 'a', 'l', 'r', 'm', 'q', 'u'};
        plainText = getPlainMono(cipher, key1);
        System.out.println("\nTry with Guess 1:");
        System.out.println("Alphabet: " + Arrays.toString(letterFrq));
        System.out.println("Try key:  " + Arrays.toString(key1));
        System.out.println("\n" + plainText);

        // guess 2: w-w, grgxqdfcsz=everything, zocsz=going, wg=we, cjgn=idea, hxcsz=bring, fopg=hope, nbckg=alice
        // v-r, n-s, g-z, p-p, w-w, d=j, l-b
        char[] key2 = {'n','h', 'k', 'j', 'g', 'l', 'z', 'f', 'c', 'y', 'v', 'b', 'i',
                's', 'o', 'p', 'e', 'x', 't', 'd', 'a', 'r', 'w', 'm', 'q', 'u'};
        plainText = getPlainMono(cipher, key2);
        System.out.println("\nTry with Guess 2:");
        System.out.println("Alphabet: " + Arrays.toString(letterFrq));
        System.out.println("Try key:  " + Arrays.toString(key2));
        System.out.println("\n" + plainText);

        // guess 3: ixgg=free, ixcjnq=friday, znlgt=games, oi=of, ias=fun, iggb=feel, loxg=more
        // f-i, m-l, l-b
        char[] key3 = {'n','h', 'k', 'j', 'g', 'i', 'z', 'f', 'c', 'y', 'v', 'b', 'l',
                's', 'o', 'p', 'e', 'x', 't', 'd', 'a', 'r', 'w', 'm', 'q', 'u'};
        plainText = getPlainMono(cipher, key3);
        System.out.println("\nTry with Guess 3:");
        System.out.println("Alphabet: " + Arrays.toString(letterFrq));
        System.out.println("Try key:  " + Arrays.toString(key3));
        System.out.println("\n" + plainText);
        System.out.println("Found key: " + Arrays.toString(key3));
    }

    private static void ex2() {
        System.out.println("\nEx2 for Assignment 3\n");
        String cipher = readFile("ex2.enc");
        System.out.println("Ciphertext: " + cipher);
        dfs(0, 0);
        for (String k : K) {
            String plainText = getPlainPoly(cipher, k);
            for (String day : week) {
                if (plainText.contains(day)) {
                    System.out.println("Plaintext: " + plainText);
                    System.out.println("key: " + k);
                }
            }
        }
        K.clear();
    }

    private static char[] getFrequencySort(String text){
        char[] res = new char[26];
        char[] count = new char[26];
        for (int i = 0; i < res.length; i++) {
            res[i] = (char)(97+i);
            count[i] = 0;
        }
        for (int i = 0; i < text.length(); i++) {
            char a = text.charAt(i);
            String as = Character.toString(a);
            if (as.matches("[a-z]")){
                count[a-97]++;
            }
        }
        for (int i = 0; i < res.length-1; i++) {
            for (int j = i+1; j < res.length; j++) {
                if(count[j] > count[i]) {
                    swap(count, i, j);
                    swap(res, i, j);
                }

            }
        }
        return res;
    }

    private static void sortFrequency(char[] frq) {
        char[] letters = letterFrq;
        for (int i = 0; i < frq.length; i++) {
            for (int j = 0; j < frq.length; j++) {
                if(letters[i] < letters[j]) {
                    swap(letters, i, j);
                    swap(frq, i, j);
                }
            }
        }
    }

    private static String getPlainMono(String c, char[] k) {
        String res = "";

        for (int i = 0; i < c.length(); i++) {
            char a = c.charAt(i);
            String as = Character.toString(a);
            if (!as.matches("[a-z]")){
                res += as;
            } else {
                for (int j = 0; j < k.length; j++) {
                    if(a == k[j]){
                        res += (char)(97+j);
                    }
                }
            }
        }
        return res;
    }

    private static String getPlainPoly(String c, String k) {
        String res = "";
        int index_k = 0;
        for (int i = 0; i < c.length(); i++) {
            char a = c.charAt(i);
            String as = Character.toString(a);
            if (!as.matches("[a-z]")) {
                // System.out.println(i);
                res += as;
            } else {
                char b = k.charAt(index_k);
                char p = ' ';
                if (a>=b) {
                    p = (char)(((int) a - ((int) b)) + 97);
                } else {
                    p = (char)(26-((int) b - ((int) a)) + 97);
                }
                res += p;
                index_k++;
                if (index_k == k.length()) {
                    index_k = 0;
                }
            }
            // System.out.println(res);
        }
        return res;
    }

    private static void dfs(int deep, int index) {
        if (deep == num) { //get num
            String[] keys = fullSort(arr, 0, arr.length);
            K.addAll(Arrays.asList(keys));
            return;
        }
        for (int i = index; i < 26 - num + 1 + deep; i++) { //letters can be get
            arr[deep] = (char) (97 + i);
            dfs(deep + 1, i + 1); //starting index of the next layer
        }
    }

    private static String[] fullSort(char[] ch, int start, int end) {
        HashSet<String> full = new HashSet<>();
        if (start == end) {
            full.add(String.valueOf(ch));
        } else {
            for (int i = start; i < end; i++) {
                swap(ch, i, start);
                String[] res1 = fullSort(ch, start + 1, end);
                full.addAll(Arrays.asList(res1));
                swap(ch, start, i);
            }
        }
        String[] res = new String[full.size()];
        full.toArray(res);
        return res;
    }

    private static void swap(char[] arr, int i, int j) {
        char tmp = '0';
        tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static String readFile(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

}
