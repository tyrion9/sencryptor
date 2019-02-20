# Sensitive Info Encryptor (sencryptor)

sencryptor is a bi-encryption utility for encrypt/decrypt sensitive information like configuration files.

Without any configuration, `decryptIfRequire` method return the same input value.

It's useful for development environment that is not need encryption.

```java
    @Test
    public void testNotRequire() {
        String encrypted = Sencryptor.getInstance().encryptIfRequire("Hello");

        Assert.assertEquals("Hello", encrypted);  // same value
    }
```  

Otherwise, specifing system property `sencryptorkeyfile` to key file (password to encrypt/decrypt) or default file name `sencryptor.key` in Working Directory, It will use that file to get key to encrypt/decrypt.

```java
    @Test
    public void testUseProperties() throws IOException {
        File f = folder.newFile("sencryptor.key");
        FileWriter w = new FileWriter(f);
        w.write(key);
        w.flush();  // create file with key (password)

        System.setProperty("sencryptorkeyfile", f.getAbsolutePath()); //set to system properties

        String encrypted = Sencryptor.getInstance().encryptIfRequire("Hello");

        Assert.assertNotEquals(data, encrypted);
        Assert.assertEquals(data, Sencryptor.getInstance().decryptIfRequire(encrypted));
    }
```