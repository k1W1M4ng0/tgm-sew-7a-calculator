package at.ac.tgm.hit.sew7.sgao.calculator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.HashMap;
import java.util.Map;

import at.ac.tgm.hit.sew7.sgao.calculator.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private HashMap<Integer, Character> operatorMap = new HashMap<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        operatorMap.put(binding.radioPlus.getId(), '+');
        operatorMap.put(binding.radioMinus.getId(), '-');
        operatorMap.put(binding.radioMultiply.getId(), '*');
        operatorMap.put(binding.radioDivide.getId(), '/');
        return binding.getRoot();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextValue1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    binding.editTextValue1.setText("");
                }
                return false;
            }
        });

        binding.editTextValue2.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    binding.editTextValue2.setText("");
                }
                return false;
            }
        });

        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value1Str = binding.editTextValue1.getText().toString();
                String value2Str = binding.editTextValue2.getText().toString();
                if(value1Str.length() == 0 || value2Str.length() == 0) {
                    Log.d("Calculator", "no input was entered in at least one field");
                    return;
                }
                double value1 = Double.parseDouble(value1Str);
                double value2 = Double.parseDouble(value2Str);

                // calculate result
                char operator = getSelectedOperator();
                Log.d("Calculator", "operator: " + operator);
                if(operator != Character.MIN_VALUE) { // if an operator was selected
                    double calculated = 0;
                    // calculate based on which operator was chosen
                    switch(operator) {
                        case '+':
                            calculated = value1+value2;
                            break;
                        case '-':
                            calculated = value1-value2;
                            break;
                        case '*':
                            calculated = value1*value2;
                            break;
                        case '/':
                            if(value2 == 0) {
                                Log.e("Calculate", "Division by 0!");
                            } else {
                                calculated = value1/value2;
                            }
                            break;
                        default:
                            Log.e("Calculate", "Unknown operator " + operator);
                    }


                    // set the solution field to the calculated value
                    binding.textSolution.setText(Double.toString(calculated));

                    /*
                    set the color:
                    0: white
                    positive: black
                    negative: red
                     */
                    int color = Color.WHITE;
                    if(calculated > 0) {
                        color = Color.BLACK;
                    }
                    else if(calculated < 0) {
                        color = Color.RED;
                    }
                    binding.textSolution.setTextColor(color);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "set the color to green");
        binding.buttonCalculate.setBackgroundColor(Color.GREEN);
    }

    private char getSelectedOperator() {
        int selectedID = binding.radioGroupOperators.getCheckedRadioButtonId();

        return operatorMap.getOrDefault(selectedID, Character.MIN_VALUE);
    }

}