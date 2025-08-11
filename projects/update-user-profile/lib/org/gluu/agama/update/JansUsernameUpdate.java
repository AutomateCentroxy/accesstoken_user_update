package org.gluu.agama.update;

import io.jans.as.common.model.common.User;
import io.jans.as.common.service.common.EncryptionService;
import io.jans.as.common.service.common.UserService;
import io.jans.orm.exception.operation.EntryNotFoundException;
import io.jans.service.cdi.util.CdiUtil;
import io.jans.util.StringHelper;

import org.gluu.agama.user.UsernameUpdate;
import io.jans.agama.engine.script.LogUtils;
import java.io.IOException;
import io.jans.as.common.service.common.ConfigurationService;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.regex.Pattern;
import org.gluu.agama.smtp.SendEmailTemplate;
import org.gluu.agama.smtp.jans.model.ContextData;
import io.jans.model.SmtpConfiguration;
import io.jans.service.MailService;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jans.as.server.service.token.TokenService;
import io.jans.as.server.model.common.AuthorizationGrant;
import io.jans.as.server.model.common.AuthorizationGrantList;
import io.jans.as.server.model.common.AbstractToken;

public class JansUsernameUpdate extends UsernameUpdate {

    private static final String MAIL = "mail";
    private static final String UID = "uid";
    private static final String DISPLAY_NAME = "displayName";
    private static final String GIVEN_NAME = "givenName";
    private static final String LAST_NAME = "sn";
    private static final String PASSWORD = "userPassword";
    private static final String INUM_ATTR = "inum";
    private static final String EXT_ATTR = "jansExtUid";
    private static final String USER_STATUS = "jansStatus";
    private static final String EXT_UID_PREFIX = "github:";
    private static final String LANG = "lang";
    private static final SecureRandom RAND = new SecureRandom();

    private static JansUsernameUpdate INSTANCE = null;

    public JansUsernameUpdate() {
    }

    public static synchronized JansUsernameUpdate getInstance() {
        if (INSTANCE == null)
            INSTANCE = new JansUsernameUpdate();

        return INSTANCE;
    }

    // validate token starts here
    public static Map<String, Object> validateBearerToken(String access_token) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (access_token == null || access_token.trim().isEmpty()) {
                result.put("valid", false);
                result.put("error", "Access token is missing");
                return result;
            }

            // Get AuthorizationGrantList service
            AuthorizationGrantList authorizationGrantList = CdiUtil.bean(AuthorizationGrantList.class);
            if (authorizationGrantList == null) {
                result.put("valid", false);
                result.put("error", "Service not available");
                return result;
            }

            // Get the grant for this token
            AuthorizationGrant grant = authorizationGrantList.getAuthorizationGrantByAccessToken(access_token.trim());

            if (grant == null) {
                // Token not found
                result.put("valid", false);
                result.put("error", "Access token is invalid or expired");
                return result;
            }

            // Get the actual token object to check if it's valid (not expired)
            AbstractToken tokenObject = grant.getAccessToken(access_token.trim());

            // Check if token is active (exists and is valid)
            boolean isActive = tokenObject != null && tokenObject.isValid();

