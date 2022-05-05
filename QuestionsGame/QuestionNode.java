package QuestionsGame;

// The QuestionNode class is used to store the information for one node
// question in 20 Questions. The "entry" field represents the question
// for that node while the "correct" and "incorrect" fields represent
// the game path chosen depending if the computer's guess was correct or
// incorrect. In the case that there are no more questions to ask, "entry"
// represents the destination answer that is presented and "correct" and
// "incorrect" will be null

public class QuestionNode {
  public QuestionNode correct;
  public QuestionNode incorrect;
  public String entry;

  // constructs node for a question, with correct and incorrect
  // destinations
  // takes QuestionNode correct, QuestionNode incorrect, and String entry to do so
  public QuestionNode(QuestionNode correct, QuestionNode incorrect, String entry) {
      this.correct = correct;
      this.incorrect = incorrect;
      this.entry = entry;
  }

  // creates leaf node for answers, takes String entry
  public QuestionNode(String entry) {
      this(null, null, entry);
  }
}
