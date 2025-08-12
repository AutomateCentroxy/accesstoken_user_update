package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateId {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="ltr" lang="id" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: left;">
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p>Halo,</p>
        <p>Kami sedang meningkatkan pengalaman login Anda. Semuanya dimulai dengan membuat nama pengguna Anda.</p>
        <p>Dengan mengatur nama pengguna, Anda akan mendapatkan akses masuk yang lebih cepat dan lebih aman. Ini juga akan mempersiapkan akun Anda untuk fitur-fitur baru yang akan segera hadir.</p>
        <p>Anda hanya perlu melakukannya satu kali, dan prosesnya sangat cepat.</p>
        <p><b>Tips:</b> Lakukan sekarang untuk mengamankan nama pengguna favorit Anda sebelum digunakan oleh orang lain.</p>
        <p><a href="#" style="color: #ffffff; background-color: #007bff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;">Buka Aplikasi</a></p>
        <p>Butuh bantuan? Tim dukungan kami siap membantu Anda.</p>
        <p>Salam hangat,<br>Tim Phi Wallet</p>
    </div>

    

    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: flex-start;">
        <div>
            <img src="https://phiwallet.com/components/images/logo.png" alt="Logo Phi" style="height: 40px;">
        </div>
    </div>
</div>
        """;

        return Map.of(
            "subject", "Fitur baru! Klaim nama pengguna Anda hari ini",
            "body", html
        );
    }

}
