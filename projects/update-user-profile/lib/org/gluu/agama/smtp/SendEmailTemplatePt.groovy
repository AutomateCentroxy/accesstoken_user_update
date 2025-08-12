package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplatePt {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="ltr" lang="pt" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p>Olá,</p>
        <p>Parabéns! O teu nome de utilizador foi criado com sucesso.</p>
        <p>Nome de utilizador: <b>""" + username + """</b></p>
        <p>Agora já podes usar o teu nome de utilizador em vez do e-mail para iniciares sessão, tornando a tua experiência mais simples e segura.</p>
        <p><b>Mas isso não é tudo!</b></p>
        <p>Não estamos apenas a melhorar a forma como inicias sessão, estamos a preparar o terreno para algo entusiasmante. Novas funcionalidades estão a caminho, desenhadas para impulsionar o teu percurso rumo a um futuro financeiro mais próspero.</p>
        <p>O melhor ainda está para vir!</p>
        <p>Entretanto, se tiveres alguma dúvida ou precisares de ajuda, estamos apenas a um clique de distância.</p>
        <p>Com os melhores cumprimentos,<br>Equipa Phi Wallet</p>
    </div>

    

    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: flex-start;">
        <div>
            <img src="https://phiwallet.com/components/images/logo.png" alt="Logo Phi" style="height: 40px;">
        </div>
    </div>
</div>
        """;

        return Map.of(
            "subject", "O teu nome de utilizador foi criado com sucesso",
            "body", html
        );
    }

    
}
