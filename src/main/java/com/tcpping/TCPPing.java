package com.tcpping;

import com.tcpping.sockets.Catcher;
import com.tcpping.sockets.Pitcher;
import org.apache.commons.cli.*;

import java.io.IOException;

public class TCPPing {
    private int port = 9000;
    private String ip = "192.168.0.1";
    private int mps = 30;
    private int size = 1000;

    private static Options setCliOptions () {
        Options options = new Options();
        options.addOption("p", false, "Run in Pitcher mode");
        options.addOption("c", false, "Run in Catcher mode");
        options.addOption("port", true, "Port for the socket to connect to");
        options.addOption("bind", true, "IP Address to bind to");
        options.addOption("mps", true, "Messages per second");
        options.addOption("size", true, "Size of the message in bytes");
        options.addOption("hostname", false, "Hostname for the machine the server is running on");

        return options;
    }

    private static CommandLine cliParser (Options options, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse( options, args);
    }

    public static void main(String[] args) throws MissingArgumentException, IOException {
        TCPPing app = new TCPPing();
        CommandLine cmd;

        try {
            cmd = cliParser(setCliOptions(), args);
        } catch (ParseException e) {
            throw new Error(e);
        }

        if (cmd.hasOption("port")) {
            app.port = Integer.parseInt(cmd.getOptionValue("port"));
        }

        if (cmd.hasOption("bind")) {
            app.ip = cmd.getOptionValue("bind");
        }

        if (cmd.hasOption("mps")) {
            app.mps = Integer.parseInt(cmd.getOptionValue("mps"));
        }

        if (cmd.hasOption("size")) {
            int size = Integer.parseInt(cmd.getOptionValue("size"));
            if (size >= 3000 || size <= 50) {
                size = 300;
            }

            app.size = size;
        }

        if (cmd.hasOption("c")) {

            Catcher catcher = new Catcher();
            catcher.Server(app.port);

        } else if (cmd.hasOption("p")) {

            Pitcher pitcher = new Pitcher();
            pitcher.Client(app.ip, app.port, app.mps, app.size);

        } else {
            throw new MissingArgumentException("You need to select a valid mode for the application to run in");
        }
    }
}
