package accesoDatos;

import accesoDatos.Json.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Joakin on 10/01/2017.
 */
public class JSON implements Datos {

    ApiRequests encargadoPeticiones = new ApiRequests();

    public JSON() {
        obtenerDepositos();
        obtenerDispensadores();

    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {

        HashMap<Integer, Deposito> depositos = new HashMap<>();
        try {
            System.out.println("Lanzamos peticion JSON para consultas abiertas");

            String url = "http://localhost/newJson/JSON/jsondep.php"; // ¿Habría que sacarla de un fichero de configuracion?

            System.out.println("La url a la que lanzamos la petición es " + url);

            String response = encargadoPeticiones.getRequest(url);

            //System.out.println(response);
            JSONParser parser = new JSONParser();

            JSONArray root = (JSONArray) parser.parse(response);


            int counter = 0;
            String idPeticion = null;
            String usuarioPeticion;
            String duda;
            String cantidad;
            Iterator<String> it = root.iterator();
            String resuelta;
            for (int i = 0; i < root.size(); i++) {
                JSONObject peticion = (JSONObject) root.get(i);

                int id = Integer.parseInt((String) peticion.get("id"));
                usuarioPeticion = (String) peticion.get("nombreMoneda");
                duda = (String) peticion.get("valor");
                int cant = Integer.parseInt((String) peticion.get("cantidad"));

                depositos.put(id, new Deposito(usuarioPeticion, id, cant));
                System.out.println(depositos.get(id));


            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error en la busqueda de datos");
            System.exit(-1);
        }

        return depositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String, Dispensador> dispensadores = new HashMap<>();
        try {
            System.out.println("Lanzamos peticion JSON para consultas abiertas");

            String url = "http://localhost/newJson/JSON/json.php"; // ¿Habría que sacarla de un fichero de configuracion?

            System.out.println("La url a la que lanzamos la petición es " + url);

            String response = encargadoPeticiones.getRequest(url);

            //System.out.println(response);
            JSONParser parser = new JSONParser();

            JSONArray root = (JSONArray) parser.parse(response);


            int counter = 0;
            String idPeticion = null;
            String usuarioPeticion;
            String duda;
            String cantidad;
            Iterator<String> it = root.iterator();
            String resuelta;
            for (int i = 0; i < root.size(); i++) {
                JSONObject peticion = (JSONObject) root.get(i);

                String id = (String) peticion.get("clave");
//                System.out.println("lslslslslslslslsls" + Integer.parseInt((String) peticion.get("clave")));
                usuarioPeticion = (String) peticion.get("nombre");
                duda = (String) peticion.get("precio");
                int cant = Integer.parseInt((String) peticion.get("cantidad"));

                dispensadores.put(id, new Dispensador((String) usuarioPeticion, (String) usuarioPeticion, Integer.parseInt(duda), cant));
                System.out.println(dispensadores.get(idPeticion));


            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error en la busqueda de datos");
            System.exit(-1);
        }

        return dispensadores;

    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        boolean rsp = true;
        for (int key : depositos.keySet()) {
            try {
                String url = "http://localhost/newJson/JSON/jsonupdate2.php";
                JSONObject obj = new JSONObject();
                obj.put("cantidad", depositos.get(key).getCantidad());
                obj.put("id", depositos.get(key).getId());
                //System.out.println(depositos.get(key).ge());
                obj.put("nombreMoneda", depositos.get(key).getNombreMoneda());
                obj.put("valor", depositos.get(key).getValor());
                // System.out.println(obj.toJSONString());
                String str = obj.toJSONString();
                System.out.println("json" + str);

                String response = null;

                response = encargadoPeticiones.postRequestWithParams(url, str);
                System.out.println("RESPONSE depositos" + response);
                if (response.isEmpty()) {

                } else {
                    rsp = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return rsp;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        boolean rsp = true;
        for (String key : dispensadores.keySet()) {
            try {
                String url = "http://localhost/newJson/JSON/jsonupdate.php";
                JSONObject obj = new JSONObject();
                obj.put("cantidad", dispensadores.get(key).getCantidad());
                obj.put("clave", dispensadores.get(key).getClave());
                System.out.println(dispensadores.get(key).getClave());
                obj.put("nombre", dispensadores.get(key).getNombreProducto());
                obj.put("precio", dispensadores.get(key).getPrecio());
                // System.out.println(obj.toJSONString());
                String str = obj.toJSONString();
                System.out.println("json" + str);

                String response = null;

                response = encargadoPeticiones.postRequestWithParams(url, str);
                System.out.println("RESPONSE" + response);
                if (response.isEmpty()) {

                } else {
                    rsp = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return rsp;
    }
}
