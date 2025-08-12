package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateEs {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="ltr" lang="es" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Logo at top -->
    <div style="text-align: center; padding: 20px 0;">
        <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
    </div>
    <hr style="border: none; border-top: 1px solid #ccc; margin: 0 0 20px 0;">
    <div style="padding: 12px; border-bottom: 1px solid #ccc;">
        <p>Hola,</p>
        <p>¡Felicidades! Tu nombre de usuario ha sido creado con éxito.</p>

        <p>Nombre de usuario: <span style="font-weight: bold;">""" + username + """</span></p>

        <p>Ahora puedes usar tu nombre de usuario en lugar de tu correo electrónico para iniciar sesión, haciendo que tu experiencia sea más fluida y segura.</p>

        <p><span style="font-weight: bold;">¡Pero eso no es todo!</span></p>

        <p>No solo estamos mejorando tu forma de iniciar sesión, sino que estamos preparando tu cuenta para nuevas funciones diseñadas para impulsar tu camino hacia un futuro financiero más próspero.</p>
        <p>¡Lo mejor está por venir!</p>
        <p>Mientras tanto, si tienes alguna pregunta o necesitas asistencia, estamos a un clic de distancia.</p>
        <p>Un saludo cordial,<br>Equipo de Phi Wallet</p>
    </div>

    
</div>
        """;

        return Map.of(
            "subject", "Tu nombre de usuario ha sido creado correctamente",
            "body", html
        );
    }

    
}
