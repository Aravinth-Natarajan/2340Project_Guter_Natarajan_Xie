package com.example.a2340project;

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

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a2340project.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String USER_DATA_KEY = "USER_DATA_KEY";
    private static final String COURSES_KEY_SUFFIX = "_COURSES";
    private static final String TASKS_KEY_SUFFIX = "_TASKS";
    private static final String PREFS_KEY_SUFFIX = "_PREFS";
    public static final String NOTIF_CHANNEL_ID = "scheduler_notif_channel";

    private ActivityMainBinding binding;
    private FragmentManager fragManager;
    private ActionBarDrawerToggle drawerToggle;
    private List<Course> courses;
    private ToDoList toDoList;
    private TaskbarMenuState menuState = TaskbarMenuState.HIDE_MENU;
    private CourseViewModel selectedCourse;
    private TaskViewModel selectedTask;
    private SharedPreferences userData;
    private Gson gson;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private String username;
    private String timeToAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(LoginActivity.USERNAME_KEY);
        }

        // Inflate base layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadUserData();
        Log.d("Logged in", "Active user: " + username);

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

        createNotificationChannel();

        fragManager.setFragmentResultListener(
                "timeToAlarmUpdateKey", this,
                (key, bundle) -> {
                    String res = bundle.getString("timeToAlarmStateKey");
                    try {
                        timeToAlarm = res;
                        Log.d("Time Prelay", timeToAlarm);
                    } catch (Exception e) {
                        Log.e("Menu Update Error: ", e.getStackTrace().toString());
                    }
                });

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
        selectedTask = new ViewModelProvider(this).get(TaskViewModel.class);
    }

    private void setAlarm() {
        long duration = SystemClock.elapsedRealtime();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //get Time in ms
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, duration, pendingIntent);
        Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notifications for tasks";
            String description = "Per task according to global setting";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void setNotification(Calendar dueDate, String title) {
        int timeInMs;
        if (timeToAlarm.equals("10 Minutes")) {
            timeInMs = 60 * 10 * 1000;
        } else if (timeToAlarm.equals("30 Minutes")) {
            timeInMs = 60 * 30 * 1000;
        } else if (timeToAlarm.equals("1 Hour")) {
            timeInMs = 60 * 60 * 1000;
        } else if (timeToAlarm.equals("2 Hours")) {
            timeInMs = 60 * 60 * 2 * 1000;
        } else if (timeToAlarm.equals("1 Day")) {
            timeInMs = 60 * 60 * 1000 * 24;
        } else {
            timeInMs = 2 * 60 * 60 * 1000 * 24;
        }
        long timeForNotify = dueDate.getTimeInMillis() - timeInMs;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("TaskTitle", title);
        intent.putExtra("TimeRemaining", timeToAlarm);
        intent.putExtra("sourceUser", username);
        pendingIntent = PendingIntent.getBroadcast(
                this,
                (int) (Math.random() * Integer.MAX_VALUE),
//                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE);
        //get Time in ms
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeForNotify, pendingIntent);
        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show();
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
            setTitle(R.string.todo_fragment_label);
            binding.drawerLayout.closeDrawers();
        });
        binding.includeNavDrawer.swUserButton.setOnClickListener((view) -> {
            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivity(newIntent);
        });
        binding.includeNavDrawer.settingsButton.setOnClickListener((view) -> {
            swapToFragment(new SettingsFragment(timeToAlarm));
            setTitle(R.string.settings_fragment_label);
            binding.drawerLayout.closeDrawers();
        });

    }
    @Override
    protected void onStop() {
        super.onStop();

        saveUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        saveUserData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuItem[] visible;
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
                visible = new MenuItem[]{
                        menu.findItem(R.id.action_edit_task),
                        menu.findItem(R.id.action_delete_task),
                        menu.findItem(R.id.action_back_to_tasks_from_details)};
                break;
            case EDIT_TASK:
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                drawerToggle.setDrawerIndicatorEnabled(false);
                visible = new MenuItem[]{
                        menu.findItem(R.id.action_confirm_task),
                        menu.findItem(R.id.action_back_to_tasks_from_editor)};
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
            setTitle(R.string.course_editing_fragment_label);
        } else if (id == R.id.action_delete_class) {
            makeClassDeletionDialog();
        }

        else if (id == R.id.action_back_to_tasks_from_details) {
            selectedTask.setTask(null);
            swapToFragment(new TodoFragment(toDoList));
            setTitle(R.string.todo_fragment_label);
        } else if (id == R.id.action_back_to_tasks_from_editor) {
            makeTaskDiscardDialog();
        } else if (id == R.id.action_edit_task) {
            swapToFragment(new ToDoEditorFragment(selectedTask.getTask().getValue(), false, courses));
            setTitle(R.string.task_editor_fragment_label);
        } else if (id == R.id.action_confirm_task) {
            makeTaskConfirmationDialog();
        } else if (id == R.id.action_add_task) {
            Task newTask = new Task("Unknown", Calendar.getInstance(), "Unknown");
            selectedTask.setTask(newTask);
            selectedTask.setIsNew(true);
            swapToFragment(new ToDoEditorFragment(selectedTask.getTask().getValue(), true, courses));
            setTitle(R.string.task_editor_fragment_label);
        } else if (id == R.id.action_delete_task) {
            makeTaskDeletionDialog();
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

    private void makeTaskConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm changes?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    setNotification(selectedTask.getTask().getValue().getDueDate(), selectedTask.getTask().getValue().getTitle());
                    if (selectedTask.isNew().getValue()) {
                        toDoList.addTask(selectedTask.getTask().getValue());
                    }
                    selectedTask.setTask(null);
                    selectedTask.setIsNew(false);
                    swapToFragment(new TodoFragment(toDoList));
                    setTitle(R.string.todo_fragment_label);
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

    private void makeTaskDeletionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait!");
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    Task task = selectedTask.getTask().getValue();
                    toDoList.removeTask(task);
                    selectedTask.setTask(null);
                    selectedTask.setIsNew(false);
                    swapToFragment(new TodoFragment(toDoList));
                    setTitle(R.string.todo_fragment_label);
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

    private void makeTaskDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wait!");
        builder.setMessage("Are you sure you want to discard this task?");
        builder.setPositiveButton("Yes",
                (dialog, id) -> {
                    selectedTask.setTask(null);
                    selectedTask.setIsNew(false);
                    swapToFragment(new TodoFragment(toDoList));
                    setTitle(R.string.todo_fragment_label);
                });
        builder.setNegativeButton("No", (dialog, id) -> {});
        builder.create().show();
    }

    private void loadUserData() {
        username = username == null ? getIntent().getStringExtra(LoginActivity.USERNAME_KEY) : username;
        userData = getSharedPreferences(USER_DATA_KEY, MODE_PRIVATE);

        String userCoursesJSONString = userData.getString(
                username + COURSES_KEY_SUFFIX, null);
        String userTasksJSONString = userData.getString(
                username + TASKS_KEY_SUFFIX, null);
        String userPrefsString = userData.getString(
                username + PREFS_KEY_SUFFIX, null);

        gson = new Gson();

        Type coursesType = new TypeToken<ArrayList<Course>>(){}.getType();
        ArrayList<Course> loadedCourses = gson.fromJson(userCoursesJSONString, coursesType);
        courses = loadedCourses == null ? new ArrayList<>() : loadedCourses;

        Type tasksType = new TypeToken<ArrayList<Task>>(){}.getType();
        ArrayList<Task> loadedTasks = gson.fromJson(userTasksJSONString, tasksType);
        toDoList = new ToDoList(loadedTasks == null ? new ArrayList<>() : loadedTasks);

        timeToAlarm = userPrefsString == null ? "10 minutes" : userPrefsString;
    }

    private void saveUserData() {
        SharedPreferences.Editor dataEditor = userData.edit();
        String coursesJSON = gson.toJson(courses);
        String tasksJSON = gson.toJson(toDoList.returnList());
        String prefsString = timeToAlarm;
        dataEditor.putString(username + COURSES_KEY_SUFFIX, coursesJSON);
        dataEditor.putString(username + TASKS_KEY_SUFFIX, tasksJSON);
        dataEditor.putString(username + PREFS_KEY_SUFFIX, prefsString);
        dataEditor.apply();
    }
    private void printCourses() {
        // this is for debug
        for (Course course : courses) {
            Log.e("courses List:", course.toString());
        }
    }
}