package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateFr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="ltr" lang="fr" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Logo at top -->
    <div style="text-align: center; padding: 20px 0;">
        <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
    </div>
    <hr style="border: none; border-top: 1px solid #ccc; margin: 0 0 20px 0;">
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p>Bonjour,</p>
        <p>Félicitations ! Votre nom d'utilisateur a été créé avec succès </p>

        <p>Nom d'utilisateur : <span style="font-weight: bold;">""" + username + """</span></p>
        
        <p>Vous pouvez désormais utiliser votre nom d'utilisateur au lieu de votre adresse e-mail pour vous connecter, offrant ainsi une expérience plus fluide et sécurisée.</p>
        <p><b>Mais ce n'est pas tout !</b></p>
        <p>Nous ne faisons pas que simplifier votre connexion ; nous préparons aussi quelque chose d'excitant. De nouvelles fonctionnalités arriveront bientôt, conçues pour booster votre parcours vers un avenir financier plus prospère.</p>
        <p>Restez à l'écoute, le meilleur est à venir !</p>
        <p>Entre-temps, si vous avez des questions ou besoin d'assistance, nous sommes à un clic de distance.</p>
        <p>Cordialement,<br>L'équipe Phi Wallet</p>
    </div>



</div>
        """;

        return Map.of(
            "subject", "Votre nom d'utilisateur a été créé avec succès",
            "body", html
        );
    }

}
