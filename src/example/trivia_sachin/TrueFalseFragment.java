package example.trivia_sachin;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class TrueFalseFragment extends Fragment {
	private static String DisplayQuestion, CorrectAnsTrueFalse,  CorrectAnswerString,selectedRadio ;
	Button submit,next;
	private RadioButton trueRadioButton, falseRadioButton;
	TextView tvForQue;
	MediaPlayer ourSong;
	public TrueFalseFragment() {
		// Required empty public constructor
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
	LinearLayout linear =  (LinearLayout) inflater.inflate(R.layout.fragment_true_false, container, false);
	submit= (Button)linear.findViewById(R.id.submitButtonn);
	next= (Button)linear.findViewById(R.id.nextButton);
	trueRadioButton=(RadioButton)linear.findViewById(R.id.True);
	falseRadioButton=(RadioButton)linear.findViewById(R.id.False);
	tvForQue=(TextView)linear.findViewById(R.id.TrueFalseQ);
	tvForQue.setText(DisplayQuestion);
	
	trueRadioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedRadio = (String) trueRadioButton.getText();
				}
		});
	
	falseRadioButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedRadio = (String) falseRadioButton.getText();
				}
		});
	 
	
	submit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		if(trueRadioButton.isChecked() || falseRadioButton.isChecked() ){
			//Toast.makeText(getActivity(), selectedRadio +" hii"+CorrectAnsTrueFalse , Toast.LENGTH_SHORT).show();
			if(selectedRadio.equalsIgnoreCase(CorrectAnsTrueFalse)){
				ourSong = MediaPlayer.create(((MainActivity)getActivity()), R.raw.correct);
				//ourSong.prepare();
				ourSong.start();
				MainActivity.latestScore++;
//				((MainActivity)getActivity()).bestScore(MainActivity.oldScore);
				Toast.makeText(getActivity(), "Right answer" , Toast.LENGTH_SHORT).show();
				submit.setEnabled(false);
			}
			else{
				ourSong = MediaPlayer.create(((MainActivity)getActivity()), R.raw.wrong);
				ourSong.start();
				submit.setEnabled(false);
				Toast.makeText(getActivity(), "Wrong Answer" , Toast.LENGTH_SHORT).show();
			}
			if (trueRadioButton.isEnabled() && !selectedRadio.equalsIgnoreCase(CorrectAnsTrueFalse)) {
				falseRadioButton.setTypeface(null, Typeface.BOLD);
			} else if (falseRadioButton.isEnabled() && selectedRadio.equalsIgnoreCase(CorrectAnsTrueFalse)) {
				falseRadioButton.setTypeface(null, Typeface.BOLD);
			}
		}
		else{
			Toast.makeText(getActivity(), "Please select atleast one answer" , Toast.LENGTH_SHORT).show();
		}
			//Toast.makeText(getActivity(), trueRadioButton.getText(), Toast.LENGTH_LONG).show(); 
			
		}
	});
	next.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(trueRadioButton.isChecked() || falseRadioButton.isChecked() ){
				((MainActivity) getActivity()).getQuestions();
					
				}
			else{
				Toast.makeText(getActivity(), "Please select atleast one answer" , Toast.LENGTH_SHORT).show();
			  }
		}
	});
	
	return linear;
	}
	
	public void setFields(String getQuestion, String getCorrect, String correctAnswer){
		DisplayQuestion = getQuestion;
		CorrectAnsTrueFalse = getCorrect;
		CorrectAnswerString = correctAnswer;
		
		
	}

}
