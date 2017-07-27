package com.algolstudio.idadealienigena;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class IdadeAlien extends AppCompatActivity implements View.OnClickListener{

    private InterstitialAd mInterstitialAd;
    static final String DATA = "data";
    static final String HORA = "hora";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;// = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idade_alien);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String tex=sharedPreferences.getString(HORA,null);
        String tex1=sharedPreferences.getString(DATA,null);
        if(tex!=null) {
            ((EditText) findViewById(R.id.data)).setText(tex1);
            ((EditText) findViewById(R.id.hora)).setText(tex);
        }
        else {
            ((EditText) findViewById(R.id.data)).setText("01/01/2001");
            ((EditText) findViewById(R.id.hora)).setText("00:00");
        }
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2200307858949377/4633188240");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        MobileAds.initialize(this,"ca-app-pub-2200307858949377~2098524243");
        final EditText Edit_Time = (EditText) findViewById(R.id.hora);
        Edit_Time.setOnClickListener(this);
        final EditText Edit_Data = (EditText) findViewById(R.id.data);
        Edit_Data.setOnClickListener(this);
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mudaResultado();
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    @Override
    public void onClick(View v){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        switch (v.getId()) {
            case R.id.hora:
                Calendar mcurrentTime = getCalen();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(IdadeAlien.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String tex;
                        if (selectedMinute>9) {
                            if (selectedHour > 9) {
                                tex=selectedHour + ":" + selectedMinute;
                                //((EditText) findViewById(R.id.hora)).setText();
                            }else {
                                tex="0"+selectedHour + ":" + selectedMinute;
                                //((EditText) findViewById(R.id.hora)).setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }
                        else {
                            if (selectedHour > 9) {
                                tex=selectedHour + ":0" + selectedMinute;
                                //((EditText) findViewById(R.id.hora)).setText(selectedHour + ":0" + selectedMinute);
                            }else{
                                tex="0"+selectedHour + ":0" + selectedMinute;
                                //((EditText) findViewById(R.id.hora)).setText("0"+selectedHour + ":" + selectedMinute);
                            }
                        }
                        ((EditText) findViewById(R.id.hora)).setText(tex);
                        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(HORA,tex);
                        editor.putString(DATA,((TextView) findViewById(R.id.data)).getText().toString());
                        editor.commit();
                        mudaResultado();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Que horas você nasceu?");
                mTimePicker.show();
                break;
            case R.id.data:
                Calendar mcurrentDay = getCalen();
                int ano = mcurrentDay.get(Calendar.YEAR);
                int mes = mcurrentDay.get(Calendar.MONTH);
                int dia = mcurrentDay.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(IdadeAlien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMounth, int selectedDay) {
                        String tex;
                        if(selectedDay>9) {
                            if (selectedMounth > 8) {
                                tex=selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear;
                                //((EditText) findViewById(R.id.data)).setText(selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear);
                            }else{
                                tex=selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear;
                                //((EditText) findViewById(R.id.data)).setText(selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear);
                            }
                        }else{
                            if (selectedMounth > 8) {
                                tex="0"+selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear;
                                //((EditText) findViewById(R.id.data)).setText("0"+selectedDay + "/" + (selectedMounth + 1) + "/" + selectedYear);
                            }else{
                                tex="0"+selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear;
                                //((EditText) findViewById(R.id.data)).setText("0"+selectedDay + "/0" + (selectedMounth + 1) + "/" + selectedYear);
                            }
                        }
                        ((EditText) findViewById(R.id.data)).setText(tex);
                        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(DATA,tex);
                        editor.putString(HORA,((TextView) findViewById(R.id.hora)).getText().toString());
                        editor.commit();
                        mudaResultado();
                    }
                }, ano, mes, dia);
                mDatePicker.setTitle("Que dia você nasceu?");
                mDatePicker.show();
                break;
            default:
                break;
        }
    }

    public Calendar getCalen(){
        String diaIn=((TextView) findViewById(R.id.data)).getText().toString();
        int dia1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")));
        diaIn=diaIn.substring(diaIn.indexOf("/")+1);
        int mes1= Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")))-1;
        int ano1=Integer.parseInt(diaIn.substring(diaIn.indexOf("/")+1));
        String horaIn=((TextView) findViewById(R.id.hora)).getText().toString();
        int hora1= Integer.parseInt(horaIn.substring(0,horaIn.indexOf(":")));
        int minuto1=Integer.parseInt(horaIn.substring(horaIn.indexOf(":")+1));
        Calendar calendar = new GregorianCalendar(ano1,mes1,dia1,hora1,minuto1);
        return calendar;
    };

    public void mudaResultado(){
        Calendar mcurrent = Calendar.getInstance();
        int hour = mcurrent.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrent.get(Calendar.MINUTE);
        int second = mcurrent.get(Calendar.SECOND);
        int ano = mcurrent.get(Calendar.YEAR);
        int mes = mcurrent.get(Calendar.MONTH);
        int dia = mcurrent.get(Calendar.DAY_OF_MONTH);
        Calendar calendar = new GregorianCalendar(ano,mes,dia,hour,minute,second);
        double atual = GetJulianDate(calendar);
        calendar = getCalen();
        double nasceu = GetJulianDate(calendar);
        ((TextView) findViewById(R.id.Mercurio)).setText(String.valueOf((int) (atual/87.97-nasceu/87.97)));
        ((TextView) findViewById(R.id.Venus)).setText(String.valueOf((int) (atual/224.7-nasceu/224.7)));
        ((TextView) findViewById(R.id.Marte)).setText(String.valueOf((int) (atual/686.98-nasceu/686.98)));
        ((TextView) findViewById(R.id.Jupiter)).setText(String.valueOf((int) (atual/11.86/365.24-nasceu/11.86/365.24)));
        ((TextView) findViewById(R.id.Saturno)).setText(String.valueOf((int) (atual/29.46/365.24-nasceu/29.46/365.24)));
        ((TextView) findViewById(R.id.Urano)).setText(String.valueOf((int) (atual/84.02/365.24-nasceu/84.02/365.24)));
        ((TextView) findViewById(R.id.Netuno)).setText(String.valueOf((int) (atual/164.79/365.24-nasceu/164.79/365.24)));
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        String diaIn=((TextView) findViewById(R.id.data)).getText().toString();
        String dia1= diaIn.substring(0,diaIn.indexOf("/"));
        diaIn=diaIn.substring(diaIn.indexOf("/")+1);
        String mes1= Integer.toString(Integer.parseInt(diaIn.substring(0,diaIn.indexOf("/")))-1);
        String ano1=diaIn.substring(diaIn.indexOf("/")+1);
        String horaIn=((TextView) findViewById(R.id.hora)).getText().toString();
        String hora1= horaIn.substring(0,horaIn.indexOf(":"));
        String minuto1=horaIn.substring(horaIn.indexOf(":")+1);
        if(Integer.parseInt(mes1)>9) {
            if(Integer.parseInt(dia1)>9) {
                savedInstanceState.putString(DATA, dia1 + "/" + mes1 + "/" + ano1);
            }else{savedInstanceState.putString(DATA, "0"+dia1 + "/" + mes1 + "/" + ano1);}
        }else{
            if (Integer.parseInt(dia1) > 9) {
                savedInstanceState.putString(DATA, dia1 + "/0" + mes1 + "/" + ano1);
            }else{savedInstanceState.putString(DATA, "0"+dia1 + "/0" + mes1 + "/" + ano1);
            }
        }
        if(Integer.parseInt(minuto1)<10) {
            if (Integer.parseInt(hora1) > 9) {
                savedInstanceState.putString(HORA, hora1 + ":0" + minuto1);
            } else {
                savedInstanceState.putString(HORA, "0"+hora1 + ":0" + minuto1);
            }
        }else{
            if (Integer.parseInt(hora1) > 9) {
                savedInstanceState.putString(HORA, hora1 + ":" + minuto1);
            } else {
                savedInstanceState.putString(HORA, "0"+hora1 + ":" + minuto1);
            }
        }
        super.onSaveInstanceState(savedInstanceState);};

    public static double GetJulianDate(Calendar calendarDate){

        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH) + 1;
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);
        double hour = calendarDate.get(Calendar.HOUR_OF_DAY);
        double minute = calendarDate.get(Calendar.MINUTE);
        double second = calendarDate.get(Calendar.SECOND);
        int isGregorianCal = 1;
        int A;
        int B;
        int C;
        int D;
        double fraction = day + ((hour + (minute / 60) + (second / 60 / 60)) / 24);

        if (year < 1582)
        {
            isGregorianCal = 0;
        }

        if (month < 3)
        {
            year = year - 1;
            month = month + 12;
        }

        A = year / 100;
        B = (2 - A + (A / 4)) * isGregorianCal;

        if (year < 0)
        {
            C = (int)((365.25 * year) - 0.75);
        }
        else
        {
            C = (int)(365.25 * year);
        }

        D = (int)(30.6001 * (month + 1));
        double JD = B + C + D + 1720994.5 + fraction;

        return JD;
    }}