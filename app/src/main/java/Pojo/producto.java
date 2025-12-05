package Pojo;

public class producto {
    int id_venta;
    int id_cuenta;
    int id_tipo_producto;

    String nombre_producto;
    String identificador;
    String total_final;
    String nombre_producto_tipo;
    String fecha;
    String cantidad;
    String telefono;
    String correo;

    public String getNombreProducto() { return nombre_producto; }
    public void setNombreProducto(String nombre_producto) { this.nombre_producto = nombre_producto; }

    public int getId_venta() { return id_venta; }
    public void setId_venta(int id_venta) { this.id_venta = id_venta; }
    public void setId_cuenta(int id_cuenta) { this.id_cuenta = id_cuenta; }
    public int getId_tipo_producto() { return id_tipo_producto; }
    public void setId_tipo_producto(int id_tipo_producto) { this.id_tipo_producto = id_tipo_producto; }
    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }
    public String getTotal_final() { return total_final; }
    public void setTotal_final(String total_final) { this.total_final = total_final; }
    public String getNombre_producto_tipo() { return nombre_producto_tipo; }
    public void setNombre_producto_tipo(String nombre_producto_tipo) { this.nombre_producto_tipo = nombre_producto_tipo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }
}