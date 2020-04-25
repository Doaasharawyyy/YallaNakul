package com.example.yallanakul;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallanakul.Models.Category;
import com.example.yallanakul.Models.User;
import com.example.yallanakul.ViewHolder.MenuViewHolder;
import com.example.yallanakul.inteface.ItemClicklistener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
FirebaseDatabase database;
DatabaseReference menu;
TextView username;
RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
FirebaseRecyclerAdapter<Category,MenuViewHolder>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
             Intent i=new Intent(UserHome.this,Cart.class);
             startActivity(i);
            }
        });

        // init Database
            database=FirebaseDatabase.getInstance();
            menu=database.getReference("Category");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (
                        this,
                        drawer, toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                ) {
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(UserHome.this);

        View headerview=navigationView.getHeaderView(0);
        username=headerview.findViewById(R.id.username);
        Intent intent = getIntent();
                Bundle bundle=getIntent().getExtras();
        username.setText(bundle.getString("username"));

        // load menu
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        loadMenu();


    }

    private void loadMenu() {
       adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                Category.class,R.layout.menu_item,MenuViewHolder.class,menu
        ) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                        menuViewHolder.txtmenuname.setText(category.getName());
                Picasso.with(getBaseContext()).load(category.getImage()).into(menuViewHolder.imageView);
                final Category clickitem=category;
                menuViewHolder.setItemClicklistener(new ItemClicklistener() {
                    @Override
                    public void onclick(View view, int position, boolean islongclick) {
            Intent foodlist=new Intent(UserHome.this,FoodList.class);
            // get category id and pass it to foodlist
            foodlist.putExtra("categorId",adapter.getRef(position).getKey());
                    startActivity(foodlist);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            ;
        }
    }


    @SuppressWarnings("StatementWithEmtyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