            if (isActive) {
                result.put("valid", true);
            } else {
                result.put("valid", false);
                result.put("error", "Access token is invalid or expired");
            }

        } catch (Exception e) {
            result.put("valid", false);
            result.put("error", "Access token is invalid or expired");
        }

        return result;
    }

    // validate token ends here

    public boolean passwordPolicyMatch(String userPassword) {
        String regex = '''^(?=.*[!@#$^&*])[A-Za-z0-9!@#$^&*]{6,}$'''
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(userPassword).matches();
    }

    public boolean usernamePolicyMatch(String userName) {
        // Regex: Only alphabets (uppercase and lowercase), minimum 1 character
        String regex = '''^[A-Za-z]+$''';
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(userName).matches();
    }

    public Map<String, String> getUserEntityByMail(String email) {
        User user = getUser(MAIL, email);
        boolean local = user != null;
        LogUtils.log("There is % local account for %", local ? "a" : "no", email);

        if (local) {
            String uid = getSingleValuedAttr(user, UID);
            String inum = getSingleValuedAttr(user, INUM_ATTR);
            String name = getSingleValuedAttr(user, GIVEN_NAME);

            if (name == null) {
                name = getSingleValuedAttr(user, DISPLAY_NAME);
                if (name == null && email != null && email.contains("@")) {
                    name = email.substring(0, email.indexOf("@"));
                }
            }

            // Creating a truly modifiable map
            Map<String, String> userMap = new HashMap<>();
            userMap.put(UID, uid);
            userMap.put(INUM_ATTR, inum);
            userMap.put("name", name);
            userMap.put("email", email);

            return userMap;
        }

        return new HashMap<>();
    }

    public Map<String, String> getUserEntityByUsername(String username) {
        User user = getUser(UID, username);
        boolean local = user != null;
        LogUtils.log("There is % local account for %", local ? "a" : "no", username);

        if (local) {
            String email = getSingleValuedAttr(user, MAIL);
            String inum = getSingleValuedAttr(user, INUM_ATTR);
            String name = getSingleValuedAttr(user, GIVEN_NAME);
            String uid = getSingleValuedAttr(user, UID); // Define uid properly
            String displayName = getSingleValuedAttr(user, DISPLAY_NAME);
            String givenName = getSingleValuedAttr(user, GIVEN_NAME);
            String sn = getSingleValuedAttr(user, LAST_NAME);
            String lang = getSingleValuedAttr(user, LANG);

            if (name == null) {
                name = getSingleValuedAttr(user, DISPLAY_NAME);
                if (name == null && email != null && email.contains("@")) {
                    name = email.substring(0, email.indexOf("@"));
                }
            }
            // Creating a modifiable HashMap directly
            Map<String, String> userMap = new HashMap<>();
            userMap.put(UID, uid);
            userMap.put(INUM_ATTR, inum);
            userMap.put("name", name);
            userMap.put("email", email);
            userMap.put(DISPLAY_NAME, displayName);
            userMap.put(LAST_NAME, sn);
            userMap.put(LANG, lang);

            return userMap;
        }

        return new HashMap<>();
    }

    public String addNewUser(Map<String, String> profile) throws Exception {
        Set<String> attributes = Set.of("uid", "mail", "displayName", "givenName", "sn", "userPassword");
        User user = new User();

        attributes.forEach(attr -> {
            String val = profile.get(attr);
            if (StringHelper.isNotEmpty(val)) {
                user.setAttribute(attr, val);
            }
        });

        UserService userService = CdiUtil.bean(UserService.class);
        user = userService.addUser(user, true); // Set user status active

        if (user == null) {
            throw new EntryNotFoundException("Added user not found");
        }

        return getSingleValuedAttr(user, INUM_ATTR);
    }

    public String updateUser(Map<String, String> profile) throws Exception {
        String inum = profile.get(INUM_ATTR);
        User user = getUser(INUM_ATTR, inum);

        if (user == null) {
            throw new EntryNotFoundException("User not found for inum: " + inum);
        }

        // 🔒 Preserve current email and lang
        String currentEmail = getSingleValuedAttr(user, MAIL);
        String currentLanguage = getSingleValuedAttr(user, LANG);

        // ✅ Update UID if provided
        String newUid = profile.get(UID);
        if (StringHelper.isNotEmpty(newUid)) {
            user.setAttribute(UID, newUid);
            user.setUserId(newUid);
        }

        // ✅ Always preserve email and lang
        if (StringHelper.isNotEmpty(currentEmail)) {
            user.setAttribute(MAIL, currentEmail);
        }
        if (StringHelper.isNotEmpty(currentLanguage)) {
            user.setAttribute(LANG, currentLanguage);
        }

        // ✅ Save the user
        UserService userService = CdiUtil.bean(UserService.class);
        user = userService.updateUser(user);

        if (user == null) {
            throw new EntryNotFoundException("Updated user not found");
        }

        return getSingleValuedAttr(user, INUM_ATTR);
    }

    public Map<String, String> getUserEntityByInum(String inum) {
        User user = getUser(INUM_ATTR, inum);
        boolean local = user != null;
        LogUtils.log("There is % local account for %", local ? "a" : "no", inum);

        if (local) {
            String email = getSingleValuedAttr(user, MAIL);
            // String inum = getSingleValuedAttr(user, INUM_ATTR);
            String name = getSingleValuedAttr(user, GIVEN_NAME);
            String uid = getSingleValuedAttr(user, UID); // Define uid properly
            String displayName = getSingleValuedAttr(user, DISPLAY_NAME);
            String givenName = getSingleValuedAttr(user, GIVEN_NAME);
            String sn = getSingleValuedAttr(user, LAST_NAME);
            String userPassword = getSingleValuedAttr(user, PASSWORD);
            String lang = getSingleValuedAttr(user, LANG);

            if (name == null) {
                name = getSingleValuedAttr(user, DISPLAY_NAME);
                if (name == null && email != null && email.contains("@")) {
                    name = email.substring(0, email.indexOf("@"));
                }
            }
            // Creating a modifiable HashMap directly
            Map<String, String> userMap = new HashMap<>();
            userMap.put(UID, uid);
            userMap.put("userId", uid);
            userMap.put(INUM_ATTR, inum);
            userMap.put("name", name);
            userMap.put("email", email);
            userMap.put(DISPLAY_NAME, displayName);
            userMap.put(LAST_NAME, sn);
            userMap.put(PASSWORD, userPassword);
            userMap.put(LANG, lang);

            return userMap;
        }

        return new HashMap<>();
    }

    private String getSingleValuedAttr(User user, String attribute) {
        Object value = null;
        if (attribute.equals(UID)) {
            // user.getAttribute("uid", true, false) always returns null :(
            value = user.getUserId();
        } else {
            value = user.getAttribute(attribute, true, false);
        }
        return value == null ? null : value.toString();

    }

    private User getUser(String attributeName, String value) {
        UserService userService = CdiUtil.bean(UserService.class);
        return userService.getUserByAttribute(attributeName, value, true);
    }

    public boolean sendUsernameUpdateEmail(String to, String newUsername, String lang, String givenName) {
        try {
            // Fetch SMTP configuration
            ConfigurationService configService = CdiUtil.bean(ConfigurationService.class);
            SmtpConfiguration smtpConfig = configService.getConfiguration().getSmtpConfiguration();

            if (smtpConfig == null) {
                LogUtils.log("SMTP configuration is missing.");
                return false;
            }

            // Use preferred lang from Agama directly
            String preferredLang = (lang != null && !lang.isEmpty())
                    ? lang.toLowerCase()
                    : "en"; // fallback to English

            // Hardcoded translations with detailed multi-line body, no footer
            Map<String, Map<String, String>> translations = new HashMap<>();

            translations.put("en", Map.of(
                    "subject", "Your username has been successfully created",
                    "body", String.format(
                            "Dear %s,\n" +
                                    "Congratulations! Your username has been successfully created 🔒\n" +
                                    "Username: %s\n" +
                                    "You can now use your username instead of your email address to sign in, making your login experience smoother and more secure.\n"
                                    +
                                    "But that’s not all!\n" +
                                    "We’re not just upgrading how you log in, we’re setting the stage for something exciting. Powerful new features are on the way, designed to help boost your journey toward a more prosperous financial future.\n"
                                    +
                                    "Stay tuned, the best is yet to come!\n" +
                                    "In the meantime, if you have any questions or need support, we’re just a click away.\n"
                                    +
                                    "Kind regards,\n" +
                                    "Phi Wallet Team",
                            (givenName != null && !givenName.isEmpty()) ? givenName : "user", newUsername)));

            translations.put("ar", Map.of(
                    "subject", "تم إنشاء اسم المستخدم الخاص بك بنجاح",
                    "body", String.format(
                            "<div dir=\"rtl\" lang=\"ar\" style=\"text-align: right; font-family: Arial, 'Segoe UI', Tahoma, sans-serif;\">"
                                    +
                                    "مرحباً %s,<br>" +
                                    "🔒 خبر رائع! تم إنشاء اسم المستخدم الخاص بك بنجاح<br>" +
                                    "اسم المستخدم: %s<br>" +
                                    "يمكنك الآن استخدام اسم المستخدم بدلاً من بريدك الإلكتروني لتسجيل الدخول، مما يجعل تجربتك أكثر سلاسة وأمانًا.<br>"
                                    +
                                    "لكن هذا ليس كل شيء!<br>" +
                                    "نحن لا نقوم فقط بتحسين طريقة تسجيل الدخول لديك، بل نُعِد حسابك لميزات جديدة في الطريق، مصممة لدعم رحلتك نحو مستقبل مالي أكثر ازدهارًا.<br>"
                                    +
                                    "إذا كانت لديك أي أسئلة، لا تتردد في التواصل معنا.<br>" +
                                    "مع أطيب التحيات،<br>" +
                                    "فريق Phi Wallet" +
                                    "</div>",
                            (givenName != null) ? givenName : "", newUsername)));

            translations.put("es", Map.of(
                    "subject", "Tu nombre de usuario ha sido creado correctamente",
                    "body", String.format(
                            "Hola %s,\n" +
                                    "¡Felicidades! Tu nombre de usuario ha sido creado con éxito. 🔒\n" +
                                    "Nombre de usuario: %s\n" +
                                    "Ahora puedes usar tu nombre de usuario en lugar de tu correo electrónico para iniciar sesión, haciendo que tu experiencia sea más fluida y segura.\n"
                                    +
                                    "¡Pero eso no es todo!\n" +
                                    "No solo estamos mejorando tu forma de iniciar sesión, sino que estamos preparando tu cuenta para algo emocionante. Nuevas funciones diseñadas para impulsar tu camino hacia un futuro financiero más próspero.\n"
                                    +
                                    "¡Lo mejor está por venir!\n" +
                                    "Mientras tanto, si tienes alguna pregunta o necesitas asistencia, estamos a un clic de distancia.\n"
                                    +
                                    "Un saludo cordial,\n" +
                                    "Equipo de Phi Wallet",
                            (givenName != null && !givenName.isEmpty()) ? givenName : "usuario", newUsername)));

            translations.put("fr", Map.of(
                    "subject", "Votre nom d’utilisateur a été créé avec succès",
                    "body", String.format(
                            "Bonjour %s,\n" +
                                    "Félicitation! Votre nom d’utilisateur a été créé avec succès 🔒\n" +
                                    "Nom d’utilisateur : %s\n" +
                                    "Vous pouvez désormais utiliser votre nom d’utilisateur au lieu de votre adresse e-mail pour vous connecter, offrant ainsi une expérience plus fluide et sécurisée.\n"
                                    +
                                    "Mais ce n’est pas tout !\n" +
                                    "Nous ne faisons pas que simplifier votre connexion, nous préparons aussi quelque chose d’excitant. De nouvelles fonctionnalités arriveront bientôt, conçues pour booster votre parcours vers un avenir financier plus prospère.\n"
                                    +
                                    "Restez à l’écoute, le meilleur est à venir !\n" +
                                    "Entre-temps, si vous avez des questions ou besoin d’assistance, nous sommes à un clic de distance.\n"
                                    +
                                    "Cordialement,\n" +
                                    "L’équipe Phi Wallet",
                            (givenName != null && !givenName.isEmpty()) ? givenName : "utilisateur", newUsername)));

            translations.put("id", Map.of(
                    "subject", "Fitur baru! Klaim nama pengguna Anda hari ini",
                    "body", String.format(
                            "Halo %s,\n" +
                                    "Kami sedang meningkatkan pengalaman login Anda. Semuanya dimulai dengan membuat nama pengguna Anda.\n"
                                    +
                                    "Dengan mengatur nama pengguna, Anda akan mendapatkan akses masuk yang lebih cepat dan lebih aman. Ini juga akan mempersiapkan akun Anda untuk fitur-fitur baru yang akan segera hadir.\n"
                                    +
                                    "Anda hanya perlu melakukannya satu kali, dan prosesnya sangat cepat.\n" +
                                    "Tips: Lakukan sekarang untuk mengamankan nama pengguna favorit Anda sebelum digunakan oleh orang lain.\n"
                                    +
                                    "[CTA: Buka Aplikasi]\n" +
                                    "Butuh bantuan? Tim dukungan kami siap membantu Anda.\n" +
                                    "Salam hangat,\n" +
                                    "Tim Phi Wallet",
                            (givenName != null && !givenName.isEmpty()) ? givenName : "pengguna")));

            translations.put("pt", Map.of(
                    "subject", "O teu nome de utilizador foi criado com sucesso",
                    "body", String.format(
                            "Olá %s,\n" +
                                    "Parabéns! O teu nome de utilizador foi criado com sucesso. 🔒\n" +
                                    "Nome de utilizador: %s\n" +
                                    "Agora já podes usar o teu nome de utilizador em vez do e-mail para iniciares sessão, tornando a tua experiência mais simples e segura.\n"
                                    +
                                    "Mas isso não é tudo!\n" +
                                    "Não estamos apenas a melhorar a forma como inicias sessão, estamos a preparar o terreno para algo entusiasmante. Novas funcionalidades estão a caminho, desenhadas para impulsionar o teu percurso rumo a um futuro financeiro mais próspero.\n"
                                    +
                                    "O melhor ainda está para vir!\n" +
                                    "Entretanto, se tiveres alguma dúvida ou precisares de ajuda, estamos apenas a um clique de distância.\n"
                                    +
                                    "Com os melhores cumprimentos,\n" +
                                    "Equipa Phi Wallet",
                            (givenName != null && !givenName.isEmpty()) ? givenName : "utilizador", newUsername)));

            // Pick the right lang (fallback to English if missing)
            Map<String, String> bundle = translations.getOrDefault(preferredLang, translations.get("en"));

            // Build context data
            ContextData context = new ContextData();
            context.setDevice("Unknown");
            context.setLocation("Unknown");
            context.setTimeZone("UTC");

            // Prepare localized email content
            String htmlBody = SendEmailTemplate.get(newUsername, context, bundle);
            String subject = bundle.get("subject");
            String textBody = bundle.get("body");

            // Send signed email
            MailService mailService = CdiUtil.bean(MailService.class);
            boolean sent = mailService.sendMailSigned(
                    smtpConfig.getFromEmailAddress(),
                    smtpConfig.getFromName(),
                    to,
                    null,
                    subject,
                    textBody,
                    htmlBody);

            LogUtils.log("Localized username update email sent successfully to %", to);
            return sent;
        } catch (Exception e) {
            LogUtils.log("Failed to send username update email: %", e.getMessage());
            return false;
        }
    }

    // Helper method to fetch SMTP configuration
    private SmtpConfiguration getSmtpConfiguration() {
        ConfigurationService configurationService = CdiUtil.bean(ConfigurationService.class);
        return configurationService.getConfiguration().getSmtpConfiguration();
    }
}