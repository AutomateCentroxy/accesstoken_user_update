package org.gluu.agama.smtp;

import java.util.Map;

class SendEmailTemplateEn {

    static Map<String, String> get(String username) {

        String html = """
<div style="max-width: 640px; margin: 0 auto; font-size: 16px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; color: #333; background-color: #ffffff; border: 1px solid #eee; border-radius: 8px; overflow: hidden;">
    
    <!-- Logo -->
    <div style="text-align: center; padding: 30px 20px 10px;">
        <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
    </div>
    <hr style="border: none; border-top: 1px solid #ccc; margin: 0 0 20px 0;">

    <!-- Main Content -->
    <div style="padding: 0 30px 30px;">
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
    <div style="background-color: #f9f9f9; padding: 20px; text-align: center; font-size: 14px; color: #777;">
        <p style="margin: 0 0 10px;">Follow us on:</p>
        <div>
            <a href="https://apac01.safelinks.protection.outlook.com/?url=http%3A%2F%2Furl2915.phiwallet.com%2Fls%2Fclick%3Fupn%3Du001.6hG0biFS60U3cMyhuBb9Cs0Z9DCZRZWBSz3Yt4l-2FAGRhsg7O2DeRjnOlTHOOYAY23c_I_RbxHlRVBGB-2B75o4FZtgqWBVgS0v-2B5w0Vhx9rq14oNIM0NETwc5ukrzYVGRIuw5ThxOqSrCHsV-2BfkHlWBlPe0HkOM9dtLDfXkKiPXwsPG8TOOfowySiZIX-2BW3mzUBHdWzDDtROUpshU-2FxrGbR-2Bk4yHINhI9W0efwQ3irMQh5uPyvbm-2B56Gb5rquVWHnQFkopBbfkswU62JJ91A1GNIrkdBQ-3D-3D" style="margin: 0 8px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/facebook.png" width="20"></a>
            <a href="https://apac01.safelinks.protection.outlook.com/?url=http%3A%2F%2Furl2915.phiwallet.com%2Fls%2Fclick%3Fupn%3Du001.6hG0biFS60U3cMyhuBb9Ch7TiYA3hSlWtiu-2Ffp-2B1QghFzLWMXGcrGJh-2Bt1zEiEXUFaHx_RbxHlRVBGB-2B75o4FZtgqWBVgS0v-2B5w0Vhx9rq14oNIM0NETwc5ukrzYVGRIuw5ThxOqSrCHsV-2BfkHlWBlPe0HisDud4Cnu1SIcjARnLXA7LiXBB0RNC05sC5fGKOfj3BtyY1NZ08CJZ0U6Y8juedf52Ilm1Bz91Q-2BMhlujENZdZ7Nc7iuT-2FH-2BJmN9TeI6illenMUfmqt4BWKlBIJL40rnQ-3D-3D" style="margin: 0 8px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/twitter.png" width="20"></a>
            <a href="https://apac01.safelinks.protection.outlook.com/?url=http%3A%2F%2Furl2915.phiwallet.com%2Fls%2Fclick%3Fupn%3Du001.6hG0biFS60U3cMyhuBb9CnSsQPAAOZ8zuKHwPTn7Eb5YQqXBTjdLBkLnE4-2Bq62G15pob_RbxHlRVBGB-2B75o4FZtgqWBVgS0v-2B5w0Vhx9rq14oNIM0NETwc5ukrzYVGRIuw5ThxOqSrCHsV-2BfkHlWBlPe0HrvJSNDpaOBR-2Bfeu0hfEuBVHA1qCaKOAmlYGu3ViPVuzzzL8e9VnzxYuIuIZHL9wHKwGnF9lU8wIbspaYgQNe7BELaSF7AuqFfGXUC0VhbyWnznXOQ-2B93HAu5WQ1wYRAtA-3D-3D" style="margin: 0 8px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/instagram.png" width="20"></a>
            <a href="https://apac01.safelinks.protection.outlook.com/?url=http%3A%2F%2Furl2915.phiwallet.com%2Fls%2Fclick%3Fupn%3Du001.6hG0biFS60U3cMyhuBb9Cu-2Bk3X15nb-2B0LOuUCxpu2eQ9jNO9jDrguk-2FG86UtYf5NYMosYFO0XLWpn-2FjBmeMbUA-3D-3DJkpn_RbxHlRVBGB-2B75o4FZtgqWBVgS0v-2B5w0Vhx9rq14oNIM0NETwc5ukrzYVGRIuw5ThxOqSrCHsV-2BfkHlWBlPe0Hj-2BdkkERxfX4xFVgipNEp0f5DhFIKJtDXJc-2BlbBnkkznT4OJgwHhzvN7mzemPDinLJWDMQQ39t7tm-2BQMKaKdSE0MGz410rgWwQTEUwSC9gS3IXNP6vq0DWEY3p3xI05TNg-3D-3D" style="margin: 0 8px;"><img src="https://storage.googleapis.com/mwapp_prod_bucket/social_icon_images/linkedin.png" width="20"></a>
        </div>
        <p style="margin-top: 15px; font-size: 12px;">
            Phi Wallet Unipessoal LDA<br>
            Avenida da Liberdade 262 R/C<br>
            1250-149 Lisbon, Portugal
        </p>
    </div>

</div>
        """;

        return Map.of(
            "subject", "Your username has been updated successfully",
            "body", html
        );
    }

}
