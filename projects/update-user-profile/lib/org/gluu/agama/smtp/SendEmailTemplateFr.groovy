package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplateFr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, ContextData context) {

        String html = """
<div dir="ltr" lang="fr" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p>Bonjour,</p>
        <p>Félicitations ! Votre nom d'utilisateur a été créé avec succès </p>
        <p>Nom d'utilisateur : <b>""" + username + """</b></p>
        <p>Vous pouvez désormais utiliser votre nom d'utilisateur au lieu de votre adresse e-mail pour vous connecter, offrant ainsi une expérience plus fluide et sécurisée.</p>
        <p><b>Mais ce n'est pas tout !</b></p>
        <p>Nous ne faisons pas que simplifier votre connexion ; nous préparons aussi quelque chose d'excitant. De nouvelles fonctionnalités arriveront bientôt, conçues pour booster votre parcours vers un avenir financier plus prospère.</p>
        <p>Restez à l'écoute, le meilleur est à venir !</p>
        <p>Entre-temps, si vous avez des questions ou besoin d'assistance, nous sommes à un clic de distance.</p>
        <p>Cordialement,<br>L'équipe Phi Wallet</p>
    </div>

    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">Date de l'événement :</p>
        <p><span style="color: #48596b; font-weight: 500;">Date :</span><br>""" + computeDateTime(context.getTimeZone()) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" +
            ((context.getDevice() == null || context.getDevice().isEmpty()) ? "" : ("Appareil :</span><br>" + context.getDevice())) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" +
            ((context.getLocation() == null || context.getLocation().isEmpty()) ? "" : ("Localisation approximative :</span><br>" + context.getLocation())) + """</p>
    </div>

    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: flex-start;">
        <div>
            <img src="https://phiwallet.com/components/images/logo.png" alt="Logo Phi" style="height: 40px;">
        </div>
    </div>
</div>
        """;

        return Map.of(
            "subject", "Votre nom d'utilisateur a été créé avec succès",
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
