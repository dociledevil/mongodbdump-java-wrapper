package org.internetresources.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.InvalidParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.internetresources.util.mongodump.MongodumpService;
import org.internetresources.util.mongodump.domain.BackupConfiguration;
import org.internetresources.util.mongodump.domain.MongoServerHostConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * Specifications
 * 
 * console arg
 * -d [backup directory]
 * -n [database name]
 * -a [BACKUP|RESTORE]
 *
 */
@Slf4j
public class Main {
    private static final Options OPTIONS = new Options();
    
    public static void logOut(String msg) {
        log.info(msg);
    }

    @SuppressWarnings("static-access")
    static CommandLine initCommandLineParser(String[] args)
            throws ParseException {
        CommandLineParser parser = new PosixParser();
        OPTIONS.addOption(OptionBuilder.withArgName("Help").create("h"));
        OPTIONS.addOption(OptionBuilder.withArgName("Backup directory  (default C:\\TMP\\mongoBackup or /tmp/mongoBackup)").hasArg().create("d"));
        OPTIONS.addOption(OptionBuilder.withArgName("Database name").hasArg().create("n"));
        OPTIONS.addOption(OptionBuilder.withArgName("action 'BACKUP' or 'RESTORE' (default: BACKUP)").hasArg().create("a"));
        return parser.parse(OPTIONS, args);
    }



    public static void main(String[] args) {
        CommandLine cmd;
        try {
            cmd = initCommandLineParser(args);
        } catch (ParseException e) {
            logOut("command line error : " + e.getMessage());
            printUsage();
            return;
        }
        if (cmd.hasOption("h")) {
            printUsage();
            return;
        }
        BackupConfiguration backupConf = new BackupConfiguration();
        MongoServerHostConfiguration hostConf = new MongoServerHostConfiguration();

        if (cmd.hasOption("d")) {
            String argVal = cmd.getOptionValue("d");
            backupConf.setBackupDirectory(argVal);
        }
        if (cmd.hasOption("n")) {
            String argVal = cmd.getOptionValue("n");
            backupConf.setDbName(argVal);
        }
        String action = "BACKUP";
        if (cmd.hasOption("a")) {
            String argVal = cmd.getOptionValue("a");
            action = argVal;
        }
        try {
			MongodumpService dumpSvc = MongodumpService.getInstance(hostConf);
	        if ("BACKUP".equals(action)) {
	            dumpSvc.backup(backupConf);
	        } else if ("RESTORE".equals(action)) {
	            dumpSvc.restore(backupConf);
	        } else {
	        	log.info("invalid action {} : expected BACKUP|RESTORE", action);
	        	printUsage();
	        }
        } catch (InvalidParameterException ipe) {
        	log.error(ipe.getMessage());
        	printUsage();
        } catch (Throwable t) {
        	log.error(t.getMessage());
        }
        logOut("bye");
    }


    public static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);
        formatter.printUsage(writer, 1000, "(application)", OPTIONS);
        writer.close();
        logOut(baos.toString());
    }

}