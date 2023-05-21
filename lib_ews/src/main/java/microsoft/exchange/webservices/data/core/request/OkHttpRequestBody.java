package microsoft.exchange.webservices.data.core.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class OkHttpRequestBody extends RequestBody {
    private ByteArrayOutputStream os = null;

    public OkHttpRequestBody(OutputStream os) {
        super();
        this.os = (ByteArrayOutputStream) os;
    }

    @Override
    public long contentLength() throws IOException {
        return os.size();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse("text/xml; charset=utf-8");
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        os.writeTo(bufferedSink.outputStream());
    }

}
