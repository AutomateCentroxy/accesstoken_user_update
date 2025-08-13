package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateEn {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333;">

    <!-- Logo at top -->
    <div style="text-align:center; padding: 30px 0 10px 0;">
    <img src="https://storage.googleapis.com/email_template_staticfiles/Phi_logo320x132_Aug2024.png" alt="Phi Logo" style="height:40px;">
  </div>

  <!-- Email Content Box -->
  <div style="max-width:417px; margin:0 auto; background-color:#ffffff; padding:30px; border-radius:4px;">
    
    <p style="font-size: 18px; margin-bottom: 15px;">Dear User,</p>
        <p>Congratulations! Your username has been successfully created.</p>

        <p>Username: <span style="font-weight: bold;">""" + username + """</span></p>

        <p>You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.</p>

        <p><span style="font-weight: bold;">But that's not all!</span></p>

        <p>We're not just upgrading how you log in, we're setting the stage for something exciting.
           Powerful new features are on the way, designed to help boost your journey toward a more prosperous financial future.</p>

        <p>Stay tuned, the best is yet to come!</p>
        <p>In the meantime, if you have any questions or need support, we're just a click away.</p>

        <p style="margin-top: 30px;">Kind regards,<br>Phi Wallet Team</p>

  </div>

  <!-- Footer -->
  <div style="text-align:center; margin-top:20px; padding:20px 0; font-size:12px; color:#666;">
    <p style="margin:0 0 10px 0;">Let's stay connected:</p>
    <p>
      <a href="https://www.facebook.com/PhiWallet" style="margin:0 5px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/facebook.png" alt="Facebook" style="height:20px;"></a>
      <a href="https://x.com/PhiWallet" style="margin:0 5px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/twitter.png" alt="Twitter" style="height:20px;"></a>
      <a href="https://www.instagram.com/phi.wallet" style="margin:0 5px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/instagram.png" alt="Instagram" style="height:20px;"></a>
      <a href="https://www.linkedin.com/company/phiwallet" style="margin:0 5px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/linkedin.png" alt="LinkedIn" style="height:20px;"></a>
    </p>
    <p style="margin-top:10px; line-height:20px;">
      Phi Wallet Unipessoal LDA

      Avenida da Liberdade 262 R/C

      1250-149 Lisbon

      Portugal
    </p>
  </div>
        """;

        return Map.of(
            "subject", "Your username has been updated successfully",
            "body", html
        );
    }

}
 
 