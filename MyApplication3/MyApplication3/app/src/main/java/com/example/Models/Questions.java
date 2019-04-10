package com.example.Models;

public class Questions {
    public int background;//每章习题前边的序号背景
    public int subjectId;//每道习题的ID
    public String subject;//每道习题的题干
    public int ChoiceLength;
    public String[] Choice= new String[10];
    public String a;//每道题的A选项的url
    public String b;//每道题的B选项的url
    public String c;//每道题的C选项
    public String d;//每道题的D选项
    public String E;//每道题的E选项
    public int answer;//每道题的正确答案
    public int select;//用户选中的那项
}
