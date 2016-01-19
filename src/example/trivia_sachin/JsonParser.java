package example.trivia_sachin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class JsonParser extends AsyncTask<String, Void, String> {
	private static String jsonString;
	HttpURLConnection con;
	InputStream inputStream;
	BufferedReader buffRead;

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		String Url = params[0];
		try {
			con = (HttpURLConnection) (new URL(Url)).openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			StringBuffer buffer = new StringBuffer();
			inputStream = con.getInputStream();
			 buffRead = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = buffRead.readLine()) != null)
				buffer.append(line + "\r\n");
			inputStream.close();
			con.disconnect();
			return buffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
