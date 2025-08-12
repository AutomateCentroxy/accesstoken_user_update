package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplateAr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mma (O)");

    static Map<String, String> get(String username, String givenName, ContextData context) {

        String bodyContent = """
<p style="margin: 0; padding: 0;">مرحباً User،</p>
<p style="margin: 0; padding: 0;"> خبر رائع! تم إنشاء اسم المستخدم الخاص بك بنجاح.</p>
<p style="margin: 0; padding: 0;">اسم المستخدم: <b>%s</b></p>
<p style="margin: 0; padding: 0;">
يمكنك الآن استخدام اسم المستخدم بدلاً من بريدك الإلكتروني لتسجيل الدخول، مما يجعل تجربتك أكثر سلاسة وأمانًا.
</p>
<p style="margin: 0; padding: 0;"><b>لكن هذا ليس كل شيء!</b></p>
<p style="margin: 0; padding: 0;">
نحن لا نقوم فقط بتحسين طريقة تسجيل الدخول لديك، بل نُعِد حسابك لميزات جديدة في الطريق، مصممة لدعم رحلتك نحو مستقبل مالي أكثر ازدهارًا.
</p>
<p style="margin: 0; padding: 0;">إذا كانت لديك أي أسئلة، لا تتردد في التواصل معنا.</p>
<p style="margin: 0; padding: 0;">مع أطيب التحيات،<br>فريق Phi Wallet</p>
""".formatted(
    (givenName != null ? givenName : ""),
    username
);

        String html = """
<div dir="rtl" lang="ar" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: right;">

    %s

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">وقت حدوث ذلك:</p>
        <p><span style="color: #48596b; font-weight: 500;">التاريخ:</span><br>%s</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
""".formatted(
    bodyContent,
    computeDateTime(context.getTimeZone())
);

        return Map.of(
            "subject", "تم إنشاء اسم المستخدم الخاص بك بنجاح",
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
