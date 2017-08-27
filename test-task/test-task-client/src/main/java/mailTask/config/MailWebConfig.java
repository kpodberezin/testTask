package mailTask.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:mail.properties")
public interface MailWebConfig extends Config {

    @Key("mail.url")
    String url();
}