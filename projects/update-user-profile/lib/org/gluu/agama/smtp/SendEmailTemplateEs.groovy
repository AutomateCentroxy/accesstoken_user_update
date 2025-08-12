package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;


class SendEmailTemplateEs {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContentEs = """
<p style="margin: 0; padding: 0;">Hola,</p>
<p style="margin: 0; padding: 0;">Estimado/a %s,</p>
<p style="margin: 0; padding: 0;">Â¡Felicidades! Tu nombre de usuario ha sido creado con Ã©xito. ð</p>
<p style="margin: 0; padding: 0;">Nombre de usuario: <b>%s</b></p>
<p style="margin: 0; padding: 0;">
    Ahora puedes usar tu nombre de usuario en lugar de tu correo electrÃ³nico para iniciar sesiÃ³n, haciendo que tu experiencia sea mÃ¡s fluida y segura.
</p>
<p style="margin: 0; padding: 0;"><b>Â¡Pero eso no es todo!</b></p>
<p style="margin: 0; padding: 0;">
    No solo estamos mejorando tu forma de iniciar sesiÃ³n, sino que estamos preparando tu cuenta para algo emocionante.
    Nuevas funciones diseÃ±adas para impulsar tu camino hacia un futuro financiero mÃ¡s prÃ³spero.
</p>
<p style="margin: 0; padding: 0;">Â¡Lo mejor estÃ¡ por venir!</p>
<p style="margin: 0; padding: 0;">
    Mientras tanto, si tienes alguna pregunta o necesitas asistencia, estamos a un clic de distancia.
</p>
<p style="margin: 0; padding: 0;">Un saludo cordial,<br>Equipo de Phi Wallet</p>
""".formatted(
    (givenName != null ? givenName : "usuario"),
    username
);


        String html = """
<div dir="ltr" lang="es" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Main Content -->
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p><b>Hola,</b><br><br>
        ${bodyContent}</p>

        <div style="display: flex; justify-content: center; margin: 20px 0;">
            <div style="background-color: #B29163; color: white; font-size: 30px; font-weight: 500; padding: 10px 20px; border-radius: 8px;" align="center">
                ${username}
            </div>
        </div>
    </div>

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">Fecha del evento:</p>
        <p><span style="color: #48596b; font-weight: 500;">Fecha:</span><br>${computeDateTime(context.getTimeZone())}</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
""";

        return Map.of(
            "subject", "Tu nombre de usuario ha sido creado correctamente",
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

