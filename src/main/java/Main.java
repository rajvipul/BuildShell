import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

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
        List<String> builtIns = Arrays.asList("echo", "exit", "type");

        while(true) {
            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("exit 0"))
                break;
            if (input.startsWith("echo")) {
                String output = input.replaceFirst("^echo\\s", "");
                System.out.println(output);
            }
            else if(input.startsWith("type")){
                String output = input.replaceFirst("^type\\s+", "").trim();
                if (builtIns.stream().anyMatch(e -> e.equals(output))) {
                    System.out.println(output + " is a shell builtin");
                } else if (output.equals("invalid_command")) {
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



                    //System.out.println(output + ": not found");
                }
            }
            else {
                System.out.println(input + ": command not found");
            }
        }

    }
}
