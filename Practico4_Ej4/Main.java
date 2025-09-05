public class Main {
    public static void main(String[] args) {
        // Artículo nacional subsidiado en Montevideo
        Nacional art1 = new Nacional(1, "Arroz", 100, "Montevideo", true);

        // Artículo nacional no subsidiado del interior
        Nacional art2 = new Nacional(2, "Azúcar", 100, "Canelones", false);

        // Artículo importado antes de 2008
        Importado art3 = new Importado(3, "Electrodoméstico", 200, 2005, 50);

        // Artículo importado después de 2008
        Importado art4 = new Importado(4, "Celular", 300, 2015, 80);

        System.out.println(art1.getNombre() + " (" + art1.tipoArticulo() + "): $" + art1.precioVenta());
        System.out.println(art2.getNombre() + " (" + art2.tipoArticulo() + "): $" + art2.precioVenta());
        System.out.println(art3.getNombre() + " (" + art3.tipoArticulo() + "): $" + art3.precioVenta());
        System.out.println(art4.getNombre() + " (" + art4.tipoArticulo() + "): $" + art4.precioVenta());
    }
}

