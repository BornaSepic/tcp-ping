package com.tcpping;

import com.tcpping.sockets.Catcher;
import com.tcpping.sockets.Pitcher;
import com.tcpping.util.Print;

public class TCPPing {

    enum Mode {
        CATCHER, PITCHER
    }

    private static Mode handleMode(String mode) {
        Mode selectedMode = null;

        switch (mode) {
            case "-p": {
                selectedMode = Mode.PITCHER;
                break;
            }
            case "-c": {
                selectedMode = Mode.CATCHER;
                break;
            }
        }

        return selectedMode;
    }

    public static void main(String[] args) {
        Mode selectedMode = handleMode(args[0]);

        if (selectedMode == Mode.CATCHER) {
            Catcher catcher = new Catcher();
            catcher.Server(6000);
        } else if (selectedMode == Mode.PITCHER) {
            Pitcher pitcher = new Pitcher();
            pitcher.Client("192.168.8.107", 6000);
        }
    }
}
