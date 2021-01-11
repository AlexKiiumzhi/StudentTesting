package alone.studenttesting.controller;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ParameterValidator {

    public boolean validateNullNumber(Long number) {
        if (number == null) {
            return false;
        }
        String number1 = Long.toString(number);
        if (number1.equals("")) {
            return false;
        }
        return true;
    }

}

