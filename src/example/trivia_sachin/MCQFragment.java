package example.trivia_sachin;

import java.io.IOException;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.image.SmartImageView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MCQFragment extends Fragment {
	private RadioButton option1, option2, option3, option4;
	private static String QuestionDesription, choice1, choice2, choice3,
			choice4, ImageURL, answer;
	private SmartImageView imageToDisplay;
	private TextView txtForQuestion;
	private Button submitButtton, nextBtn;
	private String selectedRadio;
	MediaPlayer ourSong;
	public MCQFragment() {
		// Required empty public constructor
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		inflater.inflate(R.layout.fragment_mcq, container, false);

		LinearLayout linear = (LinearLayout) inflater.inflate(
				R.layout.fragment_mcq, container, false);

		// To get the referneces
		txtForQuestion = (TextView) linear.findViewById(R.id.MCQ);
		option1 = (RadioButton) linear.findViewById(R.id.option1);
		option2 = (RadioButton) linear.findViewById(R.id.option2);
		option3 = (RadioButton) linear.findViewById(R.id.option3);
		option4 = (RadioButton) linear.findViewById(R.id.option4);
		imageToDisplay = (SmartImageView) linear
				.findViewById(R.id.Displayimage);

		// To set the data in layout
		txtForQuestion.setText(QuestionDesription);
		imageToDisplay.setImageUrl(ImageURL);
		option1.setText(choice1);
		option2.setText(choice2);
		option3.setText(choice3);
		option4.setText(choice4);

		submitButtton = (Button) linear.findViewById(R.id.submitButtton);
		 option1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectedRadio = (String) option1.getText();
				}
		});
		 option2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectedRadio = (String) option2.getText();
					}
			});
		 option3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectedRadio = (String) option3.getText();
					}
			});
		 option4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selectedRadio = (String) option4.getText();
					}
			});
		 
		 
	// Onsubmit check weather answer is correct or not
		submitButtton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()){
		//Toast.makeText(getActivity(), "selectedRadio"+selectedRadio + ";answer"+answer , Toast.LENGTH_SHORT).show();
					if(selectedRadio.equalsIgnoreCase(answer)){
						ourSong = MediaPlayer.create(((MainActivity)getActivity()), R.raw.correct);
						//ourSong.prepare();
						ourSong.start();
						MainActivity.latestScore++;
//						((MainActivity)getActivity()).bestScore(MainActivity.latestScore);
						submitButtton.setEnabled(false);
						Toast.makeText(getActivity(), "Right answer" , Toast.LENGTH_SHORT).show();
					}
					else{
						ourSong = MediaPlayer.create(((MainActivity)getActivity()), R.raw.wrong);
						ourSong.start();
						submitButtton.setEnabled(false);
						Toast.makeText(getActivity(), "Wrong Answer" , Toast.LENGTH_SHORT).show();
					}
					if (option1.isEnabled() && choice1.equals(answer)) {
						option1.setTypeface(null, Typeface.BOLD);
					} else if (option2.isEnabled() && choice2.equals(answer)) {
						option2.setTypeface(null, Typeface.BOLD);
					} else if (option3.isEnabled() && choice3.equals(answer)) {
						option3.setTypeface(null, Typeface.BOLD);
					} else if (option4.isEnabled() && choice4.equals(answer)) {
						option4.setTypeface(null, Typeface.BOLD);
					}
						//ourSong.stop();
					}
				else{
					Toast.makeText(getActivity(), "Please select atleast one answer" , Toast.LENGTH_SHORT).show();
				  }
				
			}
		});

		nextBtn = (Button) linear.findViewById(R.id.nextBtn);
		// On click of nex button again call getQuestion function where we have
		// stored all json data
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(option1.isChecked() || option2.isChecked() || option3.isChecked() || option4.isChecked()){
					((MainActivity) getActivity()).getQuestions();
						
					}
				else{
					Toast.makeText(getActivity(), "Please select atleast one answer" , Toast.LENGTH_SHORT).show();
				  }
			}
		});
		return linear;
	}

	public void setFields(String question, String getOption1,
			String getOption2, String getOption3, String getOption4,
			String getUrl, String getAnswer) {
		QuestionDesription = question;
		choice1 = getOption1;
		choice2 = getOption2;
		choice3 = getOption3;
		choice4 = getOption4;
		ImageURL = getUrl;
		answer = getAnswer;
	}

}
