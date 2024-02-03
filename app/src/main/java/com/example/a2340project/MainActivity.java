package com.example.a2340project;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a2340project.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FragmentManager fragManager;
    private ActionBarDrawerToggle drawerToggle;
    private ClassesFragment activeClassesFragment;
    private TodoFragment activeTodoFragment;
    private ArrayList<Course> courses;
    private ToDoList toDoList;
    private TaskbarMenuState menuState = TaskbarMenuState.HIDE_MENU;

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
        swapToFragment(new ClassesFragment(courses));
        setTitle(R.string.first_fragment_label);

        courses = new ArrayList<>();

        fragManager.setFragmentResultListener(
                "menuUpdateKey", this,
                (key, bundle) -> {
                    String res = bundle.getString("menuStateKey");
                    try {
                        TaskbarMenuState newState = Enum.valueOf(TaskbarMenuState.class, res);
                        setMenuState(newState);
                    } catch (Exception e) {
                        Log.e("Menu Update Error: ", e.getStackTrace().toString());
                    }
                });

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
            swapToFragment(new ClassesFragment(courses));
            setTitle(R.string.first_fragment_label);
            binding.drawerLayout.closeDrawers();
        });

        binding.includeNavDrawer.todoListNavButton.setOnClickListener((view) -> {
            swapToFragment(new TodoFragment(toDoList));
            setTitle(R.string.second_fragment_label);
            binding.drawerLayout.closeDrawers();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem[] visible = new MenuItem[0];
        getMenuInflater().inflate(R.menu.menu_main, menu);
//
        switch(menuState) {
            case VIEW_CLASS:
                visible = new MenuItem[]{menu.findItem(R.id.action_edit_class), menu.findItem(R.id.action_back_to_classes)};
                break;
            case EDIT_CLASS:
                break;
            case VIEW_TASK:
                break;
            case EDIT_TASK:
                break;
            case HIDE_MENU:
            default:
                visible = new MenuItem[0];
        }
        for (int i = 0; i < menu.size(); ++i) {
            menu.getItem(i).setVisible(false);
        }
        for (MenuItem item : visible) {
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_back_to_classes) {
            swapToFragment(new ClassesFragment(courses));
            setTitle(R.string.first_fragment_label);
        } else if (id == R.id.action_edit_class) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void swapToFragment(Fragment fragment) {
        fragManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    public void setMenuState(TaskbarMenuState state) {
        menuState = state;
        invalidateOptionsMenu();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController,Ac appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}