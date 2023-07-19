package org.quantum.rms.util;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;
import com.vaadin.flow.i18n.I18NProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Translator implements I18NProvider {

    private static final long serialVersionUID = 1L;
    private static final String BUNDLE_PREFIX = "translations";
    private List<Locale> locales = List.of(new Locale("ru", "RU"), new Locale("en", "GB"), new Locale("es", "ES"));

    @Override
    public List<Locale> getProvidedLocales() {
	return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
	if (Objects.isNull(key)) {
	    log.warn("Got lang request for null value key");
	    return "";
	}
	var bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
	String value = null;

	try {
	    value = bundle.getString(key);
	} catch (MissingResourceException e) {
	    log.warn("Missing resource: {}", e);
	    return "! %s: %s".formatted(locale.getLanguage(), key);
	}
	if (params.length > 0) {
	    value = MessageFormat.format(value, params);
	}
	return value;
    }

}
