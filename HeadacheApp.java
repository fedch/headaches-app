import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.Serializable;

public class HeadacheApp {
    private static User user = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        user = DataStorage.loadUserData(); // Load the user data at startup

        while (true) {
            if (user == null) {
                System.out.println("Please enter your name:");
                String name = scanner.nextLine();
                user = new User(name);
            } else {
                System.out.println("Hello, " + user.getName() + "!");
                System.out.println("You've been headache-free for " + calculateDaysWithoutHeadache() + " days.");

                if (hasActiveHeadache()) {
                    System.out.println("Your ongoing headache started on " + getLastHeadache().getStartTime() + " and has been active for " + Duration.between(getLastHeadache().getStartTime(), LocalDateTime.now()).toMinutes() + " minutes.");
                    System.out.println("Has it ended yet? (yes/no)");
                    String ended = scanner.nextLine();

                    if (ended.equalsIgnoreCase("yes")) {
                        System.out.println("Enter the end time of headache (format: yyyy-MM-dd HH:mm):");
                        String endTimeStr = scanner.nextLine();
                        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        getLastHeadache().endHeadache(endTime);
                        DataStorage.saveUserData(user);
                    }
                } else {
                    System.out.println("Did you have a headache today? (yes/no)");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("exit")) {
                        DataStorage.saveUserData(user);
                        System.out.println("Goodbye!");
                        break;
                    }
                    if (response.equalsIgnoreCase("yes")) {
                        recordHeadache(scanner);
                        System.out.println("Feel better soon!");
                        DataStorage.saveUserData(user);
                        break;
                    } else {
                        viewHeadacheHistory();
                    }
                }
            }
        }
    }

    private static boolean hasActiveHeadache() {
        if (user.getHeadaches().isEmpty()) return false;
        return getLastHeadache().getEndTime() == null;
    }

    private static Headache getLastHeadache() {
        return user.getHeadaches().get(user.getHeadaches().size() - 1);
    }

    private static void recordHeadache(Scanner scanner) {
        System.out.println("Enter the start time of headache (format: yyyy-MM-dd HH:mm):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("On a scale of 1-10, how severe was it?");
        int severity = Integer.parseInt(scanner.nextLine());

        System.out.println("Has it ended yet? (yes/no)");
        String ended = scanner.nextLine();

        Headache headache = new Headache(startTime, severity);
        if (ended.equalsIgnoreCase("yes")) {
            System.out.println("Enter the end time of headache (format: yyyy-MM-dd HH:mm):");
            String endTimeStr = scanner.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            headache.endHeadache(endTime);
        }

        user.addHeadache(headache);
    }

    private static void viewHeadacheHistory() {
        System.out.println("Previous headaches:");
        for (Headache h : user.getHeadaches()) {
            System.out.println("Date: " + h.getStartTime().toLocalDate() + ", Duration: " + h.getDurationInMinutes() + " minutes, Severity: " + h.getSeverity());
        }
    }

    private static long calculateDaysWithoutHeadache() {
        if (user.getHeadaches().isEmpty()) return 0;
    
        Headache lastHeadache = user.getHeadaches().get(user.getHeadaches().size() - 1);
        LocalDateTime lastEndTime = lastHeadache.getEndTime();
        
        if (lastEndTime == null) return 0; // If the last headache hasn't ended yet
    
        return Duration.between(lastEndTime, LocalDateTime.now()).toDays();
    }
    
}
