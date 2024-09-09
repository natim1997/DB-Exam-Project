package dbproject.OldProject;

import dbproject.Answer;
import dbproject.Exam;
import dbproject.Question;

import java.util.Random;

public class AutomaticExam extends Exam {

	private final static Random rnd = new Random();
	// private String[] generated;
	// private int numGenrated;

	public AutomaticExam(Repo repo, int maxNumQue) throws NumOfQuestionsException {
		super(repo, maxNumQue);
		// this.generated = new String[4];
	}

	@Override
	public void createExam() {
		int maxRange = repo.getNumQuestions();
		int genQue = 0;
		for (int i = 0; i < questions.length; i++) {
			genQue = rnd.nextInt(maxRange);
			Question questionSelected = repo.getQuestionByID(genQue);
			if (questionSelected instanceof OpenEndedQuestion) {

				try {
					this.addQuestion((Question) new OpenEndedQuestion((OpenEndedQuestion) questionSelected));
				} catch (NumOfQuestionsException e) {
					System.out.println("Error! " + e);
				}

			} else if (questionSelected instanceof MultiSelectQuestion) {
				MultiSelectQuestion multiGen = new MultiSelectQuestion((MultiSelectQuestion) questionSelected);
				multiGen.clear();

				// genrate 4 answers
				int genAns = 0;
				int numAnswers = repo.getNumAnswers();

				int correctIndex = rnd.nextInt(4);
				String[] genrated = new String[4];
				for (int j = 0; j < 4; j++) {
					String ans = "";
					boolean wasGen = false;
					do {
						genAns = rnd.nextInt(numAnswers - 2) + 1; // not include defult answers
						ans = repo.getAnswerByIndex(genAns);
						wasGen = wasGenerated(genrated, ans);
						if(!wasGen)
							genrated[j] = ans;
					} while (wasGen);
					
					multiGen.addAnswer(new Answer(ans, correctIndex == j));
				}
				
				try {
					this.addQuestion(multiGen, repo);
				} catch (NumOfAnswersException | NumOfQuestionsException e) {
					System.out.println("Error! " + e);
				}

			} else {
				i--; // didn't find question
			}
		}

	}

	private boolean wasGenerated(String[] generated, String question) {
		for (int i = 0; i < generated.length; i++) {
			if (generated[i] != null && generated[i].equals(question)) {
				return true;
			}
		}
		return false;
	}

}
