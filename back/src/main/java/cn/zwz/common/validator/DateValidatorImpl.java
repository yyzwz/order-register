package cn.zwz.common.validator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * @author 郑为中
 */
public class DateValidatorImpl implements ConstraintValidator<DateValidator, String> {

    private String dateFormat;

    @Override
    public void initialize(DateValidator constraintAnnotation) {

        // 获取时间格式
        this.dateFormat = constraintAnnotation.dateFormat();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (StrUtil.isBlank(value)) {
            return true;
        }
        try {
            Date date = DateUtil.parse(value, dateFormat);
            return date != null;
        } catch (Exception e) {
            return false;
        }
    }
}
