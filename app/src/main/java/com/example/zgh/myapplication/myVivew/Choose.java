package com.example.zgh.myapplication.myVivew;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.zgh.myapplication.R;

public class Choose extends LinearLayout {

    private EditText text1;
    private EditText text2;
    private String choose1;
    private String choose2;

    public Choose(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.choose,this,true);
        text1 = findViewById(R.id.ed1);
        text2 = findViewById(R.id.ed2);
    }

    public Choose(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.choose,this,true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Choose);
         choose1 = typedArray.getString(R.styleable.Choose_choose1);
         choose2 = typedArray.getString(R.styleable.Choose_choose2);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        text1 = findViewById(R.id.ed1);
        text2 = findViewById(R.id.ed2);
        setText1(choose1);
        setText2(choose2);
    }


    public void setText1(String s){
        ((TextView)findViewById(R.id.tv1)).setText(s);
    }

    public void setText2(String s){
        ((TextView)findViewById(R.id.tv2)).setText(s);
    }

    public int getAns1(){
        return Integer.parseInt(text1.getText().toString());
    }

    public int getAns2(){
        return Integer.parseInt(text2.getText().toString());
    }
}
