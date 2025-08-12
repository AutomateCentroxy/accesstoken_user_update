package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplatePt {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
<p style="margin: 0; padding: 0;">OlÃ¡,</p>
<p style="margin: 0; padding: 0;">%s,</p>
<p style="margin: 0; padding: 0;">ParabÃ©ns! O teu nome de utilizador foi criado com sucesso. ð</p>
<p style="margin: 0; padding: 0;">Nome de utilizador: <b>%s</b></p>
<p style="margin: 0; padding: 0;">
    Agora jÃ¡ podes usar o teu nome de utilizador em vez do e-mail para iniciares sessÃ£o, 
    tornando a tua experiÃªncia mais simples e segura.
</p>
<p style="margin: 0; padding: 0;"><b>Mas isso nÃ£o Ã© tudo!</b></p>
<p style="margin: 0; padding: 0;">
    NÃ£o estamos apenas a melhorar a forma como inicias sessÃ£o, estamos a preparar o terreno para algo entusiasmante. 
    Novas funcionalidades estÃ£o a caminho, desenhadas para impulsionar o teu percurso rumo a um futuro financeiro mais prÃ³spero.
</p>
<p style="margin: 0; padding: 0;">O melhor ainda estÃ¡ para vir!</p>
<p style="margin: 0; padding: 0;">
    Entretanto, se tiveres alguma dÃºvida ou precisares de ajuda, estamos apenas a um clique de distÃ¢ncia.
</p>
<p style="margin: 0; padding: 0;">Com os melhores cumprimentos,<br>Equipa Phi Wallet</p>
""".formatted(
    (givenName != null ? givenName : "utilizador"),
    username
);

        String html = """
<div dir="ltr" lang="pt" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

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
    username,
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
