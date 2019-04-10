package com.example.utils;

import android.util.Xml;

import com.example.Models.Groups;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AnalysisUtils {
    public static List<Groups> getExercisesInfos(InputStream is) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<Groups> exercisesInfos=null;
        Groups exercisesInfo=null;
        int type = parser.getEventType();
        while (type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if ("infos".equals(parser.getName())){
                        exercisesInfos=new ArrayList<Groups>();
                    }else if ("exercises".equals(parser.getName())){
                        exercisesInfo=new Groups();
                        String ids=parser.getAttributeValue(0);
                       // exercisesInfo.subjectId = Integer.parseInt(ids);
                    }else if ("subject".equals(parser.getName())){
                        String subject=parser.nextText();
                        //exercisesInfo.subject = subject;
                    }else if ("a".equals(parser.getName())){
                        String a=parser.nextText();
                       // exercisesInfo.a = a;
                    }else if ("b".equals(parser.getName())){
                        String b=parser.nextText();
                       // exercisesInfo.b = b;
                    }else if ("c".equals(parser.getName())){
                        String c=parser.nextText();
                       // exercisesInfo.c = c;
                    }else if ("d".equals(parser.getName())){
                        String d=parser.nextText();
                       // exercisesInfo.d = d;
                    }else if ("answer".equals(parser.getName())){
                        String answer=parser.nextText();
                      //  exercisesInfo.answer = Integer.parseInt(answer);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("exercises".equals(parser.getName())){
                        exercisesInfos.add(exercisesInfo);
                        exercisesInfo=null;
                    }
                    break;
            }
            type=parser.next();
        }
        return exercisesInfos;
    }
}
