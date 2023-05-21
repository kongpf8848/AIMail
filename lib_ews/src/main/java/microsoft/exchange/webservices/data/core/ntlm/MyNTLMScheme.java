package microsoft.exchange.webservices.data.core.ntlm;
import org.apache.http.impl.auth.NTLMScheme;

public class MyNTLMScheme extends NTLMScheme {

    public MyNTLMScheme() {
        super(new MyNTLMEngineImpl());
    }
}
