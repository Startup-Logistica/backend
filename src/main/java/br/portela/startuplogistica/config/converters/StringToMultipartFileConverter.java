package br.portela.startuplogistica.config.converters;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class StringToMultipartFileConverter implements Converter<String, MultipartFile> {

    @Override
    public MultipartFile convert(@NonNull String source) {
        return null;
    }
}
