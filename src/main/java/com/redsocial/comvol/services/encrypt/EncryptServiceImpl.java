package com.redsocial.comvol.services.encrypt;

import com.redsocial.comvol.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements EncryptService{

    @Autowired
    PersonaRepository personaRepository;

    @Override
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public boolean verifyPassword(String original, String hashPassword) {

        return BCrypt.checkpw(original,hashPassword);
    }




}
