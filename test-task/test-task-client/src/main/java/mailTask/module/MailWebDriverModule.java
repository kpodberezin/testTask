package mailTask.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import common.selenide.SelenideModule;
import mailTask.config.MailWebConfig;
import org.aeonbits.owner.ConfigFactory;

public class MailWebDriverModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new SelenideModule(provideMailWebConfig().url()));
    }

    @Provides
    @Singleton
    public MailWebConfig provideMailWebConfig() {
        return ConfigFactory.newInstance().create(MailWebConfig.class);
    }
}
