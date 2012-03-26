package com.vialette.maxime.android.bodybuldroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BodyBuldroidActivity extends Activity {

	boolean isRunning = false;
	private long lastPause;

	private int iterationNumber = 0;
	private int currentSecond = 45;

	private long pauseTime;

	private MouvementSpecification mouvementSpecification;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// bdd

		// Création d'une instance de ma classe LivresBDD
		final MouvementSpecificationDAO dao = new MouvementSpecificationDAO(
				this);

		// On ouvre la base de données pour écrire dedans
		dao.open();

		MouvementSpecification mouvementSpecificationFromBdd = dao
				.getMouvementSpecificationWithName("papillon");
		if (mouvementSpecificationFromBdd == null) {
			MouvementSpecification mouvementSpecification = new MouvementSpecification(
					"papillon", 20, 1);
			dao.insertMouvementSpecification(mouvementSpecification);
			mouvementSpecificationFromBdd = dao
					.getMouvementSpecificationWithName("papillon");
			if (mouvementSpecificationFromBdd == null) {

			}
			mouvementSpecification = mouvementSpecificationFromBdd;
		} else {
			mouvementSpecification = mouvementSpecificationFromBdd;
		}

		// fin bdd

		final Duration duration = new Duration(45 * 1000, 1000,
				(TextView) findViewById(R.id.countDownTextView),
				getApplicationContext(), 40 * 1000, 35 * 1000,
				(Button) findViewById(R.id.btn_start));

		final Button buttonStartTimer = (Button) findViewById(R.id.btn_start);
		buttonStartTimer.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				buttonStartTimer.setEnabled(false);
				duration.start();
				TextView textViewIterationNumber = (TextView) findViewById(R.id.textViewIterationNumber);
				iterationNumber++;
				textViewIterationNumber.setText("" + iterationNumber);
			}
		});

		final Button buttonResumeTimer = (Button) findViewById(R.id.btn_resume);
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

		final EditText chargeText = (EditText) findViewById(R.id.chargeText);
		chargeText.setText("" + mouvementSpecification.getCharge());

		Spinner s = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.exercise, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String nameToFind = ((TextView) arg1).getText().toString();
				mouvementSpecification = dao
						.getMouvementSpecificationWithName(nameToFind);
				
				chargeText.setText("" + mouvementSpecification.getCharge());
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		final Button buttonUpdate = (Button) findViewById(R.id.btn_update);
		buttonUpdate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				int newCharge = Integer.parseInt(chargeText.getText()
						.toString());

				mouvementSpecification.setCharge(newCharge);
				dao.saveOrUpdate(mouvementSpecification);
			}
		});
	}
}