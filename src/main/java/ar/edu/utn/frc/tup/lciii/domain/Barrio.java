package ar.edu.utn.frc.tup.lciii.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Barrio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "barrio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Local> localesDelBarrio = new ArrayList<>();

    @OneToMany(mappedBy = "barrio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Usuario> usuariosDelBarrio = new ArrayList<>();


    public void agregarLocal(Local local)
    {
        localesDelBarrio.add(local);

    }

    public void quitarLocal(Local local)
    {
        localesDelBarrio.remove(local);
        local.setBarrio(null);
    }



}
