package example.trivia_sachin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class WelcomeFragment extends Fragment {
	private ImageView start;
	
	final String PREFS_NAME = "Score_prefs";
	public WelcomeFragment() {
		// Required empty public constructor
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		SharedPreferences prefer = getActivity().getSharedPreferences(PREFS_NAME, 0);
		int oldScore = prefer.getInt("Score_prefs", 0);
		((MainActivity)getActivity()).highScore(0, oldScore);
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_welcome, container, false);
		start= (ImageView)linearLayout.findViewById(R.id.playButton);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity)getActivity()).getQuestions();
			
			}
		});
		return linearLayout;
	}

}
