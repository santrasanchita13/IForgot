package com.santra.sanchita.iforgot.di.component;

import com.santra.sanchita.iforgot.di.PerActivity;
import com.santra.sanchita.iforgot.di.module.ActivityModule;
import com.santra.sanchita.iforgot.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by sanchita on 6/12/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);
}
