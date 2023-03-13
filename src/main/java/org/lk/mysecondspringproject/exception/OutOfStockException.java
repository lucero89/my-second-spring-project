package org.lk.mysecondspringproject.exception;

public class OutOfStockException extends RuntimeException {

    public static final String MESSAGE = "La cantidad de salida de stock no puede ser mayor al stock actual." + "\n" +
            "Por favor verificar el stock físico en el almacén e ingresar un cantidad correcta.";

    public OutOfStockException() {
    }




}
