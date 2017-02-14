package accesoDatos.Json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Intermediario {

    ApiRequests encargadoPeticiones;

    Scanner teclado;

    public Intermediario() {
        teclado = new Scanner(System.in); // Para leer las opciones de teclado
        encargadoPeticiones = new ApiRequests();
    }

    public void ejecucion() {
        int op = 0; // Opcion
        boolean salir = false;

        while (!salir) { // Estructura que repite el algoritmo del menu principal hasta que se la condicion sea falsa
            // Se muestra el menu principal
            System.out.println(".......................... \n"
                    + ".  0 Salir \n"
                    + ".  1 Leer consultas abiertas  \n"
                    + ".  2 Iniciar consulta \n"
                    + ".  3 Cerrar consulta \n"

                    + "..........................");
            try {
                op = teclado.nextInt(); // Se le da a la variable op el valor del teclado
                System.out.println("OPCION SELECCIONADA:" + op);
                switch (op) {
                    case 0:
                        System.out.println("Adios");
                        System.exit(0);
                    case 1://
                        leerConsultas();
                        break;
                    case 2://
                        writeConsulta();
                        break;
                    case 3://
                        closeConsulta();
                        break;
                    default:// No valido
                        System.out.println("Opcion invalida: marque un numero de 1 a 3");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Excepcion por opcion invalida: marque un numero de 1 a 3");
                // flushing scanner
                //e.printStackTrace();
                teclado.next();
            }
        }

        //teclado.close();

    }

    private void writeConsulta() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Usuario: ");
        String usuario = sc.nextLine();
        System.out.println("Duda: ");
        String texto = sc.nextLine();
        String url = "http://localhost/Practica/Backend/newDuda2.php";
        JSONObject obj = new JSONObject();
        obj.put("usuario", usuario);
        obj.put("texto", texto);
        System.out.println(obj.toJSONString());
        String str = "datos=" + obj.toJSONString();
        System.out.println(str);

        String response = encargadoPeticiones.postRequest(url, str);
        System.out.println(response);
    }

    private void closeConsulta() throws IOException {
        leerConsultas();
        System.out.println("---------------------");
        System.out.println("Que consulta quieres cerrar?: ");
        Scanner sc = new Scanner(System.in);
        System.out.println("Id de la consulta: ");
        String key = sc.nextLine();
        String url = "http://localhost/Practica/Backend/solve2.php";
        JSONObject obj = new JSONObject();
        obj.put("key", key);
        System.out.println(obj.toJSONString());
        String response = encargadoPeticiones.postRequestWithParams(url, obj.toJSONString());

    }

    private void leerConsultas() {


}}