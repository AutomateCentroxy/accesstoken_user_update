package org.gluu.agama.smtp

import groovy.lang.Binding
import groovy.lang.GroovyShell
import org.gluu.agama.smtp.jans.model.ContextData

class SendEmailTemplate {

    private static File getTemplateFile(String templateName, String lang) {
        String fileLang = lang?.toLowerCase() ?: "en"
        String fileName = "${templateName}_${fileLang}.groovy"

        File baseDir = new File(SendEmailTemplate.protectionDomain.codeSource.location.path)
        if (baseDir.isFile()) {
            baseDir = baseDir.getParentFile()
        }

        File templateFile = new File(baseDir, fileName)
        if (!templateFile.exists()) {
            templateFile = new File(baseDir, "${templateName}_en.groovy")
        }
        return templateFile
    }

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
