package com.example.zgh.myapplication.calculTool;

import com.example.zgh.myapplication.mathUtil.ErrorTypeException;

public class EquationManger<T> {
    public Analyzer<T> analyzer = new Analyzer<>();
    private Queue<T> equationMember = new Queue<>();
    private Queue<String> operation = new Queue<>();

    public EquationManger() {

    }

    public void addEquationOperate(String operator){
        operation.enqueue(operator);
    }

    public void addEquationMember(T Member){
        equationMember.enqueue(Member);
    }

    public void DelAOperator(){
        operation.dequeue();
    }

    public void DelAEqutonMember(){
        equationMember.dequeue();
    }

    public void clean(){
        equationMember.clean();
        operation.clean();
    }

    public T getEquationValue() throws ErrorTypeException, lessElementException {
        if(equationMember.isEmpty())
            return null;
        analyzer.setMember(equationMember);
        analyzer.setOperates(operation);
        return analyzer.AnalyzeQueues();
    }
}
