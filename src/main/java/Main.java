import org.apache.hc.core5.http.NameValuePair;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.classfile.constantpool.NameAndTypeEntry;
import java.nio.file.Files;
import java.util.List;

public class Main {
    static void main() {
        Server server = new Server();
        server.start();
    }
}
