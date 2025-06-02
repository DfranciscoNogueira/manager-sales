package br.com.manager.util;

import java.util.Objects;

public class Constants {

    private Constants() {
        // Construtor privado para evitar instanciamento
    }

    public static final String PATH_ICONS = Objects.requireNonNull(Constants.class.getResource("/icons/")).getPath();
    public static final String PATH_IMAGES = Objects.requireNonNull(Constants.class.getResource("/image/")).getPath();
    public static final String SPACE_BLANK = " ";

}
