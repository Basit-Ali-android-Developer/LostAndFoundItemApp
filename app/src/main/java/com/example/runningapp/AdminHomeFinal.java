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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;


public class AdminHomeFinal extends AppCompatActivity {

    DrawerLayout main;
    ImageButton Drawer_button;
    NavigationView navigationView;
    TextView bartitle;

    ImageView bell;
   TextView Drawer_name,drawer_regno;

    private TextView bellBadge;
    String userdetail,username;





    //code of method load that load fragmant in container
    private void loadFragment(Fragment fragment) {


        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.Admincontentpart,fragment);
        ft.commit();
    }

    private void loadSpecificFragment(String fragmentName) {
        Fragment fragment = null;

        switch (fragmentName) {
            case "AdminDeliveredFragment":
                fragment = new AdminDeliveredFragment();
                break;
            case "AdminFoundFragment":
                fragment = new AdminFoundFragment();
                break;

            case "AdminAuctionFragment":
                fragment = new AdminAuctionFragment();
                break;
            case "AdminAuctionHistoryFragment":
                fragment = new AdminAuctionHistoryFragment();
                break;
            // Add other cases for different fragments
            case "AdminLostFragment":
                fragment = new AdminLostFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.Admincontentpart, fragment);
            transaction.commit();
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home_final);






        main=findViewById(R.id.Adminmain);
        bartitle = findViewById(R.id.bartitle);



        bellBadge = findViewById(R.id.Admin_notification_badge);

        bell = findViewById(R.id.bell_icon);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeFinal.this, Notification.class);
                startActivity(intent);

            }
        });

        // Fetch the unread count

        fetchUnreadNotificationCount();





//        button code to open drawer--------------------

        Drawer_button=findViewById(R.id.Drawer_button);
        Drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.open();
            }
        });

        String fragmentToLoad = getIntent().getStringExtra("FragmentToLoad");

        // If the fragment name exists, load it
        if (fragmentToLoad != null) {
            loadSpecificFragment(fragmentToLoad);
        }



//bydefault fragment report fragment is loaded----------------

        loadFragment(new AdminLostFragment());
        bartitle.setText(R.string.menu_Reports);


        navigationView=findViewById(R.id.AdminnavigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {







                // other fragment are loaded by press so----------------------

                int itemid = item.getItemId();



                if(itemid == R.id.AdminReports){

                    loadFragment(new AdminLostFragment());
                    bartitle.setText(R.string.menu_Reports);

                } else if (itemid == R.id.AdminFound) {

                    loadFragment(new AdminFoundFragment());
                    bartitle.setText(R.string.menu_Found);


                }else if (itemid == R.id.AdminDelivered) {

                    loadFragment(new AdminDeliveredFragment());
                    bartitle.setText(R.string.menu_Delivered);

                }else if (itemid == R.id.AdminAuction) {

                    loadFragment(new AdminAuctionFragment());
                    bartitle.setText(R.string.menu_Auction);

                }
                else if (itemid == R.id.AdminProfile) {

                    loadFragment(new AdminProfileFragment());
                    bartitle.setText(R.string.menu_profile);

                }else if (itemid == R.id.AdminAuctionHistroy) {

                    loadFragment(new AdminAuctionHistoryFragment());
                    bartitle.setText(R.string.menu_AuctionHistory);

                }
                else if (itemid == R.id.Adminsignout) {


                    Toast.makeText(AdminHomeFinal.this, "signout", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(AdminHomeFinal.this,Login.class);
                    startActivity(intent);



                }

                //when we select item from drawer then drawer automaticaly closed is because of this---------------------------


                main.close();

                return true;
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




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Adminmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    private void fetchUnreadNotificationCount() {
        String url = "http://10.0.2.2/Projectfinal/api/AdminNotificationHandler/GetUnreadCount"; // Replace with your API endpoint

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JSON Object request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the unread count from the response
                            int unreadCount = response.getInt("unreadCount");

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