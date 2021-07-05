package Server;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.*;

public abstract class Entrance {

    public static User signUp(String name, String nationalId, String password, String phoneNumber, String email) throws DuplicateNationalIdException, InvalidEmailAddressException {
        User currentUser = null;
        for (User user : Information.users) {
            if (user.getNationalId().compareTo(nationalId) == 0) {
                throw new DuplicateNationalIdException();
            }
        }
        currentUser = new User(name, nationalId, password, phoneNumber, email);
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
        if (currentUser.getAccounts() != null) {
            for (Account account : currentUser.getAccounts()) {
                account.payLoan();
            }
        }
        return currentUser;
    }

    public static void sendEmail(String to, int code) throws InvalidEmailAddressException {
        String from = "bank.project.m.t@gmail.com";
        String password = "09151675292";
        String sub = "Verify your email address!";
        String msg = "Please use this code to verify your email address: " + code;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new InvalidEmailAddressException();
        }
    }

}