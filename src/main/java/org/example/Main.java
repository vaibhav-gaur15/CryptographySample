package org.example;
import com.google.crypto.tink.*;
import com.google.crypto.tink.hybrid.HybridConfig;
import com.google.crypto.tink.hybrid.PredefinedHybridParameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static void main(String[] args) throws Exception {
//        System.out.println("Hello, World!");
        if (args.length != 4 && args.length != 5) {
            System.err.printf("Expected 4 or 5 parameters, got %d\n", args.length);
            System.err.println(
                    "Usage: java HybridExample encrypt/decrypt key-file input-file output-file context-info");
            System.exit(1);
        }

        String mode = args[0];
        if (!mode.equals("encrypt") && !mode.equals("decrypt")) {
            System.err.println("Incorrect mode. Please select encrypt or decrypt.");
            System.exit(1);
        }
        Path keyFile = Paths.get(args[1]);
        Path inputFile = Paths.get(args[2]);
        byte[] input = Files.readAllBytes(inputFile);
        Path outputFile = Paths.get(args[3]);
        byte[] contextInfo = new byte[0];
        if (args.length == 5) {
            contextInfo = args[4].getBytes(UTF_8);
        }

        HybridConfig.register();

        KeysetHandle handle = TinkJsonProtoKeysetFormat.parseKeyset(
                new String(Files.readAllBytes(keyFile), UTF_8), InsecureSecretKeyAccess.get()
        );
        // alternative to tinkey

//        KeysetHandle handle2 = KeysetHandle.generateNew(PredefinedHybridParameters.
//                ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256);
//        String serializedKeyset = TinkJsonProtoKeysetFormat.serializeKeyset(handle2,InsecureSecretKeyAccess.get());

        // alternative to tinkey end -----
        if (mode.equals("encrypt")) {
            HybridEncrypt encyptor = handle.getPrimitive(RegistryConfiguration.get(), HybridEncrypt.class);
            byte[] cipherText = encyptor.encrypt(input, contextInfo);
            Files.write(outputFile, cipherText);
        } else {
            HybridDecrypt decryptor = handle.getPrimitive(RegistryConfiguration.get(), HybridDecrypt.class);
            byte[] plainText = decryptor.decrypt(input, contextInfo);
            Files.write(outputFile, plainText);
        }
    }

}