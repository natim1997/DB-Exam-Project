package dbproject.OldProject;

public class ExamCreationException extends Exception {

	public ExamCreationException(String message) {
		super(message);
	}
	
	public ExamCreationException() {
		super("General dbproject.OldProject.ExamCreationException: Failed to create an dbproject.Exam!");
	}
}
