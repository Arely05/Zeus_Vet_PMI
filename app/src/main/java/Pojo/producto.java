package Pojo;

public class producto {
    // Nuevos campos para registro de Compra/Venta
    String NombreComprador;
    String TelefonoComprador;
    String CorreoComprador;
    String NombreProducto;
    String TipoProducto; // Para el Spinner: shampoo, acondicionador, crema
    String CantidadComprada;
    String TotalCompra; // Campo nuevo

    // Getters y Setters actualizados
    public String getNombreComprador() { return NombreComprador; }
    public void setNombreComprador(String NombreComprador) { this.NombreComprador = NombreComprador; }

    public String getTelefono() { return TelefonoComprador; }
    public void setTelefonoComprador(String TelefonoComprador) { this.TelefonoComprador = TelefonoComprador; }

    public String getCorreo() { return CorreoComprador; }
    public void setCorreoComprador(String CorreoComprador) { this.CorreoComprador = CorreoComprador; }

    public String getNombreProducto() { return NombreProducto; }
    public void setNombreProducto(String NombreProducto) { this.NombreProducto = NombreProducto; }

    public String getTipoProducto() { return TipoProducto; }
    public void setTipoProducto(String TipoProducto) { this.TipoProducto = TipoProducto; }

    public String getCantidad() { return CantidadComprada; }
    public void setCantidadComprada(String CantidadComprada) { this.CantidadComprada = CantidadComprada; }

    public String getTotalCompra() { return TotalCompra; }
    public void setTotalCompra(String TotalCompra) { this.TotalCompra = TotalCompra; }

    public void setTelefono(String string) {
    }

    public void setCantidad(String string) {

    }

    public void setCorreo(String string) {
    }
}