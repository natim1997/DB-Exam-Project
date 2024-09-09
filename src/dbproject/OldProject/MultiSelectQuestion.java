package dbproject.OldProject;

import dbproject.Answer;
import dbproject.Difficulty;
import dbproject.Question;

import java.io.Serializable;
import java.util.Arrays;

public class MultiSelectQuestion extends Question implements Serializable {
	private int numAnswers;
	private Answer[] answers;
	private int numCorrect;

	/**
	 * C'tor
	 * 
	 * @param text the question itself
	 */
	MultiSelectQuestion(String text, Difficulty difficulty) {
		super(text, difficulty);
		this.numAnswers = 0;
		this.numCorrect = 0;
		this.answers = new Answer[10];
	}

	/**
	 * copy c'tor
	 * 
	 * @param other object to copy
	 */
	MultiSelectQuestion(MultiSelectQuestion other) {
		super(other.text, other.difficulty);
		this.numCorrect = other.numCorrect;
		this.numAnswers = other.numAnswers;
		this.answers = new Answer[10];
		for (int i = 0; i < other.numAnswers; i++) {
			this.answers[i] = new Answer(other.answers[i]);
		}
	}

	/**
	 * @return the question's possible answers
	 */
	public Answer[] getAnswers() {
		return answers;
	}

	public void clear() {
		this.answers = new Answer[10];
		this.numAnswers = 0;
		this.numCorrect = 0;
	}

	/**
	 * @return number of possible answers
	 */
	public int getNumAnswers() {
		return numAnswers;
	}

	/**
	 * @return the number of correct answers
	 */
	public int getNumCorrect() {
		return numCorrect;
	}

//	/**
//	 * @param displayAnswers whether to display the answers to every question
//	 * @return object values
//	 */
//	public String toStringSolution() {
//		StringBuilder builder = new StringBuilder();
//		builder.append(super.toString());
//
//		for (int i = 0; i < numAnswers; i++) {
//			builder.append("\s\s\s");
//			builder.append((char)('A'+i));
//			builder.append(". ");
//			builder.append(answers[i].toString(true));
//		}
//		builder.append("\n");
//		return builder.toString();
//	}

	/**
	 * @return object values
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());

		for (int i = 0; i < numAnswers; i++) {
			builder.append(i + 1);
			builder.append(". ");
			answers[i].setDisplaySolution(displaySolution);
			builder.append(answers[i].toString());
		}
		builder.append("\n");
		return builder.toString();
	}

	/**
	 * adds and aswers to the question's possible answers
	 * 
	 * @param ansToAdd answer given
	 * @return whether the answer was added
	 */
	public boolean addAnswer(Answer ansToAdd) {
		if (numAnswers >= answers.length) {
			return false;// array full
		}

		answers[numAnswers++] = ansToAdd;
		if (ansToAdd.isCorrect()) {
			numCorrect++;
		}
		return true;
	}

	/**
	 * deletes an answer
	 * 
	 * @param index answer to be deleted
	 * @return whether the answer was deleted
	 */
	public boolean deleteAnswerByIndex(int index) {
		if (numAnswers <= 0 && answers != null) {
			// System.out.println("Error! No more answers left to remove!");
			return false;
		}

		if (numAnswers <= index || index < 0) {
			return false;
		}

		if (answers[index].isCorrect())
			numCorrect--;

		answers[index] = answers[--numAnswers];
		answers[numAnswers] = null;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiSelectQuestion))
			return false;

		MultiSelectQuestion que = (MultiSelectQuestion) obj;
		return super.equals(obj) && Arrays.equals(que.answers, this.answers);
	}
}
