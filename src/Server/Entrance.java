package Server;

public class Entrance {

    public static void signUp(String name, String nationalId, String password, String phoneNumber, String email) {
        Information.users.add(new User(name, nationalId, password, phoneNumber, email));
    }

    public static void logIn(String nationalId, String password) throws Exception {
        boolean found = false;
        for (User user : Information.users) {
            if (user.getNationalId().compareTo(nationalId) == 0 && user.getPassword().compareTo(password) == 0) {
                found = true;
                break;
            }
        }
        if (!found) throw new Exception("Wrong username or password.");
    }

}