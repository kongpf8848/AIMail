package microsoft.exchange.webservices.data.core.ntlm;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.auth.NTLMEngineException;

import java.util.List;
import java.util.Locale;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class NTLMAuthenticator implements Authenticator {

    private final String domain;
    private final String username;
    private final String password;
    private final String workstation;
    private final MyNTLMEngineImpl engine = new MyNTLMEngineImpl();

    public NTLMAuthenticator(String username, String password, String domain, String workstation) {
        this.username = username;
        this.password = password;
        this.domain = !StringUtils.isEmpty(domain) ? domain.toUpperCase(Locale.ROOT) : "";
        this.workstation = !StringUtils.isEmpty(workstation) ? workstation.toUpperCase(Locale.ROOT) : "";
    }

    @Override
    public Request authenticate(Route route, Response response) {
        final List<String> WWWAuthenticate = response.headers().values("WWW-Authenticate");
        if (WWWAuthenticate.contains("NTLM")) {
            try {
                String ntlmMsg1 = engine.generateType1Msg(null, null);
                return response.request().newBuilder().header("Authorization", "NTLM " + ntlmMsg1).build();
            } catch (NTLMEngineException e) {
                e.printStackTrace();
            }
        }
        String ntlmMsg3 = null;
        try {
            ntlmMsg3 = engine.generateType3Msg(username, password, domain, workstation, WWWAuthenticate.get(0).substring(5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.request().newBuilder().header("Authorization", "NTLM " + ntlmMsg3).build();
    }
}
