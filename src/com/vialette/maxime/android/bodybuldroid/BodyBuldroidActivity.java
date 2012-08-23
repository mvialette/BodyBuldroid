package com.vialette.maxime.android.bodybuldroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BodyBuldroidActivity extends Activity {

	boolean isRunning = false;
	private long lastPause;

	private int iterationNumber = 0;
	private int currentSecond = 45;

	private long pauseTime;

	private MouvementSpecification mouvementSpecification;
	
	private MouvementSpecificationDAO dao = null;
	
	private Duration duration = null;
	
	private List<Button> buttonsToDisable = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		final Spinner serieSpinner = (Spinner) findViewById(R.id.serieSpinner);
		final ArrayAdapter<CharSequence> adapterSerieSpinner = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);
		
		final Spinner excerciceSpinner = (Spinner) findViewById(R.id.exerciceSpinner);
		final ArrayAdapter<CharSequence> adapterExercice = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_item);

		buttonsToDisable = new ArrayList<Button>();
		buttonsToDisable.add((Button) findViewById(R.id.btn_start));
		buttonsToDisable.add((Button) findViewById(R.id.btn_next));
		buttonsToDisable.add((Button) findViewById(R.id.btn_resume));
		
		duration = new Duration(45 * 1000, 1000,
				(TextView) findViewById(R.id.countDownTextView),
				getApplicationContext(), 45 * 1000, 35 * 1000,
				buttonsToDisable);
		
		final Button buttonStartTimer = (Button) findViewById(R.id.btn_start);

		final EditText chargeText = (EditText) findViewById(R.id.chargeText);

		final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar.setStepSize(1);

		final Button buttonResumeTimer = (Button) findViewById(R.id.btn_resume);

		final Button buttonNext = (Button) findViewById(R.id.btn_next);

		final Button buttonUpdate = (Button) findViewById(R.id.btn_update);

		// Création d'une instance de ma classe LivresBDD
		dao = new MouvementSpecificationDAO(
				this);

		// On ouvre la base de données pour écrire dedans
		dao.open();
		
		adapterSerieSpinner
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		for (String serie : Constantes.getSeries()) {
			adapterSerieSpinner.add(serie);
		}
		serieSpinner.setAdapter(adapterSerieSpinner);

		serieSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String serieNameToFind = serieSpinner.getSelectedItem().toString();
				String practiceNameToFind = excerciceSpinner.getSelectedItem().toString();
				
				adapterExercice.clear();
				for (String serie : Constantes.getExerciesBySerie(serieNameToFind)) {
					adapterExercice.add(serie);
				}

				mouvementSpecification = dao
						.getMouvementSpecificationWithPracticeNameAndSerieName(
								practiceNameToFind,
								serieNameToFind);

				excerciceSpinner.setSelection(0);
				chargeText.setText("" + mouvementSpecification.getCharge());
				
				upgradeCharge();
				ratingBar.setRating(mouvementSpecification.getCompleteTime());
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// Nothing to do in this case.
			}
		});

		adapterExercice
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		for (String serie : Constantes.getExerciesBySerie(serieSpinner
				.getSelectedItem().toString())) {
			adapterExercice.add(serie);
		}

		excerciceSpinner.setAdapter(adapterExercice);

		excerciceSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						String nameToFind = ((TextView) arg1).getText()
								.toString();
						mouvementSpecification = dao.getMouvementSpecificationWithPracticeNameAndSerieName(
								nameToFind,
								(String) serieSpinner.getSelectedItem());
						
						if(serieSpinner.getSelectedItemPosition() == 6)
						duration = new Duration(45 * 1000, 1000,
								(TextView) findViewById(R.id.countDownTextView),
								getApplicationContext(), 45 * 1000, 35 * 1000,
								buttonsToDisable);
						
						upgradeCharge();

						chargeText.setText(""
								+ mouvementSpecification.getCharge());
						ratingBar.setRating(mouvementSpecification
								.getCompleteTime());
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// Nothing to do in this case.
					}
				});

		MouvementSpecification mouvementSpecificationFromBdd = dao
				.getMouvementSpecificationWithPracticeNameAndSerieName(excerciceSpinner
						.getSelectedItem().toString(), serieSpinner
						.getSelectedItem().toString());
		
		if (mouvementSpecificationFromBdd == null) {
			MouvementSpecification mouvementSpecification = new MouvementSpecification(
					excerciceSpinner.getSelectedItem().toString(), 20, 1,
					serieSpinner.getSelectedItem().toString());
			dao.insertMouvementSpecification(mouvementSpecification);
			mouvementSpecificationFromBdd = dao
					.getMouvementSpecificationWithPracticeNameAndSerieName(
							excerciceSpinner.getSelectedItem().toString(),
							serieSpinner.getSelectedItem().toString());
			if (mouvementSpecificationFromBdd == null) {

			}
			mouvementSpecification = mouvementSpecificationFromBdd;
		} else {
			mouvementSpecification = mouvementSpecificationFromBdd;
		}

		if (mouvementSpecification == null) {
			ratingBar.setRating(0F);
			chargeText.setText("");
		} else {
			upgradeCharge();
			ratingBar.setRating(mouvementSpecification.getCompleteTime());
			chargeText.setText("" + mouvementSpecification.getCharge());
		}

		buttonStartTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				buttonStartTimer.setEnabled(false);
				buttonResumeTimer.setEnabled(false);
				buttonNext.setEnabled(false);
				
				duration.start();
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber++;
				textViewIterationNumber.setText("" + iterationNumber);
			}
		});

		buttonResumeTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber = 0;
				textViewIterationNumber.setText("" + iterationNumber);

				TextView countdown = (TextView) findViewById(R.id.countDownTextView);
				countdown.setText("");

				buttonStartTimer.setEnabled(true);

				duration.cancel();
			}
		});

		buttonNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				int newCharge = Integer.parseInt(chargeText.getText()
						.toString());
				
				mouvementSpecification.setCharge(newCharge);
				mouvementSpecification.setCompleteTime((int) (ratingBar
						.getRating() + 1));
				dao.saveOrUpdate(mouvementSpecification);

				if (excerciceSpinner.getSelectedItemPosition() < excerciceSpinner
						.getCount() - 1) {
					excerciceSpinner.setSelection(excerciceSpinner
							.getSelectedItemPosition() + 1);
				}
				
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber = 0;
				textViewIterationNumber.setText("" + iterationNumber);

				TextView countdown = (TextView) findViewById(R.id.countDownTextView);
				countdown.setText("");

				buttonStartTimer.setEnabled(true);

				duration.cancel();
			}
		});

		buttonUpdate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				int newCharge = Integer.parseInt(chargeText.getText()
						.toString());

				mouvementSpecification.setCharge(newCharge);
				mouvementSpecification.setCompleteTime((int) ratingBar
						.getRating());

				dao.saveOrUpdate(mouvementSpecification);
			}
		});
	}
	
	private void upgradeCharge() {
		
		if(mouvementSpecification.getPracticeName().startsWith("6-")){
			duration = new Duration(60 * 1000, 1000,
					(TextView) findViewById(R.id.countDownTextView),
					getApplicationContext(), 60 * 1000, 35 * 1000,
					buttonsToDisable);
		}else{
			duration = new Duration(45 * 1000, 1000,
					(TextView) findViewById(R.id.countDownTextView),
					getApplicationContext(), 45 * 1000, 35 * 1000,
					buttonsToDisable);			
		}
		
		// Do we have to upgrade the charge ?
		if(mouvementSpecification.getCompleteTime() == 5){
			int previousCharge = mouvementSpecification.getCharge();
			int nextCharge = mouvementSpecification.getCharge() + 5;
			Context context = getApplicationContext();
			CharSequence text = "Isn't that too easy? Let's try harder, before you do with ["+previousCharge+" Kg], now try with as least ["+nextCharge+" Kg]";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			mouvementSpecification.setCompleteTime(1);
			mouvementSpecification.setCharge(nextCharge);
			
			dao.saveOrUpdate(mouvementSpecification);
		}
	}
}