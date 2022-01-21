package com.github.misterchangray.monitor;

import com.github.misterchangray.monitor.log.Recorder;

public interface Notify {
    void init() ;

    void notify(Recorder recorder);

    void notify(StringBuilder recorder);

}
