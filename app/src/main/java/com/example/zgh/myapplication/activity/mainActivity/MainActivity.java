package com.example.zgh.myapplication.activity.mainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zgh.myapplication.R;
import com.example.zgh.myapplication.activity.matrixWindows.matrixWindows;
import com.example.zgh.myapplication.calculTool.EquationManger;
import com.example.zgh.myapplication.calculTool.Queue;
import com.example.zgh.myapplication.calculTool.lessElementException;
import com.example.zgh.myapplication.mathUtil.*;
import com.example.zgh.myapplication.myVivew.Choose;

import java.util.TreeMap;

import static com.example.zgh.myapplication.mathUtil.MatrixAndRationOperate.*;

public class MainActivity extends AppCompatActivity {

    private TextView equationView;
    private TextView answerView;

    private StringBuilder numberBuffer = new StringBuilder();
    private StringBuilder equationInString = new StringBuilder();
    private TreeMap<String, Matrix> matrixTreeMap = new TreeMap<>();

    private EquationManger<AdjMatrix> equationManger = new EquationManger<>();
    private boolean equaled = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText qa = new EditText(this);
        initialiseViewLink();
        initialMatrix();
        initialiseOperator();
        initButton();
    }

    private void initialiseViewLink() {
        equationView = findViewById(R.id.equationView);
        answerView = findViewById(R.id.answerView);
        findViewById(R.id.board).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) ((Button) v).getText();
                numberBuffer.append(s);
                equationInString.append(s);
                setEquationViewText(equationInString);
            }
        });
    }

    private void initButton() {
        View.OnLongClickListener longClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("MainActivity","MatrixClick");
                String name = ((Button)v).getText().toString();
                Matrix send = matrixTreeMap.get(name);
                Intent intent = new Intent(MainActivity.this,matrixWindows.class);
                intent.putExtra("Matrix",send);
                startActivityForResult(intent,1);
                return true;
            }
        };
        findViewById(R.id.MatrixA).setOnLongClickListener(longClick);
        findViewById(R.id.MatrixB).setOnLongClickListener(longClick);
        findViewById(R.id.MatrixC).setOnLongClickListener(longClick);
        findViewById(R.id.MatrixD).setOnLongClickListener(longClick);
        findViewById(R.id.MatrixE).setOnLongClickListener(longClick);
    }

    private void initialMatrix() {
        matrixTreeMap.put("A", ActMatrix.getUnitaryMatrix(1));
        matrixTreeMap.put("B", ActMatrix.getUnitaryMatrix(3));
        matrixTreeMap.put("C", ActMatrix.getUnitaryMatrix(5));
        matrixTreeMap.put("D", ActMatrix.getUnitaryMatrix(6));
        matrixTreeMap.put("E", ActMatrix.getUnitaryMatrix(7));
    }

    public void matrixClick(View view) {
    }

    public void operatorClick(View view) {
        EqClean();
        clearBufferAndToNumber();
        doActionOf((Button) view);
        setEquationViewText(equationInString);
    }

    private void doActionOf(Button button) {
        String s = (String) button.getText();
        switch (s) {
            case "=":
                equalActivity();
                Toast.makeText(this, "e", Toast.LENGTH_LONG).show();
                break;
            case "ce":
                clean();
                Toast.makeText(this, "c", Toast.LENGTH_LONG).show();
                break;
            case "R(A)":
            case "V(A)":
            case "T(A)":
            case "N(A)":
                Toast.makeText(this, "b", Toast.LENGTH_LONG).show();
                equationManger.addEquationOperate(String.valueOf(s.charAt(0)));
                equationManger.addEquationOperate(String.valueOf(s.charAt(1)));
                equationInString.append(s);
                break;
            default:
                equationManger.addEquationOperate(s);
                equationInString.append(s);
                Toast.makeText(this, "normal", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void equalActivity() {
        AdjMatrix answer = null;

        try {
            answer = equationManger.getEquationValue();
        } catch (ErrorTypeException e) {
            answerView.setText(e.getMessage());
        } catch (lessElementException e) {
            answerView.setText("输入有误");
        } finally {
            equaled = true;
            equationManger.clean();
        }

        if (answer == null)
            return;
        answerView.setText(answer.toString());
    }

    private void EqClean() {
        if (equaled) {
            clean();
            equaled = false;
        }
    }

    private void clean() {
        equationInString.delete(0, equationInString.length());
        equationManger.clean();
    }

    private void clearBufferAndToNumber() {
        if (numberBuffer.length() != 0) {
            double s = Double.valueOf(numberBuffer.toString());
            equationManger.addEquationMember(Ration.valueOf(s));
            numberBuffer.delete(0, numberBuffer.length());
        }
    }

    private void setEquationViewText(CharSequence s) {
        equationView.setText(s.toString());
    }

    private void initialiseOperator() {
        equationManger.analyzer.AddALvOperator("*", Tim);
        equationManger.analyzer.AddALvOperator("/", Div);
        equationManger.analyzer.AddBLvOperator("+", Add);
        equationManger.analyzer.AddBLvOperator("-", Sub);
        equationManger.analyzer.AddUnOperator("R", R_A_);
        equationManger.analyzer.AddUnOperator("V", V_A_);
        equationManger.analyzer.AddUnOperator("T", T_A_);
        equationManger.analyzer.AddUnOperator("N", N_A_);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void testClick(View view) {
        Intent intent = new Intent(this, matrixWindows.class);
        startActivity(intent);
    }
}
