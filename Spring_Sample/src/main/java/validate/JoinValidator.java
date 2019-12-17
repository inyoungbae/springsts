package validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vo.Member;

public class JoinValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("in validate");
		Member regReq = (Member) target;
		if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty())
			errors.rejectValue("email", "required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name을 입력해주세요");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "비밀번호를 입력해주세요");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birth", "생일을 입력해주세요");
	}

}
