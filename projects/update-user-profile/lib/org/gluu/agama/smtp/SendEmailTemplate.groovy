package org.gluu.agama.smtp

import groovy.lang.Binding
import groovy.lang.GroovyShell
import java.nio.file.Files
import java.nio.file.Paths

class SendEmailTemplate {

    private static File getTemplateFile(String templateName, String lang) {
        String fileLang = lang?.toLowerCase() ?: "en"
        String fileName = "${templateName}_${fileLang}.groovy"

        // Locate the directory of this class file
        File baseDir = new File(SendEmailTemplate.protectionDomain.codeSource.location.path)
        if (baseDir.isFile()) {
            baseDir = baseDir.getParentFile()
        }

        File templateFile = new File(baseDir, fileName)
        if (!templateFile.exists()) {
            // fallback to English
            templateFile = new File(baseDir, "${templateName}_en.groovy")
        }
        return templateFile
    }

    static String get(String templateName, String username, String givenName, String lang) {
        File templateFile = getTemplateFile(templateName, lang)

        def binding = new Binding([
            username : username,
            givenName: givenName
            timeZone  : context?.timeZone ?: "UTC"
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
