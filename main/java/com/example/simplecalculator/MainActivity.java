/*
        date: 2023-06-08
        file: MainActivity.java
        author: Jaime Rump
        desc: This activity supports the calculator functions and various features
 */
package com.example.simplecalculator;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.Integer;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    boolean isNewOperator = true;
    String op = "+";
    double prevNumber = 0;
    EditText editText;
    double currentNumber = 0;
    final int MAX = 15;
    boolean plusMinus = false;
    double result = 0;
    NumberFormat formatter = new DecimalFormat("#0.00");
    String data;
    int decimalPlaces;
    boolean calculationPerformed = false;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = findViewById(android.R.id.content);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_land);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }

        editText = findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (getString(R.string.display).equals(editText.getText().toString())) {
                    editText.setText("");
                }
            }
        });

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HalfScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        updateDecimals();
        updateButtonColor();
        updateBackgroundColor();
    }

    public void numberEvent(View view) {
        if (isNewOperator) {
            editText.setText("");
            isNewOperator = false;
        }

        if (editText.getText().length() == MAX) {
            Toast toast = Toast.makeText(getApplicationContext(), "Total number of digits reached", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String number = editText.getText().toString();

            switch (view.getId()) {
                case R.id.bu9:
                    number += "9";
                    break;
                case R.id.bu8:
                    number += "8";
                    break;
                case R.id.bu7:
                    number += "7";
                    break;
                case R.id.bu6:
                    number += "6";
                    break;
                case R.id.bu5:
                    number += "5";
                    break;
                case R.id.bu4:
                    number += "4";
                    break;
                case R.id.bu3:
                    number += "3";
                    break;
                case R.id.bu2:
                    number += "2";
                    break;
                case R.id.bu1:
                    number += "1";
                    break;
                case R.id.bu0:
                    number += "0";
                    break;
                case R.id.buPoint:
                    number += ".";
                    break;
                case R.id.buPlusMinus:
                    if (!plusMinus) {
                        number = "-" + number;
                        plusMinus = true;
                    } else {
                        number = number.replaceAll("-", "");
                        plusMinus = false;
                    }
                    break;
            }
            currentNumber = Double.parseDouble(number);
            editText.setText(number);
        }
    }

    public void operatorEvent(View view) {
        try {
            double temp = Double.parseDouble(editText.getText().toString());
            currentNumber = temp;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (!calculationPerformed) {
            switch (view.getId()) {
                case R.id.buDivide:
                    if (op != "/") {
                        if (op == "*") {
                            result = prevNumber * currentNumber;
                        } else if (op == "+") {
                            result = prevNumber + currentNumber;
                        } else if (op == "-") {
                            result = prevNumber - currentNumber;
                        }
                        op = "/";
                    } else {
                        if (prevNumber == 0) {
                            Toast.makeText(getApplicationContext(), "Cannot divide by zero!", Toast.LENGTH_SHORT).show();
                        } else {
                            result = prevNumber / currentNumber;
                            op = "/";
                        }
                    }
                    prevNumber = result;
                    break;
                case R.id.buMultiply:
                    if (op != "*") {
                        if (op == "-") {
                                 result = prevNumber - currentNumber;
                             } else if (op == "+") {
                                result = prevNumber + currentNumber;
                             } else if (op == "/") {
                                result = prevNumber / currentNumber;
                             }
                        op = "*";
                    } else {
                        op = "*";
                        result = prevNumber * currentNumber;
                    }
                    prevNumber = result;
                    break;
                case R.id.buPlus:
                    if (op != "+") {
                        if (op == "-") {
                            result = prevNumber - currentNumber;
                        } else if (op == "*") {
                            result = prevNumber * currentNumber;
                        }  else if (op == "/") {
                            result = prevNumber / currentNumber;
                        }
                        op = "-";
                    } else {
                        op = "+";
                        result = currentNumber + prevNumber;
                    }
                    prevNumber = result;
                    break;
                case R.id.buMinus:
                    if (op != "-") {
                        if (op == "+") {
                            result = prevNumber + currentNumber;
                        } else if (op == "*") {
                        result = prevNumber * currentNumber;
                        }  else if (op == "/") {
                        result = prevNumber / currentNumber;
                         }
                        op = "-";
                    } else {
                        op = "-";
                        if (prevNumber != 0) {
                            result = prevNumber - currentNumber;
                        } else {
                            result = currentNumber;
                        }
                    }
                    prevNumber = result;
                    break;
                default:
                    break;
            }
        } else {
            calculationPerformed = false;
        }

        isNewOperator = true;
        editText.setText(formatter.format(result) + "");
    }

    public void clearEvent(View view) {
        editText.setText("0");
        prevNumber = 0;
        currentNumber = 0;
        isNewOperator = true;

    }

    public void backspaceEvent(View view) {
        if (editText.getText().length() < 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "There are no digits to backspace!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            editText.setText(editText.getText().toString().substring(0, editText.getText().length() - 1));
            editText.setSelection(editText.getText().length());
        }
        isNewOperator = true;
    }

    public void equalsEvent(View view) {
        isNewOperator = true;

        switch (op) {
            case "+":
                result = prevNumber + currentNumber;
                prevNumber = result;
                break;
            case "-":
                result = prevNumber - currentNumber;
                prevNumber = result;
                break;
            case "*":
                result = prevNumber * currentNumber;
                prevNumber = result;
                break;
            case "/":
                try {
                    result = prevNumber / currentNumber;
                    prevNumber = result;
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Cannot divide by zero!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        calculationPerformed = true;
        editText.setText(formatter.format(result) + "");
    }

    public void deciEvent(View view) {
        String input = editText.getText().toString();
        String numberType = detectNumberType(input);

        if (input.contains(".")) {
            input = String.valueOf((int) Math.round(Float.parseFloat(input)));
        }
        if (numberType.equals("Decimal")) {
            // Already in decimal format
            editText.setText(input);
        } else if (numberType.equals("Hexadecimal")) {
            // Convert hexadecimal to decimal
            int decimal = Integer.parseInt(input, 16);
            editText.setText(String.valueOf(decimal));
        } else if (numberType.equals("Octal")) {
            // Convert octal to decimal
            int decimal = Integer.parseInt(input, 8);
            editText.setText(String.valueOf(decimal));
        } else if (numberType.equals("Binary")) {
            // Convert binary to decimal
            int decimal = Integer.parseInt(input, 2);
            editText.setText(String.valueOf(decimal));
            editText.setSelection(editText.getText().length());
        }
    }

    public void hexEvent(View view) {
        String input = editText.getText().toString();
        String numberType = detectNumberType(input);

        if (input.contains(".")) {
            input = String.valueOf((int) Math.round(Float.parseFloat(input)));
        }

        if (numberType.equals("Hexadecimal")) {
            // Already in hexadecimal format
            editText.setText(input);
        } else if (numberType.equals("Decimal")) {
            // Convert decimal to hexadecimal
            int decimal = Integer.parseInt(input);
            String hexadecimal = Integer.toHexString(decimal);
            editText.setText(hexadecimal);
        } else if (numberType.equals("Octal")) {
            // Convert octal to hexadecimal
            int decimal = Integer.parseInt(input, 8);
            String hexadecimal = Integer.toHexString(decimal);
            editText.setText(hexadecimal);
        } else if (numberType.equals("Binary")) {
            // Convert binary to hexadecimal
            int decimal = Integer.parseInt(input, 2);
            String hexadecimal = Integer.toHexString(decimal);
            editText.setText(hexadecimal);
            editText.setSelection(editText.getText().length());
        }
    }

    public void octEvent(View view) {
        String input = editText.getText().toString();
        String numberType = detectNumberType(input);
        if (input.contains(".")) {
            input = String.valueOf((int) Math.round(Float.parseFloat(input)));
        }

        if (numberType.equals("Octal")) {
            // Already in octal format
            editText.setText(input);
        } else if (numberType.equals("Decimal")) {
            // Convert decimal to octal
            int decimal = Integer.parseInt(input);
            String octal = Integer.toOctalString(decimal);
            editText.setText(octal);
        } else if (numberType.equals("Hexadecimal")) {
            // Convert hexadecimal to octal
            int decimal = Integer.parseInt(input, 16);
            String octal = Integer.toOctalString(decimal);
            editText.setText(octal);
        } else if (numberType.equals("Binary")) {
            // Convert binary to octal
            int decimal = Integer.parseInt(input, 2);
            String octal = Integer.toOctalString(decimal);
            editText.setText(octal);
            editText.setSelection(editText.getText().length());
        }
    }

    public void binEvent(View view) {
        String input = editText.getText().toString();
        String numberType = detectNumberType(input);
        if (input.contains(".")) {
            input = String.valueOf((int) Math.round(Float.parseFloat(input)));
        }

        if (numberType.equals("Binary")) {
            // Already in binary format
            editText.setText(input);
        } else if (numberType.equals("Decimal")) {
            // Convert decimal to binary
            int decimal = Integer.parseInt(input);
            String binary = Integer.toBinaryString(decimal);
            editText.setText(binary);
        } else if (numberType.equals("Hexadecimal")) {
            // Convert hexadecimal to binary
            int decimal = Integer.parseInt(input, 16);
            String binary = Integer.toBinaryString(decimal);
            editText.setText(binary);
        } else if (numberType.equals("Octal")) {
            // Convert octal to binary
            int decimal = Integer.parseInt(input, 8);
            String binary = Integer.toBinaryString(decimal);
            editText.setText(binary);
            editText.setSelection(editText.getText().length());
        }
    }

    public static String detectNumberType(String number) {
        if (number.startsWith("0x") || number.startsWith("0X")) {
            return "Hexadecimal";
        } else if (number.startsWith("0")) {
            return "Octal";
        } else if (number.matches("[01]+")) {
            return "Binary";
        } else {
            return "Decimal";
        }
    }

    public  void updateDecimals() {
        Intent intent = getIntent();
        data = intent.getStringExtra("key");

        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");
        }

        if (data != null) {
            if (data.equals("2")) {
                formatter = new DecimalFormat("#0.00");
            } else if (data.equals("3")) {
                formatter = new DecimalFormat("#0.000");
            } else if (data.equals("4")) {
                formatter = new DecimalFormat("#0.0000");
            } else {
                decimalPlaces = Integer.parseInt(data);
                String formatPattern = "#0." + "0".repeat(decimalPlaces);
                formatter = new DecimalFormat(formatPattern);
            }
        }
    }

    public void updateButtonColor() {

        Intent intent = getIntent();
        data = intent.getStringExtra("key3");

        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");

        } else if (data != null) {
            for (int i = 0; i <= 9; i++) {
                int buttonId = getResources().getIdentifier("bu" + i, "id", getPackageName());
                Button button = findViewById(buttonId);
                button.setBackgroundColor(Integer.parseInt(data));
            }

            List<Button> buttons = new ArrayList<>();
            buttons.add((Button) findViewById(R.id.buC));
            buttons.add((Button) findViewById(R.id.buPlusMinus));
            buttons.add((Button) findViewById(R.id.buDivide));
            buttons.add((Button) findViewById(R.id.buBackspace));
            buttons.add((Button) findViewById(R.id.buDecimal));
            buttons.add((Button) findViewById(R.id.buHex));
            buttons.add((Button) findViewById(R.id.buBinary));
            buttons.add((Button) findViewById(R.id.buOctal));
            buttons.add((Button) findViewById(R.id.buMultiply));
            buttons.add((Button) findViewById(R.id.buPlus));
            buttons.add((Button) findViewById(R.id.buMinus));
            buttons.add((Button) findViewById(R.id.buEquals));
            buttons.add((Button) findViewById(R.id.buPoint));

            for (Button button : buttons) {
                button.setBackgroundColor(Integer.parseInt(data));
            }
        }
    }

    public void updateBackgroundColor() {
        Intent intent = getIntent();
        data = intent.getStringExtra("key4");

        if (data == null) {
            Log.d("TAG", "The passed variable 'data' is null");

        } else {
            rootView.setBackgroundColor(Integer.parseInt(data));
        }
    }
}