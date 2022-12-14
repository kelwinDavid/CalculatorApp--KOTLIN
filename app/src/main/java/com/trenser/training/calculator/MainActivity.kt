package com.trenser.training.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.trenser.training.calculator.databinding.ActivityMainBinding

/*private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_OPERAND1_STORED = "Operand1Stored"*/

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*val viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)*/
//        val viewModel: CalculatorViewModel by viewModels()
        val viewModel: BigDecimalViewModel by viewModels()
        viewModel.stringResult.observe(this) { stringResult -> binding.result.setText(stringResult) }
        viewModel.stringNewNumber.observe(this) { stringResult -> binding.newNumber.setText(stringResult) }
        viewModel.stringOperationPerforming.observe(this) { stringResult ->
            binding.operationPerforming.text = stringResult
        }

        val newNumberListener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }

        binding.apply {
            button0.setOnClickListener(newNumberListener)
            button1.setOnClickListener(newNumberListener)
            button2.setOnClickListener(newNumberListener)
            button3.setOnClickListener(newNumberListener)
            button4.setOnClickListener(newNumberListener)
            button5.setOnClickListener(newNumberListener)
            button6.setOnClickListener(newNumberListener)
            button7.setOnClickListener(newNumberListener)
            button8.setOnClickListener(newNumberListener)
            button9.setOnClickListener(newNumberListener)
            buttonDot.setOnClickListener(newNumberListener)
        }

        val operationListener = View.OnClickListener { v ->
            viewModel.operandPressed((v as Button).text.toString())
        }

        binding.apply{
            buttonAddition.setOnClickListener(operationListener)
            buttonSubstract.setOnClickListener(operationListener)
            buttonDivide.setOnClickListener(operationListener)
            buttonProduct.setOnClickListener(operationListener)
            buttonEquals.setOnClickListener(operationListener)
            buttonNeg?.setOnClickListener {
                viewModel.negPressed()
            }
        }

    }
}
