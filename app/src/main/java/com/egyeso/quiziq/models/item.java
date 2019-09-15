package com.egyeso.quiziq.models;

public class item {
    public final int ID;
    public final int ID_answer;
    public final String Question;
    public final String Answer_1;
    public final String Answer_2;
    public final String Answer_3;
    public final String Answer_4;


    public item(int ID, String question, String answer_1, String answer_2, String answer_3, String answer_4, int ID_answer) {
        this.ID = ID;
        Question = question;
        Answer_1 = answer_1;
        Answer_2 = answer_2;
        Answer_3 = answer_3;
        Answer_4 = answer_4;
        this.ID_answer = ID_answer;
    }
}
