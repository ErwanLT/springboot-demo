package fr.eletutour.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stats {
    private int hp;
    private int atk;
    private int def;
    private int spe_atk;
    private int spe_def;
    private int vit;
}
