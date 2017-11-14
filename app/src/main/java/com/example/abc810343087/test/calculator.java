package com.example.abc810343087.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.text.method.LinkMovementMethod;
import android.app.Activity.*;

import java.math.BigDecimal;
import java.util.Vector;
import java.util.Stack;
import java.lang.*;

class MyString{
    public String exp;
    public boolean isNum;
    public MyString(String nowexp,boolean flag){
        exp=nowexp;
        isNum=flag;
    }
}//用于储存表达式中的字符串，isNum用于判断该字符串是否为数字

public class calculator {
    private String Expressions;
    //表达式（显示在显示框中的）
    private Vector<String> History_Expressions;
    //历史表达式
    private static boolean warningflag;
    //用于判断表达式是否合法
    private int Nowpos;
    //当前表达式在历史表达式的位置
    public calculator(){Expressions="";warningflag=true;History_Expressions=new Vector<String>();Nowpos=0;}
    //构造函数
    public void add(char id){
        if(Expressions==null){
            Expressions=String.valueOf(id);
        }
        else Expressions+=id;
    }
    //用于给添加数字或字符
    public String getExpressions(){
        return Expressions;
    }//表达式接口
    public void setExpressions(String exp){
        Expressions=exp;
    }
    public void Remove(){
        if(Expressions.length()>0)Expressions=Expressions.substring(0,Expressions.length()-1);
    }//用于计算器的退格
    public void Removeall(){
        if(!warningflag){
            Expressions=null;
            warningflag=true;
        }
    }//用于计算器的清屏
    private int sign(char ch){
        if (ch == '*' || ch == '/')return 2;
        if (ch == '+' || ch == '-')return 1;
        if (ch == ')')return 3;
        if (ch == '(')return -1;
        return 0;
    }//用于判断运算符优先级
    public void Calculate(){
        int i=0,j=0;
        boolean flag=false;
        Stack<MyString> nowstack=new Stack<MyString>(),tempstack=new Stack<MyString>();
        for(i=0;i<Expressions.length();++i){
            if(Expressions.charAt(i)=='.'&&!Character.isDigit(Expressions.charAt(i+1))&&i<Expressions.length()-1){
                warningflag=false;
            }
            if(!warningflag)break;
            if(Expressions.charAt(i)=='.'||Character.isDigit(Expressions.charAt(i))||((Expressions.charAt(i) == '-') && (i - 1<0 || Expressions.charAt(i-1)=='('))){
                //处理负数
                flag=true;
                if (!Character.isDigit(Expressions.charAt(i))) {
                    j = i + 1;
                }
                else j=i;
                for(;j<Expressions.length();++j){
                    if(!Character.isDigit(Expressions.charAt(j))&&Expressions.charAt(j)!='.'){
                        break;
                    }
                }//用于从字符中得到数字
                nowstack.push(new MyString(Expressions.substring(i,j),true));
                i=j-1;
                continue;
            }
            if(Expressions.charAt(i)=='('){
                tempstack.push(new MyString(Expressions.substring(i,i+1),false));
            }//处理左括号
            if(Expressions.charAt(i)==')'){
                while(!tempstack.empty()){
                    MyString now=tempstack.pop();
                    if(now.exp.equals("("))break;
                    nowstack.push(now);
                }
            }//处理右括号
            if(sign(Expressions.charAt(i))>0&&sign(Expressions.charAt(i))<3){
                if(tempstack.empty()||tempstack.peek().exp.equals("(")){
                    tempstack.push(new MyString(Expressions.substring(i,i+1),false));
                }
                else if(sign(Expressions.charAt(i))>sign(tempstack.peek().exp.charAt(0))){
                    tempstack.push(new MyString(Expressions.substring(i,i+1),false));
                }
                else{
                    while(!tempstack.empty()&&sign(Expressions.charAt(i))<=sign(tempstack.peek().exp.charAt(0))&&!tempstack.peek().exp.equals("(")){
                        nowstack.push(tempstack.pop());
                    }
                    tempstack.push(new MyString(Expressions.substring(i,i+1),false));
                }
            }//处理加减乘除
        }
        if(!flag){
            warningflag=false;
        }
        while(!tempstack.empty()){
            nowstack.push(tempstack.pop());
        }//以上代码用于中缀转后缀
        while(!nowstack.empty()){
            tempstack.push(nowstack.pop());
        }
        Stack<Double> numstack=new Stack<Double>();
        while(!tempstack.empty()){
            if(!warningflag){
                break;
            }
            if(tempstack.peek().isNum){
                String now=tempstack.pop().exp;
                double res=Double.valueOf(now);
                numstack.push(res);
            }
            else{
                String now=tempstack.pop().exp;
                double a=numstack.pop();
                if(!numstack.empty()){
                    double b=numstack.pop(),res=0;
                    if(now.equals("+"))res=b+a;
                    if(now.equals("-"))res=b-a;
                    if(now.equals("*"))res=b*a;
                    if(now.equals("/")){
                        if(a==0){
                            warningflag=false;
                            break;
                        }
                        res=b/a;
                    }
                    numstack.push(res);
                }
                else {
                    warningflag=false;
                    numstack.push(a);
                    break;
                }
            }
        }//以上代码用于后缀表达式求值
        if(warningflag){
            double res=numstack.pop();
            while(!numstack.empty()){
                res*=numstack.pop();
            }
            BigDecimal b=new BigDecimal(res);
            Expressions=b.setScale(4,BigDecimal.ROUND_HALF_UP).toString();
            History_Expressions.add(Expressions);
            Nowpos=History_Expressions.size()-1;
        }
        else {
            Expressions="Wrong Input!";
        }
    }
    public void ToUp(){
        if(Nowpos>0){
            Nowpos--;
            Expressions=History_Expressions.get(Nowpos);
        }
    }//返回历史表达式的上一个
    public void ToDown(){
        if(Nowpos<History_Expressions.size()-1){
            Nowpos++;
            Expressions=History_Expressions.get(Nowpos);
        }
    }//返回历史表达式的下一个
    public void Clear(){
        History_Expressions.clear();
        Nowpos=0;
        Expressions="";
    }//清除所有历史表达式
    public int Factorial(int n){
        if(!warningflag){
            warningflag=true;
        }
        if(n<0){
            warningflag=false;
            Expressions="Wrong Input!";
            return -1;
        }
        int res=1;
        for(int i=2;i<=n;++i){
            res*=i;
        }
        Expressions=String.valueOf(res);
        return res;
    }
    public int Permutation(int n,int r){
        if(!warningflag){
            warningflag=true;
        }
        if(n<r){
            warningflag=false;
            Expressions="Wrong Input!";
            return -1;
        }
        int res=1,beg=n-r+1;
        for(int i=beg;i<=n;++i){
            res*=i;
        }
        Expressions=String.valueOf(res);
        return res;
    }
    public int Combination(int n,int r){
        if(!warningflag){
            warningflag=true;
        }
        if(n<r){
            warningflag=false;
            Expressions="Wrong Input!";
            return -1;
        }
        int res=Permutation(n,r)/Factorial(r);
        Expressions=String.valueOf(res);
        return res;
    }
    public double Power(double n,double r){
        double res=Math.pow(n,r);
        Expressions=Double.toString(res);
        return res;
    }
    private int changeToDec(int from){
        int num=0,size=Expressions.length()-1,nownum=0;
        for(int i=0;i<=size;++i){
            if(Character.isDigit(Expressions.charAt(i))){
                nownum=Expressions.charAt(i)-'0';
                nownum*=Math.pow(from,size-i);
                num+=nownum;
            }
            else if(from==16){
                if(Expressions.charAt(i)<='F'&&Expressions.charAt(i)>='A'){
                    nownum=Expressions.charAt(i)-'A'+10;
                    nownum*=Math.pow(from,size-i);
                    num+=nownum;
                }
                if(Expressions.charAt(i)<='f'&&Expressions.charAt(i)>='a'){
                    nownum=Expressions.charAt(i)-'a'+10;
                    nownum*=Math.pow(from,size-i);
                    num+=nownum;
                }
                else{
                    warningflag=false;
                    break;
                }
            }
            else {
                warningflag=false;
                break;
            }
        }
        if(warningflag)return num;
        return -1;
    }
    private String DecToOther(int num,int to){
        String newexp="";
        switch (to) {
            case 2:
                newexp=Integer.toBinaryString(num);
                break;
            case 8:
                newexp=Integer.toOctalString(num);
                break;
            case 16:
                newexp=Integer.toHexString(num);
                break;
            case 10:
                newexp=Integer.toString(num);
                break;
            default:
                warningflag=false;
                break;
        }
        return newexp;
    }
    public void changeRadix(int from,int to){
        warningflag=true;
        if(from!=to){
            int num=changeToDec(from);
            if(!warningflag){
                Expressions="Wrong Input!";
            }
            Expressions=DecToOther(num,to);
            if(!warningflag){
                Expressions="Wrong Input!";
            }
        }
    }
}
