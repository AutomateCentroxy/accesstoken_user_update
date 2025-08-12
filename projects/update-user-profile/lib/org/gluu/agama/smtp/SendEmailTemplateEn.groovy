package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;

class UsernameCreatedTemplate {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static String get(String username, ContextData context) {

        return """
<div style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333;">
    <div style="padding: 12px; border-bottom: 1px solid #ccc;">
        <p>Dear User,</p>
        <p>Congratulations! Your username has been successfully created.</p>
        <p>Username: <b>""" + username + """</b></p>
        <p>You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.</p>
        <p><b>But that's not all!</b></p>
        <p>We're not just upgrading how you log in, we're setting the stage for something exciting.
           Powerful new features are on the way, designed to help boost your journey toward a more prosperous financial future.</p>
        <p>Stay tuned, the best is yet to come!</p>
        <p>In the meantime, if you have any questions or need support, we're just a click away.</p>
        <p>Kind regards,<br>Phi Wallet Team</p>
    </div>

    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">When this happened:</p>
        <p><span style="color: #48596b; font-weight: 500;">Date:</span><br>""" + computeDateTime(context.getTimeZone()) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" + 
            ((context.getDevice() == null || context.getDevice().isEmpty()) ? "" : ("Device:</span><br>" + context.getDevice())) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" +
            ((context.getLocation() == null || context.getLocation().isEmpty()) ? "" : ("Approximate Location:</span><br>" + context.getLocation())) + """</p>
    </div>
</div>
        """;
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
