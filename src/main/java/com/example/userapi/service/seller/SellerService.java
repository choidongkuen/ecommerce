package com.example.userapi.service.seller;

import com.example.userapi.domain.SignUpForm;
import com.example.userapi.domain.model.Seller;
import com.example.userapi.exception.CustomerException;
import com.example.userapi.exception.ErrorCode;
import com.example.userapi.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public Optional<Seller> findByIdAndEmail(Long id, String email){
        return sellerRepository.findById(id)
                               .filter(c -> c.getEmail().equals(email));
    }

    public Seller findValidSeller(String email, String password){
        return sellerRepository.findByEmail(email)
                               .filter(c -> c.getPassword().equals(password) && c.isVerify())
                               .orElseThrow(() -> new CustomerException(ErrorCode.LOGIN_CHECK_FAIL));
    }

    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email){
        return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                               .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code){
        Seller seller = sellerRepository.findByEmail(email)
                                        .orElseThrow(() -> new CustomerException(ErrorCode.USER_NOT_FOUND));

        if(seller.isVerify()){
            throw new CustomerException(ErrorCode.ALREADY_VERIFIED);
        }

        if(!seller.getVerificationCode().equals(code)){
            throw new CustomerException(ErrorCode.WRONG_VERIFICATION);
        }

        if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomerException(ErrorCode.EXPIRED_CODE);
        }

        seller.setVerify(true);
    }

    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode){
        Seller seller = sellerRepository.findById(sellerId)
                                        .orElseThrow(() -> new CustomerException(ErrorCode.USER_NOT_FOUND));

        seller.setVerificationCode(verificationCode);
        seller.setVerifyExpiredAt(LocalDateTime.now().plusMinutes(20));

        return seller.getVerifyExpiredAt();
    }

}