package numbers;

import java.util.*;

public class Main {
    private static final Set<String> PROPS = Set.of("EVEN", "ODD", "BUZZ", "DUCK",
            "PALINDROMIC", "GAPFUL", "SPY", "SQUARE",
            "SUNNY", "JUMPING", "SAD", "HAPPY",
            "-EVEN", "-ODD", "-BUZZ", "-DUCK",
            "-PALINDROMIC", "-GAPFUL", "-SPY", "-SQUARE",
            "-SUNNY", "-JUMPING", "-SAD", "-HAPPY");
    private static long number;
    private static int consecutiveTimes = 0;
    private static Set<String> targetProperties = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//      write your code here
        System.out.println("Welcome to Amazing Numbers!\n");

        showInstruction();
        showPrompt();
        while (number != 0) {
            if (consecutiveTimes == 0) {
                showPropertiesForOneNumber();
            } else if (targetProperties.size() > 0) {
                findNumberWithProperties();
            } else {
                showPropertiesForConsecutiveNumbers();
            }
            showPrompt();
        }
        System.out.println("Goodbye!");
    }

    public static void showInstruction() {
        System.out.println("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }

    public static void showPrompt() {
        System.out.print("Enter a request:");
        //when input is empty
        String userInput = scanner.nextLine();

        if (Objects.equals(userInput, "")) {
            showInstruction();
            showPrompt();
            return;
        }

        String[] userInputs = userInput.split(" ");

        //when input is not a number
        try {
            number = Long.parseLong(userInputs[0]);
        } catch (NumberFormatException ex) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            showPrompt();
            return;
        }

        //when input is a number
        if (number == 0) {
            return;
        }
        if (number < 0) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            showPrompt();
            return;
        }

        //first input is a natural number, check if second input exists
        if (userInputs.length >= 2) {
            try {
                int parsed = Integer.parseInt(userInputs[1]);
                consecutiveTimes = parsed;
                if (parsed < 0) {
                    System.out.println("The second parameter should be a natural number.");
                    showPrompt();
                    return;
                }
            } catch (NumberFormatException ex) {
                System.out.println("The second parameter should be a natural number.");
                showPrompt();
                return;
            }
        }

        //if no property is specified, proceed to scanning all properties
        if (userInputs.length < 3) return;

        //if property is specified, check if inputted property is correct
        List<String> wrongInputs = new ArrayList<>();
        for (int i = 2; i < userInputs.length; i++) {
            String currProp = userInputs[i].toUpperCase();
            if (!PROPS.contains(currProp)) {
                wrongInputs.add(currProp);
            }
        }

        if (wrongInputs.size() >= 1) {
            if (wrongInputs.size() == 1) {
                System.out.println("The property " + wrongInputs.toString() + " is wrong.");
            } else {
                System.out.println("The properties " + wrongInputs.toString() + " are wrong.");
            }
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
            showPrompt();
            return;
        }

        //check if contradicting properties exist
        Set<String> propertySet = new HashSet<>();
        for (int i = 2; i < userInputs.length; i++) {
            String currProp = userInputs[i].toUpperCase();
            //check if direct opposites pair exist
            if (propertySet.contains("-" + currProp)) {
                handleExclusiveProperties(currProp, "-" + currProp);
                return;
            }
            if ((currProp.charAt(0) == '-' && propertySet.contains(currProp.substring(1)))) {
                handleExclusiveProperties(currProp, currProp.substring(1));
                return;
            }
            switch (currProp) {
                case "EVEN":
                    if (propertySet.contains("ODD")) {
                        handleExclusiveProperties("ODD", "EVEN");
                        return;
                    }
                    break;
                case "-EVEN":
                    if (propertySet.contains("-ODD")) {
                        handleExclusiveProperties("-ODD", "-EVEN");
                        return;
                    }
                    break;
                case "ODD":
                    if (propertySet.contains("EVEN")) {
                        handleExclusiveProperties("ODD", "EVEN");
                        return;
                    }
                    break;
                case "-ODD":
                    if (propertySet.contains("-EVEN")) {
                        handleExclusiveProperties("-ODD", "-EVEN");
                        return;
                    }
                    break;
                case "DUCK":
                    if (propertySet.contains("SPY")) {
                        handleExclusiveProperties("SPY", "DUCK");
                        return;
                    }
                    break;
                case "-DUCK":
                    if (propertySet.contains("-SPY")) {
                        handleExclusiveProperties("-SPY", "-DUCK");
                        return;
                    }
                    break;
                case "SPY":
                    if (propertySet.contains("DUCK")) {
                        handleExclusiveProperties("SPY", "DUCK");
                        return;
                    }
                    break;
                case "-SPY":
                    if (propertySet.contains("-DUCK")) {
                        handleExclusiveProperties("-SPY", "-DUCK");
                        return;
                    }
                    break;
                case "SUNNY":
                    if (propertySet.contains("SQUARE")) {
                        handleExclusiveProperties("SUNNY", "SQUARE");
                        return;
                    }
                    break;
                case "SQUARE":
                    if (propertySet.contains("SUNNY")) {
                        handleExclusiveProperties("SUNNY", "SQUARE");
                        return;
                    }
                    break;
                case "SAD":
                    if (propertySet.contains("HAPPY")) {
                        handleExclusiveProperties("SAD", "HAPPY");
                        return;
                    }
                    break;
                case "-SAD":
                    if (propertySet.contains("-HAPPY")) {
                        handleExclusiveProperties("-SAD", "-HAPPY");
                        return;
                    }
                    break;
                case "HAPPY":
                    if (propertySet.contains("SAD")) {
                        handleExclusiveProperties("SAD", "HAPPY");
                        return;
                    }
                    break;
                case "-HAPPY":
                    if (propertySet.contains("-SAD")) {
                        handleExclusiveProperties("-SAD", "-HAPPY");
                        return;
                    }
                    break;
            }

            propertySet.add(currProp);
        }

        //assign property to class
        targetProperties = propertySet;

    }

    private static void handleExclusiveProperties(String prop1, String prop2) {
        System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n", prop1, prop2);
        System.out.println("There are no numbers with these properties.");
        showPrompt();
        return;
    }

    public static void showPropertiesForOneNumber() {
        System.out.println("Properties of " + number);
        System.out.println("        buzz: " + isBuzz(number));
        System.out.println("        duck: " + isDuck(number));
        System.out.println(" palindromic: " + isPalindromic(number));
        System.out.println("      gapful: " + isGapful(number));
        System.out.println("         spy: " + isSpy(number));
        System.out.println("        even: " + !isOdd(number));
        System.out.println("         odd: " + isOdd(number));
        System.out.println("       sunny: " + isSunny(number));
        System.out.println("      square: " + isSquare(number));
        System.out.println("     jumping: " + isJumping(number));
        System.out.println("       happy: " + isHappy(number));
        System.out.println("         sad: " + isSad(number));
    }

    public static void showPropertiesForConsecutiveNumbers() {
        for (long curr = number; curr < number + consecutiveTimes; curr++) {
            printProperty(curr);
        }
        consecutiveTimes = 0;
    }

    private static void printProperty(long curr) {
        String currMessage = "             " + curr + " is ";
        if (isBuzz(curr)) {
            currMessage += " buzz,";
        }
        if (isDuck(curr)) {
            currMessage += " duck,";
        }
        if (isPalindromic(curr)) {
            currMessage += " palindromic,";
        }
        if (isGapful(curr)) {
            currMessage += " gapful,";
        }
        if (isSpy(curr)) {
            currMessage += " spy,";
        }
        if (isSunny(curr)) {
            currMessage += " sunny,";
        }
        if (isSquare(curr)) {
            currMessage += " square,";
        }
        if (isJumping(curr)) {
            currMessage += " jumping,";
        }
        if (isHappy(curr)) {
            currMessage += " happy,";
        }
        if (isSad(curr)) {
            currMessage += " sad,";
        }
        if (!isOdd(curr)) {
            currMessage += " even";
        } else {
            currMessage += " odd";
        }


        System.out.println(currMessage);
    }

    private static void findNumberWithProperties() {
        int numberFound = 0;
        long curr = number;
        outer:
        while (numberFound < consecutiveTimes) {
            for (String prop : targetProperties) {
                switch (prop) {
                    case "EVEN", "-ODD" -> {
                        if (isOdd(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "ODD", "-EVEN" -> {
                        if (!isOdd(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "BUZZ" -> {
                        if (!isBuzz(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-BUZZ" -> {
                        if (isBuzz(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "DUCK" -> {
                        if (!isDuck(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-DUCK" -> {
                        if (isDuck(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "PALINDROMIC" -> {
                        if (!isPalindromic(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-PALINDROMIC" -> {
                        if (isPalindromic(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "GAPFUL" -> {
                        if (!isGapful(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-GAPFUL" -> {
                        if (isGapful(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "SPY" -> {
                        if (!isSpy(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-SPY" -> {
                        if (isSpy(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "SUNNY" -> {
                        if (!isSunny(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-SUNNY" -> {
                        if (isSunny(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "SQUARE" -> {
                        if (!isSquare(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-SQUARE" -> {
                        if (isSquare(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "JUMPING" -> {
                        if (!isJumping(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "-JUMPING" -> {
                        if (isJumping(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "SAD", "-HAPPY" -> {
                        if (!isSad(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                    case "HAPPY", "-SAD" -> {
                        if (!isHappy(curr)) {
                            curr++;
                            continue outer;
                        }
                    }
                }
            }

            printProperty(curr);
            curr++;
            numberFound++;

        }
        consecutiveTimes = 0;
        targetProperties.clear();
    }

    private static boolean isOdd(long num) {
        return num % 2 == 1;
    }

    private static boolean isBuzz(long num) {

        return num % 7 == 0 || Long.toString(num).endsWith("7");
    }

    private static boolean isDuck(long num) {
        boolean res = false;
        String temp = Long.toString(num);

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '0') {
                res = true;
                break;
            }
        }

        return res;
    }

    private static boolean isPalindromic(long num) {
        boolean res = true;
        String temp = Long.toString(num);

        int i = 0;
        int j = temp.length() - 1;

        while (i < j) {
            if (temp.charAt(i) != temp.charAt(j)) {
                res = false;
                break;
            }
            i++;
            j--;
        }

        return res;
    }

    private static boolean isGapful(long num) {
        boolean res = false;
        String temp = Long.toString(num);
        if (temp.length() > 2) {
            long firstAndLastNumber = Long.parseLong(Character.toString(temp.charAt(0)) + Character.toString(temp.charAt(temp.length() - 1)));
            if (num % firstAndLastNumber == 0) {
                res = true;
            }
        }
        return res;
    }

    private static boolean isSpy(long num) {
        long sumOfDigits = 0;
        long productOfDigits = 1;
        String temp = Long.toString(num);
        for (int i = 0; i < temp.length(); i++) {
            int digit = Character.getNumericValue(temp.charAt(i));
            sumOfDigits += digit;
            productOfDigits *= digit;
        }
        return sumOfDigits == productOfDigits;
    }

    private static boolean isSunny(long num) {
        return isSquare(num + 1);
    }

    private static boolean isSquare(long num) {
        double squareRoot = Math.sqrt((double) num);
        return squareRoot % 1 == 0;
    }

    private static boolean isJumping(long num) {
        String temp = Long.toString(num);
        int curr = Character.getNumericValue(temp.charAt(0));

        for (int i = 1; i < temp.length(); i++) {
            int next = Character.getNumericValue(temp.charAt(i));
            if (Math.abs(curr - next) != 1) {
                return false;
            }
            curr = next;
        }
        return true;
    }

    private static boolean isSad(long num) {
        return !isHappy(num);
    }

    private static boolean isHappy(long num) {
        long startNum = num;
        long curr = num;
        int times = 0;
        do {
            times++;
            String tempString = Long.toString(curr);
            long temp = 0;
            for (int i = 0; i < tempString.length(); i++) {
                int currDigit = Character.getNumericValue(tempString.charAt(i));
                temp += currDigit * currDigit;
            }
            curr = temp;
        } while (times < 100 && curr != 1 && curr != startNum);

        if (curr == 1) return true;
        return false;
    }
}
