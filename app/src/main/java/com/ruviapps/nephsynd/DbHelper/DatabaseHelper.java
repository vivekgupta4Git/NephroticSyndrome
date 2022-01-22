package com.ruviapps.nephsynd.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.WindowInsets;


import com.ruviapps.nephsynd.HelperClasses.Consultation;
import com.ruviapps.nephsynd.HelperClasses.ConsultedForDiseases;
import com.ruviapps.nephsynd.HelperClasses.DailyUrineResult;
import com.ruviapps.nephsynd.HelperClasses.Diseases;
import com.ruviapps.nephsynd.HelperClasses.DiseasesToPatient;
import com.ruviapps.nephsynd.HelperClasses.DiseasesToPatientValue;
import com.ruviapps.nephsynd.HelperClasses.Doctor;
import com.ruviapps.nephsynd.HelperClasses.Dosage;
import com.ruviapps.nephsynd.HelperClasses.Medicine;
import com.ruviapps.nephsynd.HelperClasses.Patient;
import com.ruviapps.nephsynd.HelperClasses.Prescribtion;
import com.ruviapps.nephsynd.HelperClasses.Relapse;
import com.ruviapps.nephsynd.HelperClasses.SideEffect;
import com.ruviapps.nephsynd.HelperClasses.SideEffectToPatient;
import com.ruviapps.nephsynd.HelperClasses.UrineResultConstants;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    //Database name and Version
    public static final String DATABASE_NAME = "nephroticSyndrome.db";
    public static final int DATABASE_VERSION = 6;

    /*         CONSTANTS FOR TABLES NAME */
    public static final String PATIENT_TABLE_NAME = "Patients";
    public static final String DAILYURINERESULT_TABLE_NAME = "DailyUrineResults";
    public static final String CONSULTATION_TABLE_NAME = "Consultation";
    public static final String DISEASES_TABLE_NAME = "Diseases";
    public static final String DISEASE_TO_PATIENT_TABLE_NAME = "DiseaseToPatient";
    public static final String DOCTOR_TABLE_NAME = "Doctor";
    public static final String DOSAGE_TABLE_NAME = "Dosage";
    public static final String MEDICINE_TABLE_NAME = "Medicine";
    public static final String PRESCRIBTION_TABLE_NAME = "Prescribtion";
    public static final String RELAPSE_TABLE_NAME = "Relapse";
    public static final String SIDEEFFECT_TABLE_NAME = "SideEffect";
    public static final String SIDEEFFECT_TO_PATIENT_TABLE_NAME = "SideEffectToPatient";
    public static final String DISEASES_TO_PATIENT_VALUE_TABLE_NAME = "DiseaseToPatientValue";
    public static final String CONSULTED_FOR_DIESEASE_TABLE_NAME="ConsultedForDiseases";
    /*      CONSTANTS FOR COLUMN NAMES IS DEFINED IN THEIR RESPECTIVE CLASSES TO
     *       SORT OUT AMBIGUITY IF ANY OCCUR IN FUTURE
     * */

    /*             SQL QUERIES CONSTANTS                     */


    private static final String CREATE_PATIENT_TABLE = "CREATE TABLE " +
            PATIENT_TABLE_NAME + " ( " +
            Patient.COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Patient.COLUMN_PATIENT_FIRST_NAME + " TEXT NOT NULL,  "
            + Patient.COLUMN_PATIENT_LAST_NAME + " TEXT, "
            + Patient.COLUMN_PATIENT_AGE + " INTEGER NOT NULL, "
            + Patient.COLUMN_PATIENT_WEIGHT + " REAL,  "
            + Patient.COLUMN_PATIENT_SNAP + " BLOB )"     //we need to use byte[] array to insert/retrieve in db
            ;

    private static final String CREATE_DISEASES_TABLE = "CREATE TABLE " +
            DISEASES_TABLE_NAME + " ( "
            + Diseases.COLUMN_DISEASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Diseases.COLUMN_DISEASE_NAME + " TEXT NOT NULL )";

    private static final String CREATE_MEDICINE_TABLE = "CREATE TABLE "
            + MEDICINE_TABLE_NAME + " ( "
            + Medicine.COLUMN_MEDICINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Medicine.COLUMN_MEDICINE_NAME + " TEXT NOT NULL )";

    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE "
            + DOCTOR_TABLE_NAME + " ( "
            + Doctor.COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Doctor.COLUMN_DOCTOR_FIRST_NAME + " TEXT NOT NULL, "
            + Doctor.COLUMN_DOCTOR_LAST_NAME + " TEXT )";

    private static final String CREATE_SIDE_EFFECT_TABLE = "CREATE TABLE "
            + SIDEEFFECT_TABLE_NAME + " ( "
            + SideEffect.COLUMN_SIDEEFFECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SideEffect.COLUMN_SIDEEFFECT_NAME + " TEXT NOT NULL)";

    private static final String CREATE_RELAPSE_TABLE = " CREATE TABLE "
            + RELAPSE_TABLE_NAME + " ( "
            + Relapse.COLUMN_RELAPSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Relapse.COLUMN_PATIENT_ID + " INTEGER, "
            + Relapse.COLUMN_START_DATE + " INTEGER, "
            + Relapse.COLUMN_END_DATE + " INTEGER, "
            + Relapse.COLUMN_DURATION + " INTEGER, "
            + " CONSTRAINT FK_RELAPSE_TO_PATIENT"
            + " FOREIGN KEY (" + Relapse.COLUMN_PATIENT_ID + " )" + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + " ) )";


    private static final String CREATE_DAILY_URINE_RESULT_TABLE = "CREATE TABLE "
            + DAILYURINERESULT_TABLE_NAME + " ( "
            + DailyUrineResult.COLUMN_DUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DailyUrineResult.COLUMN_PATIENT_ID + " INTEGER, "
            + DailyUrineResult.COLUMN_RESULT + " INTEGER NOT NULL DEFAULT " + UrineResultConstants.NEGATIVE + ", "
            + DailyUrineResult.COLUMN_WEIGHT + " REAL, "
            + DailyUrineResult.COLUMN_DAILY_DATE + " INTEGER, "
            + DailyUrineResult.COLUMN_REMARKS + " TEXT, "
            + " CONSTRAINT FK_PATIENT_DAILY_RESULT"
            + " FOREIGN KEY (" + DailyUrineResult.COLUMN_PATIENT_ID + " )" + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + " ) )";


    private static final String CREATE_TABLE_DISEASES_TO_PATIENT = "CREATE TABLE "
            + DISEASE_TO_PATIENT_TABLE_NAME + " ( "
            + DiseasesToPatient.COLUMN_DTP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DiseasesToPatient.COLUMN_DISEASE_ID + " INTEGER, "
            + DiseasesToPatient.COLUMN_PATIENT_ID + " INTEGER, "
            + DiseasesToPatient.COLUMN_START_DATE + " INTEGER, "
            + DiseasesToPatient.COLUMN_END_DATE + " INTEGER, "
            + DiseasesToPatient.COLUMN_STATUS + " INTEGER, "
            + " CONSTRAINT FK_DTP_TO_DISEASE "
            + " FOREIGN KEY ( " + DiseasesToPatient.COLUMN_DISEASE_ID + " ) " + " REFERENCES "
            + DISEASES_TABLE_NAME + " ( " + Diseases.COLUMN_DISEASE_ID + " ) , "
            + " CONSTRAINT FK_DTP_TO_PATIENT "
            + " FOREIGN KEY ( " + DiseasesToPatient.COLUMN_PATIENT_ID + " ) " + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + ") )";


    private static final String CREATE_TABLE_CONSULTATION = "CREATE TABLE "
            + CONSULTATION_TABLE_NAME + " ( "
            + Consultation.COLUMN_CONSULTATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Consultation.COLUMN_PATIENT_ID + " INTEGER, "
            + Consultation.COLUMN_DOCTOR_ID + " INTEGER, "
            + Consultation.COLUMN_DATE + " INTEGER, "
            + Consultation.COLUMN_NEXT_FOLLOWUP + " INTEGER, "
            + "CONSTRAINT FK_CONSULTAITON_TO_PATIENT "
            + "FOREIGN KEY ( " + Consultation.COLUMN_PATIENT_ID + " ) " + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + " ) ,  "
            + "CONSTRAINT FK_CONSULTAITON_TO_DOCTOR "
            + "FOREIGN KEY ( " + Consultation.COLUMN_DOCTOR_ID + " ) " + " REFERENCES "
            + DOCTOR_TABLE_NAME + " ( " + Doctor.COLUMN_DOCTOR_ID + " ) )  ";


    private static final String CREATE_DOSAGE_TABLE = " CREATE TABLE "
            + DOSAGE_TABLE_NAME + " ( "
            + Dosage.COLUMN_DOSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Dosage.COLUMN_PATIENT_ID + " INTEGER, "
            + Dosage.COLUMN_DISEASE_ID + " INTEGER, "
            + Dosage.COLUMN_DOCTOR_ID + " INTEGER, "
            + Dosage.COLUMN_MEDICINE_ID + " INTEGER, "
            + Dosage.COLUMN_DOSAGE_VALUE + " TEXT, "
            + Dosage.COLUMN_GIVEN_ON_DATE + " INTEGER, "
            + "CONSTRAINT FK_DOSAGE_TO_PATIENT "
            + "FOREIGN KEY ( " + Dosage.COLUMN_PATIENT_ID + " ) " + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + " ) ,  "
            + "CONSTRAINT FK_DOSAGE_TO_DISEASE "
            + "FOREIGN KEY ( " + Dosage.COLUMN_DISEASE_ID + " ) " + " REFERENCES "
            + DISEASES_TABLE_NAME + " ( " + Diseases.COLUMN_DISEASE_ID + " ) ,  "
            + "CONSTRAINT FK_DOSAGE_TO_MEDICINE "
            + "FOREIGN KEY ( " + Dosage.COLUMN_MEDICINE_ID + " ) " + " REFERENCES "
            + MEDICINE_TABLE_NAME + " ( " + Medicine.COLUMN_MEDICINE_ID + " ) ,  "
            + "CONSTRAINT FK_DOSAGE_TO_DOCTOR "
            + "FOREIGN KEY ( " + Dosage.COLUMN_DOCTOR_ID + " ) " + " REFERENCES "
            + DOCTOR_TABLE_NAME + " ( " + Doctor.COLUMN_DOCTOR_ID + " ) )  ";


   private static final String CREATE_TABLE_SIDEFFECT_TO_PATIENT = " CREATE TABLE "
            + SIDEEFFECT_TO_PATIENT_TABLE_NAME + " ( "
            + SideEffectToPatient.COLUMN_SEPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SideEffectToPatient.COLUMN_PATIENT_ID + " INTEGER, "
            + SideEffectToPatient.COLUMN_SIDE_EFFECT_ID + " INTEGER, "
            + SideEffectToPatient.COLUMN_EXPLAIN + " TEXT, "
            + " CONSTRAINT FK_SIDEEFFECT_TO_PATIENT"
            + " FOREIGN KEY ( " + SideEffectToPatient.COLUMN_PATIENT_ID + " ) " + " REFERENCES "
            + PATIENT_TABLE_NAME + " ( " + Patient.COLUMN_PATIENT_ID + " ) ,"
            + " CONSTRAINT FK_SIDEEFFECT_TO_SIDEEFFECTNAME"
            + " FOREIGN KEY (" + SideEffectToPatient.COLUMN_SIDE_EFFECT_ID + " )" + " REFERENCES "
            + SIDEEFFECT_TABLE_NAME + " ( " + SideEffect.COLUMN_SIDEEFFECT_ID + " ) )";

    private static final String CREATE_TABLE_PRESCRIBTION = "CREATE TABLE "
            + PRESCRIBTION_TABLE_NAME + " ( "
            + Prescribtion.COLUMN_PRESCRIBE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Prescribtion.COLUMN_MEDICINE_ID + " INTEGER, "
            + Prescribtion.COLUMN_CONSULT_ID + " INTEGER, "
            + Prescribtion.COLUMN_PRESCRIBED_DOSAGE + " TEXT, "
            + Prescribtion.COLUMN_PRESCRIBED_FREQ + " TEXT, "
            + Prescribtion.COLUMN_PRESCRIBED_UNIT + " TEXT, "
            + "CONSTRAINT FK_PRESCIBTION_TO_MEDICINE "
            + "FOREIGN KEY ( " + Prescribtion.COLUMN_MEDICINE_ID + " ) " + " REFERENCES "
            + MEDICINE_TABLE_NAME + " ( " + Medicine.COLUMN_MEDICINE_ID + " ) ,  "
            + "CONSTRAINT FK_PRESCIBTION_TO_CONSULTATION "
            + "FOREIGN KEY ( " + Prescribtion.COLUMN_CONSULT_ID + " ) " + " REFERENCES "
            + CONSULTATION_TABLE_NAME + " ( " + Consultation.COLUMN_CONSULTATION_ID + " ) )  ";

    private static final String CREATE_TABLE_DISEASE_TO_PATIENT_VALUE = "CREATE TABLE "
            + DISEASES_TO_PATIENT_VALUE_TABLE_NAME + " ( "
            + DiseasesToPatientValue.COLUMN_DTPVID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DiseasesToPatientValue.COLUMN_DTPID + " INTEGER, "
            + DiseasesToPatientValue.COLUMN_VALUE + " TEXT, "
            + DiseasesToPatientValue.COLUMN_ONDATE + " INTEGER, "
            + " CONSTRAINT FK_DISEASE_TO_PATIENT_VALLUE_TO_DTP "
            + " FOREIGN KEY ( " + DiseasesToPatientValue.COLUMN_DTPVID + " ) " + " REFERENCES "
            + DISEASE_TO_PATIENT_TABLE_NAME + " ( "
            + DiseasesToPatient.COLUMN_DTP_ID + " ) )" ;

    private static final String CREATE_TABLE_CONSULTED_FOR_DISEASES = " CREATE TABLE "
            + CONSULTED_FOR_DIESEASE_TABLE_NAME + " ( "
            + ConsultedForDiseases.COLUMN_CONSULTED_FOR_DISEASED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ConsultedForDiseases.COLUMN_CONSULTATION_ID + " INTEGER, "
            + ConsultedForDiseases.COLUMN_DISEASE_ID + " INTEGER, "
            + ConsultedForDiseases.COLUMN_ON_DATE + " INTEGER, "
            + " CONSTRAINT FK_CONSULTED_FOR_DISEASES_TO_CONSULTATION "
            + " FOREIGN KEY ( " + ConsultedForDiseases.COLUMN_CONSULTATION_ID + " ) " + " REFERENCES "
            + CONSULTATION_TABLE_NAME + " ( " + Consultation.COLUMN_CONSULTATION_ID + " ),   "
            + " CONSTRAINT FK_CONSULTED_FOR_DISEASES_TO_DISEASE "
            + " FOREIGN KEY  ( "  + ConsultedForDiseases.COLUMN_DISEASE_ID + " ) " + " REFERENCES "
            + DISEASES_TABLE_NAME + " ( "
            + Diseases.COLUMN_DISEASE_ID + " ) ) " ;

    /* constructor */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*                                      CRUD Operations */
    //1. Table Patient
    //insert
    public long addPatient(String f_name, String l_name, int age, float weight, byte[] image) {
        long last_row_id;

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Patient.COLUMN_PATIENT_FIRST_NAME, f_name);
        values.put(Patient.COLUMN_PATIENT_LAST_NAME, l_name);
        values.put(Patient.COLUMN_PATIENT_AGE, age);
        values.put(Patient.COLUMN_PATIENT_WEIGHT, weight);
        values.put(Patient.COLUMN_PATIENT_SNAP, image);
        last_row_id = db.insert(PATIENT_TABLE_NAME, null, values);
        db.close();
        return last_row_id;
    }

    public int updatePatient(String f_name, String l_name, int age, float weight, byte[] image, int id_of_patient) {
        int no_of_rows_updated;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Patient.COLUMN_PATIENT_FIRST_NAME, f_name);
        values.put(Patient.COLUMN_PATIENT_LAST_NAME, l_name);
        values.put(Patient.COLUMN_PATIENT_AGE, age);
        values.put(Patient.COLUMN_PATIENT_WEIGHT, weight);
        values.put(Patient.COLUMN_PATIENT_SNAP, image);

        String selection = Patient.COLUMN_PATIENT_ID + " =? ";
        String[] selectionArgs = new String[]{String.valueOf(id_of_patient)};

        no_of_rows_updated = db.update(PATIENT_TABLE_NAME, values, selection, selectionArgs);
        db.close();
        return no_of_rows_updated;
    }

    public int deletePatient(long id_of_patient) {
        int no_of_rows_deleleted ;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Patient.COLUMN_PATIENT_ID + " =? ";
        String[] selectionArgs = new String[]{String.valueOf(id_of_patient)};
       no_of_rows_deleleted = db.delete(PATIENT_TABLE_NAME, selection, selectionArgs);
        db.close();
        return no_of_rows_deleleted;
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> arrayList = new ArrayList<>();
        String sql_query = "SELECT * FROM " + PATIENT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql_query, null);
        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setPatient_id(cursor.getLong(cursor.getColumnIndex(Patient.COLUMN_PATIENT_ID)));
                patient.setPatient_first_name(cursor.getString(cursor.getColumnIndex(Patient.COLUMN_PATIENT_FIRST_NAME)));
                patient.setPatient_last_name(cursor.getString(cursor.getColumnIndex(Patient.COLUMN_PATIENT_LAST_NAME)));
                patient.setPatient_age(cursor.getInt(cursor.getColumnIndex(Patient.COLUMN_PATIENT_AGE)));
                patient.setPatient_weight(cursor.getFloat(cursor.getColumnIndex(Patient.COLUMN_PATIENT_WEIGHT)));
                patient.setPatient_snap(cursor.getBlob(cursor.getColumnIndex(Patient.COLUMN_PATIENT_SNAP)));
                arrayList.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    //2. Disease Table

 public    long addDisease(String disease_name) {
        long last_row_id;
        ContentValues values = new ContentValues();
        values.put(Diseases.COLUMN_DISEASE_NAME, disease_name);
        SQLiteDatabase database = this.getWritableDatabase();
        last_row_id = database.insert(DISEASES_TABLE_NAME, null, values);
        database.close();
        return last_row_id;
    }

 public    String getDiseaseName(long disease_id) {
        String d_name = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String selection = Diseases.COLUMN_DISEASE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(disease_id)
        };
        Cursor c = database.query(DISEASES_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {

            d_name = c.getString(c.getColumnIndex(Diseases.COLUMN_DISEASE_NAME));
        }
        c.close();
        database.close();
        return d_name;
    }

  public   int updateDisease(long disease_id, String new_name) {
        int number_of_rows_update;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Diseases.COLUMN_DISEASE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(disease_id)
        };

        ContentValues values = new ContentValues();
        values.put(Diseases.COLUMN_DISEASE_NAME, new_name);
        number_of_rows_update = d.update(DISEASES_TABLE_NAME, values, selection, selectionArgs);
        d.close();
        return number_of_rows_update;
    }

