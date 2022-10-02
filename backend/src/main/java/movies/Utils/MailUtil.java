package movies.Utils;

import movies.domain.Account;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Optional;

public class MailUtil {

    public void sendMail(Account account) {

        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        String hstSMTPid = "yikanghee2";
        String hostSMTPpw = "";

        // 보내는 사람
        String fromEmail = "yikanghee2@naver.com";
        String fromName = "이강희";

        String subject = "";
        String msg = "";

        subject = "[MOBEE] 임시 비밀번호 발금 안내";
        msg += "<div align='left'>";
        msg += "<h3>";
        msg += account.getUsername() + "님의 비밀번호는" + account.getPassword() + "입니다. </h3></div>";

        // email 전송
        String mailRecipient = account.getEmail();
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setDebug(true);
            mail.setCharset(charSet);
            mail.setSSLOnConnect(true);
            mail.setHostName(hostSMTP);
            mail.setSmtpPort(587);
            mail.setAuthentication(hstSMTPid, hostSMTPpw);
            mail.setStartTLSEnabled(true);
            mail.addTo(mailRecipient, charSet);
            mail.setFrom(fromEmail, fromName, charSet);
            mail.setSubject(subject);
            mail.setHtmlMsg(msg);
            mail.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
