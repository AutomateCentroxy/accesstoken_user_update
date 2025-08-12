package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplatePt {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
<p style="margin: 0; padding: 0;">Olá,</p>
<p style="margin: 0; padding: 0;">User,</p>
<p style="margin: 0; padding: 0;">Parabéns! O teu nome de utilizador foi criado com sucesso.</p>
<p style="margin: 0; padding: 0;">Nome de utilizador: <b>%s</b></p>
<p style="margin: 0; padding: 0;">
    Agora já podes usar o teu nome de utilizador em vez do e-mail para iniciares sessão, 
    tornando a tua experiência mais simples e segura.
</p>
<p style="margin: 0; padding: 0;"><b>Mas isso não é tudo!</b></p>
<p style="margin: 0; padding: 0;">
    Não estamos apenas a melhorar a forma como inicias sessão, estamos a preparar o terreno para algo entusiasmante. 
    Novas funcionalidades estão a caminho, desenhadas para impulsionar o teu percurso rumo a um futuro financeiro mais próspero.
</p>
<p style="margin: 0; padding: 0;">O melhor ainda está para vir!</p>
<p style="margin: 0; padding: 0;">
    Entretanto, se tiveres alguma dúvida ou precisares de ajuda, estamos apenas a um clique de distância.
</p>
<p style="margin: 0; padding: 0;">Com os melhores cumprimentos,<br>Equipa Phi Wallet</p>
""".formatted(
    (givenName != null ? givenName : "utilizador"),
    username
);

        String html = """
<div dir="ltr" lang="pt" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">Data do evento:</p>
        <p><span style="color: #48596b; font-weight: 500;">Data:</span><br>%s</p>
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
            "subject", "O teu nome de utilizador foi criado com sucesso",
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
