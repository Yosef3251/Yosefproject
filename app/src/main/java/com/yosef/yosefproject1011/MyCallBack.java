package com.yosef.yosefproject1011;

import com.yosef.yosefproject1011.QuestionPack.Question;
import com.yosef.yosefproject1011.SubjectPack.Subject;

import java.util.ArrayList;

public interface MyCallBack {
     void onCallback(ArrayList<Question> attractionsList);

     void onCallBackSub(ArrayList<Subject> sub);
}
