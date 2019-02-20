package vn.com.vetc.sencryptor;

import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SencryptorTest {
    String data = "Hello";
    String key = "vetctestsencrypt";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testUseDefaultKeyFile() throws IOException {
        File f = folder.newFile("sencryptor.key");
        FileWriter w = new FileWriter(f);
        w.write(key);
        w.flush();

        Sencryptor.DEFAULT_KEYFILE_NAME = f.getAbsolutePath();
        Sencryptor sen = Sencryptor.getInstance();

        String encrypted = sen.encryptIfRequire("Hello");

        Assert.assertNotEquals(data, encrypted);
        Assert.assertEquals(data, sen.decryptIfRequire(encrypted));
    }

    @Test
    public void testUseProperties() throws IOException {
        File f = folder.newFile("sencryptor.key");
        FileWriter w = new FileWriter(f);
        w.write(key);
        w.flush();

        System.setProperty("sencryptorkeyfile", f.getAbsolutePath());

        String encrypted = Sencryptor.getInstance().encryptIfRequire("Hello");

        Assert.assertNotEquals(data, encrypted);
        Assert.assertEquals(data, Sencryptor.getInstance().decryptIfRequire(encrypted));
    }

    @Test
    public void testNotRequire() {
        String encrypted = Sencryptor.getInstance().encryptIfRequire("Hello");

        Assert.assertEquals(data, encrypted);
    }
}
