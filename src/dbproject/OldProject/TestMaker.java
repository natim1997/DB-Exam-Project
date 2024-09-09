package dbproject.OldProject;

import dbproject.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

//import dbproject.OldProject.Repo.Subject;

public class TestMaker {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);

		Subject subject = Subject.getSubjectFromUser(Subject.values(), input);
		Repo repo = null;// new dbproject.OldProject.Repo(subject);
		final int EXIT = -1;
		int selction = 0;

		try {
			repo = loadRepo(subject);
		} catch (ClassNotFoundException | IOException e) {
			repo = new Repo(subject);
		}

		hardCoddedQue(repo, subject);

		do {
			printMenu();
			selction = input.nextInt();
			input.nextLine();

			switch (selction) {
			case 1: {
				System.out.print(repo);
				break;
			}
			case 2: {
				addAnswer(repo, input);
				break;
			}
			case 3: {
				appendAnswerToQuestion(repo, input);
				break;
			}
			case 4: {
				addQuestion(repo, input);
				break;
			}
			case 5: {
				deleteAnswerFromAQuestion(repo, input);
				break;
			}
			case 6: {
				deleteQuestion(repo, input);
				break;
			}
			case 7: {
				generateTest(repo, input);
				break;
			}
			case EXIT: {
				saveRepo(repo);
				System.out.println("Goodbye!");
				break;
			}
			default:
				System.out.println("Error! option dose not exist, Please try again!");
			}
		} while (selction != EXIT);

		saveRepo(repo);
	}

	private static Repo loadRepo(Subject subject) throws FileNotFoundException, IOException, ClassNotFoundException {
		String filename = subject.name() + ".db";
		ObjectInputStream toLoad = new ObjectInputStream(new FileInputStream(filename));
		Repo repo = (Repo) toLoad.readObject();
//		if(obj != null) {
//			repo = (dbproject.OldProject.Repo) obj;
//		} else {
//			repo = new dbproject.OldProject.Repo(subject);
//		}
		toLoad.close();
		Question.setNumQuestions(repo.getNumQuestions() + 1);
		return repo;
	}

	private static void saveRepo(Repo repo) throws FileNotFoundException, IOException {
		String filename = repo.getSubject().name() + ".db";
		ObjectOutputStream toSave = new ObjectOutputStream(new FileOutputStream(filename));
		toSave.writeObject(repo);
		toSave.close();
	}

	/**
	 * Generate a test based on user input and writes it into a solution and exam
	 * files
	 * 
	 * @param repo the program's repository
	 * @throws IOException
	 * @throws NumOfAnswersException
	 */
	public static void generateTest(Repo repo, Scanner input) throws IOException {

		// Scanner input = new Scanner(System.in);
		int numQue = 0;

		do {
			System.out.println("How many question would be in the test? ");
			numQue = input.nextInt();
			input.nextLine();

			if (numQue <= 0)
				System.out.println("Error! Invalid number, Please try again!");

		} while (numQue <= 0);

		System.out.println("Do you want to generate the exam automatically? (true/false)");
		boolean isAuto = input.nextBoolean();

		Exam exam = null;
		try {
			if (isAuto) {
				exam = new AutomaticExam(repo, numQue);
				System.out.println("is auto");
			} else {
				exam = new MenualExam(repo, numQue, input);
			}

		} catch (NumOfQuestionsException e) {
			System.out.println("Error! " + e.getMessage());
		}

		exam.createExam();

		exam.writeExam(true); // write solution
		String examPath = exam.writeExam(false); // write exam
		System.out.println("Test written seccefully you can find it in " + examPath);
		System.out.println("Press any key to continue...");
		System.in.read();
	}

	/**
	 * Ask the user to user to select a question and then deletes it from the repo
	 * 
	 * @param repo the program's repository
	 */
	private static void deleteQuestion(Repo repo, Scanner input) {
		// Scanner input = new Scanner(System.in);
		boolean questionExist = false;
		int id = 0;

		do {
			System.out.println(repo.toString());
			System.out.println("Select an dbproject.Question to remove: ");
			id = input.nextInt();
			input.nextLine();
			questionExist = repo.deleteQuestionById(id);

			if (!questionExist)
				System.out.println("Error! dbproject.Question dosen't exist!");

		} while (!questionExist);

	}

	/**
	 * Ask the user to user to select a question the an answer from the question and
	 * removes it from the question's possible answers
	 * 
	 * @param repo the program's repository
	 */
	private static void deleteAnswerFromAQuestion(Repo repo, Scanner input) {
		// Scanner input = new Scanner(System.in);
		boolean answerExist = false;
		int selection = 0;
		Question que = Repo.selectQuestionFromRepo(repo, input);

		if (!(que instanceof MultiSelectQuestion)) {
			System.out.println("Error! Can't remove answers from a non Amercian dbproject.Question!");
			return;
		}

		MultiSelectQuestion multiQue = (MultiSelectQuestion) que;

		if (multiQue.getNumAnswers() == 0) {
			System.out.println("Error! No answers to remove!");
			return;
		}

		do {
			System.out.println(multiQue.toString());
			System.out.println("Select an Answers to remove: ");
			selection = input.nextInt();
			input.nextLine();
			answerExist = multiQue.deleteAnswerByIndex(--selection);

			if (!answerExist)
				System.out.println("Error! dbproject.Answer dosen't exist!");

		} while (!answerExist);

	}

	/**
	 * Ask the user to user to select a question and an answer from the repo then
	 * adds the answer to the question's possible answers
	 * 
	 * @param repo the program's repository
	 */
	public static void appendAnswerToQuestion(Repo repo, Scanner input) {
		// Scanner input = new Scanner(System.in);
		Question que = Repo.selectQuestionFromRepo(repo, input);
		String ans = Repo.selectAnswerFromRepo(repo, input);

		if (!(que instanceof MultiSelectQuestion)) {
			System.out.println("Error! That's not a Multi Select dbproject.Question, Can't add answers!");
			return;
		}

		System.out.println("Is the answers youv'e picked correct? (true/false): ");
		boolean isCorrect = input.nextBoolean();

		boolean res = ((MultiSelectQuestion) que).addAnswer(new Answer(ans, isCorrect));

		if (!res)
			System.out.println("Error! No more question could be added, reached full capacity");

	}

	/**
	 * Asks the user for a question and adds it to the repo
	 * 
	 * @param repo the program's repository
	 */
	private static void addQuestion(Repo repo, Scanner input) {
		// Scanner input = new Scanner(System.in);
		String text = "";
		// boolean res = false;;
		Question que = null;
		boolean isValid = false;
		int selction = 0;

		do {
			do {

				System.out.println("Select a question type: ");
				System.out.println("1. Multi-Selction question");
				System.out.println("2. Open-Ended question");
				selction = input.nextInt();
				input.nextLine();

				selction--;
				isValid = selction >= 0 && selction < 2;

				if (!isValid) {
					System.out.println("Error! Type dosen't exist, Try again!");
				}

			} while (!isValid);

			System.out.println("Enter a question: ");
			text = input.nextLine();
			Difficulty diff = getDifficultyFromUser(input);

			if (selction != 0) {
				System.out.println("Enter The school solution: ");
				String solution = input.nextLine();// inputPargraph(input);
				que = new OpenEndedQuestion(text, solution, diff);

			} else {
				que = new MultiSelectQuestion(text, diff);
			}

			isValid = repo.addQuestion(que);
			// isValid = res.compareTo(ArrayControl.Sucsses) == 0;
			if (!isValid)
				System.out.println("An error occurred while creating the question! Please try again!");

		} while (!isValid);

	}

	/**
	 * Asks the user for an answers and adds it to the repo
	 * 
	 * @param repo the program's repository
	 */
	private static void addAnswer(Repo repo, Scanner input) {
		// Scanner input = new Scanner(System.in);
		boolean res = true;
		String newAnswer = "";

		do {
			System.out.println("Enter an answer: ");
			newAnswer = input.nextLine();
			res = repo.addAnswer(newAnswer);

			if (!res)
				System.out.println("Error! dbproject.Answer already exists!");

		} while (!res);

	}

	/**
	 * Adds the hard codded questions to the repo
	 * 
	 * @param repo the program's repository
	 */
	public static void hardCoddedQue(Repo repo, Subject subject) {
		MultiSelectQuestion[] multiQuestions = new MultiSelectQuestion[20];
		OpenEndedQuestion[] openQuestions = new OpenEndedQuestion[10];

		if (subject == Subject.History) {
			multiQuestions[0] = new MultiSelectQuestion(
					"What year did the Titanic sink in the Atlantic Ocean on 15 April, on its maiden voyage from Southampton?",
					Difficulty.Moderate);
			multiQuestions[0].addAnswer(new Answer("1908", false));
			multiQuestions[0].addAnswer(new Answer("1912", true));
			multiQuestions[0].addAnswer(new Answer("1914", false));
			multiQuestions[0].addAnswer(new Answer("1920", false));
			// dbproject.Answer: B
			multiQuestions[5] = new MultiSelectQuestion("Who was Prime Minister of Great Britain from 1841 to 1846?",
					Difficulty.Moderate);
			multiQuestions[5].addAnswer(new Answer("William Gladstone", false));
			multiQuestions[5].addAnswer(new Answer("Benjamin Disraeli", false));
			multiQuestions[5].addAnswer(new Answer("Robert Peel", true));
			multiQuestions[5].addAnswer(new Answer("Lord Palmerston", false));
			// dbproject.Answer: C

			multiQuestions[7] = new MultiSelectQuestion("Who was the first person in space?", Difficulty.Easy);
			multiQuestions[7].addAnswer(new Answer("Yuri Gagarin  ", false));
			multiQuestions[7].addAnswer(new Answer("Neil Armstrong", false));
			multiQuestions[7].addAnswer(new Answer("John Glenn    ", false));
			multiQuestions[7].addAnswer(new Answer("Alan Shepard  ", false));
			// dbproject.Answer: A
			openQuestions[8] = new OpenEndedQuestion("What is the name of the first human to fly?",
					"Orville Wright, United States", Difficulty.Easy);
			openQuestions[4] = new OpenEndedQuestion("What is the name of the first man on the moon?",
					"Neil Armstrong, United States", Difficulty.Easy);

			multiQuestions[3] = new MultiSelectQuestion("Which metal was discovered by Hans Christian Oersted in 1825?",
					Difficulty.Hard);
			multiQuestions[3].addAnswer(new Answer("Copper", false));
			multiQuestions[3].addAnswer(new Answer("Zinc", false));
			multiQuestions[3].addAnswer(new Answer("Nickel", false));
			multiQuestions[3].addAnswer(new Answer("Aluminium", true));
			// dbproject.Answer: D
		} else if (subject == Subject.Geography) {
			multiQuestions[1] = new MultiSelectQuestion(
					"What is the name of the biggest technology company in South Korea?", Difficulty.Easy);
			multiQuestions[1].addAnswer(new Answer("Samsung", true));
			multiQuestions[1].addAnswer(new Answer("LG", false));
			multiQuestions[1].addAnswer(new Answer("Hyundai", false));
			multiQuestions[1].addAnswer(new Answer("SK", false));
			// dbproject.Answer: A
			multiQuestions[6] = new MultiSelectQuestion("What is the name of the largest desert in the world?",
					Difficulty.Easy);
			multiQuestions[6].addAnswer(new Answer("Sahara", false));
			multiQuestions[6].addAnswer(new Answer("Gobi", false));
			multiQuestions[6].addAnswer(new Answer("Kalahari", false));
			multiQuestions[6].addAnswer(new Answer("Antarctic", true));
			// dbproject.Answer: D

			openQuestions[3] = new OpenEndedQuestion("What is the name of the current president of the United States?",
					"Joe Biden, United States", Difficulty.Easy);

			openQuestions[0] = new OpenEndedQuestion("What is the capital of France?", "Paris, France",
					Difficulty.Easy);
			openQuestions[1] = new OpenEndedQuestion("What is the name of the largest ocean in the world?",
					"Pacific Ocean", Difficulty.Easy);
			openQuestions[2] = new OpenEndedQuestion("What is the name of the highest mountain in the world?",
					"Mount Everest, Nepal", Difficulty.Moderate);

			openQuestions[9] = new OpenEndedQuestion("What is the name of the largest country in the world by area?",
					"Russia", Difficulty.Moderate);

		} else if (subject == Subject.Science) {

			multiQuestions[2] = new MultiSelectQuestion("What is the name of the largest bone in the human body?",
					Difficulty.Hard);
			multiQuestions[2].addAnswer(new Answer("Humerus", false));
			multiQuestions[2].addAnswer(new Answer("Tibia", false));
			multiQuestions[2].addAnswer(new Answer("Femur", true));
			multiQuestions[2].addAnswer(new Answer("Pelvis", false));
			// dbproject.Answer: C

			multiQuestions[4] = new MultiSelectQuestion("How many breaths does the human body take daily?",
					Difficulty.Hard);
			multiQuestions[4].addAnswer(new Answer("About 10,000", false));
			multiQuestions[4].addAnswer(new Answer("About 20,000", true));
			multiQuestions[4].addAnswer(new Answer("About 30,000", false));
			multiQuestions[4].addAnswer(new Answer("About 50,000", false));
			// dbproject.Answer: B

			multiQuestions[8] = new MultiSelectQuestion("What is the chemical symbol of Tungsten?", Difficulty.Easy);
			multiQuestions[8].addAnswer(new Answer("Tn", false));
			multiQuestions[8].addAnswer(new Answer("Tu", false));
			multiQuestions[8].addAnswer(new Answer("Tg", false));
			multiQuestions[8].addAnswer(new Answer("W ", true));
			// dbproject.Answer: D
			multiQuestions[9] = new MultiSelectQuestion("What do you use to measure rainfall?", Difficulty.Moderate);
			multiQuestions[9].addAnswer(new Answer("Barometer  ", false));
			multiQuestions[9].addAnswer(new Answer("Hygrometer ", false));
			multiQuestions[9].addAnswer(new Answer("Pluviometer", true));
			multiQuestions[9].addAnswer(new Answer("Thermometer", false));
			// dbproject.Answer: C
			multiQuestions[10] = new MultiSelectQuestion("Which is the smallest planet in our solar system?",
					Difficulty.Easy);
			multiQuestions[10].addAnswer(new Answer("Venus  ", false));
			multiQuestions[10].addAnswer(new Answer("Mars   ", false));
			multiQuestions[10].addAnswer(new Answer("Mercury", true));
			multiQuestions[10].addAnswer(new Answer("Pluto  ", false));
			// dbproject.Answer: C
			
			openQuestions[5] = new OpenEndedQuestion("What is the name of the element with the atomic number 1?",
					"Hydrogen", Difficulty.Easy);
			openQuestions[6] = new OpenEndedQuestion(
					"What is the name of the force that keeps the planets in orbit around the sun?", "Gravity",
					Difficulty.Moderate);
			openQuestions[7] = new OpenEndedQuestion(
					"What is the name of the process by which plants make food from sunlight?", "Photosynthesis",
					Difficulty.Moderate);
		}else if(subject == subject.Math) {
    	  openQuestions[0] = new OpenEndedQuestion("What is the equation for the area of a circle?", "A = πr²", Difficulty.Moderate);
    	  openQuestions[1] = new OpenEndedQuestion("What is the equation for the volume of a sphere?", "V = 4/3πr³", Difficulty.Moderate);
    	  openQuestions[2] = new OpenEndedQuestion("What is the equation for the slope of a line?", "m = (y₂ - y₁)/(x₂ - x₁)", Difficulty.Moderate);
    	  openQuestions[3] = new OpenEndedQuestion("What is the equation for the Pythagorean Theorem?", "a² + b² = c²", Difficulty.Moderate);
    	  
    	  multiQuestions[0] = new MultiSelectQuestion("Which of the following are prime numbers?",
					Difficulty.Easy);
			multiQuestions[0].addAnswer(new Answer("2", true));
			multiQuestions[0].addAnswer(new Answer("1", false));
			multiQuestions[0].addAnswer(new Answer("21", false));
			multiQuestions[0].addAnswer(new Answer("4", false));
			multiQuestions[0].addAnswer(new Answer("11  ", false));
		}
		
		

		for (int i = 0; i < multiQuestions.length; i++) {
			repo.addQuestion(multiQuestions[i]);
		}

		for (int i = 0; i < openQuestions.length; i++) {
			repo.addQuestion(openQuestions[i]);
		}
	}

	/**
	 * Prints the program menu
	 */
	public static void printMenu() {
		System.out.println("\nWelcome to my Test Maker!");
		System.out.println("Please select an option: (-1 to exit)");
		System.out.println("1. Display all of the question from the repo (and their answers)");
		System.out.println("2. Add a new answers to the repo");
		System.out.println("3. Append an answer to an existing question");
		System.out.println("4. Add a new question");
//		System.out.println("4. Update an exsisting question");
//		System.out.println("5. Update an exsisting answer to a question");
		System.out.println("5. Delete an answers to a question");
		System.out.println("6. Delete a question");
		System.out.println("7. Generate a new test");
		// System.out.println("-1 to exit");
	}

