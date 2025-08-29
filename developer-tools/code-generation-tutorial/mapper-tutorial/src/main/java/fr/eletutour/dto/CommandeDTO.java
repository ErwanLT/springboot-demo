package fr.eletutour.dto;

public class CommandeDTO {
    private String id;
    private String dateFormatee;

    public CommandeDTO(String id, String dateFormatee) {
        this.id = id;
        this.dateFormatee = dateFormatee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateFormatee() {
        return dateFormatee;
    }

    public void setDateFormatee(){
        this.dateFormatee = dateFormatee;
    }

}