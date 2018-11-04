package com.example.zgh.myapplication.calculTool;


import com.example.zgh.myapplication.mathUtil.ErrorTypeException;

@SuppressWarnings("unused")
public interface UnaOperator<T> {
    T operator(T a) throws ErrorTypeException;
}
