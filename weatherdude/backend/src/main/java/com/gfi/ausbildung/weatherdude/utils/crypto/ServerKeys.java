package com.gfi.ausbildung.weatherdude.utils.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Klasse für die Erzeugung des privaten und öffentlichen Serverschlüssels 
 */
@Component
@Slf4j
public class ServerKeys
{
  private ECPublicKey         publicKey;
  private byte[]              publicKeyUncompressed;
  private String              publicKeyBase64;
  private ECPrivateKey        privateKey;
  private final CryptoService cryptoService;

  @Value("${weatherdude.server.publickeypath}")
  private String              publicKeyPath;
  @Value("${weatherdude.server.privatekeypath}")
  private String              privateKeyPath;
  
  /**
   * Konstruktor.
   * @param cryptoService Übergabe der CryptoService Komponente
   */
  public ServerKeys(CryptoService cryptoService)
  {
    this.cryptoService = cryptoService;
  }

  /**
   * Liefert das byteArray des öffentlichen Schlüssels
   * @return
   */
  public byte[] getPublicKeyUncompressed()
  {
    return this.publicKeyUncompressed;
  }

  /**
   * Liefert den öffentlichen Schlüssel Base64 codiert
   * @return Base64 codierter öffentlicher Schlüssel
   */
  public String getPublicKeyBase64()
  {
    return this.publicKeyBase64;
  }

  /**
   * Liefert den privaten Schlüssel
   * @return ECPrivateKey aus dem java.security Kontext
   */
  public ECPrivateKey getPrivateKey()
  {
    return this.privateKey;
  }

  /**
   * Liefert den öffentlichen Schlüssel
   * @return ECPublicKey aus dem java.security Kontext
   */
  public ECPublicKey getPublicKey()
  {
    return this.publicKey;
  }

  /**
   * Erzeugung der Schlüssel im postConstruct von Spring Boot
   */
  @PostConstruct
  private void initKeys()
  {
    Path appServerPublicKeyFile = Paths.get(publicKeyPath);
    Path appServerPrivateKeyFile = Paths.get(privateKeyPath);

    if (Files.exists(appServerPublicKeyFile) && Files.exists(appServerPrivateKeyFile))
    {
      try
      {
        byte[] appServerPublicKey = Files.readAllBytes(appServerPublicKeyFile);
        byte[] appServerPrivateKey = Files.readAllBytes(appServerPrivateKeyFile);

        this.publicKey = (ECPublicKey) this.cryptoService.convertX509ToECPublicKey(appServerPublicKey);
        this.privateKey = (ECPrivateKey) this.cryptoService.convertPKCS8ToECPrivateKey(appServerPrivateKey);

        this.publicKeyUncompressed = CryptoService.toUncompressedECPublicKey(this.publicKey);

        this.publicKeyBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(this.publicKeyUncompressed);
      }
      catch (IOException | InvalidKeySpecException e)
      {
        log.error("read files", e);
      }
    }
    else
    {
      try
      {
        Files.createDirectories(appServerPublicKeyFile.getParent());
        
        KeyPair pair = this.cryptoService.getKeyPairGenerator().generateKeyPair();
        this.publicKey = (ECPublicKey) pair.getPublic();
        this.privateKey = (ECPrivateKey) pair.getPrivate();
        Files.write(appServerPublicKeyFile, this.publicKey.getEncoded(), StandardOpenOption.CREATE_NEW);
        Files.write(appServerPrivateKeyFile, this.privateKey.getEncoded(), StandardOpenOption.CREATE_NEW);

        this.publicKeyUncompressed = CryptoService.toUncompressedECPublicKey(this.publicKey);

        this.publicKeyBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(this.publicKeyUncompressed);
      }
      catch (IOException e)
      {
        log.error("write files", e);
      }
    }
  }
}
