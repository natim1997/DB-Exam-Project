package dbproject;

import java.io.Serializable;

public class Answer implements Serializable {
	protected String text;
	protected boolean isCorrect;
	private boolean displaySolution;

	/**
	 * C'tor
	 * 
	 * @param answer    the answer text
	 * @param isCorrect whether the answer is correct
	 */
	public Answer(String answer, boolean isCorrect) {
		this.text = answer;
		this.isCorrect = isCorrect;
		this.displaySolution = false;
	}

	/**
	 * Copy c'tor
	 * 
	 * @param other answer to copy
	 */
	public Answer(Answer other) {
		this(other.text, other.isCorrect);
	}

	/**
	 * @param displaySolution the displaySolution to set
	 */
	public void setDisplaySolution(boolean displaySolution) {
		this.displaySolution = displaySolution;
	}

	/**
	 * @return the answer text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return whether the answer is correct
	 */
	public boolean isCorrect() {
		return isCorrect;
	}

	/**
	 * @param isCorrect change the correctness of the answer
	 */
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	/**
	 * @return toString
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// builder.append("answer: ");
		builder.append(text);
		if (displaySolution) {
			builder.append(" [");
			builder.append(isCorrect ? "x" : " ");
			builder.append("]");
		}
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Answer))
			return false;

		Answer ans = (Answer) obj;
		return (ans.text == this.text) && (ans.isCorrect == this.isCorrect);
	}

}
