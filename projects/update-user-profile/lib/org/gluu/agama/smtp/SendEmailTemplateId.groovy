package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateId {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="ltr" lang="id" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">

    <!-- Logo at top -->
    <div style="text-align: center; padding: 20px 0;">
        <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
    </div>
    <hr style="border: none; border-top: 1px solid #ccc; margin: 0 0 20px 0;">

    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p>Halo,</p>
        <p>Selamat! Nama pengguna Anda telah berhasil dibuat.</p>

        <p>Nama pengguna: <span style="font-weight: bold;">""" + username + """</span></p>

        <p>Sekarang Anda dapat menggunakan nama pengguna Anda sebagai pengganti alamat email untuk masuk, membuat pengalaman login lebih cepat dan aman.</p>
        <p><strong>Tapi itu belum semuanya!</strong></p>
        <p>Kami tidak hanya meningkatkan cara Anda masuk, tetapi juga mempersiapkan fitur-fitur menarik yang akan membantu perjalanan Anda menuju masa depan keuangan yang lebih baik.</p>
        <p>Nantikan, yang terbaik masih akan datang!</p>
        <p>Sementara itu, jika Anda memiliki pertanyaan atau memerlukan bantuan, kami siap membantu Anda.</p>
        <p>Salam hangat,<br>Tim Phi Wallet</p>
    </div>

</div>
        """;

        return Map.of(
            "subject", "Nama pengguna Anda telah berhasil dibuat",
            "body", html
        );
    }

}