public     int deleteDisease(long disease_id) {
        int number_of_rows_update;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Diseases.COLUMN_DISEASE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(disease_id)
        };


        number_of_rows_update = d.delete(DISEASES_TABLE_NAME, selection, selectionArgs);
        d.close();
        return number_of_rows_update;


    }

  public   ArrayList<Diseases> getAllDiseases() {
        ArrayList<Diseases> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DISEASES_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                Diseases obj = new Diseases();

                obj.setDisease_id(cursor.getInt(cursor.getColumnIndex(Diseases.COLUMN_DISEASE_ID)));
                obj.setDisease_name(cursor.getString(cursor.getColumnIndex(Diseases.COLUMN_DISEASE_NAME)));

                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    //3. DOCTOR TABLE
  public   long addDoctor(String doctor_fname, String doctor_lname) {
        long last_row_id = 0;
        ContentValues values = new ContentValues();
        values.put(Doctor.COLUMN_DOCTOR_FIRST_NAME, doctor_fname);
        values.put(Doctor.COLUMN_DOCTOR_LAST_NAME, doctor_lname);
        SQLiteDatabase database = this.getWritableDatabase();
        last_row_id = database.insert(DOCTOR_TABLE_NAME, null, values);
        database.close();
        return last_row_id;
    }

  public   String getDoctorName(long doctor_id) {
        String doc_name = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String selection = Doctor.COLUMN_DOCTOR_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(doctor_id)
        };
        Cursor c = database.query(DOCTOR_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {

            doc_name = c.getString(c.getColumnIndex(Doctor.COLUMN_DOCTOR_FIRST_NAME));
            doc_name = doc_name + " " + c.getString(c.getColumnIndex(Doctor.COLUMN_DOCTOR_LAST_NAME));
        }
        c.close();
        database.close();
        return doc_name;
    }

  public   int updateDoctor(long doctor_id, String[] new_name) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Doctor.COLUMN_DOCTOR_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(doctor_id)
        };

        ContentValues values = new ContentValues();
        values.put(Doctor.COLUMN_DOCTOR_FIRST_NAME, new_name[0]);
        values.put(Doctor.COLUMN_DOCTOR_LAST_NAME, new_name[1]);
        number_of_rows_update = d.update(DOCTOR_TABLE_NAME, values, selection, selectionArgs);
        d.close();
        return number_of_rows_update;
    }

  public   int deleteDoctor(long doctor_id) {
        int number_of_rows_update;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Doctor.COLUMN_DOCTOR_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(doctor_id)
        };


        number_of_rows_update = d.delete(DOCTOR_TABLE_NAME, selection, selectionArgs);
        d.close();
        return number_of_rows_update;


    }

  public   ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DOCTOR_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                Doctor obj = new Doctor();

                obj.setDoctor_id(cursor.getInt(cursor.getColumnIndex(Doctor.COLUMN_DOCTOR_ID)));
                obj.setDoctor_first_name(cursor.getString(cursor.getColumnIndex(Doctor.COLUMN_DOCTOR_FIRST_NAME)));
                obj.setDoctor_last_name(cursor.getString(cursor.getColumnIndex(Doctor.COLUMN_DOCTOR_LAST_NAME)));

                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    //4. Medicine Table
  public   long addMedicine(String medicine_name) {
        long last_row_id = 0;
        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, medicine_name);
        SQLiteDatabase database = this.getWritableDatabase();
        last_row_id = database.insert(MEDICINE_TABLE_NAME, null, values);
        database.close();
        return last_row_id;
    }

    public String getMedicineName(long medicine_id) {
        String m_name = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String selection = Medicine.COLUMN_MEDICINE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(medicine_id)
        };
        Cursor c = database.query(MEDICINE_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {

            m_name = c.getString(c.getColumnIndex(Medicine.COLUMN_MEDICINE_NAME));
        }
        c.close();
        database.close();
        return m_name;
    }

 public    int updateMedicine(long medicine_id, String new_name) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Medicine.COLUMN_MEDICINE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(medicine_id)
        };

        ContentValues values = new ContentValues();
        values.put(Medicine.COLUMN_MEDICINE_NAME, new_name);
        number_of_rows_update = d.update(MEDICINE_TABLE_NAME, values, selection, selectionArgs);
        d.close();
        return number_of_rows_update;
    }

   public int deleteMedicine(long medicine_id) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = Medicine.COLUMN_MEDICINE_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(medicine_id)
        };


        number_of_rows_update = d.delete(MEDICINE_TABLE_NAME, selection, selectionArgs);
        d.close();
        return number_of_rows_update;


    }

   public ArrayList<Medicine> getAllMedicines() {
        ArrayList<Medicine> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + MEDICINE_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                Medicine obj = new Medicine();

                obj.setMedicine_id(cursor.getInt(cursor.getColumnIndex(Medicine.COLUMN_MEDICINE_ID)));
                obj.setMedicine_name(cursor.getString(cursor.getColumnIndex(Medicine.COLUMN_MEDICINE_NAME)));

                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    //5. Side Effect Table
  public   long addSideEffect(String sideEffect_name) {
        long last_row_id = 0;
        ContentValues values = new ContentValues();
        values.put(SideEffect.COLUMN_SIDEEFFECT_NAME, sideEffect_name);
        SQLiteDatabase database = this.getWritableDatabase();
        last_row_id = database.insert(SIDEEFFECT_TABLE_NAME, null, values);
        database.close();
        return last_row_id;
    }

  public   String getSideEffectName(long sideEffect_id) {
        String s_name = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String selection = SideEffect.COLUMN_SIDEEFFECT_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(sideEffect_id)
        };
        Cursor c = database.query(SIDEEFFECT_TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {

            s_name = c.getString(c.getColumnIndex(SideEffect.COLUMN_SIDEEFFECT_NAME));
        }
        c.close();
        database.close();
        return s_name;
    }

    public  int updateSideEffect(long sideEffect_id, String new_name) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = SideEffect.COLUMN_SIDEEFFECT_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(sideEffect_id)
        };

        ContentValues values = new ContentValues();
        values.put(SideEffect.COLUMN_SIDEEFFECT_NAME, new_name);
        number_of_rows_update = d.update(SIDEEFFECT_TABLE_NAME, values, selection, selectionArgs);
        d.close();
        return number_of_rows_update;
    }

    public int deleteSideEffect(long sideEffect_id) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = SideEffect.COLUMN_SIDEEFFECT_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(sideEffect_id)
        };


        number_of_rows_update = d.delete(SIDEEFFECT_TABLE_NAME, selection, selectionArgs);
        d.close();
        return number_of_rows_update;

    }

    public ArrayList<SideEffect> getAllSideEffects()
    {
        ArrayList<SideEffect> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
       // Cursor cursor = database.query(SIDEEFFECT_TABLE_NAME,null,null,null,null,null,null);
        //Cursor cursor = database.query(SIDEEFFECT_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        Cursor cursor = database.rawQuery("SELECT * FROM " + SIDEEFFECT_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                SideEffect obj = new SideEffect();

                obj.setSideEffect_id(cursor.getInt(cursor.getColumnIndex(SideEffect.COLUMN_SIDEEFFECT_ID)));
                obj.setSideEffect_name(cursor.getString(cursor.getColumnIndex(SideEffect.COLUMN_SIDEEFFECT_NAME)));

                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public  ArrayList<SideEffect> getAllSideEffects(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<SideEffect> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SIDEEFFECT_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + SIDEEFFECT_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                SideEffect obj = new SideEffect();

                obj.setSideEffect_id(cursor.getInt(cursor.getColumnIndex(SideEffect.COLUMN_SIDEEFFECT_ID)));
                obj.setSideEffect_name(cursor.getString(cursor.getColumnIndex(SideEffect.COLUMN_SIDEEFFECT_NAME)));

                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


//now relational queries for rest of 7 tables
    //6. Daily Urine Result table


  public   long addDailyResult(long patient_id, int result, float change_in_weight, String remarks, int date) {
        long inserted_row_id = -1;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DailyUrineResult.COLUMN_PATIENT_ID, patient_id);
        values.put(DailyUrineResult.COLUMN_RESULT, result);
        values.put(DailyUrineResult.COLUMN_WEIGHT, change_in_weight);
        values.put(DailyUrineResult.COLUMN_REMARKS, remarks);
        values.put(DailyUrineResult.COLUMN_DAILY_DATE, date);

        inserted_row_id = database.insert(DAILYURINERESULT_TABLE_NAME, null, values);
        return inserted_row_id;

    }

   public int updateDailyResult(long patient_id, int result, float weight, String remarks, int date, int duid) {
        int number_of_rows_updated = 0;
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DailyUrineResult.COLUMN_PATIENT_ID, patient_id);
        values.put(DailyUrineResult.COLUMN_RESULT, result);
        values.put(DailyUrineResult.COLUMN_WEIGHT, weight);
        values.put(DailyUrineResult.COLUMN_REMARKS, remarks);
        values.put(DailyUrineResult.COLUMN_DAILY_DATE, date);

        String selection = DailyUrineResult.COLUMN_DUR_ID + " = ?";
        String[] selectionArgs = new String[]{
                String.valueOf(duid)
        };

        number_of_rows_updated = database.update(DAILYURINERESULT_TABLE_NAME, values, selection, selectionArgs);
        return number_of_rows_updated;
    }

  public   int deleteDailyResult(long duid) {
        int number_of_rows_updated = 0;
        SQLiteDatabase database = this.getWritableDatabase();


        String selection = DailyUrineResult.COLUMN_DUR_ID + " = ?";
        String[] selectionArgs = new String[]{
                String.valueOf(duid)
        };

        number_of_rows_updated = database.delete(DAILYURINERESULT_TABLE_NAME, selection, selectionArgs);
        return number_of_rows_updated;

    }

  public   ArrayList<DailyUrineResult> getAllDailyResult(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<DailyUrineResult> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
Cursor c = database.query(DAILYURINERESULT_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
    //    Cursor c = database.rawQuery("SELECT * FROM " + DAILYURINERESULT_TABLE_NAME, null, null);
        if (c.moveToFirst()) {

            do {
                DailyUrineResult obj = new DailyUrineResult();

                obj.setDUR_id(c.getLong(c.getColumnIndex(DailyUrineResult.COLUMN_DUR_ID)));
                obj.setPatient_id(c.getLong(c.getColumnIndex(DailyUrineResult.COLUMN_PATIENT_ID)));
                obj.setResult(c.getInt(c.getColumnIndex(DailyUrineResult.COLUMN_RESULT)));
                obj.setWeight(c.getFloat(c.getColumnIndex(DailyUrineResult.COLUMN_WEIGHT)));
                obj.setRemarks(c.getString(c.getColumnIndex(DailyUrineResult.COLUMN_REMARKS)));
                obj.setDaily_date(c.getInt(c.getColumnIndex(DailyUrineResult.COLUMN_DAILY_DATE)));
                list.add(obj);
            } while (c.moveToNext());
        }


        return list;
    }

    //7.Relapse Table
   public long addRelapse(long patient_id, int s_date, int e_date, int duration) {
        long last_row_id;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Relapse.COLUMN_PATIENT_ID, patient_id);
        values.put(Relapse.COLUMN_START_DATE, s_date);
        values.put(Relapse.COLUMN_END_DATE, e_date);
        values.put(Relapse.COLUMN_DURATION, duration);
        last_row_id = database.insert(RELAPSE_TABLE_NAME, null, values);
        database.close();
        return last_row_id;
    }

  public   int updateRelapse(long relapse_id,long patient_id,int s_date,int e_date,int duration)
    {
        int number_of_rows_updated;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Relapse.COLUMN_PATIENT_ID,patient_id);
        values.put(Relapse.COLUMN_START_DATE, s_date);
        values.put(Relapse.COLUMN_END_DATE, e_date);
        values.put(Relapse.COLUMN_DURATION, duration);
        String selection = Relapse.COLUMN_RELAPSE_ID + " = ?";
        String[] selectionArgs = new String[] {
                                    String.valueOf(relapse_id)
                                    };

        number_of_rows_updated = database.update(RELAPSE_TABLE_NAME,values,selection,selectionArgs);

        return number_of_rows_updated;


    }

  public   int deleteRelapse(long relapse_id)
    {
        int number_of_rows_deleted;
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = Relapse.COLUMN_RELAPSE_ID + " = ?";
        String[] selectionArgs = new String[] {
                String.valueOf(relapse_id)
        };

        number_of_rows_deleted = database.delete(RELAPSE_TABLE_NAME,selection,selectionArgs);

        return number_of_rows_deleted;
    }