//	/**
//	 * Ask the user to select an answer from the repo and retrives it
//	 * 
//	 * @param repo the program's repository
//	 * @return the question that was selected
//	 */
//	private static dbproject.Question selectQuestionFromRepo(dbproject.OldProject.Repo repo) {
//		Scanner input = new Scanner(System.in);
//		dbproject.Question que = null;
//		int selection = 0;
//
//		do {
//			System.out.print(repo);
//			System.out.println("Select a question: ");
//			selection = input.nextInt();
//			que = repo.getQuestionByID(selection);
//
//			if (que == null)
//				System.out.println("Error! dbproject.Question dosen't exist!");
//
//		} while (que == null);
//
//		return que;
//	}

//	/**
//	 * Ask the user to select a question from the repo and retrives it
//	 * 
//	 * @param repo the program's repository
//	 * @return the answers that was selected
//	 */
//	private static String selectAnswerFromRepo(dbproject.OldProject.Repo repo) {
//		Scanner input = new Scanner(System.in);
//		String ans = null;
//		int selection = 0;
//
//		do {
//			System.out.print(repo.toStringAnswers());
//			System.out.println("Select an answer: ");
//			selection = input.nextInt();
//			ans = repo.getAnswerByIndex(--selection);
//
//			if (ans == null)
//				System.out.println("Error! dbproject.Answer dosen't exist!");
//
//		} while (ans == null);
//
//		return ans;
//	}

	// didn't use at the end
	private static String inputPargraph(Scanner input) {
		StringBuffer buffer = new StringBuffer();
		String s = "";
		boolean fin = false;

		do {

			String line = input.nextLine();
			if (line.equals("end")) {
				if (buffer.isEmpty())
					System.out.println("Error! Noting was entered, Please try again!");
				else
					fin = true;
			} else {
				buffer.append(line);
				buffer.append("\n");
			}
		} while (!fin || buffer.isEmpty());

		return buffer.toString();
	}

	/**
	 * @param input where to read the difficulty from
	 * @return difficulty from the user
	 */
	private static Difficulty getDifficultyFromUser(Scanner input) {
		Difficulty[] difficulties = Difficulty.values();
		int diff = 0;
		boolean isValid = false;

		do {
			System.out.println("Select difficulty: ");
			for (Difficulty d : difficulties) {
				System.out.println((d.ordinal() + 1) + ". " + d.name());
			}
			diff = input.nextInt();
			input.nextLine(); // clears buffer
			diff--;

			isValid = diff < difficulties.length && diff >= 0;
			if (!isValid)
				System.out.println("Error! Difficulty dosen't exist, Try again!");

		} while (!isValid);

		return difficulties[diff];

	}

	/**
	 * @param input where to read the subject from
	 * @return subject from the user
	 */
	/*private static Subject getSubjectFromUser(Scanner input) { // sad i am not allowed to use generics
		Subject[] subjects = Subject.values();
		int subject = 0;
		boolean isValid = false;

		do {
			System.out.println("Select a subject: ");
			for (Subject d : subjects) {
				System.out.println((d.ordinal() + 1) + ". " + d.name());
			}
			subject = input.nextInt();
			input.nextLine(); // clears buffer
			subject--;

			isValid = subject < subjects.length && subject >= 0;
			if (!isValid)
				System.out.println("Error! Subject dosen't exist, Try again!");

		} while (!isValid);

		return subjects[subject];
	}*/

}
