package dbproject.DBClasses;

import dbproject.Exam;
import dbproject.Subject;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
   private  String firstName;
   private String lastName;
   private List<Subject> subjects;
   //private List<Exam> exams;

   private int ID;


    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = new ArrayList<>();
//        this.exams = new ArrayList<>();
    }

    public Teacher(int tid, String firstName, String lastName) {
        this(firstName, lastName);
        this.ID = tid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    public void addSubjects(List<Subject> subjects) {
        this.subjects.addAll(subjects);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
