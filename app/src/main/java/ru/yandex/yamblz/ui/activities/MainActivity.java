package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.BaseFragment;
import ru.yandex.yamblz.ui.fragments.DashboardFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new DashboardFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);

//        menu.addSubMenu(0, 0, 0, "sadfasdf");

        menu.addSubMenu(0, Menu.NONE, 1, "settings");
        menu.addSubMenu(0, Menu.NONE, 2, "statistic");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_setting:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // В целом, идея для навигации правильная - делегирование некоторому менджеру, но по принципу dependency inversion
    // эти методы стоит инкапсулировать с помощью интерфейса. Иначе клиент будет иметь доступ ко всем пабликам
    // активити. Это излешне открытое API. Изляшняя открытость API ведет к усложнению кода (труднее отслеживать зависимости,
    // больше кейсов придется поддерживать и т.д.). Кроме того поэтому в своем коде по дефолту я бы старался
    // по максимуму скрывать API, а также final'изировать и делать иммутабельным все, что можно (если не страдает перфоманс,
    // тут уже придется думать в каждом конкретном случае).
    public void applyFragment(BaseFragment baseFragment) {
        // Гм, это лишнее, кажется. Какая польза от такой проверки?
        if (findViewById(R.id.main_frame_layout) == null) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_layout, baseFragment)
                .addToBackStack(null)
                .commit();
    }

    public void finishFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
