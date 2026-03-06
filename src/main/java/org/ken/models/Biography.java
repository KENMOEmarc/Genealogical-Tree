package org.ken.models;

import java.util.*;

public record Biography(
        List<Diploma> diplomas,
        List<String> jobs,
        List<String> associations,
        String birthPlace,
        List<Map<String, String>> socialMediaAccounts
) {
    public Biography {
        Objects.requireNonNull(diplomas, "Diplomas map must not be null");
        Objects.requireNonNull(jobs, "Jobs list must not be null");
        Objects.requireNonNull(associations, "Associations list must not be null");
        Objects.requireNonNull(socialMediaAccounts, "Social media list must not be null");

        // Create immutable copies
        diplomas = List.copyOf(diplomas);
        jobs = List.copyOf(jobs);
        associations = List.copyOf(associations);
        socialMediaAccounts = List.copyOf(socialMediaAccounts);
    }

    public Biography(List<Diploma> diplomas, List<String> jobs,
                     List<String> associations, List<Map<String, String>> socialMediaAccounts) {
        this(diplomas, jobs, associations, null,  socialMediaAccounts);
    }
}
