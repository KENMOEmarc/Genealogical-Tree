package org.ken.models;

import java.util.Objects;

public record Diploma(String title, String institution, String grade, int year) {
    public Diploma {
        Objects.requireNonNull(title, "Diploma title cannot be null");
        Objects.requireNonNull(institution, "Institution cannot be null");
        // grade can be null
    }
}