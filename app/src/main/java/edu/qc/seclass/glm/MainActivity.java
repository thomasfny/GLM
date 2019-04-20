package edu.qc.seclass.glm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import edu.qc.seclass.glm.database.DBSource;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ArrayAdapter<String> mAdapter;
    ListView userListView;

    DBSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataSource = new DBSource(this);
        mDataSource.open();

        Toast.makeText(this,"DB happened successfully" , Toast.LENGTH_SHORT).show();

        /**
         * make sure to use the Toolbar with android support for v7
         * we are adding the support since we got rid of the action bar
         * and we want to use our toolbar as the action bar
         * use activity_main.xml for reference
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        /**
         * The navigationView must be implemented for us to be
         * able to set "this" context to be the navigation listener
         * A listener represents the interfaces responsible to handle events.
         *
         * our other option was to pass a new Navigation item
         * but then we have to write a lot more onCreate methods
         */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //the drawer and toggle are the two views that need to be synchronized
        //the following toggle is a drawerListener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //this takes care of the hamburger icon to get to the drawer itself
        toggle.syncState();

        /**
         * we don't want to start the app with an empty activity, so we show the list manager
         * then we can't forget to give the nav_item(ListManager) a "selected" state
         *
         * Now we run into an issue:
         * Remember that your app restarts every time you rotate a screen
         * or leave the app momentarily. This means whatever fragment the user is at will
         * be replaced by the ListManager, which is a poor design
         *
         * But that is fixed by checking if the saved instance was null when we get passed onCreate
         *
         * Now it will only show the ListManager when we first start it or when we leave the app completely
         */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ListManagerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_listManager_item);
        }
    }







    /**
     * We make use of this method to handle the fragments
     * we use the item ID to know which fragment we are calling
     * <p>
     * Then we replace the fragment_container (refer to activity_main)
     * with the corresponding inflater
     * The inflater is returned by the fragment's class
     * which is why we commit a new instance of the class
     *
     * @param menuItem Selected item from the navigation menu
     * @return "true" value for the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_listManager_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListManagerFragment()).commit();
                userListView = (ListView)findViewById(R.id.user_list_view);
                loadUserList();


                break;//when this case is handled we will leave the switch segment

            case R.id.nav_category_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CategoryFragment()).commit();
                break;

            case R.id.nav_search_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;

            case R.id.nav_about_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                break;
        }

        //added next two lines to test if drawer will close after click
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void loadUserList() {
      //  ArrayList<String> userlist = db.Helper
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();

        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }
}
