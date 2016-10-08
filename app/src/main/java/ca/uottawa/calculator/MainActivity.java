package ca.uottawa.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private enum Operator {
        none, add, minus, multiply, divide} // Possible operations
    private double data1 = 0, data2 = 0;    // operand 1 and 2
    private Operator optr = Operator.none;  // Operator enum reference
    boolean hasResult = false;              // Flag to ensure pressing result more than once
                                            // does not clear the current result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Event listener for when a user clicks on a number from 0-9 or the decimal point button
     *
     * Updates the TextView with the digit associated with the button clicked
     *
     * @param view the current view
     */
    public void onClickNumericalButton(View view) {
        // If the result of an operation is currently displayed,
        // clear the view and reset the hasResult flag to allow
        // performing more operations
        if (hasResult) {
            clearView(view);
            hasResult = false;
        }
        // Get ID of pressed button
        int pressID = view.getId();

        // Getting TextView object
        TextView curText = (TextView) findViewById(R.id.resultText);

        // Figure out which button was pressed and update
        // the TextField with the corresponding number
        switch (pressID) {
            case R.id.btn00:
                curText.setText(curText.getText() + "0");
                break;
            case R.id.btn01:
                curText.setText(curText.getText() + "1");
                break;
            case R.id.btn02:
                curText.setText(curText.getText() + "2");
                break;
            case R.id.btn03:
                curText.setText(curText.getText() + "3");
                break;
            case R.id.btn04:
                curText.setText(curText.getText() + "4");
                break;
            case R.id.btn05:
                curText.setText(curText.getText() + "5");
                break;
            case R.id.btn06:
                curText.setText(curText.getText() + "6");
                break;
            case R.id.btn07:
                curText.setText(curText.getText() + "7");
                break;
            case R.id.btn08:
                curText.setText(curText.getText() + "8");
                break;
            case R.id.btn09:
                curText.setText(curText.getText() + "9");
                break;
            case R.id.btnDot:
                curText.setText(curText.getText() + ".");
                break;
            default:
                curText.setText("ERROR");
                Log.d("Error", "Error: Unknown button pressed!");
                break;
        }
    }

    /**
     * Event listener for when a user clicks on an operator button
     *
     * @param view the current view
     */
    public void onClickOperator(View view) {
        // Get ID of pressed button
        int pressID = view.getId();

        // Getting TextView object
        TextView curText = (TextView) findViewById(R.id.resultText);

        // If there is nothing in the TextView, we cannot possibly perform an operation
        // Return to the calling method to avoid a crash (better way to handle this?)
        if (curText.getText().toString().trim().length() == 0) {
            return;
        }

        // If no operator has been pressed yet, we capture the current value as data1
        // Otherwise, we have a pending operation to perform first
        if (optr == Operator.none) {
            data1 = Double.valueOf(curText.getText().toString());
            clearView(view);
        } else {
            onClickResult(view);
        }

        // Change optr to reflect selected operation
        switch (pressID) {
            case (R.id.btnPlus):
                optr = Operator.add;

                break;
            case (R.id.btnMinus):
                optr = Operator.minus;
                break;
            case (R.id.btnMult):
                optr = Operator.multiply;
                break;
            case (R.id.btnDiv):
                optr = Operator.divide;
                break;
        }
    }

    /**
     * Event listener for the equals (result) button
     *
     * @param view the current view
     */
    public void onClickResult(View view) {
        // We don't want clicking the result button more than once to clear the screen
        // This flag ensures clicking the result button more than once does not do anything
        if (!hasResult) {
            // Getting TextView object
            TextView curText = (TextView) findViewById(R.id.resultText);

            // An operator has been selected, so the current TextView number will be data2
            if (optr != Operator.none) {
                data2 = Double.valueOf(curText.getText().toString());
            }

            // Get the result from the operation
            double result = performOperation();
            // Update the TextView with the result
            //   - Integer if there is no decimal part
            //   - Double otherwise
            if (result % 1 == 0) {
                curText.setText(Integer.toString((int) result));
            } else {
                curText.setText(Double.toString(result));

            }
            optr = Operator.none;

            // data1 is now the result of the last operation, and we clear the operator
            // This allows us to chain multiple operations
            data1 = Double.valueOf(curText.getText().toString());
        }

        // We've gotten a result, so we don't need this method to do anything
        // until we perform another operation
        hasResult = true;
    }

    /**
     * Event listener for the clear button.
     * Clears the TextView and initializes data1, data2 to zero.
     *
     * @param view the current view
     */
    public void onClickClear(View view) {
       clearView(view);
       data1 = data2 = 0;
    }

    /**
     * Performs an operation with the current operands (add/subtract/multiply/divide)
     * based on the currently selected operator.
     *
     * @return the result of the operation
     */
    private double performOperation() {
        // Perform operation and return the result;
        switch (optr) {
            case add:
                return data1 + data2;
            case minus:
                return data1 - data2;
            case multiply:
                return data1 * data2;
            case divide:
                return data1 / data2;
        }
        return 0;
    }

    /**
     * Helper method that clears the TextView.
     *
     * @param view the current view
     */
    private void clearView(View view) {
        // Getting TextView object
        TextView curText = (TextView) findViewById(R.id.resultText);
        curText.setText("");
    }
}
