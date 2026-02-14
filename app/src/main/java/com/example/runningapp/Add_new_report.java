package com.example.runningapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Add_new_report extends AppCompatActivity {

    ImageButton back_button;

    String LostrFound;
    String Catagory,lostDate,lostLocation,foundDate,foundLocation,Nominate;

    Integer awardInt,Priceint ;
   Button Submit;
    Spinner spinner;
    private String selectedValue;
    String[] items={"Mobile","Laptop","Wrist Watch","Keys","Bag","Glasses","Charger","EarPhone"};

    private String[] etAttributeValues;
    private String[] attributesNames;

    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;

    ImageView imageView;
    ImageButton btnCameraIcon;

    String UrgentCheck="False";

    private Uri imageUri;

    private static final int IMAGE_REQUEST = 1;



    private void loadFragment(Fragment fragment) {


        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.Dynamic_attribute_container,fragment);
        ft.commit();
    }

    private void loadAttributeFragment(Fragment fragment) {


        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.replace(R.id.LostFoundAttributesContainer,fragment);
        ft.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_report);

// Set up image Uri for demo (This will be handled by your image selection process)
        //imageUri = Uri.parse("content://path/to/your/image.jpg");


        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String regNo = sharedPreferences.getString("RegNo", "");



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValue = parent.getItemAtPosition(position).toString();

                switch (selectedValue) {
                    case "Mobile":
                        loadFragment(new MobileFragment());
                        break;
                    case "Laptop":
                        loadFragment(new LaptopFragment());
                        break;
                    case "Wrist Watch":
                        loadFragment(new WatchFragment());
                        break;
                    case "Keys":
                        loadFragment(new KeysFragment());
                        break;
                    case "Bag":
                        loadFragment(new BagFragment());
                        break;
                    case "Glasses":
                        loadFragment(new GlassesFragment());
                        break;
                    case "Charger":
                        loadFragment(new ChargerFragment());
                        break;
                    case "EarPhone":
                        loadFragment(new EarPhonesFragment());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected if necessary
            }
        });


        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add_new_report.this,HomeFinal.class);
                startActivity(intent);
            }
        });
