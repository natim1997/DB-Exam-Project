package dbproject.OldProject;

public class NumOfAnswersException extends ExamCreationException {

	private int numOfAnswers;

	/**
	 * @param numOfAnswers
	 */
	public NumOfAnswersException(int numOfAnswers) {
		super("Failed adding the question because it contains only " + numOfAnswers
				+ " when the requirement is at least 4!");
		this.numOfAnswers = numOfAnswers;
	}
}
