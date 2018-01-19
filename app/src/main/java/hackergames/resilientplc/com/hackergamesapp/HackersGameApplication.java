package hackergames.resilientplc.com.hackergamesapp;

import android.app.Application;
import android.content.Context;

import hackergames.resilientplc.com.hackergamesapp.data.ChatProvider;
import hackergames.resilientplc.com.hackergamesapp.data.NetworkClient;
import hackergames.resilientplc.com.hackergamesapp.data.model.User;
import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;
import toothpick.configuration.Configuration;
import toothpick.registries.FactoryRegistryLocator;
import toothpick.registries.MemberInjectorRegistryLocator;
import toothpick.smoothie.FactoryRegistry;
import toothpick.smoothie.MemberInjectorRegistry;

/**
 * Created by eduar on 18/01/2018.
 */

public class HackersGameApplication extends Application {


    public static final String SCOPE = "scope";

    private static User user;

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static void initDependencies() {
        Toothpick.setConfiguration(Configuration.forProduction().disableReflection());
        MemberInjectorRegistryLocator.setRootRegistry(new MemberInjectorRegistry());
        FactoryRegistryLocator.setRootRegistry(new FactoryRegistry());

        Scope appScope = Toothpick.openScopes(SCOPE);
        appScope.installModules(new Module() {{
            bind(NetworkClient.class).toInstance(new NetworkClient(appContext, null));
            bind(ChatProvider.class).toInstance(new ChatProvider(appContext));
        }});

        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment());
            return;
        }

        Toothpick.setConfiguration(Configuration.forProduction());
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }
}
