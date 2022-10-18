package com.trenser.training.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    //    Variables for store the operands and type of operation to be performed
    private var operand1: Double? = null
    private var pendingOperation = "="

    private val result = MutableLiveData<Double>()
    val stringResult: LiveData<String>
        get() = Transformations.map(result) { it.toString() }
    private val newNumber = MutableLiveData<String>()
    val stringNewNumber: LiveData<String>
        get() = newNumber
    private val operationPerforming = MutableLiveData<String>()
    val stringOperationPerforming: LiveData<String>
        get() = operationPerforming

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            newNumber.value += caption
        } else {
            newNumber.value = caption
        }
    }

    fun operandPressed(operation: String) {
        try {
            val value = newNumber.value?.toDouble()
            if (value != null) {
                performOperation(value, operation)
            }
        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }
        pendingOperation = operation
        operationPerforming.value = pendingOperation
    }

    fun negPressed() {
        val value = newNumber.value
        if (value == null || value.isEmpty()) {
            newNumber.value = "-"
        } else {
            try {
                var doubleValue = value.toDouble()
                doubleValue *= -1
                newNumber.value = doubleValue.toString()
            } catch (e: NumberFormatException) {
                newNumber.value = ""
            }
        }
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN    // handle attempt to division by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.value = operand1
        newNumber.value = ""

        if (!(operand1 == null && !operand1!!.isNaN()) && pendingOperation == "=") operand1 = null
    }
}