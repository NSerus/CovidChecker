package com.boom.covidchecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private Fragment fragmentActual = null;
    private int menuActual = R.menu.menu_checklist;
    private Menu menu;

    public void setFragmentActual(Fragment fragmentActual) {
        this.fragmentActual = fragmentActual;
    }

    public void setMenuActual(int menuActual) {
        if (menuActual != this.menuActual) {
            this.menuActual = menuActual;
            invalidateOptionsMenu();
        }
    }

    public void AtualizaOpcoesMenuChecklist() {
        ChecklistFragment checklistFragment = (ChecklistFragment) fragmentActual;

        Item item = checklistFragment.getItemSelecionado();

        boolean mostraEditarEliminar = (item != null);

        menu.findItem(R.id.action_alterar_item).setVisible(mostraEditarEliminar);
        menu.findItem(R.id.action_eliminar_item).setVisible(mostraEditarEliminar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChecklistFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_checklist);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_checklist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChecklistFragment()).commit();
                break;
            case R.id.nav_covid:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CovidFragment()).commit();
                break;
            case R.id.nav_solidao:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SolidaoFragment()).commit();
                break;
            case R.id.nav_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OptionsFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuActual, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (menuActual == R.menu.menu_checklist) {
            if (processaOpcoesMenuListaItems(id)) return true;
        } else if (menuActual == R.menu.menu_add_item) {
            if (processaOpcoesMenuInserirItem(id)) return true;
        }else if (menuActual == R.menu.menu_edit_only) {
            if (processaOpcoesMenuEditarItem(id)) return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean processaOpcoesMenuEditarItem(int id) {

        if (id == R.id.action_alterar_item) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItemUpdateFragment()).commit();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuInserirItem(int id) {
        ItemAddFragment adicionarItemFragment = (ItemAddFragment) fragmentActual;

        if (id == R.id.action_guardar) {
            adicionarItemFragment.guardar();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChecklistFragment()).commit();
            return true;
        }

        return false;
    }

    private boolean processaOpcoesMenuListaItems(int id) {
        ChecklistFragment listaItemsFragment = (ChecklistFragment) fragmentActual;

        if (id == R.id.action_inserir_item) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ItemAddFragment()).commit();

            return true;
        } else if (id == R.id.nav_alterar_item) {
            listaItemsFragment.alteraItem();
            return true;
        } else if (id == R.id.nav_eliminar_item) {
            return true;
        }

        return false;
    }
}