public  ArrayList<Relapse> getAllRelapse(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
{


    ArrayList<Relapse> list = new ArrayList<>();
    SQLiteDatabase database = this.getReadableDatabase();
   // Cursor cursor= database.rawQuery("SELECT * FROM " + RELAPSE_TABLE_NAME,null);

   Cursor cursor = database.query(RELAPSE_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
    if(cursor.moveToFirst())
    {
        do {
            Relapse ob = new Relapse();
            ob.setRelapse_id(cursor.getLong(cursor.getColumnIndex(Relapse.COLUMN_RELAPSE_ID)));
            ob.setPatient_id(cursor.getLong(cursor.getColumnIndex(Relapse.COLUMN_PATIENT_ID)));
            ob.setStart_date(cursor.getInt(cursor.getColumnIndex(Relapse.COLUMN_START_DATE)));
            ob.setEnd_date(cursor.getInt(cursor.getColumnIndex(Relapse.COLUMN_END_DATE)));
            ob.setDuration(cursor.getInt(cursor.getColumnIndex(Relapse.COLUMN_DURATION)));

            list.add(ob);
        }while (cursor.moveToNext());
    }

    return list;
}

//8.Disease to Patient Table
   public long addDiseaseToPatient(long disease_id,int status,int start_date,int end_date,long patient_id)
    {
        long last_row_added_id;
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DiseasesToPatient.COLUMN_DISEASE_ID,disease_id);
        values.put(DiseasesToPatient.COLUMN_PATIENT_ID,patient_id);
        values.put(DiseasesToPatient.COLUMN_STATUS,status);
        values.put(DiseasesToPatient.COLUMN_START_DATE,start_date);
        values.put(DiseasesToPatient.COLUMN_END_DATE,end_date);
        last_row_added_id = database.insert(DISEASE_TO_PATIENT_TABLE_NAME,null,values);

        return last_row_added_id;

    }

    public int updateDiseaseToPatient(long Dtp_id,long disease_id,int status,int start_date,int end_date,long patient_id)
    {
        int number_of_rows_updated;
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DiseasesToPatient.COLUMN_DISEASE_ID,disease_id);
        values.put(DiseasesToPatient.COLUMN_PATIENT_ID,patient_id);
        values.put(DiseasesToPatient.COLUMN_STATUS,status);
        values.put(DiseasesToPatient.COLUMN_START_DATE,start_date);
        values.put(DiseasesToPatient.COLUMN_END_DATE,end_date);

        String selection = DiseasesToPatient.COLUMN_DTP_ID + " = ?";
        String[] selectionArgs = new String[] {
                String.valueOf(Dtp_id)
        };

        number_of_rows_updated = database.update(DISEASE_TO_PATIENT_TABLE_NAME,values,selection,selectionArgs);


        return number_of_rows_updated;

    }

    public int deleteDiseaseToPatient(long dtp_id)
    {
        int number_of_rows_deleted;
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = DiseasesToPatient.COLUMN_DTP_ID + " = ?";
        String[] selectionArgs = new String[] {
                String.valueOf(dtp_id)
        };

        number_of_rows_deleted = database.delete(DISEASE_TO_PATIENT_TABLE_NAME,selection,selectionArgs);

        return number_of_rows_deleted;
    }

    public ArrayList<DiseasesToPatient> getAllDiseasesToPatient(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<DiseasesToPatient> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(DISEASE_TO_PATIENT_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        //Cursor cursor= database.rawQuery("SELECT * FROM " + DISEASE_TO_PATIENT_TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                DiseasesToPatient ob = new DiseasesToPatient();
                ob.setDTP_id(cursor.getLong(cursor.getColumnIndex(DiseasesToPatient.COLUMN_DTP_ID)));
                ob.setDisease_id(cursor.getInt(cursor.getColumnIndex(DiseasesToPatient.COLUMN_DISEASE_ID)));
                ob.setStatus(cursor.getInt(cursor.getColumnIndex(DiseasesToPatient.COLUMN_STATUS)));
                ob.setPatient_id(cursor.getLong(cursor.getColumnIndex(DiseasesToPatient.COLUMN_PATIENT_ID)));
                ob.setStart_date(cursor.getInt(cursor.getColumnIndex(DiseasesToPatient.COLUMN_START_DATE)));
                ob.setEnd_date(cursor.getInt(cursor.getColumnIndex(DiseasesToPatient.COLUMN_END_DATE)));


                list.add(ob);
            }while (cursor.moveToNext());
        }

        return list;
    }

