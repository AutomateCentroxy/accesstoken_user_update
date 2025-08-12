package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplateFr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
<p style="margin: 0; padding: 0;">Bonjour,</p>
<p style="margin: 0; padding: 0;">Cher/Chère User,</p>
<p style="margin: 0; padding: 0;">Félicitations ! Votre nom d’utilisateur a été créé avec succès 🔒</p>
<p style="margin: 0; padding: 0;">Nom d’utilisateur : <b>%s</b></p>
<p style="margin: 0; padding: 0;">
    Vous pouvez désormais utiliser votre nom d’utilisateur au lieu de votre adresse e-mail pour vous connecter, offrant ainsi une expérience plus fluide et sécurisée.
</p>
<p style="margin: 0; padding: 0;"><b>Mais ce n’est pas tout !</b></p>
<p style="margin: 0; padding: 0;">
    Nous ne faisons pas que simplifier votre connexion ; nous préparons aussi quelque chose d’excitant.
    De nouvelles fonctionnalités arriveront bientôt, conçues pour booster votre parcours vers un avenir financier plus prospère.
</p>
<p style="margin: 0; padding: 0;">Restez à l’écoute, le meilleur est à venir !</p>
<p style="margin: 0; padding: 0;">
    Entre-temps, si vous avez des questions ou besoin d’assistance, nous sommes à un clic de distance.
</p>
<p style="margin: 0; padding: 0;">Cordialement,<br>L’équipe Phi Wallet</p>
""".formatted(
    (givenName != null ? givenName : "utilisateur"),
    username
);

        String html = """
<div dir="ltr" lang="fr" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

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
        <p style="color: #48596b; font-weight: 500;">Date de l’événement :</p>
        <p><span style="color: #48596b; font-weight: 500;">Date :</span><br>%s</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Logo Phi" style="height: 40px;">
        </div>
    </div>
</div>
""".formatted(
    bodyContent,
    computeDateTime(context.getTimeZone())
);

        return Map.of(
            "subject", "Votre nom d’utilisateur a été créé avec succès",
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
