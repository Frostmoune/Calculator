package com.example.abc810343087.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.text.method.ScrollingMovementMethod;
import android.text.method.LinkMovementMethod;
import android.app.Activity.*;

public class MainActivity extends AppCompatActivity {
    private calculator Mycalculator;
    private static final char []Output_ids={'0','1','2','3','4','5','6','7','8','9','+','-','*','/','(',')','.'};//输入键对应的字符
    private static final int []Output_id_names={R.id.but0,R.id.but1,R.id.but2,R.id.but3,R.id.but4,R.id.but5,R.id.but6,
                                                    R.id.but7,R.id.but8,R.id.but9, R.id.butPlus,R.id.butMinus,R.id.butMultiply,
                                                    R.id.butDivide,R.id.butParentheses_left,R.id.butParentheses_right,R.id.butPoint};//输入键对应的id
    private static final int[]function_id_names={R.id.butEqual,R.id.butUp,R.id.butDown,R.id.butRemove,R.id.butClear};
    private static final int[]Radix_number={2,8,10,16};
    public void Prepare_for_output(){
        for(int i=0;i<17;++i){
            Button newbut=(Button)findViewById(Output_id_names[i]);//得到相应按钮
            newbut.setTag(i);//设置标签
            newbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Mycalculator.Removeall();
                    addExpressions(Output_ids[(int)v.getTag()]);
                }
            });//设置相应按钮的onclick事件
        }
    }//用于设定输入键的onclick事件
    public void Prepare_for_advanced_function(){
        Button newbut=(Button)findViewById(R.id.butTo);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.Clear();
                EditText nowedit=(EditText)findViewById(R.id.Edit);
                if(nowedit.getText().toString().length()>0) {
                    Mycalculator.setExpressions(nowedit.getText().toString());
                    Mycalculator.Calculate();
                }
                TextView nowtext=(TextView)findViewById(R.id.Output);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//输入求值的事件
        newbut=(Button)findViewById(R.id.butCal);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.Clear();
                RadioGroup group=(RadioGroup)findViewById(R.id.radioGroup);
                TextView mytextb=(TextView)findViewById(R.id.Output_2);
                mytextb.setText("");
                TextView mytexta=(TextView)findViewById(R.id.Output);
                mytexta.setText("");
                for(int i=0;i<group.getChildCount();++i){
                    RadioButton now=(RadioButton)group.getChildAt(i);
                    if(now.isChecked()){
                        EditText editr=(EditText)findViewById(R.id.Editr),editn=(EditText)findViewById(R.id.Editn);
                        String rstr=editr.getText().toString(),nstr=editn.getText().toString();
                        if(rstr.length()>0&&nstr.length()>0){
                            double r=Double.valueOf(editr.getText().toString()),n=Double.valueOf(editn.getText().toString());
                            if(i<=1&&(r!=(int)r||n!=(int)n)){
                                Mycalculator.setExpressions("Wrong Input!");
                                mytexta.setText(Mycalculator.getExpressions());
                            }
                            else{
                                switch(i){
                                    case 0:
                                        Mycalculator.Combination((int)n,(int)r);
                                        break;
                                    case 1:
                                        Mycalculator.Permutation((int)n,(int)r);
                                        break;
                                    case 2:
                                        Mycalculator.Power(n,r);
                                        break;
                                    default:
                                        break;
                                }
                                String nowexp=Mycalculator.getExpressions();
                                if(nowexp.equals("Wrong Input!")){
                                    mytexta.setText(Mycalculator.getExpressions());
                                }
                                else{
                                    mytextb.setText(Mycalculator.getExpressions());
                                }
                            }
                        }
                    }
                }
            }
        });
        newbut=(Button)findViewById(R.id.butTo_radix);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.Clear();
                RadioGroup groupA=(RadioGroup)findViewById(R.id.radixA),groupB=(RadioGroup)findViewById(R.id.radixB);
                TextView mytextb=(TextView)findViewById(R.id.Result_radix);
                mytextb.setText("");
                TextView mytexta=(TextView)findViewById(R.id.Output);
                mytexta.setText("");
                int from=0,to=0;
                for(int i=0;i<groupA.getChildCount();++i){
                    RadioButton nowfrom=(RadioButton)groupA.getChildAt(i);
                    if(nowfrom.isChecked()){
                        EditText nowedit=(EditText)findViewById(R.id.Edit_radix);
                        Mycalculator.setExpressions(nowedit.getText().toString());
                        from=Radix_number[i];
                    }
                }
                for(int i=0;i<groupA.getChildCount();++i){
                    RadioButton nowto=(RadioButton)groupB.getChildAt(i);
                    if(nowto.isChecked()){
                        to=Radix_number[i];
                        if(from!=0)Mycalculator.changeRadix(from,to);
                        if(Mycalculator.getExpressions().equals("Wrong Input!")){
                            mytexta.setText(Mycalculator.getExpressions());
                        }
                        else{
                            mytextb.setText(Mycalculator.getExpressions());
                        }
                    }
                }
            }
        });
    }
    public void Prepare_for_function(){
        Button newbut=(Button)findViewById(R.id.butEqual);
        newbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mycalculator.getExpressions().length()>0)Mycalculator.Calculate();
                TextView nowtext=(TextView)findViewById(R.id.input);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//设定“=”号的onclick事件：用于计算
        newbut=(Button)findViewById(R.id.butRemove);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.Remove();
                TextView nowtext=(TextView)findViewById(R.id.input);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//退格的事件
        newbut=(Button)findViewById(R.id.butUp);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.ToUp();
                TextView nowtext=(TextView)findViewById(R.id.input);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//返回上一个表达式的事件
        newbut=(Button)findViewById(R.id.butDown);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.ToDown();
                TextView nowtext=(TextView)findViewById(R.id.input);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//返回下一个表达式的事件
        newbut=(Button)findViewById(R.id.butClear);
        newbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mycalculator.Clear();
                TextView nowtext=(TextView)findViewById(R.id.input);
                nowtext.setText(Mycalculator.getExpressions());
            }
        });//清除所有表达式的事件
        TextView nowtext=(TextView)findViewById(R.id.input);
        nowtext.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mycalculator=new calculator();
        Prepare_for_output();
        Prepare_for_function();
        Prepare_for_advanced_function();
    }//此函数实际相当于main函数
    public void addExpressions(char id){
        Mycalculator.add(id);
        TextView nowtext=(TextView)findViewById(R.id.input);
        nowtext.setText(Mycalculator.getExpressions());
    }//为表达式添加字符
}
