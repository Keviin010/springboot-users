package com.springboot.springmvc.app.services;

import com.springboot.springmvc.app.entities.User;
import com.springboot.springmvc.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicelmplService implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) this.repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public User saver(User user) {
        return repository.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
    repository.deleteById(id);
    }
}
