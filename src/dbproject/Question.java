package dbproject;

import java.io.Serializable;

public class Question implements Serializable{
	private static int numQuestions = 1;
	
	protected String text;
	protected int id;
	protected Difficulty difficulty;
	protected boolean displaySolution;
	
	/**
	 * C'tor
	 * @param	text the question itself
	 */
	public Question(String text, Difficulty difficulty) {
		this.text = text;
		this.id = numQuestions++;
		this.difficulty = difficulty;
		this.displaySolution = false;
	}

	public Question(int id, String text, Difficulty difficulty) {
		this(text, difficulty);
		this.id = id;
	}
	
	public static void setNumQuestions(int numQuestions) {
		Question.numQuestions = numQuestions;
	}

	/**
	 * copy c'tor
	 * 
	 * @param other   object to copy
	 */
	Question(Question other) {
		this(other.text, other.difficulty);
	}

	/**
	 * @param displaySolution wheather 
	 */
	public void setDisplaySolution(boolean displaySolution) {
		this.displaySolution = displaySolution;
	}

	/**
	 * @return the question itself
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return object values
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(text);
		builder.append("\n");
		builder.append("ID: " + id);
		builder.append("\n");
		builder.append("Difficulty: ");
		builder.append(difficulty.name());
		builder.append("\n");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Question))
			return false;
		
		Question que = (Question) obj;
		
		return que.id == this.id && que.text == this.text && this.difficulty == que.difficulty;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}
}
