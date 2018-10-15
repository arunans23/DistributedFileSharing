package com.semicolon.ds;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Constants {

    public static final String BS_PROPERTIES = "Bootstrap.properties";
    public static final String LOCALHOST = "127.0.0.1";

    public static final String MSG_FORMAT = "%04d %s";

    public static final String REG_FORMAT = "REG %s %s %s";
    public static final String UNREG_FORMAT = "UNREG %s %s %s";

    public static final String REGOK = "REGOK";
    public static final String UNROK = "UNROK";

    public static final String PING_FORMAT = "PING %s %s";
    public static final String PONG_FORMAT = "PONG %s %s";

    public static final int TIMEOUT_REG = 10000;

    public static final int MIN_NEIGHBOURS = 2;
    public static final int MAX_NEIGHBOURS = 4;

}
