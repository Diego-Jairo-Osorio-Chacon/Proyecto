package proyectotambo;

import java.util.Scanner;

public class ProyectoTambo {

    public static void main(String[] args) {

        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.err.println("Error al configurar la codificación UTF-8");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        RegistroProducto registro = new RegistroProducto(); // Instancia de RegistroProducto
        RegistroVenta registroVenta = new RegistroVenta();
        String opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Consultar Productos");
            System.out.println("3. Registrar Venta");
            System.out.println("4. Generar Boleta Electrónica");
            System.out.println("5. Reporte");
            System.out.println("6. Salir");
            System.out.println("————————————————————");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            System.out.print("————————————————————\n");

            switch (opcion) {
                case "1": // Registrar Producto
                    System.out.println("--- Registro del Producto ---");
                    System.out.print("Ingrese código: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Ingrese nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el precio: ");
                    double precio = Double.parseDouble(scanner.nextLine());
                    System.out.print("Ingrese cantidad: ");
                    int cantidadStock = Integer.parseInt(scanner.nextLine());
                    System.out.print("Categoría: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Fecha Vencimiento (dd/MM/yyyy): ");
                    String fechaVencimiento = scanner.nextLine();

                    Producto producto = new Producto(codigo, nombre, cantidadStock, precio, categoria, fechaVencimiento);
                    registro.registrarProducto(producto);
                    System.out.print("————————————————————\n");
                    break;

                case "2": // Consultar Productos
                    System.out.println("\n--- Lista de Productos ---");
                    registro.listarProductos();
                    System.out.println("----------------------");
                    System.out.println("1. Editar");
                    System.out.println("2. Eliminar");
                    System.out.println("3. Regresar");
                    System.out.println("----------------------");
                    System.out.print("Seleccione una función para el producto: ");
                    String opcionReg = scanner.nextLine();

                    if (opcionReg.equals("1")) { // Editar Producto
                        System.out.print("Ingrese código del producto: ");
                        String codigoEditar = scanner.nextLine();
                        Producto productoEncontrado = registro.buscarProducto(codigoEditar);

                        if (productoEncontrado != null) {
                            System.out.print("Editar Nombre: ");
                            String nuevoNombre = scanner.nextLine();
                            System.out.print("Editar Precio: ");
                            double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                            System.out.print("Editar Cantidad: ");
                            int nuevoStock = Integer.parseInt(scanner.nextLine());
                            System.out.print("Editar Categoría: ");
                            String nuevaCategoria = scanner.nextLine();
                            System.out.print("Editar Fecha Vencimiento: ");
                            String nuevaFecha = scanner.nextLine();

                            System.out.println(registro.editarProducto(codigoEditar, nuevoNombre, nuevoPrecio, nuevoStock, nuevaCategoria, nuevaFecha));
                        } else {
                            System.out.println("Producto no encontrado.");
                        }
                    } else if (opcionReg.equals("2")) { // Eliminar Producto
                        System.out.print("Ingrese código del producto a eliminar: ");
                        String codigoEliminar = scanner.nextLine();
                        System.out.println(registro.eliminarProducto(codigoEliminar));
                    }
                    System.out.print("————————————————————\n");
                    break;

                case "3": // Registrar Venta
                    System.out.println("--- Registrar Venta ---");
                    System.out.print("Ingrese código del producto: ");
                    String codigoBusqueda = scanner.nextLine();

                    Producto produc = registro.buscarProducto(codigoBusqueda);
                    if (produc == null) {
                        System.out.println("Producto no encontrado.");
                        break;
                    }
                    System.out.print("Ingrese cantidad a vender: ");
                    int cantidadVender;
                    try {
                        cantidadVender = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Cantidad inválida.");
                        break;
                    }
                    if (cantidadVender > produc.getCantidadStock()) {
                        System.out.println("Stock insuficiente.");
                        break;
                    }
                    System.out.print("Ingrese nombre del vendedor: ");
                    String vendedor = scanner.nextLine();
                    produc.setCantidadStock(produc.getCantidadStock() - cantidadVender);
                    Venta venta = new Venta(codigoBusqueda, produc.getNombre(), produc.getPrecio(), cantidadVender, vendedor, produc.getCategoria());
                    registroVenta.agregarVenta(venta);
                    System.out.print("————————————————————\n");
                    break;

                case "4": // Generar Boleta Electrónica
                    // Mostrar las ventas con el vendedor y el IGV
                    System.out.print("Ingrese nombre del vendedor: ");
                    String vendedorReporte = scanner.nextLine();
                    registroVenta.mostrarBoletaElectronica(vendedorReporte);
                    System.out.print("————————————————————\n");
                    break;

                case "5": // Reporte
                    System.out.println("\n--- Reporte de Productos y Ventas ---");
                    System.out.println("1. Producto más vendido y menos vendido");
                    System.out.println("2. Reporte por Categoría");
                    System.out.print("Seleccione una opción: ");
                    String opcionReporte = scanner.nextLine();

                    switch (opcionReporte) {
                        case "1":
                            registroVenta.reporteMasMenosVendidos(registro);
                            break;
                        case "2":
                            registroVenta.reportePorCategoria(registro);
                            break;
                        default:
                            System.out.println("Opción inválida.");
                    }
                    System.out.print("————————————————————\n");
                    break;

                case "6": // Salir
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    System.out.print("————————————————————\n");
                    break;
            }
        } while (!opcion.equals("6"));

        scanner.close();
    }
}
