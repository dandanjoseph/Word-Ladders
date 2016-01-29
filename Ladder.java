/*
 * Word ladder project by Joe Dandan
 */
package ladder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Joe Dandan
 */
//this is the main ladder class
public class Ladder {

    //this is an array list we are going to hold our words from the file input in
    public ArrayList<String> words = new ArrayList<String>();
    //this holds the endlist we want to print out although it is backwards
    public LinkedList<String> endlist = new LinkedList<String>();
    //this is where we move the list from not being backwards in the right order
    public ArrayList<String> endlist1 = new ArrayList<String>();
    //this holds the start word
    public String startingWord;
    //this holds the end word
    public String endingWord;

    //this is the method that is called to start the program from the main
    public void Ladder1(String filename, String start, String end) throws FileNotFoundException {

        //this sets the start word to the global var
        startingWord = start;
        //this sets the end word to the global var
        endingWord = end;
        //this calls a method that loads the list of words to our global variable holding all the words
        loadlist(filename, start.length());

        //we check if the words passed are in the word list
        if (!words.contains(start) || !words.contains(end)) {
            //if they are not we prompt the user about it
            System.out.println("One of these words are not contained in the file:");
        }
        //this is where we check if the words are the same length
        if (start.length() != end.length()) {
            //we prompt the user if they are not the same length
            System.out.println("These words are not the same length");
        }
        //this is the call to the search method used to find the word ladder, 
        //we pass the start word and the end word and the list of the words
        search(start, end, words);
        //we reverse the list to print it out correctly with this method call on the endlist
        reverselist(endlist);
    }
    
    //this is the method to reverse our endlist
    public void reverselist(LinkedList<String> list) {
        //for each loop in our linked list it takes our items and adds them to a array list
        for (String item : list) {
            //we add them here
            endlist1.add(item);
        }
        //we make a call to reverse the arraylist accordingly
        Collections.reverse(endlist1);
        //for each of the items in the array list we print out the word ladder
        for (String item : endlist1) {
            //this is the print statement for the word ladder
            System.out.print(item + " -> ");
        }
    }

    //first thing we do is seperate the words from the list by length of the start word
    public void loadlist(String filename, Integer length) throws FileNotFoundException {
        //scan in a new file
        Scanner scan = new Scanner(new File(filename));
        //while there is still stuff in the file
        while (scan.hasNextLine()) {
            //we scan the next line into our string word
            String word = scan.nextLine();
            //if the length is appropriate for the length of the start word
            if (word.length() == length) {
                //we add the word for our list
                words.add(word);
            }
        }
    }
    //this is our main search method that looks for the shortest ladder
    public void search(String start, String end, ArrayList<String> words) {
        //we have a linked list to hold our words that are one character off from eachother
        LinkedList<String> friends = new LinkedList();
        //this is a hashmap to hold the father of the word, holds the word its one char off from
        HashMap<String, String> father = new HashMap<>();
        //this is the hashset to hold the seen list of words
        HashSet<String> seen = new HashSet<String>();
        //this is the linked list to hold the final list
        LinkedList<String> finallist = new LinkedList();
        //first we add our start word to our used list
        seen.add(start);
        //next we add the start word to our friends list
        friends.add(start);
        //so while there are friends in the list greater than zero
        while (friends.size() > 0) {
            //we take out the word and store it in the string var word
            String word = friends.removeFirst();
            //if its the end word
            if (word.equals(end)) {
                //we add it to our final list
                finallist.add(end);
                //while our hashmap is not null, when we get the word were it came from, 
                //this loops till we get all the words up to our start word
                while (father.get(word) != null) {
                    //we add that to our final list
                    finallist.add(father.get(word));
                    //we set the new word to the word grabbed
                    word = father.get(word);
                }
            } else { //else if we are not at the end word
                //for the following word in our words list
                for (String followingword : words) {
                    //we check to see if they are one character off
                    if (checkChars(word, followingword)) {
                        //if it is not in our seen list
                        if (!seen.contains(followingword)) {
                            //we add it to our friends
                            friends.add(followingword);
                            //we then put it in our hashmap
                            father.put(followingword, word);
                            //we also add it to our seen list
                            seen.add(followingword);
                        }
                    }
                }
            }
        }
        //this is where we set our final list to a global list endlist
        endlist = finallist;
    }

    //this checks the characters of the words that are passed to this method
    public boolean checkChars(String word1, String word2) {
        //this variable is to hold the amount of difference in the words
        int difference = 0;
        //this is a loop that goes the word length of the words
        for (int i = 0; i < word1.length(); i++) {
            //for the first word and the second word, if they are different at one point of eachother
            if (word1.charAt(i) != word2.charAt(i)) {
                //we increment our difference counter
                difference++;
            }
            //if it is greater than 1
            if (difference > 1) {
                //we return false
                return false;
            }
        }
        //else we will return true
        return true;
    }

    //this is the main function that takes in command line args
    public static void main(String[] args) throws FileNotFoundException {
        //for string x, we take in our first arg
        String x = (args[0]);
        //for string y it takes in the second arg
        String y = (args[1]);
        //for string z it takes in the third arg
        String z = (args[2]);
        //this is a new instance of our ladder game
        Ladder lad = new Ladder();
        //we pass in arguments to our method that starts the game
        lad.Ladder1(x, y, z);
    }
}