//9.Dosage Table

public  long addDosage(long medicine_id,long disease_id,String dosage,int date,long doc_id,long patient_id)
{
    long last_row_added;

    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(Dosage.COLUMN_MEDICINE_ID, medicine_id);
    values.put(Dosage.COLUMN_DISEASE_ID,disease_id);
    values.put(Dosage.COLUMN_DOSAGE_VALUE,doc_id);
    values.put(Dosage.COLUMN_GIVEN_ON_DATE,date);
    values.put(Dosage.COLUMN_DOCTOR_ID,doc_id);
    values.put(Dosage.COLUMN_PATIENT_ID,patient_id);

    last_row_added = database.insert(DOSAGE_TABLE_NAME,null,values);
database.close();
    return last_row_added;


}

public  int updateDosage(long dosage_id,long medicine_id,long disease_id,String dosage,int date,long doc_id,long patient_id)
{
    int number_of_rows_updated;
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(Dosage.COLUMN_MEDICINE_ID, medicine_id);
    values.put(Dosage.COLUMN_DISEASE_ID,disease_id);
    values.put(Dosage.COLUMN_DOSAGE_VALUE,doc_id);
    values.put(Dosage.COLUMN_GIVEN_ON_DATE,date);
    values.put(Dosage.COLUMN_DOCTOR_ID,doc_id);
    values.put(Dosage.COLUMN_PATIENT_ID,patient_id);
    String selection = Dosage.COLUMN_DOSAGE_ID + " = ?";
            String[] selectionArgs = new String[] { String.valueOf(dosage_id)};
    number_of_rows_updated = database.update(DOSAGE_TABLE_NAME,values,selection,selectionArgs);
    database.close();

   return number_of_rows_updated;
}

