package Server;

public abstract class Entrance {

    public static User signUp(String name, String nationalId, String password, String phoneNumber, String email) throws DuplicateNationalId {
        User currentUser = null;
        for (User user : Information.users) {
            if (user.getNationalId().compareTo(nationalId) == 0) {
                throw new DuplicateNationalId();
            }
        }
        currentUser = new User(name, nationalId, password, phoneNumber, email);
        Information.users.add(currentUser);
        return currentUser;
    }

    public static User logIn(String nationalId, String password) throws UserNotFoundException {
        User currentUser = null;
        boolean found = false;
        for (User user : Information.users) {
            if (user.getNationalId().compareTo(nationalId) == 0 && user.getPassword().compareTo(password) == 0) {
                found = true;
                currentUser = user;
                break;
            }
        }
        if (!found) throw new UserNotFoundException();
        for (Account account : currentUser.getAccounts()) {
            account.payLoan();
        }
        return currentUser;
    }

}