//Set up image selection (camera or gallery)
        imageView = findViewById(R.id.imageView);
        btnCameraIcon = findViewById(R.id.btn_camera_icon);
        btnCameraIcon.setOnClickListener(v -> openImagePicker());


        // Initialize EditText fields

        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        // Set OnCheckedChangeListener to handle RadioButton selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which RadioButton was selected
                if (checkedId == R.id.radioButton1) {
                    loadAttributeFragment(new LostAttributeFragment());


                } else if (checkedId == R.id.radioButton2) {
                    loadAttributeFragment(new FoundAttributesFragment());

                }
            }
        });


        RadioGroup radioGroup1 = findViewById(R.id.radioGroup1);



        // Set OnCheckedChangeListener to handle RadioButton selection
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which RadioButton was selected
                if (checkedId == R.id.urgent) {

                    UrgentCheck="True";

                }
            }
        });











        Submit = findViewById(R.id.report_submit_btn);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.Dynamic_attribute_container);

                if (currentFragment instanceof MobileFragment) {
                    MobileFragment mobileFragment = (MobileFragment) currentFragment;
                    Catagory="Mobile";
                    etAttributeValues = new String[4];
                    etAttributeValues[0] = mobileFragment.getBrand();
                    etAttributeValues[1] = mobileFragment.getModel();
                    etAttributeValues[2] = mobileFragment.getColor();
                    etAttributeValues[3] = mobileFragment.getStorage();

                    attributesNames = new String[4]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Model";
                    attributesNames[2] = "Color";
                    attributesNames[3] = "Storage";


                } else if (currentFragment instanceof LaptopFragment) {

                    Catagory="Laptop";
                    etAttributeValues = new String[4];
                    LaptopFragment laptopFragment = (LaptopFragment) currentFragment;
                    etAttributeValues[0] = laptopFragment.getBrand();
                    etAttributeValues[1] = laptopFragment.getModel();
                    etAttributeValues[2] = laptopFragment.getColor();
                    etAttributeValues[3] = laptopFragment.getStorage();

                    attributesNames = new String[4]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Model";
                    attributesNames[2] = "Color";
                    attributesNames[3] = "Storage";

                } else if (currentFragment instanceof GlassesFragment) {

                    Catagory="Glasses";
                    etAttributeValues = new String[5];
                    GlassesFragment glassesFragment = (GlassesFragment) currentFragment;
                    etAttributeValues[0] = glassesFragment.getBrand();
                    etAttributeValues[1]= glassesFragment.getMaterial();
                    etAttributeValues[2] = glassesFragment.gettype();
                    etAttributeValues[3] = glassesFragment.getLenscolor();
                    etAttributeValues[4] = glassesFragment.getFramecolor();

                    attributesNames = new String[5]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Material";
                    attributesNames[2] = "Type";
                    attributesNames[3] = "Lens Color";
                    attributesNames[4] = "Frame Color";

                      } else if (currentFragment instanceof WatchFragment) {

                    Catagory="Watch";
                    etAttributeValues = new String[5];
                    WatchFragment watchFragment = (WatchFragment) currentFragment;
                    etAttributeValues[0] = watchFragment.getBrand();
                    etAttributeValues[1] = watchFragment.getModel();
                    etAttributeValues[2] = watchFragment.getStrap();
                    etAttributeValues[3] = watchFragment.getMovement();
                    etAttributeValues[4] = watchFragment.getcolor();

                    attributesNames = new String[5]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Model";
                    attributesNames[2] = "Strap";
                    attributesNames[3] = "Movement";
                    attributesNames[4] = "Color";
                    }else if (currentFragment instanceof KeysFragment) {

                    Catagory="Keys";
                    etAttributeValues = new String[3];
                    KeysFragment keysFragment = (KeysFragment) currentFragment;
                    etAttributeValues[0] = keysFragment.getMaterial();
                    etAttributeValues[1] = keysFragment.gettype();
                    etAttributeValues[2] = keysFragment.gettidentity();

                    attributesNames = new String[3];
                    attributesNames[0]="Material";
                    attributesNames[1]="Type";
                    attributesNames[2]="Identity";


                }else if (currentFragment instanceof EarPhonesFragment) {

                    Catagory="Ear Phones";
                    etAttributeValues = new String[4];
                    EarPhonesFragment earPhonesFragment = (EarPhonesFragment) currentFragment;
                    etAttributeValues[0] = earPhonesFragment.getbrand();
                    etAttributeValues[1] = earPhonesFragment.gettype();
                    etAttributeValues[2] = earPhonesFragment.getConnectorType();
                    etAttributeValues[3] = earPhonesFragment.getcolor();

                    attributesNames = new String[4]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Type";
                    attributesNames[2] = "Connector type";
                    attributesNames[3] = "Color";


                }else if (currentFragment instanceof ChargerFragment) {

                    Catagory="Charger";
                    etAttributeValues = new String[4];
                    ChargerFragment chargerFragment = (ChargerFragment) currentFragment;
                    etAttributeValues[0] = chargerFragment.getdevicetype();
                    etAttributeValues[1] = chargerFragment.getBrand();
                    etAttributeValues[2] = chargerFragment.getConnector();
                    etAttributeValues[3] = chargerFragment.getcolor();

                    attributesNames = new String[4]; // Declare array with size 4
                    attributesNames[0] = "Device type"; // Assign values to each index
                    attributesNames[1] = "Brand";
                    attributesNames[2] = "Connector";
                    attributesNames[3] = "Color";


                }else if (currentFragment instanceof BagFragment) {

                    Catagory="Bag";
                    etAttributeValues = new String[4];
                    BagFragment bagFragment = (BagFragment) currentFragment;
                    etAttributeValues[0] = bagFragment.getbrand();
                    etAttributeValues[1] = bagFragment.getStuff();
                    etAttributeValues[2] = bagFragment.getcolor();
                    etAttributeValues[3] = bagFragment.gettype();

                    attributesNames = new String[4]; // Declare array with size 4
                    attributesNames[0] = "Brand"; // Assign values to each index
                    attributesNames[1] = "Stuff";
                    attributesNames[2] = "Color";
                    attributesNames[3] = "Type";

                }


                Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.LostFoundAttributesContainer);

                if (curFragment instanceof LostAttributeFragment) {
                    LostAttributeFragment lAttributeFragment = (LostAttributeFragment) curFragment;

                     lostDate = lAttributeFragment.getLost_Date();
                     lostLocation= lAttributeFragment.getLost_location();
                     String award = lAttributeFragment.getaward();
                     awardInt = award.isEmpty() ? null : Integer.valueOf(award);
                    Nominate = lAttributeFragment.getNominate();
                    Toast.makeText(Add_new_report.this, "Image encoding is successful", Toast.LENGTH_SHORT).show();

                    LostrFound="lost";


                } else if (curFragment instanceof FoundAttributesFragment) {
                    FoundAttributesFragment fAttributesFragment = (FoundAttributesFragment) curFragment;

                    foundDate = fAttributesFragment.getFound_Date();
                    foundLocation= fAttributesFragment.getFound_location();
                    String price = fAttributesFragment.getEstimated_price();
                    Priceint =  Integer.valueOf(price);

                    LostrFound="found";

                }



                if (etAttributeValues != null) {
                    for (int i = 0; i < etAttributeValues.length; i++) {
                        if (TextUtils.isEmpty(etAttributeValues[i])) {
                            Toast.makeText(Add_new_report.this,
                                    "Please fill all attributes for the selected category.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(Add_new_report.this,
                            "Please select a category and fill its details.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }






                    List<JSONObject> attributeList = new ArrayList<>();
                for (int i = 0; i < attributesNames.length; i++) {
                    if (!TextUtils.isEmpty(etAttributeValues[i])) {
                        try {
                            JSONObject attribute = new JSONObject();
                            attribute.put("AttributeName", attributesNames[i]);
                            attribute.put("AttributeValue", etAttributeValues[i]);
                            attributeList.add(attribute);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Convert the image Uri to Base64 string
                String encodedImage = encodeImageToBase64(imageUri); // imageUri is the Uri of the selected image

                // If the image encoding is successful

                if (encodedImage != null) {
                    Toast.makeText(Add_new_report.this, "Image encoding is successful", Toast.LENGTH_SHORT).show();
                    if (LostrFound == "lost") {

                        JSONObject lostItemData = new JSONObject();
                        try {
                            lostItemData.put("Image",  encodedImage);
                            lostItemData.put("CategoryName", Catagory);
                            lostItemData.put("LostDate", lostDate);
                            lostItemData.put("LostLocation", lostLocation);
                            lostItemData.put("Lost_by", regNo);
                            lostItemData.put("Award", awardInt);
                            lostItemData.put("Urgent", UrgentCheck);
                            lostItemData.put("Nominate", Nominate);

                            // Add attributes list
                            lostItemData.put("Attributes", new JSONArray(attributeList));

                            Catagory = null;
                            lostDate = null;
                            lostLocation = null;
                            awardInt = 0;


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Add_new_report.this, "Error creating data object.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Send data to the API
                        sendLostItemData(lostItemData);

//                        Intent intent = new Intent(Add_new_report.this, HomeFinal.class);
//                        startActivity(intent);


                    } else if (LostrFound == "found") {

                        JSONObject FoundItemData = new JSONObject();
                        try {
                            FoundItemData.put("Image",  encodedImage);
                            FoundItemData.put("CategoryName", Catagory);
                            FoundItemData.put("FoundDate", foundDate);
                            FoundItemData.put("FoundLocation", foundLocation);
                            FoundItemData.put("Foundby", regNo);
                            FoundItemData.put("Estimatedprice", Priceint);

                            // Add attributes list
                            FoundItemData.put("Attributes", new JSONArray(attributeList));

                            Catagory = null;
                            foundDate = null;
                            foundLocation = null;
                            Priceint = 0;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Add_new_report.this, "Error creating data object.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Send data to the API
                        sendFoundItemData(FoundItemData);
//
//                        Intent intent = new Intent(Add_new_report.this, HomeFinal.class);
//                        startActivity(intent);


                    }

                }else {
                    Toast.makeText(Add_new_report.this, "Image encoding problem", Toast.LENGTH_SHORT).show();
                }
            }
        });




                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
    });
}
    private void sendLostItemData(JSONObject lostItemData) {
        String url = "http://10.0.2.2/Projectfinal/api/LostItem/AddLostItem"; // Replace with your API endpoint



        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                lostItemData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Add_new_report.this, "Lost item submitted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Add_new_report.this, HomeFinal.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_new_report.this, "Failed to submit lost item. Please try again.", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }


    private void sendFoundItemData(JSONObject FoundItemData) {
        String url = "http://10.0.2.2/Projectfinal/api/FoundItem/AddFoundItem"; // Replace with your API endpoint

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                FoundItemData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Add_new_report.this, "Found item submitted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Add_new_report.this, HomeFinal.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Add_new_report.this, "Failed to submit Found item. Please try again.", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();  // Get URI of the selected image
            // Optionally, you can display the image on the ImageView
            Bitmap bitmap = getResizedBitmap(imageUri);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }


    // Method to encode image URI to Base64 string
    private String encodeImageToBase64(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // Compress the image to JPEG
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT); // Convert byte array to Base64 string
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getResizedBitmap(Uri imageUri) {
        try {
            Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            int maxWidth = 1024;
            int maxHeight = 1024;

            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            float ratioBitmap = (float) width / height;
            float ratioMax = (float) maxWidth / maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;

            if (ratioMax > ratioBitmap) {
                finalWidth = (int) (maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) (maxWidth / ratioBitmap);
            }

            return Bitmap.createScaledBitmap(originalBitmap, finalWidth, finalHeight, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    }







