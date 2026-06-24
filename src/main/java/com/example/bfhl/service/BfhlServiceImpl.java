package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    // ---- Hardcoded identity fields ----
    private static final String USER_ID = "animesh_soni_27102004";
    private static final String EMAIL = "animeshsoni777@gmail.com";
    private static final String ROLL_NUMBER = "2310990011";

    @Override
    public BfhlResponse process(BfhlRequest request) {
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();

        BigInteger sum = BigInteger.ZERO;
        StringBuilder allAlphaChars = new StringBuilder();

        List<String> data = request.getData();
        if (data == null) {
            data = List.of();
        }

        for (String item : data) {
            if (item == null) {
                continue;
            }

            if (isNumeric(item)) {
                // Numeric token: classify odd/even, accumulate sum.
                BigInteger value = new BigInteger(item);
                sum = sum.add(value);

                BigInteger remainder = value.abs().mod(BigInteger.TWO);
                if (remainder.equals(BigInteger.ZERO)) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                // Alphabetic token: store as uppercase, collect chars for concat logic.
                alphabets.add(item.toUpperCase());
                allAlphaChars.append(item);
            } else {
                // Anything else (mixed/special characters).
                specialCharacters.add(item);
            }
        }

        String concatString = buildConcatString(allAlphaChars.toString());

        return new BfhlResponse(
                true,
                USER_ID,
                EMAIL,
                ROLL_NUMBER,
                oddNumbers,
                evenNumbers,
                alphabets,
                specialCharacters,
                sum.toString(),
                concatString
        );
    }

    /**
     * Builds the concatenation string: all alphabetic characters from the
     * input (pooled in original order), reversed, then alternating caps
     * starting with uppercase from the last character.
     */
    private String buildConcatString(String allAlphaChars) {
        if (allAlphaChars.isEmpty()) {
            return "";
        }

        String reversed = new StringBuilder(allAlphaChars).reverse().toString();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }

    private boolean isNumeric(String str) {
        if (str.isEmpty()) {
            return false;
        }
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isAlphabetic(String str) {
        if (str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
