package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;

class SendEmailTemplateAr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
Hello ${givenName != null ? givenName : ""},<br><br>
ð Great news! Your username has been successfully created.<br><br>
Username: <b>${username}</b><br><br>
You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.<br><br>
<b>But thatâs not all!</b><br><br>
Weâre not just improving how you log in â weâre preparing your account for exciting new features that are on the way, designed to support your journey toward a more prosperous financial future.<br><br>
If you have any questions, feel free to reach out to us.<br><br>
Best regards,<br>
The Phi Wallet Team
""";

        String html = """
<div dir="ltr" lang="en" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Main Content -->
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p><b>Hello,</b><br><br>
        ${bodyContent}</p>

        <div style="display: flex; justify-content: center; margin: 20px 0;">
            <div style="background-color: #B29163; color: white; font-size: 30px; font-weight: 500; padding: 10px 20px; border-radius: 8px;" align="center">
                ${username}
            </div>
        </div>
    </div>

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">When this happened:</p>
        <p><span style="color: #48596b; font-weight: 500;">Date:</span><br>${computeDateTime(context.getTimeZone())}</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
""";

        return Map.of(
            "subject", "Your username has been successfully created",
            "body", html
        );
    }

    private static String computeDateTime(String zone) {
        Instant now = Instant.now();
        try {
            return now.atZone(ZoneId.of(zone)).format(formatter);
        } catch (Exception e) {
            return now.atOffset(ZoneOffset.UTC).format(formatter);
        }
    }
}
