package com.example.a2340project;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.Menu;
import android.view.MenuItem;

import com.example.a2340project.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FragmentManager fragManager;
    private ActionBarDrawerToggle drawerToggle;
    private ClassesFragment activeClassesFragment;
    private TodoFragment activeTodoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set up action bar
        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainToolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();

        // Set fragment manager and display home fragment
        fragManager = getSupportFragmentManager();
        activeClassesFragment = new ClassesFragment();
        fragManager.beginTransaction().replace(R.id.content_frame, activeClassesFragment).commit();


//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
//                .setOpenableLayout(binding.drawerLayout)
//                .build();
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.mainToolbar, navController, appBarConfiguration);
//
//        // set up navigation drawer
//        NavigationUI.setupWithNavController(binding.mainNavView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set up event handlers
        binding.includeNavDrawer.classesNavButton.setOnClickListener((view) -> {
            fragManager.beginTransaction()
                    .replace(R.id.content_frame,
                            activeClassesFragment == null ? new ClassesFragment() : activeClassesFragment)
                    .commit();
            binding.drawerLayout.closeDrawers();
        });

        binding.includeNavDrawer.todoListNavButton.setOnClickListener((view) -> {
            fragManager.beginTransaction()
                    .replace(R.id.content_frame,
                            activeTodoFragment == null ? new TodoFragment() : activeTodoFragment)
                    .commit();
            binding.drawerLayout.closeDrawers();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController,Ac appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}