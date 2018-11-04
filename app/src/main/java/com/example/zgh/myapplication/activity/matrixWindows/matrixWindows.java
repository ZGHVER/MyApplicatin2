package com.example.zgh.myapplication.activity.matrixWindows;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.zgh.myapplication.R;
import com.example.zgh.myapplication.mathUtil.Matrix;
import com.example.zgh.myapplication.myVivew.Choose;
import com.example.zgh.myapplication.myVivew.MatrixBox;

public class matrixWindows extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_windows);
        Intent intent = getIntent();
        Matrix get = (Matrix) intent.getSerializableExtra("Matrix");
        MatrixBox matrixBox = new MatrixBox(this,get);
        matrixBox.setBacColor(Color.parseColor("#90cddc"));
        RelativeLayout a = findViewById(R.id.real);
        RelativeLayout.LayoutParams ly = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ly.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        a.addView(matrixBox,ly);
    }

    public void click(View view) {
        final Choose c = new Choose(this);
        c.setText1("行数：");
        c.setText2("列数：");
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("请输入行列数")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(c)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                });
    }

}