public  int deleteDosage(long dosage_id)
{
    int number_of_rows_updated;
    SQLiteDatabase database = this.getWritableDatabase();
    String selection = Dosage.COLUMN_DOSAGE_ID + " = ?";
    String[] selectionArgs = new String[] { String.valueOf(dosage_id)};
    number_of_rows_updated = database.delete(DOSAGE_TABLE_NAME,selection,selectionArgs);
    database.close();

    return number_of_rows_updated;

}

public     ArrayList<Dosage> getAllDosage(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<Dosage> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(DOSAGE_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        //Cursor cursor= database.rawQuery("SELECT * FROM " + DISEASE_TO_PATIENT_TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                Dosage ob = new Dosage();
                ob.setDosage_id(cursor.getLong(cursor.getColumnIndex(Dosage.COLUMN_DOSAGE_ID)));
                ob.setDisease_id(cursor.getLong(cursor.getColumnIndex(Dosage.COLUMN_DISEASE_ID)));
                ob.setMedicine_id(cursor.getLong(cursor.getColumnIndex(Dosage.COLUMN_MEDICINE_ID)));
                ob.setPatient_id(cursor.getLong(cursor.getColumnIndex(Dosage.COLUMN_PATIENT_ID)));
                ob.setDoctor_id(cursor.getLong(cursor.getColumnIndex(Dosage.COLUMN_DOCTOR_ID)));
                ob.setDosage_value(cursor.getString(cursor.getColumnIndex(Dosage.COLUMN_DOSAGE_VALUE)));
                ob.setGiven_on_date(cursor.getInt(cursor.getColumnIndex(Dosage.COLUMN_GIVEN_ON_DATE)));
                list.add(ob);
            }while (cursor.moveToNext());
        }

        return list;
    }

