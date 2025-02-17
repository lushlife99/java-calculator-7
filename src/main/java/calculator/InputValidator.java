package calculator;

import calculator.enums.ErrorMessage;

import java.util.Objects;

public class InputValidator {


    private InputValidator(){}

    public static void validate(String input) {

        if (Objects.isNull(input))
            throw new IllegalArgumentException(ErrorMessage.INPUT_IS_NULL.getMessage());

        input = input.trim();
        String expression = input;

        if (input.startsWith("//")) {
            String[] tokens = input.split("\\\\n");
            validCustomSeparator(input);

            if (tokens.length == 1) // "//-\n" 로만 이루어진 문자열도 결과값을 출력해야함.
                return;

            expression = tokens[1];
        }

        validExpression(expression, CalculatorUtil.getSeparatorRegex(input));
    }

    private static void validCustomSeparator(String input) {

        String[] tokens = input.split("\\\\n");

        if (tokens.length > 2)
            throw new IllegalArgumentException(ErrorMessage.INVALID_CUSTOM_SEPARATOR_SYNTAX.getMessage());

        String separator = tokens[0].substring(2);
        if (separator.length() != 1)
            throw new IllegalArgumentException(ErrorMessage.INVALID_CUSTOM_SEPARATOR_LENGTH.getMessage());

        if (Character.isDigit(separator.charAt(0)))
            throw new IllegalArgumentException(ErrorMessage.CUSTOM_SEPARATOR_IS_NUMBER.getMessage());
    }

    private static void validExpression(String expression, String separatorRegex) {
        String[] numbers = expression.split(separatorRegex);

        for (String n : numbers) {
            if (n.length() == 0)
                continue;

            if (!n.matches("[0-9]+"))
                throw new IllegalArgumentException(ErrorMessage.INVALID_OPERAND.getMessage());

        }
    }
}