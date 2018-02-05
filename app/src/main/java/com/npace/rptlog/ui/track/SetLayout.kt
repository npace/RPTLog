package com.npace.rptlog.ui.track

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.npace.rptlog.R
import com.npace.rptlog.model.entity.WeightSet
import kotlinx.android.synthetic.main.layout_set.view.*
import java.text.DecimalFormat


/**
 * Created by lubo on 2/4/2018.
 */

class SetLayout
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    var onClickCopyListener: ((View) -> Unit)? = null
    var onClickDeleteListener: ((View) -> Unit)? = null
    var onRepsChangedListener: ((Int) -> Unit)? = null
    var onWeightChangedListener: ((Float) -> Unit)? = null


    private var _reps = 0
    private var _weight = 0f

    var reps: Int
        get() = _reps
        private set(value) {
            val text = if (value == 0) "" else value.toString()
            editTextReps.setText(text)
        }
    var weight: Float
        get() = _weight
        private set(value) {
            val df = DecimalFormat.getInstance()
            df.apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 0
                maximumIntegerDigits = 3
            }
            val text = if (value > 0) df.format(value) else ""
            editTextWeight.setText(text)
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_set, this, true)

        imageViewCopy.setOnClickListener {
            onClickCopyListener?.invoke(it)
        }

        imageViewDelete.setOnClickListener {
            onClickDeleteListener?.invoke(it)
        }

        editTextReps.addTextChangedListener(SimpleTextWatcher({
            _reps = if (it.isEmpty()) 0 else Integer.parseInt(it)
            onRepsChangedListener?.invoke(_reps)
        }))

        editTextWeight.filters = arrayOf(WeightFilter(3, 2))
        editTextWeight.addTextChangedListener(SimpleTextWatcher({
            _weight = if (it.isEmpty()) 0f else it.toFloat()
            onWeightChangedListener?.invoke(_weight)
        }))
    }

    fun displaySet(weightSet: WeightSet) {
        this.reps = weightSet.reps
        this.weight = weightSet.weight
    }
}

class SimpleTextWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {
    override fun afterTextChanged(p0: Editable) {
        onTextChanged(p0.toString())
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}

class WeightFilter(integerLength: Int, fractionalLength: Int) : InputFilter {
    private val regex = ("(([1-9]{1})([0-9]{0,${(integerLength - 1)}})?)?(\\.[0-9]{0,$fractionalLength})?").toRegex()

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        val builder = StringBuilder(dest)
        builder.replace(dstart, dend, source.subSequence(start, end).toString())
        return if (!builder.toString().matches(regex)) {
            if (source.isEmpty()) dest.subSequence(dstart, dend) else ""
        } else null
    }
}

