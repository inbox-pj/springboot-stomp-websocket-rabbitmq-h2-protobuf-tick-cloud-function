package com.cardconnect.stom.stockexchange.cli;

import com.cardconnect.stom.stockexchange.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class JWTCommandLineRunner implements CommandLineRunner {

    private final JwtUtil jwtUtil;

    @Autowired
    public JWTCommandLineRunner(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("generate-token")) {
            Options options = new Options();
            args = Arrays.copyOfRange(args, 0, 7);

            options.addOption("n", "username", true, "User name");
            options.addOption("r", "role", true, "User role");
            options.addOption("i", "encoded", true, "Indicates if the token should be encoded");
            options.addOption("h", "help", false, "Display help message");

            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();

            try {
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption("h")) {
                    return;
                }

                if (cmd.hasOption("n") && cmd.hasOption("r")) {
                    String username = cmd.getOptionValue("username");
                    String role = cmd.getOptionValue("role");

                    String token = jwtUtil.generateToken(username, role);

                    if (cmd.hasOption("i") && cmd.getOptionValue("encoded").equalsIgnoreCase("true")) {
                        token = jwtUtil.encodeToken(token);
                    }
                    log.info("Following is the generated JWT token: {}", token);
                } else {
                    log.error("Invalid arguments!");
                }

            } catch (ParseException e) {
                log.error("Error parsing arguments", e);
            } finally {
                formatter.printHelp(getClass().getName() + " :: ", options);
            }
        }
    }
}