package com.tcpping;

import com.tcpping.sockets.Catcher;
import com.tcpping.sockets.Pitcher;
import org.apache.commons.cli.*;

public class TCPPing {

    enum Mode {
        CATCHER, PITCHER
    }

    private static Options setCliOptions () {
        Options options = new Options();
        options.addOption("p", false, "Run in Pitcher mode");
        options.addOption("c", false, "Run in Catcher mode");
        options.addOption("port", true, "Port for the socket to connect to");
        options.addOption("bind", true, "IP Address to bind to");
        options.addOption("mps", true, "Messages per second");
        options.addOption("size", true, "Size of the message in bytes");

        return options;
    }

    private static CommandLine cliParser (Options options, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse( options, args);
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
        CommandLine cmd;

        try {
            cmd = cliParser(setCliOptions(), args);
        } catch (ParseException e) {
            throw new Error(e);
        }

        String mode = cmd.getOptionValue("port");

        System.out.println(mode);

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
