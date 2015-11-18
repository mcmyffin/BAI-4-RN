package de.haw_chat.server;

import de.haw_chat.server.network.implementations.DeviceFactory;
import de.haw_chat.server.network.interfaces.Server;
import de.haw_chat.server.network.interfaces.ServerConfiguration;

/**
 * Created by Andreas on 31.10.2015.
 */
public class Main {

    private static int defaultPort       = 12345;
    private static int defaultMaxClients = 10;
    private static boolean defaultSsl    = false;

    private static String c_help = "-h";
    private static String usage  =  "-- USAGE --\n"+
                                    "   *.jar [OPTION] <PORT> <MAX_CLIENTS> <SSL>\n\n"+
                                    "   [OPTION]        : "+c_help+"\n" +
                                    "   <PORT>          : port number. Default 12345\n"+
                                    "   <MAX_CLIENTS>   : maximum Client connections\n"+
                                    "   <SSL>           : 1 for ssl enable or 0 for disable. Default 0\n";

    private static void showUsage(){
        System.out.println(usage);
        System.exit(1);
    }

//    private

    public static void main(String[] args) {

        if(args.length != 0 && args.length < 3) showUsage();
        ServerConfiguration configuration =
                DeviceFactory.createChatServerConfiguration(defaultPort, defaultMaxClients, defaultSsl);
        Server server = DeviceFactory.createChatServer(configuration);

        Thread thread = new Thread(server);
        thread.start();

        System.out.println("SERVER STARTED!");
    }
}
