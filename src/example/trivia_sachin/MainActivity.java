package example.trivia_sachin;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	// ProgressDialog proDialog = new ProgressDialog(MainActivity.this);
	private static JSONObject QuizQuestions;
	private static String jsonResult;
	private static JSONArray questionsArray = getjsonData();
	public static int COUNT_Question = 0;
	String QuestionType;
	public static int oldScore;
	public static int latestScore=0;
	final String PREFS_NAME = "Score_prefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 /*Toast.makeText(MainActivity.this, questionsArray.toString(),
		 Toast.LENGTH_LONG).show();*/
		/*
		 * proDialog.setMessage("Quiz is Starting");
		 * proDialog.setIndeterminate(false); proDialog.setMax(100);
		 * proDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		 */
		latestScore = 0;
		setContentView(R.layout.activity_main);
		//goToMainScren();
		FragmentManager FragManager = getSupportFragmentManager();
		FragmentTransaction trans = FragManager.beginTransaction();
		WelcomeFragment welcome = new WelcomeFragment();
		trans.add(R.id.showLayout, welcome);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.addToBackStack(null);
		trans.commit();
	}
	/*@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static JSONArray getjsonData() {
		JsonParser json = new JsonParser();
		try {
			jsonResult = json.execute(
					"https://dl.dropboxusercontent.com/u/8606210/trivia.json")
					.get();
			QuizQuestions = new JSONObject(jsonResult);
			questionsArray = QuizQuestions.getJSONArray("questions");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// To shuffle Json array
		// http://stackoverflow.com/questions/5531130/an-efficient-way-to-shuffle-a-json-array-in-java
		// (Reference)
		Random rnd = new Random();
		for (int i = questionsArray.length() - 1; i >= 0; i--) {
			int j = rnd.nextInt(i + 1);
			// Simple swap

			try {
				JSONObject object = questionsArray.getJSONObject(j);
				questionsArray.put(j, questionsArray.getJSONObject(i));
				questionsArray.put(i, object);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return questionsArray;
	}
	
	
	
	// To shuffle options
		public String[] schuffling(String[] array){
		Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--){
	        int j = rnd.nextInt(i + 1);
	        // Simple swap
	       	String str = array[j];
	       	array[j] = array[i];
	       	array[i] = str;
        }
        return array;
	}
// For true false question
		public void callTrueFalseFragment(JSONObject jObj) throws JSONException{
			//bestScore(0);
			FragmentManager FragManager = getSupportFragmentManager();
			FragmentTransaction trans = FragManager.beginTransaction();
			TrueFalseFragment trueFalseFragment = new TrueFalseFragment();
			trueFalseFragment.setFields(jObj.getString("question"), jObj.getString("correctAnswer"), jObj.getString("answer"));
		//Toast.makeText(MainActivity.this, "Question"+jObj.getString("question")+"COrrect anse"+ jObj.getString("correctAnswer")+"anser"+jObj.getString("answer"), Toast.LENGTH_LONG).show(); 
			trans.replace(R.id.showLayout, trueFalseFragment);
			trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			trans.addToBackStack(null);
			trans.commit();
		}
	
	// For MCQ question type
public void callMCQFragment(JSONObject jObj) throws JSONException{
	
		FragmentManager FragManager = getSupportFragmentManager();
		FragmentTransaction trans = FragManager.beginTransaction();
		MCQFragment mcqFrag = new MCQFragment();
		JSONArray wrongAnswers = jObj.getJSONArray("incorrectAnswers");
		final String [] options = new String[wrongAnswers.length()+1];
		for(int i = 0; i< wrongAnswers.length(); i++){
			options[i] = wrongAnswers.getString(i);
			 
		}
		options[3] = jObj.getString("correctAnswer");
		String[] shuffledOptions = schuffling(options);
		//Toast.makeText(MainActivity.this, "1"+shuffledOptions[0] +"2"+shuffledOptions[1]+"3"+shuffledOptions[2]+"4"+shuffledOptions[3] , Toast.LENGTH_LONG).show();
		mcqFrag.setFields(jObj.getString("question"), shuffledOptions[0], shuffledOptions[1],
				shuffledOptions[2], shuffledOptions[3],jObj.getString("imageUrl"), jObj.getString("correctAnswer"));
		trans.replace(R.id.showLayout, mcqFrag);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.addToBackStack(null);
		trans.commit();

	}
	
	
	
	public void getQuestions() {
		//Toast.makeText(MainActivity.this, questionsArray.toString(), Toast.LENGTH_LONG).show(); 
		int length=  questionsArray.length();
			//Toast.makeText(MainActivity.this, Integer.toString(length), Toast.LENGTH_LONG).show();  
		while (COUNT_Question < questionsArray.length()) {
		 try {
			QuestionType= questionsArray.getJSONObject(COUNT_Question).getString("questionType");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //Toast.makeText(MainActivity.this, QuestionType, Toast.LENGTH_LONG).show(); 
		
		
		try {
			if(QuestionType.equals("multipleChoice")){
			
				callMCQFragment(questionsArray.getJSONObject(COUNT_Question));
				bestScore(latestScore);
				
			}
			else if (QuestionType.equals("trueFalse")) {
				//Toast.makeText(MainActivity.this, "In true false", Toast.LENGTH_LONG).show(); 
				callTrueFalseFragment(questionsArray.getJSONObject(COUNT_Question));
				bestScore(latestScore);
			}{
				
			} 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
		
	}
		COUNT_Question++;
		if(COUNT_Question > questionsArray.length()){
			COUNT_Question = 0;
			questionsArray = getjsonData();
			bestScore(latestScore);
			goToMainScren();
			
			
	}
}
	public void goToMainScren(){
		//latestScore=0;
		//bestScore(latestScore);
		FragmentManager FragManager = getSupportFragmentManager();
		FragmentTransaction trans = FragManager.beginTransaction();
		WelcomeFragment welcome = new WelcomeFragment();
		trans.replace(R.id.showLayout, welcome);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.addToBackStack(null);
		trans.commit();
		latestScore=0;
	}

	public void highScore(int recent, int highestScore) {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Current Score: " + recent + " Best Score: " + highestScore);
	}

	public void bestScore(int latestScore) {
		SharedPreferences prefer = getSharedPreferences(PREFS_NAME, 0);
		oldScore = prefer.getInt("Score_prefs", 0);
		Editor editor = prefer.edit();
		if (oldScore < latestScore) {
			//Toast.makeText(this, "latestScore"+latestScore , Toast.LENGTH_SHORT).show();
			//Toast.makeText(this, "OldScore"+oldScore , Toast.LENGTH_SHORT).show();
			editor.putInt("Score_prefs", latestScore);
			editor.commit();
			//highScore(score, score);
			highScore(latestScore, latestScore);
		} else {
			//Toast.makeText(this, ""+latestScore , Toast.LENGTH_SHORT).show();
			editor.putInt("Score_prefs", oldScore);
			editor.commit();
			highScore(latestScore, oldScore);
		}
	}
}