//10. Consultation Table


  public   long addConsultation(long doctor_id,long patient_id,int consulation_date,int followupdate)
    {
        long last_row_update;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Consultation.COLUMN_NEXT_FOLLOWUP,followupdate);
        values.put(Consultation.COLUMN_DATE,consulation_date);
        values.put(Consultation.COLUMN_PATIENT_ID,patient_id);
        values.put(Consultation.COLUMN_DOCTOR_ID,doctor_id);
        last_row_update = database.insert(CONSULTATION_TABLE_NAME,null,values);
        database.close();
        return last_row_update;
    }

  public   int updateConsultation(long consulation_id,long doctor_id,long patient_id,int consulation_date,int followupdate)
    {
        int number_of_row_update;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Consultation.COLUMN_NEXT_FOLLOWUP,followupdate);
        values.put(Consultation.COLUMN_DATE,consulation_date);
        values.put(Consultation.COLUMN_PATIENT_ID,patient_id);
        values.put(Consultation.COLUMN_DOCTOR_ID,doctor_id);
        String selection = Consultation.COLUMN_CONSULTATION_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(consulation_id)};

        number_of_row_update = database.update(CONSULTATION_TABLE_NAME,values,selection,selectionArgs);
        database.close();
        return number_of_row_update;
    }

  public   int deleteConsultation(long consulation_id)
    {
        int number_of_row_update;
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = Consultation.COLUMN_CONSULTATION_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(consulation_id)};

        number_of_row_update = database.delete(CONSULTATION_TABLE_NAME,selection,selectionArgs);
        database.close();
        return number_of_row_update;
    }

  public   ArrayList<Consultation> getAllConsultation(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<Consultation> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(CONSULTATION_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        //Cursor cursor= database.rawQuery("SELECT * FROM " + DISEASE_TO_PATIENT_TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                Consultation ob = new Consultation();
                   ob.setConsultation_id(cursor.getLong(cursor.getColumnIndex(Consultation.COLUMN_CONSULTATION_ID)));
                   ob.setPatient_id(cursor.getLong(cursor.getColumnIndex(Consultation.COLUMN_PATIENT_ID)));
                   ob.setDoctor_id(cursor.getLong(cursor.getColumnIndex(Consultation.COLUMN_DOCTOR_ID)));
                   ob.setDate_of_consultation(cursor.getInt(cursor.getColumnIndex(Consultation.COLUMN_DATE)));
                   ob.setNext_followup(cursor.getInt(cursor.getColumnIndex(Consultation.COLUMN_NEXT_FOLLOWUP)));

                list.add(ob);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }



//11.Prescibtion Table

    public long addPrescribtion(long consultation_id,long medicine_id,String dosage)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        long last_row_update_id;
        ContentValues values = new ContentValues();
        values.put(Prescribtion.COLUMN_CONSULT_ID,consultation_id);
        values.put(Prescribtion.COLUMN_MEDICINE_ID,medicine_id);
        values.put(Prescribtion.COLUMN_PRESCRIBED_DOSAGE,dosage);
last_row_update_id = database.insert(PRESCRIBTION_TABLE_NAME,null,values);

return last_row_update_id;
    }

    public int updatePrescribtion(long prescribtion_id,long consultation_id,long medicine_id,String dosage)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        int number_of_rows_updated;
        ContentValues values = new ContentValues();
        values.put(Prescribtion.COLUMN_CONSULT_ID,consultation_id);
        values.put(Prescribtion.COLUMN_MEDICINE_ID,medicine_id);
        values.put(Prescribtion.COLUMN_PRESCRIBED_DOSAGE,dosage);
        values.put(Prescribtion.COLUMN_PRESCRIBE_ID,prescribtion_id);
        String selection = Prescribtion.COLUMN_PRESCRIBE_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(prescribtion_id)};


        number_of_rows_updated = database.update(PRESCRIBTION_TABLE_NAME,values,selection,selectionArgs);

        return number_of_rows_updated;
    }

    public int deletePrescribtion(long prescribtion_id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        int number_of_rows_updated;
        String selection = Prescribtion.COLUMN_PRESCRIBE_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(prescribtion_id)};


        number_of_rows_updated = database.delete(PRESCRIBTION_TABLE_NAME,selection,selectionArgs);

        return number_of_rows_updated;
    }

