package dbproject;

import dbproject.OldProject.MultiSelectQuestion;
import dbproject.OldProject.NumOfAnswersException;
import dbproject.OldProject.NumOfQuestionsException;
import dbproject.OldProject.Repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public abstract class Exam implements Examable {
	private String date;
	protected Repo repo;
	protected int currNumQue;
	protected Question[] questions;
	private boolean displaySolution;

	/**
	 * C'tor saves the date and time the test was created
	 * 
	 * @param maxNumQue max number of question in test
	 * @throws NumOfQuestionsException
	 */
	public Exam(Repo repo, int maxNumQue) throws NumOfQuestionsException {
		if (maxNumQue > 10)
			throw new NumOfQuestionsException(maxNumQue);

		questions = new Question[maxNumQue];
		// this.maxNumQue = maxNumQue;
		displaySolution = false;
		currNumQue = 0;
		this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm"));
		this.repo = repo;
	}

	/**
	 * Adds a question to the test
	 * 
	 * @param queToAdd question given
	 * @return whether the question was added
	 * @throws NumOfQuestionsException
	 */
	public boolean addQuestion(Question queToAdd) throws NumOfQuestionsException {
		if (currNumQue >= questions.length) {
			throw new NumOfQuestionsException(currNumQue + 1, questions.length);
			// System.out.println("Error! No more question could be added, reached full
			// capacity");
		}

		questions[currNumQue++] = queToAdd;
		return true;
	}

	public boolean addQuestion(MultiSelectQuestion queToAdd, Repo repo)
			throws NumOfAnswersException, NumOfQuestionsException {
		int numCorrect = queToAdd.getNumCorrect();
		Answer[] defaults = repo.generateDefaultAnswers((numCorrect == 0), (numCorrect > 1));

		int numOfAns = queToAdd.getNumAnswers();
		if (numOfAns <= 3)
			throw new NumOfAnswersException(numOfAns);

		queToAdd.addAnswer(defaults[0]);
		queToAdd.addAnswer(defaults[1]);
		return addQuestion((Question) queToAdd);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < currNumQue; i++) {
			questions[i].setDisplaySolution(displaySolution);
			builder.append(questions[i].toString());
		}
//		builder.append(Arrays.toString(questions));
//		builder.append("]");
		return builder.toString();
	}

	/**
	 * writes the to string to a file in the specified format
	 * (exam/solution_yyyy_MM_dd_HH_mm)
	 * 
	 * @param displaySolution exam/solution
	 * @throws FileNotFoundException
	 */
	public String writeExam(boolean displaySolution) throws FileNotFoundException {
		String filePath = (displaySolution ? "solution_" : "exam_") + date + ".txt";
		File file = new File(filePath);
		PrintWriter pw = new PrintWriter(file);
		this.displaySolution = displaySolution;
		pw.write(this.toString());
		pw.close();
		return filePath;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Exam))
			return false;

		Exam other = (Exam) obj;

		if (this.currNumQue != other.currNumQue) {
			return false;
		}

		return Arrays.equals(this.questions, other.questions);
	}

	@Override
	public abstract void createExam();

}
