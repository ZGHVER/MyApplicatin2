package com.example.zgh.myapplication.calculTool;

import com.example.zgh.myapplication.mathUtil.ErrorTypeException;

import java.util.NoSuchElementException;
import java.util.TreeMap;


@SuppressWarnings("unused")
public class Analyzer<T> {
    private TreeMap<String, T> ValueMap;
    private TreeMap<String, BinOperator<T>> LvAOperator;
    private TreeMap<String, BinOperator<T>> LvBOperator;
    private TreeMap<String, UnaOperator<T>> UnaOperate;

    void setMember(Queue<T> member) {
        this.member = member;
    }

    void setOperates(Queue<String> operates) {
        this.operates = operates;
    }

    private Queue<T> member = new Queue<>();
    private Queue<String> operates = new Queue<>();

    Analyzer() {
        initialise();
    }

    public Analyzer(Queue<T> EquationMember, Queue<String> EquationOperator) {
        this.member = EquationMember;
        this.operates = EquationOperator;
        initialise();
    }

    private void initialise() {
        ValueMap = new TreeMap<>();
        LvAOperator = new TreeMap<>();
        LvBOperator = new TreeMap<>();
        UnaOperate = new TreeMap<>();
    }

    public void AddValue(String Key, T Value) {
        ValueMap.put(Key, Value);
    }

    public void DelValue(String key) {
        ValueMap.remove(key);
    }

    public void AddALvOperator(String key, BinOperator<T> operate) {
        LvAOperator.put(key, operate);
    }

    public void DelALvOperator(String key) {
        LvAOperator.remove(key);
    }

    public void AddBLvOperator(String key, BinOperator<T> operator) {
        LvBOperator.put(key, operator);
    }

    public void DelBlvoperator(String key) {
        LvBOperator.remove(key);
    }

    public void AddUnOperator(String key, UnaOperator<T> operator) {
        UnaOperate.put(key, operator);
    }

    public void DelUnoperator(String key) {
        UnaOperate.remove(key);
    }

    public T Analyze(String s) throws lessElementException, ErrorTypeException {
        splitStringToQueues(s);
        return AnalyzeQueues();
    }

    private void splitStringToQueues(String equation) throws ErrorTypeException {
        String[] ss = toStringArrays(equation);
        for (int i = 0; i < equation.length(); i++) {
            resolve(ss[i]);
        }
    }

    private void resolve(String s) throws ErrorTypeException {
        switch (s) {
            case "(":
                operates.enqueue(s);
                break;
            case ")":
                operates.enqueue(s);
                break;
            default:
                if (ContainsOperator(s))
                    operates.enqueue(s);
                else
                    AddInMember(s);
                break;
        }
    }

    private void AddInMember(String s) throws ErrorTypeException {
        try {
            member.enqueue(ValueMap.get(s));
        } catch (NoSuchElementException e) {
            throw new ErrorTypeException();
        }
    }

    private String[] toStringArrays(String s) {
        String[] o = new String[s.length()];
        for (int i = 0; i < s.length(); i++)
            o[i] = String.valueOf(s.charAt(i));
        return o;
    }

    private boolean ContainsOperator(String operator) {
        return LvAOperator.containsKey(operator) ||
                LvBOperator.containsKey(operator) ||
                UnaOperate.containsKey(operator);
    }

    T AnalyzeQueues() throws ErrorTypeException, lessElementException {
        Queue<T> n = new Queue<>();
        Queue<String> s = new Queue<>();
        String s1;
        if (!operates.contains("("))
            return GetValueOfQueues(member, operates);
        while (!operates.isEmpty()) {
            s1 = operates.dequeue();
            try {
                switch (s1) {
                    case ")":
                        member.appendInFirst(AnalyzeQueues());
                        break;
                    case "(":
                        n.enqueue(member.dequeue());
                        return GetValueOfQueues(n, s);
                    default:
                        s.enqueue(s1);
                        n.enqueue(member.dequeue());
                        break;
                }
            } catch (NullPointerException e) {
                throw new lessElementException();
            }
        }
        if (!member.isEmpty())          //如果算式最后有括号内的表达式
            n.enqueue(member.dequeue());
        return GetValueOfQueues(n, s);
    }

    private T GetValueOfQueues(Queue<T> number, Queue<String> opera)
            throws ErrorTypeException, lessElementException {
        Queue<T> TNumberA = new Queue<>();
        Queue<String> TOperaA = new Queue<>();
        Queue<T> TNumberB = new Queue<>();
        Queue<String> TOperaB = new Queue<>();

        try {
            while (!opera.isEmpty()) {
                String nowOperator = opera.dequeue();
                if (UnaOperate.containsKey(nowOperator)) {
                    T element = number.dequeue();
                    T tempValue = UnaOperate.get(nowOperator).operator(element);
                    number.appendInFirst(tempValue);
                } else {
                    TNumberA.enqueue(number.dequeue());
                    TOperaA.enqueue(nowOperator);
                }
            }
            TNumberA.enqueue(number.dequeue());

            while (!TOperaA.isEmpty()) {
                String tempOpera = TOperaA.dequeue();
                if (LvAOperator.containsKey(tempOpera)) {
                    T temp = LvAOperator.get(tempOpera).operate(TNumberA.dequeue(), TNumberA.dequeue());
                    TNumberA.appendInFirst(temp);
                } else {
                    TOperaB.enqueue(tempOpera);
                    TNumberB.enqueue(TNumberA.dequeue());
                }
            }
            TNumberB.enqueue(TNumberA.dequeue());

            while (!TOperaB.isEmpty()) {
                String op = TOperaB.dequeue();
                T tem = LvBOperator.get(op).operate(TNumberB.dequeue(), TNumberB.dequeue());
                TNumberB.appendInFirst(tem);
            }
        } catch (NullPointerException e) {
            throw new lessElementException();
        }
        return TNumberB.dequeue();
    }
}
