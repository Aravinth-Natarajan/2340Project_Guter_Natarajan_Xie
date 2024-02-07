package com.example.a2340project;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a2340project.databinding.ActivityMainBinding;
import com.example.a2340project.databinding.ClassesFragmentBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FragmentManager fragManager;
    private ActionBarDrawerToggle drawerToggle;
    private ArrayList<Course> courses;
    private ToDoList toDoList;
    private TaskbarMenuState menuState = TaskbarMenuState.HIDE_MENU;
    private CourseViewModel selectedCourse;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = mPrefs.getString("MyObject", "");
//        Log.d("TestTag of Json", json);
//        courses = gson.fromJson(json, courses.getClass());
//        Log.e("Course list info", courses.getClass().getSimpleName());
//        Log.e("Course list info", courses.get(0).getClass().getSimpleName());
        String connectionsJSONString = getPreferences(MODE_PRIVATE).getString("MyObject", null);
        Type type = new TypeToken<ArrayList<Course>>() {}.getType();
        ArrayList<Course> loadedCourses = new Gson().fromJson(connectionsJSONString, type);
        courses = loadedCourses == null ? new ArrayList<>() : loadedCourses;
        printCourses();
        setAlarm();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createNotificationChannel();
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
        setTitle(R.string.classes_fragment_label);


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

        selectedCourse = new ViewModelProvider(this).get(CourseViewModel.class);

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
    private void setAlarm() {
        long duration = SystemClock.elapsedRealtime();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //get Time in ms
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, duration, pendingIntent);
        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Wazzup Beijing";
            String description = "Today on the news";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("venkyandroid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Set up event handlers
        binding.includeNavDrawer.classesNavButton.setOnClickListener((view) -> {
            swapToFragment(new ClassesFragment(courses));
            setTitle(R.string.classes_fragment_label);
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
            case CLASS_LIST:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                drawerToggle.setDrawerIndicatorEnabled(true);
                visible = new MenuItem[]{menu.findItem(R.id.action_add_class)};
                break;
            case TASK_LIST:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                drawerToggle.setDrawerIndicatorEnabled(true);
                visible = new MenuItem[]{menu.findItem(R.id.action_add_task)};
                break;
            case VIEW_CLASS:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerToggle.setDrawerIndicatorEnabled(false);
                visible = new MenuItem[]{
                        menu.findItem(R.id.action_edit_class),
                        menu.findItem(R.id.action_back_to_classes_from_details),
                        menu.findItem(R.id.action_delete_class)};
                break;
            case EDIT_CLASS:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerToggle.setDrawerIndicatorEnabled(false);
                visible = new MenuItem[]{
                        menu.findItem(R.id.action_confirm_class),
                        menu.findItem(R.id.action_back_to_classes_from_editor)};
                break;
            case VIEW_TASK:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerToggle.setDrawerIndicatorEnabled(false);
                break;
            case EDIT_TASK:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerToggle.setDrawerIndicatorEnabled(false);
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

        if (id == R.id.action_back_to_classes_from_details) {
            selectedCourse.setCourse(null);
            swapToFragment(new ClassesFragment(courses));
            setTitle(R.string.classes_fragment_label);
        } else if (id == R.id.action_back_to_classes_from_editor) {
            makeClassDiscardDialog();
        } else if (id == R.id.action_edit_class) {
            swapToFragment(new ClassEditorFragment(selectedCourse.getCourse().getValue(), false));
            setTitle(R.string.course_editing_fragment_label);
        } else if (id == R.id.action_confirm_class) {
            makeClassConfirmationDialog();
        } else if (id == R.id.action_add_class) {
            Course newCourse = new Course("Unknown");
            selectedCourse.setCourse(newCourse);
            selectedCourse.setIsNew(true);
            swapToFragment(new ClassEditorFragment(newCourse, true));
        } else if (id == R.id.action_delete_class) {
            makeClassDeletionDialog();
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
    public static void updateMenu(FragmentManager manager, TaskbarMenuState newState) {
        Bundle menuUpdate = new Bundle();
        menuUpdate.putString("menuStateKey", newState.name());
        manager.setFragmentResult("menuUpdateKey", menuUpdate);
    }

    private void makeClassConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm changes?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    if (selectedCourse.isNew().getValue()) {
                        courses.add(selectedCourse.getCourse().getValue());
                    }
                    selectedCourse.setCourse(null);
                    selectedCourse.setIsNew(false);
                    swapToFragment(new ClassesFragment(courses));
                    setTitle(R.string.classes_fragment_label);
                });
        builder.setNegativeButton("No", (dialog, id) -> {});
        builder.create().show();
    }

    private void makeClassDeletionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait!");
        builder.setMessage("Are you sure you want to delete this class?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    Course course = selectedCourse.getCourse().getValue();
                    courses.remove(course);
                    selectedCourse.setCourse(null);
                    selectedCourse.setIsNew(false);
                    swapToFragment(new ClassesFragment(courses));
                    setTitle(R.string.classes_fragment_label);
                });
        builder.setNegativeButton("No", (dialog, id) -> {});
        builder.create().show();
    }

    private void makeClassDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait!");
        builder.setMessage("Are you sure you want to discard this class?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    selectedCourse.setCourse(null);
                    selectedCourse.setIsNew(false);
                    swapToFragment(new ClassesFragment(courses));
                    setTitle(R.string.classes_fragment_label);
                });
        builder.setNegativeButton("No", (dialog, id) -> {});
        builder.create().show();
    }

    private void printCourses() {
        for (Course course : courses) {
            Log.e("courses List:", course.toString());
        }
    }

    public void onDestroy() {
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(courses);
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();
        Log.d("Check Save Tage", json);
        super.onDestroy();
    }
}