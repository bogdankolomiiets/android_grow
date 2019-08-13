package com.example.practiceone;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Constants{

    private EditText firstValueEt;
    private EditText secondValueEt;
    private TextView resultTv;
    private CheckBox floatCheckBox;
    private CheckBox signedCheckBox;
    private RadioGroup groupOne;
    private RadioGroup groupTwo;

    private String operationSign;
    private double val1;
    private double val2;
    private double result;
    private Pattern pattern;
    private Matcher matcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initUi();
    }

    private void initUi() {
        //find radioGroup
        groupOne = findViewById(R.id.groupOne);
        groupTwo = findViewById(R.id.groupTwo);

        //find another elements
        firstValueEt = findViewById(R.id.firstValueEt);
        secondValueEt = findViewById(R.id.secondValueEt);
        resultTv = findViewById(R.id.resultTv);

        Button calculateBtn = findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(this);

        floatCheckBox = findViewById(R.id.floatCheckBox);
        floatCheckBox.setOnClickListener(this);

        signedCheckBox = findViewById(R.id.signedCheckBox);
        signedCheckBox.setOnClickListener(this);

        //init radioButtons
        initRadioButtons();
    }

    private void initRadioButtons() {
        RadioButton plusRadio = findViewById(R.id.plusRadio);
        plusRadio.setOnClickListener(this);
        plusRadio.setTag(PLUS);

        RadioButton minusRadio = findViewById(R.id.minusRadio);
        minusRadio.setOnClickListener(this);
        minusRadio.setTag(MINUS);

        RadioButton multipleRadio = findViewById(R.id.multipleRadio);
        multipleRadio.setOnClickListener(this);
        multipleRadio.setTag(MULTIPLE);

        RadioButton divideRadio = findViewById(R.id.divideRadio);
        divideRadio.setOnClickListener(this);
        divideRadio.setTag(DIVIDE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clean_item) {
            showClearDialog();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void showClearDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.clearDataQuestion);
        builder.setMessage(getString(R.string.cleanMessage,
                firstValueEt.getHint(), secondValueEt.getHint(), getString(R.string.resultText)));
        builder.setPositiveButton(R.string.btnYES, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearData();
            }
        });
        builder.setNegativeButton(R.string.btnNO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearData() {
        firstValueEt.setText("");
        secondValueEt.setText("");
        resultTv.setText(R.string.resultText);
    }


    //save values from resultTv and operationSign to bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESULT_TEXT, resultTv.getText().toString());
        outState.putString(OPERATION_SIGN, operationSign);
    }

    //restore values from bundle to resultTv and operationSign
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultTv.setText(savedInstanceState.getString(RESULT_TEXT));
        operationSign = savedInstanceState.getString(OPERATION_SIGN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculateBtn:
                if (checkIfAnyOperationSelected() && dataValidation()) {
                    calculate(val1, val2);
                }
                break;
            case R.id.signedCheckBox:
            case R.id.floatCheckBox:
                if (!signedCheckBox.isChecked()) {
                    isSignAvailableInEditText(SIGNED_PATTERN);
                } else if (!floatCheckBox.isChecked()){
                    isSignAvailableInEditText(FLOAT_PATTERN);
                }
                changeInputType();
                break;
            case R.id.plusRadio:
            case R.id.minusRadio:
            case R.id.multipleRadio:
            case R.id.divideRadio:
                operationSign = view.getTag().toString();
                groupOne.clearCheck();
                groupTwo.clearCheck();
                ((RadioButton)view).setChecked(true);
                break;
        }
    }

    private void isSignAvailableInEditText(String signedPattern) {
        pattern = Pattern.compile(signedPattern);
        matcher = pattern.matcher(firstValueEt.getText().toString());
        if (matcher.find()) {
            firstValueEt.setText("");
        }
        matcher = pattern.matcher(secondValueEt.getText().toString());
        if (matcher.find()) {
            secondValueEt.setText("");
        }
    }

    private void changeInputType() {
        int inputType = getInputType();
        firstValueEt.setInputType(inputType);
        secondValueEt.setInputType(inputType);
    }

    private boolean dataValidation() {
        try {
            String temp = firstValueEt.getText().toString();
            pattern = Pattern.compile(VALIDATION_PATTERN);
            //match value from firstValueEt
            matcher = pattern.matcher(temp);
            if (matcher.matches()) {
                val1 = Double.valueOf(temp);
            } else throw new IllegalArgumentException(temp);
            //match value from secondValueEt
            temp = secondValueEt.getText().toString();
            matcher = pattern.matcher(temp);
            if (matcher.matches()) {
                val2 = Double.valueOf(temp);
            } else throw new IllegalArgumentException(temp);
            return true;
        } catch (IllegalArgumentException ex) {
            showErrorDialog(getResources().getString(R.string.incorrectNumberFormat) + " " + ex.getMessage());
        }
        return false;
    }

    private int getInputType() {
        int inputType = InputType.TYPE_CLASS_NUMBER;
        if (signedCheckBox.isChecked()) {
            inputType |=  InputType.TYPE_NUMBER_FLAG_SIGNED;
        }
        if (floatCheckBox.isChecked()) {
            inputType |= InputType.TYPE_NUMBER_FLAG_DECIMAL;
        }
        return inputType;
    }

    private void calculate(double val1, double val2) {
        try {
            switch (operationSign) {
                case PLUS:
                    result = val1 + val2;
                    break;
                case MINUS:
                    result = val1 - val2;
                    break;
                case MULTIPLE:
                    result = val1 * val2;
                    break;
                case DIVIDE:
                    if (val2 == 0) throw new ArithmeticException();
                    result = val1 / val2;
                    break;
            }
            resultTv.setText(NumberFormat.getInstance().format(result));
        } catch (ArithmeticException ex){
            showErrorDialog(getString(R.string.divisionByZero));
        } catch (Exception ex){
            showErrorDialog(ex.toString());
        }
    }

    private boolean checkIfAnyOperationSelected() {
        if (groupOne.getCheckedRadioButtonId() != -1 || groupTwo.getCheckedRadioButtonId() != -1){
            return true;
        } else {
            Toast.makeText(this, R.string.operationNotFound, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void showErrorDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.btnOK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
