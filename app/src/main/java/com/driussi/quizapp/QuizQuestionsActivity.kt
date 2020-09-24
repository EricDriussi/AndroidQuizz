package com.driussi.quizapp

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentQuestion: Int = 1
    private var mCorrect: Int = 0
    private var mSelectedOption: Int = 0
    private var submitted = false

    private var mUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUser = intent.getStringExtra(Constants.USER_NAME)

        setNewQuestion()

        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)

        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {


        when (v?.id) {
            R.id.optionOne -> setSelection(optionOne, 1)
            R.id.optionTwo -> setSelection(optionTwo, 2)
            R.id.optionThree -> setSelection(optionThree, 3)
            R.id.optionFour -> setSelection(optionFour, 4)
            R.id.btnSubmit -> checkSubmit()
        }
    }

    private fun setNewQuestion() {

        val questionToSet = Constants.getQuestions()[mCurrentQuestion - 1]!!

        resetSelections()

        progressBar.progress = mCurrentQuestion
        progressBarNum.text = "$mCurrentQuestion / ${Constants.getQuestions().size}"

        question.text = questionToSet!!.question

        image.setImageResource(questionToSet.img)

        optionOne.text = questionToSet.optionOne
        optionTwo.text = questionToSet.optionTwo
        optionThree.text = questionToSet.optionThree
        optionFour.text = questionToSet.optionFour
    }

    private fun finishGame() {
        val intent: Intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUser)
        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrect.toString())
        intent.putExtra(Constants.TOTAL_QUESTIONS, Constants.getQuestions().size.toString())

        startActivity(intent)
        finish()
    }


    private fun checkSubmit() {
        if (submitted) {
            mCurrentQuestion++

            if (mCurrentQuestion <= Constants.getQuestions().size)
                setNewQuestion()
            else
                finishGame()
        }

        if (mSelectedOption != 0 && mCurrentQuestion <= Constants.getQuestions().size) {

            submitted = true

            painIncorrect()
            paintCorrect()
            checkCorrect()

            if (mCurrentQuestion == Constants.getQuestions().size)
                btnSubmit.text = "See Results!"
            else
                btnSubmit.text = "Go to the next question!"
        }

    }

    private fun painIncorrect() {
        when (mSelectedOption) {

            1 -> optionOne.background = ContextCompat.getDrawable(this, R.drawable.incorr_border)
            2 -> optionTwo.background = ContextCompat.getDrawable(this, R.drawable.incorr_border)
            3 -> optionThree.background = ContextCompat.getDrawable(this, R.drawable.incorr_border)
            4 -> optionFour.background = ContextCompat.getDrawable(this, R.drawable.incorr_border)
        }
    }

    private fun paintCorrect() {
        when (Constants.getQuestions()[mCurrentQuestion - 1].correct) {

            1 -> optionOne.background = ContextCompat.getDrawable(this, R.drawable.corr_border)
            2 -> optionTwo.background = ContextCompat.getDrawable(this, R.drawable.corr_border)
            3 -> optionThree.background = ContextCompat.getDrawable(this, R.drawable.corr_border)
            4 -> optionFour.background = ContextCompat.getDrawable(this, R.drawable.corr_border)
        }
    }

    private fun checkCorrect(){

        if (mSelectedOption == Constants.getQuestions()[mCurrentQuestion - 1].correct) {
            mCorrect++
        }
    }

    private fun resetSelections() {

        val options = ArrayList<TextView>()

        submitted = false
        mSelectedOption = 0
        btnSubmit.text = "Submit"

        options.add(0, optionOne)
        options.add(1, optionTwo)
        options.add(2, optionThree)
        options.add(3, optionFour)

        for (option in options) {
            option.setTextColor(R.color.myHintTextColor.toInt())
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.def_border)
        }
    }

    private fun setSelection(tv: TextView, selNum: Int) {

        if (!submitted) {
            resetSelections()
            mSelectedOption = selNum

            tv.setTextColor(R.color.myTextColor.toInt())
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(this, R.drawable.sel_border)
        }
    }
}