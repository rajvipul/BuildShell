import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Stage REPL
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//        do {
//            System.out.println(input + ": command not found");
//
//            System.out.print("$ ");
//            input = scanner.nextLine();
//
//        }
//        while (!input.matches(""));
//        scanner.close();


        //Stage -- The exit builtin
//        while(true) {
//            System.out.print("$ ");
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//            if (input.equals("exit 0"))
//                break;
//            System.out.println(input + ": command not found");
//        }

        //Stage -- The echo command
//        while(true) {
//            System.out.print("$ ");
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//            if (input.equals("exit 0"))
//                break;
//            if (input.startsWith("echo")) {
//                String output = input.replaceFirst("^echo\\s", "");
//                System.out.println(output);
//            }
//            else {
//                System.out.println(input + ": command not found");
//            }
//        }

        //Stage -- The type-builtin & executable stage
//        List<String> builtIns = Arrays.asList("echo", "exit", "type");
//
//        while(true) {
//            System.out.print("$ ");
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//            if (input.equals("exit 0"))
//                break;
//            if (input.startsWith("echo")) {
//                String output = input.replaceFirst("^echo\\s", "");
//                System.out.println(output);
//            }
//            else if(input.startsWith("type")){
//                String output = input.replaceFirst("^type\\s+", "").trim();
//                if (builtIns.stream().anyMatch(e -> e.equals(output))) {
//                    System.out.println(output + " is a shell builtin");
//                } else if (output.equals("invalid_command")) {
//                    System.out.println(output + ": command not found");
//                } else {
//                    String systemPath = System.getenv("PATH"); // Get system PATH
//                    boolean found = false;
//
//                    if (systemPath != null) {
//                        for (String dir : systemPath.split(File.pathSeparator)) {
//                            File file = new File(dir, output);
//                            if (file.exists() && file.canExecute()) {
//                                System.out.println(output + " is " + file.getAbsolutePath());
//                                found = true;
//                                break;
//                            }
//                        }
//                    }
//                    if (!found) {
//                        System.out.println(output + ": not found");
//                    }
//                }
//            }
//            else {
//                System.out.println(input + ": command not found");
//            }
//        }


        //Stage -- Run a program
        while (true) {
            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+"); // Split input into command + arguments

            if (input.isEmpty()) continue;// Ignore empty input
            if (input.equals("exit 0"))
                break;
            else if (input.startsWith("echo")) {
                String output = input.replaceFirst("^echo\\s", "");
                System.out.println(output);
            }
            else if (input.equals("pwd")) {
                System.out.println(System.getProperty("user.dir"));
            }
            //String command = parts[0];
            else if (input.startsWith("type")) {
                handleTypeCommand(parts);
            }
            else if (parts.length > 1 && !input.startsWith("echo")) {
                // Try to execute an external command
                executeExternalCommand(parts);
            }
            else {
                System.out.println(input + ": command not found");

            }
        }

    }

    private static void handleTypeCommand(String[] parts) {

        String output = parts[1];
        if (Arrays.asList("echo", "exit", "type", "pwd").contains(output)) {
            System.out.println(output + " is a shell builtin");
        } else if (output.equals("invalid(_.*)?_command")) {
            System.out.println(output + ": command not found");
        } else {
            String systemPath = System.getenv("PATH"); // Get system PATH
            boolean found = false;

            if (systemPath != null) {
                for (String dir : systemPath.split(File.pathSeparator)) {
                    File file = new File(dir, output);
                    if (file.exists() && file.canExecute()) {
                        System.out.println(output + " is " + file.getAbsolutePath());
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                System.out.println(output + ": not found");
            }
        }
    }

    private static String findCommandInPath(String command) {
        String systemPath = System.getenv("PATH");
        if (systemPath != null) {
            for (String dir : systemPath.split(File.pathSeparator)) {
                File file = new File(dir, command);
                if (file.exists() && file.canExecute()) {
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }

    private static void executeExternalCommand(String[] commandWithArgs) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandWithArgs);
            processBuilder.redirectErrorStream(true); // Merge stdout and stderr
            Process process = processBuilder.start();

            // Capture and print output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for process to finish
            process.waitFor();
        } catch (Exception e) {
        }
    }

}
