package pl.edu.pg.eti.kask.app.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Jakarta App")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/JakartaAppRecipes",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where login = ?)"/*,
        hashAlgorithm = Pbkdf2PasswordHash.class*/
)
public class AuthenticationConfig {
}
