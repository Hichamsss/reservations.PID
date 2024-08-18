package be.iccbxl.pid.Dto;

import be.iccbxl.pid.Model.Show;

import java.time.LocalDateTime;

public class ShowDTO {
    private String title;
    private String description;
    private LocalDateTime date;

    // Constructeur
    public ShowDTO(Show show) {
        this.title = show.getTitle();
        this.description = show.getDescription();
        this.date = show.getCreatedAt(); // Utilisation de createdAt comme date
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
