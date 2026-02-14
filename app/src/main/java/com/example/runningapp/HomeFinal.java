package com.example.runningapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;


public class HomeFinal extends AppCompatActivity {

    DrawerLayout main;
    ImageButton Drawer_button;
    NavigationView navigationView;
    TextView bartitle;
    ImageView bell;
    TextView Drawer_name,drawer_regno;
    private TextView bellBadge;
    FloatingActionButton Add_button_movtoform;

    String regNo,username,userdetail;


//code of method load that load fragmant in container
    private void loadFragment(Fragment fragment) {


        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.contentpart,fragment);
        ft.commit();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_final);


        main=findViewById(R.id.main);
        bartitle = findViewById(R.id.bartitle);

//        button code to open drawer--------------------

        Drawer_button=findViewById(R.id.Drawer_button);
        Drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.open();
            }
        });



//bydefault fragment report fragment is loaded----------------

        loadFragment(new ReportsFragment());
        bartitle.setText(R.string.menu_Reports);

        navigationView=findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                int itemid = item.getItemId();

                if(itemid == R.id.Reports){

                    loadFragment(new ReportsFragment());
                    bartitle.setText(R.string.menu_Reports);

                } else if (itemid == R.id.Found) {

                    loadFragment(new FoundFragment());
                    bartitle.setText(R.string.menu_Found);


                }else if (itemid == R.id.Delivered) {

                    loadFragment(new HistoryFragment());
                    bartitle.setText(R.string.menu_History);

                }else if (itemid == R.id.Auction) {

                    loadFragment(new AuctionFragment());
                    bartitle.setText(R.string.menu_Auction);

                }else if (itemid == R.id.AuctionHistory) {

                    loadFragment(new StudentAuctionHistoryFragment());
                    bartitle.setText(R.string.menu_AuctionHistory);

                }
                else if (itemid == R.id.Profile) {

                    loadFragment(new ProfileFragment());
                    bartitle.setText(R.string.menu_profile);

                }else if (itemid == R.id.signout) {


                    Toast.makeText(HomeFinal.this, "signout", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(HomeFinal.this,Login.class);
                    startActivity(intent);

                }

                main.close();

                return true;
            }


       });

//code of add button to move to new item form------------------------------------

        Add_button_movtoform = findViewById(R.id.Add_button_movtoform);
        Add_button_movtoform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeFinal.this,Add_new_report.class);
                startActivity(intent);
            }
        });

        View headerView = navigationView.getHeaderView(0);
        Drawer_name = headerView.findViewById(R.id.Drawer_name);
        drawer_regno = headerView.findViewById(R.id.drawer_regno);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        userdetail = sharedPreferences.getString("DrawerDetail", "");
        username=sharedPreferences.getString("Drawername", "");

        Drawer_name.setText(username);
        drawer_regno.setText(userdetail);



        bellBadge = findViewById(R.id.Student_notification_badge);

        bell = findViewById(R.id.Studentbell_icon);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFinal.this, StudentNotification.class);
                startActivity(intent);

            }
        });

        fetchUnreadNotificationCount();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void fetchUnreadNotificationCount() {
        String url = "http://10.0.2.2/Projectfinal/api/StudentNotificationHandler/GetNotificationCount?regNo="+userdetail; // Replace with your API endpoint

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JSON Object request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the unread count from the response
                            int unreadCount = response.getInt("NotificationCount");

                            // Update the badge on the bell icon
                            updateBellBadge(unreadCount);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Add the request to the queue
        queue.add(request);
    }

    private void updateBellBadge(int count) {
        if (count > 0) {
            bellBadge.setVisibility(View.VISIBLE);
            bellBadge.setText(String.valueOf(count));
        } else {
            bellBadge.setVisibility(View.GONE); // Hide the badge if no unread notifications
        }
    }


}
