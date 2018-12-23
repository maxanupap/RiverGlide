package com.max.hello;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;

public class  MapsActivity extends FragmentActivity implements
        GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback{

    private GoogleMap mMap;
    private LatLng p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,
            t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,
            t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40,r1;

    private Marker P1,P2,P3,P4,P5,P6,P7,P8,P9,P10,P11,P12,P13,P14,P15,P16,P17,P18,P19,P20,P21,
            T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,
            T21,T22,T23,T24,T25,T26,T27,T28,T29,T30,T31,T32,T33,T34,T35,T36,T37,T38,T39,T40,R1;

    //private static final String URL = "http://www.manocamera.com/locationupdate.php";

    String la, lo;
    String latstop, lonstop;
    String name, id, stop, type, line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button bttime = (Button) findViewById(R.id.bttime);
        final TextView txtitle = (TextView) findViewById(R.id.txview);
        final TextView txttime = (TextView) findViewById(R.id.txtime);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        la = intent.getStringExtra("slat");
        lo = intent.getStringExtra("slon");
        latstop = intent.getStringExtra("lat");
        lonstop = intent.getStringExtra("log");
        id = intent.getStringExtra("id");
        stop = intent.getStringExtra("stop");
        type = intent.getStringExtra("type");
        line = intent.getStringExtra("line");



        bttime.setOnClickListener(new View.OnClickListener() {

            String idstopboat = id;

            public void onClick(View v) {

                Response.Listener<String> responseListener = new
                        Response.Listener<String>() {
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {

                                        String dis1 = jsonResponse.getString("dis1");
                                        String dis2 = jsonResponse.getString("dis2");
                                        la = jsonResponse.getString("lat");
                                        lo = jsonResponse.getString("lon");
                                        Double s1 = Double.parseDouble(dis1);
                                        Double s2 = Double.parseDouble(dis2);
                                        Double vb = s2 / 60;
                                        Double time = s1 / vb;
                                        int totalSecs = time.intValue();
                                        int hours = totalSecs / 3600;
                                        int minutes = (totalSecs % 3600) / 60;
                                        int seconds = totalSecs % 60;

                                        String timetxt = String.format("%02d ชั่วโมง %02d นาที %02d วินาที ", hours, minutes, seconds);
                                        String title = "เรือ " + name + " จะมาถึง " + stop + " ในอีก ";
                                        Toast.makeText(getBaseContext(), "ความเร็ว เรือ : " + String.format("%.0f", vb) + " m/s \n ระยะห่างจากเรือ "
                                                + name + " - " + stop + " : " + String.format("%.0f", s1) + " m", Toast.LENGTH_SHORT).show();

                                        txtitle.setText(title);
                                        txttime.setText(timetxt);

                                    } else {

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                DistanRequest distanRequest = new DistanRequest(idstopboat, type, line, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
                queue.add(distanRequest);

            }
        });
    }
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SuptportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        Double stlat = Double.parseDouble(latstop);
        Double stlon = Double.parseDouble(lonstop);
        Double lat = Double.parseDouble(la);
        Double lon = Double.parseDouble(lo);


        // Add a marker in Sydney and move the camera
        p1 = new LatLng(13.7065312, 100.5039185);//ลำดับตามพี่เขียนตามเล่ม
        p2 = new LatLng(13.709282, 100.506822);
        p3 = new LatLng(13.752383, 100.488301);
        p4 = new LatLng(13.746141, 100.490146);
        p5 = new LatLng(13.739927, 100.498164);
        p6 = new LatLng(13.738520, 100.504571);
        p7 = new LatLng(13.732483, 100.511963);
        p8 = new LatLng(13.728497, 100.513235);
        p9 = new LatLng(13.842143, 100.490956);
        p10 = new LatLng(13.832213, 100.493867);
        p11 = new LatLng(13.812973, 100.513380);
        p12 = new LatLng(13.806424, 100.518834);
        p13 = new LatLng(13.798720, 100.517220);
        p14 = new LatLng(13.787467, 100.508251);
        p15 = new LatLng(13.781863, 100.501076);
        p16 = new LatLng(13.771977, 100.500218);
        p17 = new LatLng(13.763646, 100.494132);
        p18 = new LatLng(13.763021, 100.490613);
        p19 = new LatLng(13.755976, 100.486815);
        p20 = new LatLng(13.723335, 100.513589);
        p21 = new LatLng(13.718973, 100.512795);
        t1 = new LatLng(13.7047039,100.5004527);
        t2 = new LatLng(13.724067 , 100.513905);
        t3 = new LatLng(13.723391, 100.514432);
        t4 = new LatLng(13.733109 , 100.512034);
        t5 = new LatLng(13.746307 , 100.490101);
        t6 = new LatLng(13.746152 , 100.490467);
        t7 = new LatLng(13.799404 , 100.518530);
        t8 = new LatLng(13.798696 , 100.519336);
        t9 = new LatLng(13.771792 , 100.500223);
        t10 = new LatLng(13.768379 , 100.499590);
        t11 = new LatLng(13.764051 , 100.495198);
        t12 = new LatLng(13.763875 , 100.495763);
        t13 = new LatLng(13.842326 , 100.491713);
        t14 = new LatLng(13.842277 , 100.492142);
        t15 = new LatLng(13.842810 , 100.494738);
        t16 = new LatLng(13.842605,100.492301);
        t17 = new LatLng(13.738744 , 100.504310);
        t18 = new LatLng(13.828120 , 100.495862);
        t19 = new LatLng(13.812616 , 100.517896);
        t20 = new LatLng(13.813746 , 100.519030);
        t21 = new LatLng(13.744396 , 100.488301);
        t22 = new LatLng(13.742152 , 100.490845);
        t23 = new LatLng(13.781499 , 100.500729);
        t24 = new LatLng(13.781952 , 100.500993);
        t25 = new LatLng(13.740500 , 100.498091);
        t26 = new LatLng(13.7349206,100.4970624);
        t27 = new LatLng(13.752092 , 100.488420);
        t28 = new LatLng(13.756336 , 100.488880);
        t29 = new LatLng(13.756439 , 100.489113);
        t30 = new LatLng(13.756547 , 100.488923);
        t31 = new LatLng(13.763900 , 100.489965);
        t32 = new LatLng(13.762449 , 100.489708);
        t33 = new LatLng(13.755901 , 100.486511);
        t34 = new LatLng(13.755868 , 100.486329);
        t35 = new LatLng(13.755868 , 100.486329);
        t36 = new LatLng(13.705753 , 100.502941);
        t37 = new LatLng(13.705804 , 100.504240);
        t38 = new LatLng(13.720343 , 100.509119);
        t39 = new LatLng(13.726447 , 100.510205);
        t40 = new LatLng(13.728607 , 100.514092);
        r1 = new LatLng(13.755462,100.487234);


        P1 = mMap.addMarker(new MarkerOptions()
                .position(p1)
                .title("วัดราขสิงขร")
                .snippet("Wat-raj-cha-sing-korn"));
        P1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P2 = mMap.addMarker(new MarkerOptions()
                .position(p2)
                .title("วัดวรจรรยาวาส")//อธิบาย
                .snippet("Wat-wo-ra-chan-ya-was"));//อธิบาย
        P2.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P3 = mMap.addMarker(new MarkerOptions()
                .position(p3)
                .title("ท่าช้าง")
                .snippet("Tha-chang"));
        P3.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P4 = mMap.addMarker(new MarkerOptions()
                .position(p4)
                .title("ท่าเตียน")
                .snippet("Tha-tien"));
        P4.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P5 = mMap.addMarker(new MarkerOptions()
                .position(p5)
                .title("สะพานพุทธ")
                .snippet("Memorial-bridge"));
        P5.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P6 = mMap.addMarker(new MarkerOptions()
                .position(p6)
                .title("ราชวงศ์")
                .snippet("Rajchawongse"));
        P6.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P7 = mMap.addMarker(new MarkerOptions()
                .position(p7)
                .title("กรมเจ้าท่า")
                .snippet("Marinedept"));
        P7.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P8 = mMap.addMarker(new MarkerOptions()
                .position(p8)
                .title("สี่พระยา")
                .snippet("Si-phra-ya"));
        P8.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P9 = mMap.addMarker(new MarkerOptions()
                .position(p9)
                .title("นนทบุรี")
                .snippet("Nonthaburi"));
        P9.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P10 = mMap.addMarker(new MarkerOptions()
                .position(p10)
                .title("พระราม 5")
                .snippet("Rama 5 Bridge"));
        P10.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P11 = mMap.addMarker(new MarkerOptions()
                .position(p11)
                .title("พระราม 7")
                .snippet("Rama 7 bridge"));
        P11.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P12 = mMap.addMarker(new MarkerOptions()
                .position(p12)
                .title("บางโพ")
                .snippet("Bang-po"));
        P12.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P13 = mMap.addMarker(new MarkerOptions()
                .position(p13)
                .title("เกียกกาย")
                .snippet("Kiak-kai"));
        P13.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P14 = mMap.addMarker(new MarkerOptions()
                .position(p14)
                .title("พายัพ")
                .snippet("Pa-yap"));
        P14.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P15 = mMap.addMarker(new MarkerOptions()
                .position(p15)
                .title("สะพานกรุงธน")
                .snippet("Krungthon bridge"));
        P15.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P16 = mMap.addMarker(new MarkerOptions()
                .position(p16)
                .title("เทเวศร์")
                .snippet("Tewat"));
        P16.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P17 = mMap.addMarker(new MarkerOptions()
                .position(p17)
                .title("พระอาทิตย์")
                .snippet("Phra-a-thit"));
        P17.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P18 = mMap.addMarker(new MarkerOptions()
                .position(p18)
                .title("พระปิ่นเกล้า")
                .snippet("Phra-pin-klao"));
        P18.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P19 = mMap.addMarker(new MarkerOptions()
                .position(p19)
                .title("วังหลัง (พรานนก)")
                .snippet("Prannok"));

        P19.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P20 = mMap.addMarker(new MarkerOptions()
                .position(p20)
                .title("โอเรียนเท็ล")
                .snippet("Oriealtal"));
        P20.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        P21 = mMap.addMarker(new MarkerOptions()
                .position(p21)
                .title("สาทร (ตากสิน)")
                .snippet("Sathorn"));
        P21.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        MarkerOptions stop = new MarkerOptions();
        stop.position(new LatLng(stlat, stlon));
        stop.title("คุณอยุ่ที่นี่");
        stop.icon(BitmapDescriptorFactory.fromResource(R.mipmap.people));
        mMap.addMarker(stop);

        MarkerOptions r1 = new MarkerOptions();
        r1.position(new LatLng(lat, lon));
        r1.title(name);
        r1.icon(BitmapDescriptorFactory.fromResource(R.mipmap.boat1));
        mMap.addMarker(r1);


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(stlat, stlon), 5));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 3000, null);
    }

    @Override
    public void onInfoWindowClick (Marker marker) {
        //  T1 = mMap.addMarker(new MarkerOptions()
        // .position(t1)
        //   .title("Asia")
        //   .snippet("Sathorn"));
        // T1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boat));

        T2 = mMap.addMarker(new MarkerOptions()
                .position(t2)
                .title("โอเรียนเตล")
                .snippet("Mandarin oriental Bangkok"));
        T2.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.slp));
        T3 = mMap.addMarker(new MarkerOptions()
                .position(t3)
                .title("ห้องแสดงงานศิลปะ")
                .snippet("Ananta gallery art"));
        T3.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T4 = mMap.addMarker(new MarkerOptions()
                .position(t4)
                .title("ตลาดน้อย")
                .snippet("Talad-noi wall art"));
        T4.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T5 = mMap.addMarker(new MarkerOptions()
                .position(t5)
                .title("โป๊ะท่าเตียน")
                .snippet("Pontoon tha tian"));
        T5.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T6 = mMap.addMarker(new MarkerOptions()
                .position(t6)
                .title("หลงเตียน")
                .snippet("long tian"));
        T6.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T7 = mMap.addMarker(new MarkerOptions()
                .position(t7)
                .title("วัดแก้วฟ้าจุฬามณี")
                .snippet("Wat kaew fah chulamanee"));
        T7.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T8 = mMap.addMarker(new MarkerOptions()
                .position(t8)
                .title("สวนสาธารณะเฉลิมพระเกียรติเกียกกาย")
                .snippet("Chaloem phrakiat kiakkai park"));
        T8.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T9 = mMap.addMarker(new MarkerOptions()
                .position(t9)
                .title("Inlove bar")
                .snippet("Inlove bar"));
        T9.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T10 = mMap.addMarker(new MarkerOptions()
                .position(t10)
                .title("พระตำหนักวังขุนพรหม")
                .snippet("Bang khun phron palace"));
        T10.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T11 = mMap.addMarker(new MarkerOptions()
                .position(t11)
                .title("พระที่นั่งสันติชัยปราการ")
                .snippet("Santichai Prakan pavillion"));
        T11.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T12 = mMap.addMarker(new MarkerOptions()
                .position(t12)
                .title("ป้อมพระสุเมรุ")
                .snippet("Phra sumen fort"));
        T12.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T13 = mMap.addMarker(new MarkerOptions()
                .position(t13)
                .title("หอนาฬิกา")
                .snippet("Nonthaburi Clock Tower"));
        T13.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T14 = mMap.addMarker(new MarkerOptions()
                .position(t14)
                .title("ศาลากลางจังหวัดนนทบุรี(เก่า)")
                .snippet("Nonthaburi City Hall (Old building)"));
        T14.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T15 = mMap.addMarker(new MarkerOptions()
                .position(t15)
                .title("ขนมฝรั่งแม่ไน้ ตลาดนนทบุรี")
                .snippet("Maenight Bakery"));
        T15.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T16 = mMap.addMarker(new MarkerOptions()
                .position(t16)
                .title("ร้านนนท์ตำแหลก")
                .snippet("Nontumlake"));
        T16.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T17 = mMap.addMarker(new MarkerOptions()
                .position(t17)
                .title("ภัตตาคารแว่นฟ้า")
                .snippet("Waen fa restuarant"));
        T17.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T18 = mMap.addMarker(new MarkerOptions()
                .position(t18)
                .title("วัดเขียน")
                .snippet("Wat-kean"));
        T18.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tr));

        T19 = mMap.addMarker(new MarkerOptions()
                .position(t19)
                .title("วัดสร้อยทอง")
                .snippet("Wat soi thong  "));
        T19.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tr));

        T20 = mMap.addMarker(new MarkerOptions()
                .position(t20)
                .title("ไวท์มอล")
                .snippet("White mall"));
        T20.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T21 = mMap.addMarker(new MarkerOptions()
                .position(t21)
                .title("วัดอรุณราชวรารามราชวรมหาวิหาร")
                .snippet("Wat arun ratchawararamahawihan"));
        T21.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tr));

        T22 = mMap.addMarker(new MarkerOptions()
                .position(t22)
                .title("ป้อมวิไชยประสิทธิ์")
                .snippet("Wichai prasit fort "));
        T22.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T23 = mMap.addMarker(new MarkerOptions()
                .position(t23)
                .title("ริเวอร์ฟ้อน")
                .snippet("Water front"));
        T23.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));


        T24 = mMap.addMarker(new MarkerOptions()
                .position(t24)
                .title("ขนาบน้ำ")
                .snippet("kanab nam restaurant"));
        T24.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T25 = mMap.addMarker(new MarkerOptions()
                .position(t25)
                .title("พระบรมรูปสมเด็จพระพุทธยอดฟ้าจุฬาโลกมหาราช")
                .snippet("???"));
        T25.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T26 = mMap.addMarker(new MarkerOptions()
                .position(t26)
                .title("ตลาดสะพานพุทธ")
                .snippet("???"));
        T26.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T27 = mMap.addMarker(new MarkerOptions()
                .position(t27)
                .title("ครัวคุณกุ้ง")
                .snippet("Krua khun kung"));
        T27.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T28 = mMap.addMarker(new MarkerOptions()
                .position(t28)
                .title("ร้านอาหารท่าพระจันทร์")
                .snippet("Tha Prachan Restaurant"));
        T28.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T29 = mMap.addMarker(new MarkerOptions()
                .position(t29)
                .title("มหาชัยไอศกรีม")
                .snippet("Mahachai icecream"));
        T29.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T30 = mMap.addMarker(new MarkerOptions()
                .position(t30)
                .title("ร้านลุงหลุยส์")
                .snippet("Lung louis"));
        T30.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T31 = mMap.addMarker(new MarkerOptions()
                .position(t31)
                .title("ริมน้ำหมูกระทะ")
                .snippet("Rimnam mookra ta"));
        T31.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T32 = mMap.addMarker(new MarkerOptions()
                .position(t32)
                .title("ร้านก๋วยเตี๋ยวนายฮั่น")
                .snippet("Noodle shop nayhun"));
        T32.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T33 = mMap.addMarker(new MarkerOptions()
                .position(t33)
                .title("อรทัยซูชิ")
                .snippet("Orathai sushi"));
        T33.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T34 = mMap.addMarker(new MarkerOptions()
                .position(t34)
                .title("วังหลังเบเกอรี่")
                .snippet("Wang lang bakery"));
        T34.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

        T35 = mMap.addMarker(new MarkerOptions()
                .position(t35)
                .title("สลาม")
                .snippet("Salam restuarant"));
        T35.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T36 = mMap.addMarker(new MarkerOptions()
                .position(t36)
                .title("เอเชียทีค เดอะ ริเวอร์ฟร้อนท์")
                .snippet("Asiatique The Riverfront  "));
        T36.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T37 = mMap.addMarker(new MarkerOptions()
                .position(t37)
                .title("วัดราชสิงขร")
                .snippet("Wat ratcha singkhorn"));
        T37.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.tr));

        T38 = mMap.addMarker(new MarkerOptions()
                .position(t38)
                .title("สวนป่าเฉลิมพระเฉลิมพระเกียรติสะพานตากสิน")
                .snippet("Forest Park Chalermphrakiat Bridge Taksin"));
        T38.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T39 = mMap.addMarker(new MarkerOptions()
                .position(t39)
                .title("ไอคอนสยาม")
                .snippet("Shopping mall Iconsiam"));
        T39.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trrr));

        T40 = mMap.addMarker(new MarkerOptions()
                .position(t40)
                .title("วอล")
                .snippet("A wall stucco of the Portuguest ambassador"));
        T40.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.trr));

    }

    public void clearAllMarker(View v){
        Intent itn = new Intent(this, ChooseActivity.class);
        startActivity(itn);
    }
}
