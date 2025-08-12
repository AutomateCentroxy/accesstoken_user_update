package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class SendEmailTemplateAr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mma (O)");

    static Map<String, String> get(String username) {

        String html = """
<div dir="rtl" lang="ar" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: right;">

    <!-- Logo at top -->
    <div style="text-align: center; padding: 20px 0;">
        <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
    </div>
    <hr style="border: none; border-top: 1px solid #ccc; margin: 0 0 20px 0;">


    <div style="padding: 12px; border-bottom: 1px solid #ccc;">
        <p>مرحباً،</p>
        <p>خبر رائع! تم إنشاء اسم المستخدم الخاص بك بنجاح.</p>

        <p>اسم المستخدم: <span style="font-weight: bold;">""" + username + """</span></p>
        
        <p>يمكنك الآن استخدام اسم المستخدم بدلاً من بريدك الإلكتروني لتسجيل الدخول، مما يجعل تجربتك أكثر سلاسة وأمانًا.</p>

        <p><span style="font-weight: bold;">لكن هذا ليس كل شيء!</span></p>

        <p>نحن لا نقوم فقط بتحسين طريقة تسجيل الدخول لديك، بل نُعِد حسابك لميزات جديدة في الطريق، مصممة لدعم رحلتك نحو مستقبل مالي أكثر ازدهارًا.</p>
        <p>إذا كانت لديك أي أسئلة، لا تتردد في التواصل معنا.</p>
        <p>مع أطيب التحيات،<br>فريق Phi Wallet</p>
    </div>

    
</div>
        """;

        return Map.of(
            "subject", "تم إنشاء اسم المستخدم الخاص بك بنجاح",
            "body", html
        );
    }
}