public ArrayList<Prescribtion> getAllPrescribtion(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
{
    ArrayList<Prescribtion> list = new ArrayList<>();
    SQLiteDatabase database = this.getReadableDatabase();

    Cursor cursor = database.query(PRESCRIBTION_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
    //Cursor cursor= database.rawQuery("SELECT * FROM " + DISEASE_TO_PATIENT_TABLE_NAME,null);
    if(cursor.moveToFirst())
    {
        do {
            Prescribtion ob = new Prescribtion();
            ob.setConsult_id(cursor.getLong(cursor.getColumnIndex(Prescribtion.COLUMN_CONSULT_ID)));
            ob.setPrescribtion_id(cursor.getLong(cursor.getColumnIndex(Prescribtion.COLUMN_PRESCRIBE_ID)));
            ob.setMedicine_id(cursor.getLong(cursor.getColumnIndex(Prescribtion.COLUMN_MEDICINE_ID)));
            ob.setPrescribed_dosage(cursor.getString(cursor.getColumnIndex(Prescribtion.COLUMN_PRESCRIBED_DOSAGE)));
            list.add(ob);
        }while (cursor.moveToNext());
    }

    return list;


}


//12. SIDE EFFECT TO PATIENT TABLE
public long addSideEffectToPatient(long sideEffect_id,long patient_id,String remarks) {
    long last_row_id;
    ContentValues values = new ContentValues();
    values.put(SideEffectToPatient.COLUMN_SIDE_EFFECT_ID,sideEffect_id);
    values.put(SideEffectToPatient.COLUMN_PATIENT_ID,patient_id);
    values.put(SideEffectToPatient.COLUMN_EXPLAIN,remarks);

    SQLiteDatabase database = this.getWritableDatabase();

    last_row_id = database.insert(SIDEEFFECT_TO_PATIENT_TABLE_NAME, null, values);

    database.close();
    return last_row_id;
}


   public int updateSideEffectToPatient(long SETP_id,long sideEffect_id, long patient_id,String remarks) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = SideEffectToPatient.COLUMN_SEPT_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(SETP_id)
        };

        ContentValues values = new ContentValues();
        values.put(SideEffectToPatient.COLUMN_PATIENT_ID,patient_id);
        values.put(SideEffectToPatient.COLUMN_SIDE_EFFECT_ID,sideEffect_id);
        values.put(SideEffectToPatient.COLUMN_EXPLAIN,remarks);
        number_of_rows_update = d.update(SIDEEFFECT_TO_PATIENT_TABLE_NAME, values, selection, selectionArgs);
        d.close();
        return number_of_rows_update;
    }

   public int deleteSideEffectToPatient(long setp_id) {
        int number_of_rows_update = 0;
        SQLiteDatabase d = this.getWritableDatabase();
        String selection = SideEffectToPatient.COLUMN_SEPT_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(setp_id)
        };


        number_of_rows_update = d.delete(SIDEEFFECT_TO_PATIENT_TABLE_NAME, selection, selectionArgs);
        d.close();
        return number_of_rows_update;

    }

   public ArrayList<SideEffectToPatient> getAllSideEffectsToPatient(String[] projection,String selection,String[] selectionArgs,String groupBy,String having,String orderBy,String limit)
    {
        ArrayList<SideEffectToPatient> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(SIDEEFFECT_TO_PATIENT_TABLE_NAME,projection,selection,selectionArgs,groupBy,having,orderBy,limit);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + SIDEEFFECT_TABLE_NAME, null, null);
        if (cursor.moveToFirst()) {
            do {
                SideEffectToPatient obj = new SideEffectToPatient();

                obj.setPatient_id(cursor.getLong(cursor.getColumnIndex(SideEffectToPatient.COLUMN_PATIENT_ID)));
                obj.setSETP_id(cursor.getLong(cursor.getColumnIndex(SideEffectToPatient.COLUMN_SEPT_ID)));
                obj.setSide_effect_id(cursor.getLong(cursor.getColumnIndex(SideEffectToPatient.COLUMN_SIDE_EFFECT_ID)));
                obj.setExplain(cursor.getString(cursor.getColumnIndex(SideEffectToPatient.COLUMN_EXPLAIN)));
                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


//13. DISEASE VALUE TO PATIENT

    public long addDiseaseValueToPatient(long dtp_id,String value,int DateInDays)
    {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DiseasesToPatientValue.COLUMN_DTPID,dtp_id);
        contentValues.put(DiseasesToPatientValue.COLUMN_VALUE,value);
        contentValues.put(DiseasesToPatientValue.COLUMN_ONDATE,DateInDays);
       return database.insert(DISEASES_TO_PATIENT_VALUE_TABLE_NAME,null,contentValues);

    }

    public int updateDiseaseValueToPatient(long dtp_id,String value,int DateInDays,long Dtpv_id)
    {
        int number_of_rows;
        String Selection = DiseasesToPatientValue.COLUMN_DTPVID + " = ? ";
        String[] SelectionArgs = new String[]{
                String.valueOf(Dtpv_id)
        } ;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DiseasesToPatientValue.COLUMN_DTPVID,dtp_id);
        contentValues.put(DiseasesToPatientValue.COLUMN_VALUE,value);
        contentValues.put(DiseasesToPatientValue.COLUMN_ONDATE,DateInDays);
        number_of_rows = database.update(DISEASES_TO_PATIENT_VALUE_TABLE_NAME,contentValues,Selection,SelectionArgs);
        return number_of_rows;
    }

    //14. CONSULTED FOR DISEASES

    public long addConsultedForDiseases(long diseaseId, long consultation_id,int Ondate)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConsultedForDiseases.COLUMN_DISEASE_ID,diseaseId);
        contentValues.put(ConsultedForDiseases.COLUMN_CONSULTATION_ID,consultation_id);
        contentValues.put(ConsultedForDiseases.COLUMN_ON_DATE,Ondate);

        return database.insert(CONSULTED_FOR_DIESEASE_TABLE_NAME,null, contentValues);
    }

    public long getLatestConsulation(long disease_id,int date)
    {
        SQLiteDatabase database  = this.getReadableDatabase();
       // ArrayList<Long> consultedIds = new ArrayList<>();
        long latestConsulationId = -1;
        String selection = ConsultedForDiseases.COLUMN_DISEASE_ID  + " = ?" + " AND " + ConsultedForDiseases.COLUMN_ON_DATE  + " <= ? ";
        String orderBy = ConsultedForDiseases.COLUMN_ON_DATE + " DESC ";
        String[] selectionArgs = new String[] {
          String.valueOf(disease_id),String.valueOf(date)
        };
         Cursor c = database.query(CONSULTED_FOR_DIESEASE_TABLE_NAME,
                 new String[] {ConsultedForDiseases.COLUMN_CONSULTATION_ID },
                 selection,selectionArgs,null,null,orderBy);

         if(c.moveToFirst())
         {
             do {
                latestConsulationId = c.getLong(c.getColumnIndex(ConsultedForDiseases.COLUMN_CONSULTATION_ID));

             }while (c.moveToNext());
         }
         c.close();
         return  latestConsulationId;

    }
    public ArrayList<Long> getAllDiseaseConsultedForGivenConsultation(long consulation_id)
    {
        SQLiteDatabase database = this.getReadableDatabase();

        ArrayList<Long> disease_ids_list = new ArrayList<>();
        String selection = ConsultedForDiseases.COLUMN_CONSULTATION_ID + " =?";
        String sql_query = "SELECT " + ConsultedForDiseases.COLUMN_DISEASE_ID  + " FROM " + CONSULTED_FOR_DIESEASE_TABLE_NAME + " Where " + selection;

        String[] selectionArgs = new String[] {
                String.valueOf(consulation_id)
        };

        Cursor c = database.rawQuery(sql_query,selectionArgs);

        if(c.moveToFirst())
        {
            do {
                disease_ids_list.add(c.getLong(c.getColumnIndex(ConsultedForDiseases.COLUMN_DISEASE_ID)));

            }while (c.moveToNext());
        }
        c.close();
        return disease_ids_list;
    }

