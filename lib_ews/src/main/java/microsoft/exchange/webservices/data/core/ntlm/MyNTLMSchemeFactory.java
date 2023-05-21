package microsoft.exchange.webservices.data.core.ntlm;

import org.apache.http.auth.AuthScheme;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class MyNTLMSchemeFactory extends NTLMSchemeFactory {

    @Override
    public AuthScheme newInstance(final HttpParams params) {
        return new MyNTLMScheme();
    }

    @Override
    public AuthScheme create(final HttpContext context) {
        return new MyNTLMScheme();
    }
}
