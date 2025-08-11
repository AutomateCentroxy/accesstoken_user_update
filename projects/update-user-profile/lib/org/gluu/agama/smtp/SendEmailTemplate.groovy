package org.gluu.agama.smtp

import groovy.lang.Binding
import groovy.lang.GroovyShell
import org.gluu.agama.smtp.jans.model.ContextData

class SendEmailTemplate {

    private static String getTemplateContent(String templateName, String lang) {
    String fileLang = lang?.toLowerCase() ?: "en"
    String fileName = "${templateName}_${fileLang}.groovy"
    InputStream is = SendEmailTemplate.class.getResourceAsStream("/org/gluu/agama/smtp/" + fileName)
    if (is == null) {
        is = SendEmailTemplate.class.getResourceAsStream("/org/gluu/agama/smtp/${templateName}_en.groovy")
    }
    if (is == null) {
        throw new FileNotFoundException("Template file not found in classpath")
    }
    return is.text
}}

    static String get(String templateName, String username, String givenName, String lang, ContextData context) {
        File templateFile = getTemplateFile(templateName, lang)

        def binding = new Binding([
            username  : username,
            givenName : givenName,
            timeZone  : context?.timeZone
        ])

        def shell = new GroovyShell(binding)
        def templateMap = shell.evaluate(templateFile)

        return """
        <html>
            <head><meta charset="UTF-8"></head>
            <body ${lang?.toLowerCase() == "ar" ? 'dir="rtl"' : ''}>
                ${templateMap.body}
            </body>
        </html>
        """
    }

    static String getSubject(String templateName, String lang) {
        File templateFile = getTemplateFile(templateName, lang)
        def shell = new GroovyShell()
        def templateMap = shell.evaluate(templateFile)
        return templateMap.subject
    }
}
