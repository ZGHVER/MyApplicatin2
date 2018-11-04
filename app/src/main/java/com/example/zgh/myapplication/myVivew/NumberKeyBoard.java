package com.example.zgh.myapplication.myVivew;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.zgh.myapplication.R;

public class NumberKeyBoard extends LinearLayout {

    private String name = "keyboard";
    private Button[] numberButton=new Button[11];

    public NumberKeyBoard(Context context) {
        super(context);
    }

    public NumberKeyBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.number_keyboard,this,true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.NumberKeyBoard);
        name = typedArray.getString(R.styleable.NumberKeyBoard_name);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initButton();

    }

    private void initButton(){
        numberButton[0] = findViewById(R.id.num0);
        numberButton[1] = findViewById(R.id.num1);
        numberButton[2] = findViewById(R.id.num2);
        numberButton[3] = findViewById(R.id.num3);
        numberButton[4] = findViewById(R.id.num4);
        numberButton[5] = findViewById(R.id.num5);
        numberButton[6] = findViewById(R.id.num6);
        numberButton[7] = findViewById(R.id.num7);
        numberButton[8] = findViewById(R.id.num8);
        numberButton[9] = findViewById(R.id.num9);
        numberButton[10] = findViewById(R.id.num_);
    }

    public void setOnClickListener(final OnClickListener listener){
        for (Button aNumberButton : numberButton) {
            aNumberButton.setOnClickListener(listener);
        }
    }
}
