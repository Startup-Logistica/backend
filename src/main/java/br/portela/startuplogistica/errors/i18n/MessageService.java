package br.portela.startuplogistica.errors.i18n;

import br.portela.startuplogistica.errors.ExceptionCode;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {
    private final MessageSourceAccessor accessor;

    MessageService() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        accessor = new MessageSourceAccessor(messageSource, new Locale("pt", "BR"));
    }

    private String missingMessagePropertyEntry() {
        return accessor.getMessage("MISSING_MESSAGE_ENTRY");
    }

    public String get(ExceptionCode messageProp) {
        try {
            return accessor.getMessage(messageProp.toString());
        } catch (NoSuchMessageException e) {
            return missingMessagePropertyEntry();
        }
    }

    public String get(ExceptionCode messageProp, String... args) {
        try {
            return accessor.getMessage(messageProp.toString(), args);
        } catch (NoSuchMessageException e) {
            return missingMessagePropertyEntry();
        }
    }
}
