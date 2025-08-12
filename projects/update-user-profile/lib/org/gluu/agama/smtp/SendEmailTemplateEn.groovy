package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;

class SendEmailTemplateEn {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
<p style="margin: 0; padding: 0;">Hi,</p>
<p style="margin: 0; padding: 0;">Dear %s,</p>
<p style="margin: 0; padding: 0;">Congratulations! Your username has been successfully created ð</p>
<p style="margin: 0; padding: 0;">Username: <b>%s</b></p>
<p style="margin: 0; padding: 0;">
    You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.
</p>
<p style="margin: 0; padding: 0;"><b>But thatâs not all!</b></p>
<p style="margin: 0; padding: 0;">
    Weâre not just upgrading how you log in, weâre setting the stage for something exciting.
    Powerful new features are on the way, designed to help boost your journey toward a more prosperous financial future.
</p>
<p style="margin: 0; padding: 0;">Stay tuned, the best is yet to come!</p>
<p style="margin: 0; padding: 0;">
    In the meantime, if you have any questions or need support, weâre just a click away.
</p>
<p style="margin: 0; padding: 0;">Kind regards,<br>Phi Wallet Team</p>
""".formatted(
    (givenName != null ? givenName : "user"),
    username
);

        String html = """
<div dir="ltr" lang="en" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Main Content -->
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        %s
        <div style="display: flex; justify-content: center; margin: 20px 0;">
            <div style="background-color: #B29163; color: white; font-size: 30px; font-weight: 500; padding: 10px 20px; border-radius: 8px;" align="center">
                %s
            </div>
        </div>
    </div>

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">When this happened:</p>
        <p><span style="color: #48596b; font-weight: 500;">Date:</span><br>%s</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
""".formatted(
    bodyContent,
    username,
    computeDateTime(context.getTimeZone())
);

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
