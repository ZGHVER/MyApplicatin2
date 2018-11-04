package com.example.zgh.myapplication.myVivew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.example.zgh.myapplication.mathUtil.ActMatrix;
import com.example.zgh.myapplication.mathUtil.MathException;
import com.example.zgh.myapplication.mathUtil.Matrix;
import com.example.zgh.myapplication.mathUtil.Ration;
import com.example.zgh.myapplication.R;

public class MatrixBox extends LinearLayout {

    private LinearLayout layout;
    private EditText[][] cells;
    private Context context;
    private int color;
    private int row;
    private int column;
    private Matrix matrix;

    public MatrixBox(Context context, Matrix matrix) {
        super(context);
        LayoutInflater.from(context).inflate(
                R.layout.matrix_box, this, true);
        this.matrix = matrix;
        this.context = context;
        this.row = matrix.getRow();
        this.column = matrix.getColumn();
        layout = findViewById(R.id.matrixBox);
        cells = new EditText[row][column];
        initLayout();
    }

    public MatrixBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MatrixBox);
        row = typedArray.getInt(R.styleable.MatrixBox_Row, 1);
        column = typedArray.getInt(R.styleable.MatrixBox_column, 1);
        color = typedArray.getColor(R.styleable.MatrixBox_color,Color.parseColor("#90cddc"));
        matrix = ActMatrix.getUnitaryMatrix(Math.max(column, row));
        this.context = context;
        initLayout();
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        layout = findViewById(R.id.matrixBox);
        cells = new EditText[row][column];
    }

    @SuppressLint("SetTextI18n")
    private void initLayout() {
        for (int i = 0; i < row; i++) {
            LinearLayout line = new LinearLayout(context);
            layout.addView(line);
            for (int j = 0; j < column; j++) {
                cells[i][j] = new EditText(context);
                cells[i][j].setText(matrix.getCell(i, j).toString());
                cells[i][j].setInputType(InputType.TYPE_CLASS_PHONE);
                cells[i][j].setBackgroundColor(color);
                cells[i][j].setEms(3);
                cells[i][j].setMaxLines(1);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMarginEnd(10);
                lp.bottomMargin = 10;
                line.addView(cells[i][j],lp);
            }
        }
    }

    public void setBacColor(@ColorInt int color){
        this.color = color;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                cells[i][j].setBackgroundColor(color);
            }
        }
    }

    public Matrix getReturn() throws MathException {
        Ration[][] ret = new Ration[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                ret[i][j] = Ration.valueOf(cells[i][j].getText().toString());
            }
        }
        return new Matrix(ret);
    }
}
