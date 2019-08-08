package com.example.practiceone;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.google.android.material.snackbar.Snackbar;

public class Main_activity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton radio_plus;
    private RadioButton radio_minus;
    private RadioButton radio_multiple;
    private RadioButton radio_divide;
    private EditText ET_Field1;
    private EditText ET_Field2;
    private TextView tv_Result;
    private CheckBox float_checkBox;
    private CheckBox signed_checkBox;
    private Button btn_Calculate;

    //set up default operation sign +
    private char operationSign;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //find radioButtons
        radio_plus = (RadioButton) findViewById(R.id.radio_plus);
        radio_minus = (RadioButton) findViewById(R.id.radio_minus);
        radio_multiple = (RadioButton) findViewById(R.id.radio_multiple);
        radio_divide = (RadioButton) findViewById(R.id.radio_divide);

        //find another elements
        ET_Field1 = (EditText) findViewById(R.id.ET_Field1);
        ET_Field2 = (EditText) findViewById(R.id.ET_Field2);
        tv_Result = (TextView) findViewById(R.id.tv_Result);
        btn_Calculate = (Button) findViewById(R.id.btn_Calculate);
        float_checkBox = (CheckBox) findViewById(R.id.float_checkBox);
        signed_checkBox = (CheckBox) findViewById(R.id.signed_checkBox);
        btn_Calculate.setOnClickListener(this);
        float_checkBox.setOnClickListener(this);
        signed_checkBox.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clean_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.clearDataQuestion);
                builder.setMessage(
                        getResources().getString(R.string.cleanMessage) + " " +
                        getResources().getString(R.string.field1) + ", " +
                        getResources().getString(R.string.field2) + ", " +
                        getResources().getString(R.string.tv_ResultText));
                builder.setPositiveButton(R.string.btnYES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ET_Field1.setText("");
                        ET_Field2.setText("");
                        tv_Result.setText(R.string.tv_ResultText);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //save values from tv_Result and operationSign to bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tv_ResultText", tv_Result.getText().toString());
        outState.putChar("operationSign", operationSign);
    }

    //restore values from bundle to tv_Result and operationSign
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tv_Result.setText(savedInstanceState.getString("tv_ResultText"));
        operationSign = savedInstanceState.getChar("operationSign");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Calculate:
                if (checkIfOperationSelected()) {
                    calculate();
                }
                break;
            case R.id.float_checkBox:
                if (float_checkBox.isChecked()){
                    ET_Field1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                } else {
                    ET_Field1.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            case R.id.signed_checkBox:
                if (signed_checkBox.isChecked()){
                    ET_Field1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                } else {
                    ET_Field1.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
        }
    }

    private void calculate() {
        try {
            //todo
        } catch (ArithmeticException ex){
            showErrorDialog(getResources().getString(R.string.dividionByZero));
        } catch (Exception ex){
            showErrorDialog(ex.toString());
        }
    }

    private boolean checkIfOperationSelected() {
        if (operationSign != '\u0000'){
            return true;
        } else {
            Toast.makeText(this, R.string.operationNotFound, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //set operation sign
    public void setOperationSing(View view){
        switch (view.getId()){
            case R.id.radio_plus:
                operationSign = '+';
                break;
            case R.id.radio_minus:
                operationSign = '-';
                break;
            case R.id.radio_multiple:
                operationSign = '*';
                break;
            case R.id.radio_divide:
                operationSign = '/';
                break;
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
