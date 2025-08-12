package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.gluu.agama.smtp.jans.model.ContextData;
import java.util.Map;

class SendEmailTemplateAr {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mma (O)");

    static Map<String, String> get(String username, ContextData context) {

        String html = """
<div dir="rtl" lang="ar" style="width: 640px; font-size: 18px; font-family: Arial, 'Segoe UI', Tahoma, sans-serif; font-weight: 300; color: #333; text-align: right;">
    <div style="padding: 12px; border-bottom: 1px solid #ccc;">
        <p>مرحباً،</p>
        <p>خبر رائع! تم إنشاء اسم المستخدم الخاص بك بنجاح.</p>
        <p>اسم المستخدم: <b>""" + username + """</b></p>
        <p>يمكنك الآن استخدام اسم المستخدم بدلاً من بريدك الإلكتروني لتسجيل الدخول، مما يجعل تجربتك أكثر سلاسة وأمانًا.</p>
        <p><b>لكن هذا ليس كل شيء!</b></p>
        <p>نحن لا نقوم فقط بتحسين طريقة تسجيل الدخول لديك، بل نُعِد حسابك لميزات جديدة في الطريق، مصممة لدعم رحلتك نحو مستقبل مالي أكثر ازدهارًا.</p>
        <p>إذا كانت لديك أي أسئلة، لا تتردد في التواصل معنا.</p>
        <p>مع أطيب التحيات،<br>فريق Phi Wallet</p>
    </div>

    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">وقت حدوث ذلك:</p>
        <p><span style="color: #48596b; font-weight: 500;">التاريخ:</span><br>""" + computeDateTime(context.getTimeZone()) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" + 
            ((context.getDevice() == null || context.getDevice().isEmpty()) ? "" : ("الجهاز:</span><br>" + context.getDevice())) + """</p>
        <p><span style="color: #48596b; font-weight: 500;">""" +
            ((context.getLocation() == null || context.getLocation().isEmpty()) ? "" : ("الموقع التقريبي:</span><br>" + context.getLocation())) + """</p>
    </div>

    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: flex-end;">
        <div>
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
        """;

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
