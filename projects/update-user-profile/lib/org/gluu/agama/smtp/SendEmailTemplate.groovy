package org.gluu.agama.smtp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.gluu.agama.smtp.jans.model.ContextData;

class SendEmailTemplate {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mma (O)");

    static String get(String username, ContextData context, Map<String, String> bundle) {
        String body = bundle.getOrDefault("body", "");
        String footer = bundle.getOrDefault("footer", "");
        String dateLabel = bundle.getOrDefault("dateLabel", "When this happened:");

        return String.format("""
<div style="width: 640px; font-size: 18px; font-family: 'Roboto', sans-serif; font-weight: 300; color: #333;">

    <!-- Main Content -->
    <div style="padding: 20px; border-bottom: 1px solid #ccc;">
        <p><b>Hi,</b><br><br>%s</p>

        <div style="display: flex; justify-content: center; margin: 20px 0;">
            <div style="background-color: #B29163; color: white; font-size: 30px; font-weight: 500; padding: 10px 20px; border-radius: 8px;" align="center">
                %s
            </div>
        </div>

        <p style="font-size: 14px;">%s</p>
    </div>

    <!-- Date Section -->
    <div style="padding: 12px; background-color: #ecf0f5; font-size: 16px;">
        <p style="color: #48596b; font-weight: 500;">%s</p>
        <p><span style="color: #48596b; font-weight: 500;">Date:</span><br>%s</p>
    </div>

    <!-- Contact Us Section -->
    <div style="background-color: #f9f9f9; padding: 20px; font-size: 14px; display: flex; justify-content: space-between; align-items: flex-start;">
        <div style="flex: 1;">
            <img src="https://phiwallet.com/components/images/logo.png" alt="Phi Logo" style="height: 40px;">
        </div>
    </div>
</div>
""", body, username, footer, dateLabel, computeDateTime(context.getTimeZone()));
    }

    private static String computeDateTime(String zone) {
        Instant now = Instant.now();
        try {
            return now.atZone(ZoneId.of(zone)).format(FORMATTER);
        } catch (Exception e) {
            return now.atOffset(ZoneOffset.UTC).format(FORMATTER);
        }
    }
}
