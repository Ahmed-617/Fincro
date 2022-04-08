package tn.microfinance.fincro.services.implementations;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.microfinance.fincro.dao.exceptions.EmailExistException;
import tn.microfinance.fincro.dao.exceptions.EmailNotFoundException;
import tn.microfinance.fincro.dao.exceptions.UserNotFoundException;
import tn.microfinance.fincro.dao.exceptions.UsernameExistException;
import tn.microfinance.fincro.dao.model.*;
import tn.microfinance.fincro.dao.repositories.UserRepository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import static tn.microfinance.fincro.dao.constant.FileConstant.*;
import static tn.microfinance.fincro.dao.constant.UserImplConstant.*;
import static tn.microfinance.fincro.dao.constant.UserImplConstant.DEFAULT_USER_IMAGE_PATH;
import static tn.microfinance.fincro.dao.model.Role.ROLE_CLIENT;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;
    private final SmsService smsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, LoginAttemptService loginAttemptService, EmailService emailService, SmsService smsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
        this.smsService = smsService;
    }

public User findUserById (Long id){
        return userRepository.findById(id).get();
}
    public void setScore(Long userId) throws UserNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->new UserNotFoundException("User Not Found"));

        int score = 0;
        Date now = new Date();
        long age = (now.getTime() - user.getBirthDate().getTime()) / (1000L *60*60*24*365);

        if (user.getGender().toLowerCase().equals("male")) {
            score = score + 1;
        } else {
            score = score + 2;
        }

        if(age >= 18 && age < 25){
        score=score+3;}
        else if (age >= 25 && age < 45){
        score=score+5;}
        else{
        score=score+2;}

        if ((user.getPersonalSituation() == PersonalSituation.MARRIED) || user.getPersonalSituation()== PersonalSituation.DIVORCED ){
            score=score+2;}
        else {
            score = score + 1;
        }
        if(user.getProfession().equals("student")){
            score=score+2;}
        else {
            score = score + 1;
        }

        if(user.getGuarantorSalary() >1000){
            score=score+2;}
        else if (user.getGuarantorSalary()>500 && user.getGuarantorSalary()<=1000){
        score=score+1;}

        user.setScore(score);
        userRepository.save(user);
    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public void matchUserSurplusRatio(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->new UserNotFoundException("User Not Found"));
        int score = user.getScore();
        String message ="";
        if(isBetween(score, 5, 8)){
            user.setSurplusRatio(0.07f);
            message = "Your current pack is 7%";
        }
        if(isBetween(score, 9, 12)) {
            user.setSurplusRatio(0.068f);
            message = "Your current pack is 6.8%";
        }
        if(score>= 13){
            user.setSurplusRatio(0.065f);
            message = "Your current pack is 6.5%";
        }
        userRepository.save(user);
        SmsRequest smsAlert = new SmsRequest(user.getPhoneNumber(),message);
        smsService.sendSms(smsAlert);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    public User register(String firstName, String lastName, String username, String email, String gender,String tel, String job, int salary, PersonalSituation personalSituation, Date birthDate) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_CLIENT.name());
        user.setAuthorities(ROLE_CLIENT.getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        user.setPhoneNumber(tel);
        user.setGender(gender);
        user.setProfession(job);
        user.setGuarantorSalary(salary);
        user.setBirthDate(birthDate);
        user.setPersonalSituation(personalSituation);
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        emailService.sendNewPasswordEmail(firstName, password, email);
        return user;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            if(userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.getUserId().equals(userByNewEmail.getUserId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }

    }


    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage, String gender,String tel, String job, int salary, PersonalSituation personalSituation, Date birthDate) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setJoinDate(new Date());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        user.setGender(gender);
        user.setProfession(job);
        user.setGuarantorSalary(salary);
        user.setPhoneNumber(tel);
        user.setPersonalSituation(personalSituation);
        user.setBirthDate(birthDate);
        userRepository.save(user);
        saveProfileImage(user, profileImage);
        LOGGER.info("New user password: " + password);
        return user;


    }

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
        if (profileImage !=null) {
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)){
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED);

            }
            Files.deleteIfExists(Paths.get(userFolder +user.getUsername()+DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() +DOT+ JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());

        userRepository.save(currentUser);
        saveProfileImage(currentUser, profileImage);
        //   LOGGER.info("New user password: " + password);
        return currentUser;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }

    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL +email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
    }


    public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, UsernameExistException, IOException {
        User user = validateNewUsernameAndEmail(username, null, null);
        saveProfileImage(user, profileImage);
        return user;
    }


    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
                + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }


    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }



}
