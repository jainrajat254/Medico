package com.example.Medico.user.dto;

import java.util.UUID;

public class ExtraDetails {
    private UUID id;

    public ExtraDetails(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
