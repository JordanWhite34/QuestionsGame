package QuestionsGame;

// Jordan White
// 3/4/2021
//
// QuestionsGame plays a game with the user where it makes guesses
// of the object the user is thinking of and if it cannot correctly
// guess the object it will get a question and answer related to that
// object so it will be able to guess it in future games. Additionally
// this can store the game's knowledge and overwrite given knowledge
// for guessing objects
//
// Files MUST have exactly an even amount of lines, lines containing
// "Q:" or "A:" are case-sensitive and must contain exactly that with no
// extra text. Also, nodes of tree appear in the file following a pre-order
// traversal and any files passed to read method must follow this format

import java.util.*;
import java.io.*;

public class QuestionsGame {
    private Scanner console;
    private QuestionNode overallRoot;

    // constructs a new QuestionsGame object with a single leaf node representing the computer
    public QuestionsGame() {
        overallRoot = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    // replaces current tree from tree in file, assuming file is legal and in standard format,
    // takes a scanner to read file
    public void read(Scanner input) {
        overallRoot = replaceTree(input);
    }

    // stores current questions tree to output file, can be used to play another QuestionsGame,
    // takes PrintStream output to put information into a file
    // can also be used to start another game
    public void write(PrintStream output) {
        write(output, overallRoot);
    }

    // uses current question tree to play one complete guessing game, begins with root node of
    // tree, ends with reaching a leaf answer node
    // if computer wins, prints a message saying so, otherwise will ask what object the user is
    // thinking of, a question to distinguish that object from the player's guess and whether the
    // player's object is yes or no for that question
    // also, this method will modify and add in a new question and answer for an incorrect guess
    public void askQuestions() {
        overallRoot = askQuestions(overallRoot);
    }

    // helper method for read, takes Scanner input to read and replace
    // the knowledge of the current tree with what it gets from the other file
    private QuestionNode replaceTree(Scanner input) {
        String type = input.nextLine();
        String data = input.nextLine();
        if (type.equals("Q:")) {
            QuestionNode path = new QuestionNode(replaceTree(input), replaceTree(input), data);
            return path;
        } else {
            return new QuestionNode(data);
        }
    }

    // helper method for write, takes PrintStream output and QuestionNode root
    // and puts guessing game information in a file
    private void write(PrintStream output, QuestionNode root) {
        if (root.incorrect == null && root.correct == null) {
            output.println("A:");
            output.println(root.entry);
        } else if (root.correct != null || root.incorrect != null) {
            output.println("Q:");
            output.println(root.entry);
            write(output, root.correct);
            write(output, root.incorrect);
        }
    }

    // helper for askQuestions, creates the process of narrowing down what the user's guess
    // might be, takes QuestionNode root to update information for the game in the case that
    // the computer is unable to correctly guess the object
    private QuestionNode askQuestions(QuestionNode root) {
        if (root.incorrect == null && root.correct == null) {
            if (yesTo("Would your object happen to be " + root.entry + "?")) {
                System.out.println("Great, I got it right!");
            } else {
                System.out.print("What is the name of your object? ");
                String newAns = console.nextLine();
                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");
                String newQ = console.nextLine();
                if (!yesTo("And what is the answer for your object?")) {
                    root = new QuestionNode(new QuestionNode(root.entry), new QuestionNode(newAns), newQ);
                } else {
                    root = new QuestionNode(new QuestionNode(newAns), new QuestionNode(root.entry), newQ);
                }
            }
        } else {
            if (!yesTo(root.entry)) {
                root.incorrect = askQuestions(root.incorrect);
            } else {
                root.correct = askQuestions(root.correct);
            }
        }
        return root;
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}