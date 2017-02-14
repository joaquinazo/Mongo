package accesoDatos;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

import java.util.HashMap;

/**
 * Created by Joakin on 31/01/2017.
 */
public class Hibernate implements Datos{
    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {

        return null;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        return null;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        return false;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        return false;
    }
}
