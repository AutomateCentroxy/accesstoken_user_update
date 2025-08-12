package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateEn {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
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

    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: flex-start;">
        <div>
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
        """;

        return Map.of(
            "subject", "Your username has been updated successfully",
            "body", html
        );
    }

}
