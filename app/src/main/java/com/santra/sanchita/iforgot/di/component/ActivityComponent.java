package com.santra.sanchita.iforgot.di.component;

import com.santra.sanchita.iforgot.di.PerActivity;
import com.santra.sanchita.iforgot.di.module.ActivityModule;
import com.santra.sanchita.iforgot.ui.gallery.GalleryActivity;
import com.santra.sanchita.iforgot.ui.main.MainActivity;
import com.santra.sanchita.iforgot.ui.main.MainCameraActivity;
import com.santra.sanchita.iforgot.ui.preview.PreviewActivity;
import com.santra.sanchita.iforgot.ui.view_image.ViewImageActivity;

import dagger.Component;

/**
 * Created by sanchita on 6/12/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(MainCameraActivity activity);

    void inject(PreviewActivity activity);

    void inject(GalleryActivity activity);

    void inject(ViewImageActivity activity);
}
