package dbproject.OldProject;

import dbproject.Difficulty;
import dbproject.Question;

import java.io.Serializable;

public class OpenEndedQuestion extends Question implements Serializable {

	private String solution;

	public OpenEndedQuestion(String text, String solution, Difficulty difficulty) {
		super(text, difficulty);
		this.solution = solution;
	}

	public OpenEndedQuestion(OpenEndedQuestion other) {
		super(other.text, other.difficulty);
		this.solution = other.solution;
	}

	public String getSolution() {
		return solution;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		if (displaySolution) {
			builder.append("Solution: ");
			builder.append(solution);
			builder.append("\n");
		}
		builder.append("\n");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OpenEndedQuestion))
			return false;

		OpenEndedQuestion que = (OpenEndedQuestion) obj;

		return super.equals(obj) && que.solution == solution;
	}

}
