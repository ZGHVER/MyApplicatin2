package com.example.zgh.myapplication.calculTool;

import com.example.zgh.myapplication.mathUtil.ErrorTypeException;

@SuppressWarnings("unused")
public interface BinOperator<T> {
    T operate(T a,T b) throws ErrorTypeException;
}
