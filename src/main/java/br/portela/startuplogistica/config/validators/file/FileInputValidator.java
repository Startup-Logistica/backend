package br.portela.startuplogistica.config.validators.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class FileInputValidator implements ConstraintValidator<File, MultipartFile> {
    private String[] mimeTypes;
    private String[] extensions;

    @Override
    public void initialize(File fileAnnotation) {
        this.mimeTypes = fileAnnotation.mimeTypes();
        this.extensions = fileAnnotation.extensions();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (Objects.isNull(multipartFile))
            return true;

        var isValidMimeType = validateMimeType(mimeTypes, multipartFile.getContentType());
        var isValidExtension = validateExtension(extensions, multipartFile.getOriginalFilename());

        if (!isValidExtension) {
            var message = "Allowed extensions: " + String.join(", ", extensions);
            setCustomMessage(context, message);
            return false;
        }

        if (!isValidMimeType) {
            var message = "Allowed types: " + String.join(", ", mimeTypes);
            setCustomMessage(context, message);
            return false;
        }

        return true;
    }

    private Boolean validateExtension(String[] supportedExtensions, String filename) {
        if (Arrays.asList(supportedExtensions).contains("*"))
            return true;

        var fileExtension = StringUtils.getFilenameExtension(filename);
        for (var extension : supportedExtensions) {
            extension = extension.startsWith(".") ? extension.substring(1) : extension;
            extension = "^" + extension.replace("*", ".*") + "$";
            if (Pattern.matches(extension, fileExtension))
                return true;
        }
        return false;
    }

    private Boolean validateMimeType(String[] supportedMimeTypes, String fileMimeType) {
        if (Arrays.asList(supportedMimeTypes).contains("*"))
            return true;

        for (var mimeTypeRegex : supportedMimeTypes) {
            mimeTypeRegex = "^" + mimeTypeRegex.replace("*", ".*") + "$";
            if (Pattern.matches(mimeTypeRegex, fileMimeType))
                return true;
        }
        return false;
    }

    private void setCustomMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        var customMessage = "Invalid file. " + message;
        context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
    }
}