public long getConsultationID(long patient_id,long disease_id)
{

    return 0;
}

public long getAllConsultationID(long patient_id){

        return 0;
}


    // implementing onCreate Method
    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL(CREATE_PATIENT_TABLE);
db.execSQL(CREATE_DISEASES_TABLE);
db.execSQL(CREATE_DOCTOR_TABLE);
db.execSQL(CREATE_MEDICINE_TABLE);
db.execSQL(CREATE_SIDE_EFFECT_TABLE);
db.execSQL(CREATE_DAILY_URINE_RESULT_TABLE);
db.execSQL(CREATE_TABLE_DISEASES_TO_PATIENT);
db.execSQL(CREATE_TABLE_CONSULTATION);
db.execSQL(CREATE_DOSAGE_TABLE);
db.execSQL(CREATE_RELAPSE_TABLE);
        db.execSQL(CREATE_TABLE_PRESCRIBTION);
        db.execSQL(CREATE_TABLE_SIDEFFECT_TO_PATIENT);
        db.execSQL(CREATE_TABLE_DISEASE_TO_PATIENT_VALUE);
        db.execSQL(CREATE_TABLE_CONSULTED_FOR_DISEASES);
    }








    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONSULTED_FOR_DIESEASE_TABLE_NAME);    //14
        db.execSQL("DROP TABLE IF EXISTS "+ DISEASES_TO_PATIENT_VALUE_TABLE_NAME); //13
        db.execSQL("DROP TABLE IF EXISTS "+ SIDEEFFECT_TO_PATIENT_TABLE_NAME);      //12
        db.execSQL("DROP TABLE IF EXISTS "+ PRESCRIBTION_TABLE_NAME);               //11
        db.execSQL("DROP TABLE IF EXISTS " + RELAPSE_TABLE_NAME);               //10
        db.execSQL("DROP TABLE IF EXISTS "+ DOSAGE_TABLE_NAME);             //9
        db.execSQL("DROP TABLE IF EXISTS "+ CONSULTATION_TABLE_NAME);           //8
        db.execSQL("DROP TABLE IF EXISTS "+ DISEASE_TO_PATIENT_TABLE_NAME);     //7
        db.execSQL("DROP TABLE IF EXISTS " + DAILYURINERESULT_TABLE_NAME);      //6

        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE_NAME);      //5
        db.execSQL("DROP TABLE IF EXISTS "+ SIDEEFFECT_TABLE_NAME);     //4
        db.execSQL("DROP TABLE IF EXISTS "+ DOCTOR_TABLE_NAME);         //3
        db.execSQL("DROP TABLE IF EXISTS "+ DISEASES_TABLE_NAME);       //2
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME);       //1
        onCreate(db);

    }
}
