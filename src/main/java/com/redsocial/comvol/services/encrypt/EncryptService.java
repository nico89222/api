package com.redsocial.comvol.services.encrypt;

public interface EncryptService {

    String encryptPassword(String password);

    boolean verifyPassword(String original, String hashPassword);
}
