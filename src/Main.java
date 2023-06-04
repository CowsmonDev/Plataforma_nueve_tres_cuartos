public class Main {
    public static void main(String[] args) {
<<<<<<< Updated upstream
=======
        ListaDeViajes l = ListaDeViajes.getInstance(); //Accede a los elementos de la estructura de Datos
        List<Empresa> e = l.getEmpresas(); //Accede a la lista de empresas y la guarda en e

        Sistemas s = new Sistemas();
        //s.listarCiudades(e);
        //s.elegirCiudades();
        for (Empresa emp : e) {
            for (Omnibus o : emp.getOmnibus()) {
                //System.out.println(o.toString());
            }

        }
        List<Empresa> empresas = s.posiblesOmn(e, new FiltroMenorVelMax(30));
        //System.out.println(empresas.size());
        for (Empresa emp : empresas) {
            for (Omnibus o : emp.getOmnibus()) {
                System.out.println(o.toString());
            }
        }

>>>>>>> Stashed changes
    }
}