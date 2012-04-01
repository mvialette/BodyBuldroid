package com.vialette.maxime.android.bodybuldroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MouvementSpecificationDAO {
	private static final int VERSION_BDD = 3;
	private static final String NOM_BDD = "BodyBuldroid.db";

	private SQLiteDatabase bdd;

	private MyBaseSQLite maBaseSQLite;

	public MouvementSpecificationDAO(Context context) {
		// On cr�er la BDD et sa table
		maBaseSQLite = new MyBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		// on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}

	public void close() {
		// on ferme l'acc�s � la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long insertMouvementSpecification(
			MouvementSpecification mouvementSpecification) {
		// Cr�ation d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		// on lui ajoute une valeur associ� � une cl� (qui est le nom de la
		// colonne dans laquelle on veut mettre la valeur)
		values.put(MyBaseSQLite.COL_PRACTICE_NAME, mouvementSpecification.getPracticeName());
		values.put(MyBaseSQLite.COL_CHARGE, mouvementSpecification.getCharge());
		values.put(MyBaseSQLite.COL_COMPLETE_TIME,mouvementSpecification.getCompleteTime());
		values.put(MyBaseSQLite.COL_SERIE_NAME,mouvementSpecification.getSerieName());

		// on ins�re l'objet dans la BDD via le ContentValues
		return bdd.insert(MyBaseSQLite.TABLE_MOUVEMENT_SPECIFICATION, null,
				values);
	}

	public int updateMouvementSpecification(int id,
			MouvementSpecification mouvementSpecification) {
		// La mise � jour d'un MouvementSpecification dans la BDD fonctionne
		// plus ou moins comme
		// une insertion
		// il faut simple pr�ciser quelle MouvementSpecification on doit mettre
		// � jour gr�ce �
		// l'ID
		ContentValues values = new ContentValues();
		values.put(MyBaseSQLite.COL_PRACTICE_NAME, mouvementSpecification.getPracticeName());
		values.put(MyBaseSQLite.COL_CHARGE, mouvementSpecification.getCharge());
		values.put(MyBaseSQLite.COL_COMPLETE_TIME,mouvementSpecification.getCompleteTime());

		return bdd.update(MyBaseSQLite.TABLE_MOUVEMENT_SPECIFICATION, values,
				MyBaseSQLite.COL_ID + " = " + id, null);
	}

	public int removeMouvementSpecificationWithID(int id) {
		// Suppression d'un MouvementSpecification de la BDD gr�ce � l'ID
		return bdd.delete(MyBaseSQLite.TABLE_MOUVEMENT_SPECIFICATION,
				MyBaseSQLite.COL_ID + " = " + id, null);
	}

	public MouvementSpecification getMouvementSpecificationWithPracticeNameAndSerieName(
			String practiceName, String serieName) {
		// R�cup�re dans un Cursor les valeur correspondant � un
		// MouvementSpecification contenu
		// dans la BDD (ici on s�lectionne le MouvementSpecification gr�ce � son
		// titre)
		Cursor c = bdd
				.query(MyBaseSQLite.TABLE_MOUVEMENT_SPECIFICATION,
						new String[] { MyBaseSQLite.COL_ID,
								MyBaseSQLite.COL_PRACTICE_NAME, MyBaseSQLite.COL_CHARGE,
								MyBaseSQLite.COL_COMPLETE_TIME, MyBaseSQLite.COL_SERIE_NAME },
						MyBaseSQLite.COL_PRACTICE_NAME + " = \"" + practiceName + "\" and  "+ MyBaseSQLite.COL_SERIE_NAME +" = \"" + serieName + "\"", null,
						null, null, null);
		MouvementSpecification resultat = cursorToMouvementSpecification(c);

		if (resultat == null) {
			resultat = new MouvementSpecification(practiceName, 10, 1, serieName);
		}

		return resultat;
	}

	// Cette m�thode permet de convertir un cursor en un MouvementSpecification
	private MouvementSpecification cursorToMouvementSpecification(Cursor c) {
		// si aucun �l�ment n'a �t� retourn� dans la requ�te, on renvoie null
		if (c.getCount() == 0)
			return null;

		// Sinon on se place sur le premier �l�ment
		c.moveToFirst();
		// On cr�� un MouvementSpecification
		MouvementSpecification mouvementSpecification = new MouvementSpecification(
				c.getString(MyBaseSQLite.COL_PRACTICE_NAME_POSITION),
				c.getInt(MyBaseSQLite.COL_CHARGE_POSITION),
				c.getInt(MyBaseSQLite.COL_COMPLETE_TIME_POSITION),
				c.getString(MyBaseSQLite.COL_SERIE_NAME_POSITION));
		// on lui affecte toutes les infos gr�ce aux infos contenues dans le
		// Cursor
		mouvementSpecification.setId(c.getInt(MyBaseSQLite.COL_ID_POSITION));
		// On ferme le cursor
		c.close();

		// On retourne le MouvementSpecification
		return mouvementSpecification;
	}

	public void saveOrUpdate(MouvementSpecification mouvementSpecification) {
		if (mouvementSpecification.getId() > 0) {
			updateMouvementSpecification(mouvementSpecification.getId(),
					mouvementSpecification);
		} else {
			insertMouvementSpecification(mouvementSpecification);
		}
	}
}
