package com.santra.sanchita.iforgot.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by sanchita on 6/12/17.
 */

public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
