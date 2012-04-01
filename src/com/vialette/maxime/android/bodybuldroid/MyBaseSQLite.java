package com.vialette.maxime.android.bodybuldroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyBaseSQLite extends SQLiteOpenHelper {

	// BodyBulDroid MouVemenT SPECification : bbd_mvt_spec
	public static final String TABLE_MOUVEMENT_SPECIFICATION = "BBD_MVT_SPEC";

	public static final String COL_ID = "ID";
	public static final int COL_ID_POSITION = 0;

	public static final String COL_PRACTICE_NAME = "PRACTICE_NAME";
	public static final int COL_PRACTICE_NAME_POSITION = 1;

	public static final String COL_CHARGE = "CHARGE";
	public static final int COL_CHARGE_POSITION = 2;

	public static final String COL_COMPLETE_TIME = "TIME";
	public static final int COL_COMPLETE_TIME_POSITION = 3;
	
	public static final String COL_SERIE_NAME = "SERIE_NAME";
	public static final int COL_SERIE_NAME_POSITION = 4;
	

	private static final String CREATE_BDD = "CREATE TABLE "
			+ TABLE_MOUVEMENT_SPECIFICATION + " (" 
			+ COL_ID	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_PRACTICE_NAME + " TEXT NOT NULL, " 
			+ COL_CHARGE + " INTEGER NOT NULL, "
			+ COL_COMPLETE_TIME + " INTEGER NOT NULL, "
			+ COL_SERIE_NAME + " TEXT NOT NULL);";

	public MyBaseSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// on créé la table à partir de la requête écrite dans la variable
		// CREATE_BDD
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table
		// et de la recréer
		// comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_MOUVEMENT_SPECIFICATION + ";");
		onCreate(db);
	}

}