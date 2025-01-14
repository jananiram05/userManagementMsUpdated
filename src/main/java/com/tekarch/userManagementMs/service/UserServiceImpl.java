package com.tekarch.userManagementMs.service;

import com.tekarch.userManagementMs.model.Account;
import com.tekarch.userManagementMs.model.User;
import com.tekarch.userManagementMs.repositories.AccountRepository;
import com.tekarch.userManagementMs.repositories.UserRepository;
import com.tekarch.userManagementMs.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserServiceImpl(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public User createUser(User user) {
        if (user.getKycStatus() == null || user.getKycStatus().isBlank()) {
            user.setKycStatus("PENDING"); // Assign default
        }


        logger.info("Creating new user: {}", user.getUsername());
        user =userRepository.save(user);
        // Now, associate each account with the saved user
       if(null!=user.getAccounts()) {
           for (Account account : user.getAccounts()) {
               account.setUser(user);  // Set the user reference for each account
           }
           logger.info("createing new accounts :");
           accountRepository.saveAll(user.getAccounts());
       }
        // Save the accounts


        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user by id : {}", id);

        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser->{
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPasswordHash(user.getPasswordHash());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            return userRepository.save(existingUser);


        }).orElseThrow(()->{ return new RuntimeException("user not found with id: {} "+id);
        });
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user by id : {}",id);
        userRepository.deleteById(id);

    }

    @Override
    public User updateKYCStatus(Long id, String kycStatus) {
        logger.info("updating kyc status by id : {}",id);
        return userRepository.findById(id).map(user->{
            user.setKycStatus(kycStatus);
            return userRepository.save(user);
        }).orElseThrow(()->{return new RuntimeException("User not found with id : {}"+id);
        });
    }

    @Override
    public Account createAccount(Long userId, Account account) {
        logger.info("Creating a new account for user ID: {}", userId);
        return userRepository.findById(userId).map(user->{
            account.setUser(user);
            return accountRepository.save(account);
        }).orElseThrow(()->{return new RuntimeException("Accound not find by id: {}"+ userId);
        });


    }

    @Override
    public List<Account> getAccountByUserId(Long userId) {

        logger.info("Fetching accounts for user ID: {}", userId);

        return accountRepository.findByUserUserId(userId);
    }
}
