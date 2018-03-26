package com.santra.sanchita.iforgot.di;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sanchita on 26/3/18.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({AutoResetMode.NEVER, AutoResetMode.UNDER, AutoResetMode.OVER, AutoResetMode.ALWAYS})
public @interface AutoResetMode {
    int UNDER = 0;
    int OVER = 1;
    int ALWAYS = 2;
    int NEVER = 3;

    class Parser {

        @AutoResetMode
        public static int fromInt(final int value) {
            switch (value) {
                case OVER:
                    return OVER;
                case ALWAYS:
                    return ALWAYS;
                case NEVER:
                    return NEVER;
                default:
                    return UNDER;
            }
        }
    }
}
