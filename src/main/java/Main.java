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
        while(true) {
            System.out.print("$ ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("exit 0"))
                break;
            System.out.println(input + ": command not found");
        }

    }
}
